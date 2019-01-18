package packt.java9.network.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Echo extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.setContentType("text/html");
        resp.getWriter().append("<html><body><h1>Http 3 Request Headers</h1><p>\n");
        resp.getWriter().append("<pre>\n");
        req.getHeaderNames().asIterator().forEachRemaining(
            headerName -> {
                req.getHeaders(headerName).asIterator().forEachRemaining(
                    header -> {
                        try {
                            resp.getWriter().append(headerName + ": " + header + "\n");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                );
            }
        );
        resp.getWriter().append("</pre></body></html>\n");


    }
}
