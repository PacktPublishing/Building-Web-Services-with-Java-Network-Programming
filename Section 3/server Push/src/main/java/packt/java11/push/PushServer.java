package packt.java11.push;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.PushBuilder;
import java.io.IOException;

import static packt.java11.push.HtmlTagWriter.htmlWriter;
import static packt.java11.push.RequestParser.parse;
import static packt.java11.push.ThrottleTool.now;
import static packt.java11.push.ThrottleTool.sleep;

@WebServlet("/push")
public class PushServer extends HttpServlet {

    private static final LogStore log = LogStore.INSTANCE;

    private static int imageSerial(int i, int j) {
        return (i * 10 + j + 1);
    }

    private static String imagePath(int i, int j, HttpServletRequest req, long etag) {
        var lag = parse(req).get(ImageServer.PAR_IMGLAG, 300);
        var delay = parse(req).get(ImageServer.PAR_IMGDELAY, 1000);
        return "image?png=" + imageSerial(i, j) +
                "&" + ImageServer.PAR_IMGLAG + "=" + lag +
                "&" + ImageServer.PAR_IMGDELAY + "=" + delay +
                "&" + ImageServer.PAR_ETAG + "=" + etag;
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        log.reset();
        final String titleText;
        var builder = req.newPushBuilder();
        var etag = now();
        if (builder == null) {
            titleText = "Good old HTTP/1.1 download";
        } else {
            var pushRequested = parse(req).get("push", 1) == 1;
            if (pushRequested) {
                titleText = "HTTP/2 Push";
                sendPush(req, builder, etag);
            } else {
                titleText = "HTTP/2 without push";
            }
        }
        var lag = parse(req).get("lag", 5000);
        var delay = parse(req).get("delay", 0);
        var sleep = new ThrottleTool.Sleeper();
        sleep.till(lag);
        log._debug("starting html");
        resp.setContentType("text/html");
        sendHtml(req, resp, titleText, etag, delay, sleep);
    }

    private void sendHtml(HttpServletRequest req, HttpServletResponse resp, String titleText, long etag, long delay, ThrottleTool.Sleeper sleep) throws IOException {
        try (var html = htmlWriter(resp.getWriter())) {
            sendHeader(titleText, html);
            sendBody(req, titleText, etag, delay, sleep, html);
        }
    }

    private void sendBody(HttpServletRequest req, String titleText, long etag, long delay, ThrottleTool.Sleeper sleep, HtmlTagWriter html) {
        try (var body = html.writer("body")) {
            try (var h1 = body.writer("h1")) {
                h1.append(titleText);
            }
            sendIconsTable(req, etag, delay, body);
            log._debug("img tags all sent");
            var debug = req.getParameter("debug");
            if (debug != null) {
                sleep.till(Long.parseLong(debug));
                sendLogMessages(html);
            }
        }
    }

    private void sendLogMessages(HtmlTagWriter html) {
        try (var pre = html.writer("pre")) {
            for (var s : log.getMessages()) {
                pre.append(s);
                pre.append("\n");
                pre.flush();
            }
        }
    }

    private void sendIconsTable(HttpServletRequest req, long etag, long delay, HtmlTagWriter body) {
        try (var table = body.writer("table")) {
            for (var i = 0; i < 10; i++) {
                try (var tr = table.writer("tr")) {
                    for (var j = 0; j < 10; j++) {
                        writeImgTableCell(tr, req, etag, i, j);
                        tr.flush();
                        sleep(delay);
                    }
                }
            }
        }
    }

    private void sendHeader(String titleText, HtmlTagWriter html) {
        try (var header = html.writer("header")) {
            try (var title = header.writer("title")) {
                title.append(titleText);
            }
        }
    }

    private void sendPush(HttpServletRequest req, PushBuilder builder, long etag) {
        for (var i = 0; i < 10; i++) {
            for (var j = 0; j < 10; j++) {
                var s = imagePath(i, j, req, etag);
                builder.path(s);
                logPush(builder);
                builder.push();
            }
        }
    }


    private void writeImgTableCell(HtmlTagWriter tr, HttpServletRequest req, long etag, int i, int j) {
        try (var td = tr.writer("td")) {
            td.tag("img", "src", imagePath(i, j, req, etag), "width", "30pt", "height", "30pt");
        }
    }

    private void logPush(PushBuilder builder) {
        var msg = new StringBuilder("pushed\n");
        msg.append(builder.getMethod() + " " + builder.getPath() + "\n");
        for (var hn : builder.getHeaderNames()) {
            msg.append(hn + ": " + builder.getHeader(hn) + "\n");
        }
        log.pending();
        log._debug(msg.toString());
    }
}
