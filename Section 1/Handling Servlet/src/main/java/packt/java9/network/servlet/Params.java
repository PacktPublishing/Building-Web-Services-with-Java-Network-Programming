package packt.java9.network.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

public class Params extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.setContentType("text/html");
        resp.getWriter().append("<h1>Get parameters</h1><p>\n");
        resp.getWriter().append("<pre>\n");
        dumpParameters(req, resp);
        resp.getWriter().append("</pre>\n");
        emitForm(resp);
    }

    private void emitForm(HttpServletResponse resp) throws IOException {
        resp.getWriter().append("<form method=\"POST\" action=\"/params/\">");
        resp.getWriter().append("a <input name=\"a\" type=\"text\" value=\"\"><br>");
        resp.getWriter().append("b <input name=\"b\" type=\"text\" value=\"\"><br>");
        resp.getWriter().append("a <input name=\"a\" type=\"text\" value=\"\"><br>");
        resp.getWriter().append("<input type=\"submit\" value=\"Go\"><br>");
        resp.getWriter().append("</form>");
    }

    private void dumpParameters(HttpServletRequest req, HttpServletResponse resp) {
        req.getParameterMap().keySet().stream().forEach(k -> {
            Arrays.stream(req.getParameterMap().get(k)).forEach(s -> {
                        try {
                            resp.getWriter().append(k + ": " + s + "\n");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
            );
        });
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.setContentType("text/html");
        resp.getWriter().append("<h1>Post 1 parameters</h1><p>\n");
        resp.getWriter().append("<pre>\n");
        dumpParameters(req,resp);
        resp.getWriter().append("</pre>\n");
        String valueOfA = req.getParameter("a");
        System.out.println(valueOfA);
    }



}
