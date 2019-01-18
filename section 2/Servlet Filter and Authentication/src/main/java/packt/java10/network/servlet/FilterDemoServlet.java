package packt.java10.network.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FilterDemoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        final int responseCode = HttpServletResponse.SC_OK;
        resp.setStatus(responseCode);
        final var out = resp.getWriter();
        out.write("<html>" +
            "<head>" +
            "</head>" +
            "<body>" +
            "This is the response from the servlet" +
            "</body>" +
            "</html>"
        );
    }
}
