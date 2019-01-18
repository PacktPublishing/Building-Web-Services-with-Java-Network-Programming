package packt.java9.network.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

public class CookieDemo extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.setContentType("text/html");
        if (req.getParameterMap().containsKey("setCookie")) {
            Cookie cookie = new Cookie("httpOnlyCookie", "this+is+the+cookie+value");
            cookie.setSecure(true);
            cookie.setDomain("mydomain1.com");
            cookie.setPath("/");
            cookie.setHttpOnly(true);
            cookie.setComment("This is some comment");
            resp.addCookie(cookie);
        }
        resp.getWriter().append("<h1>Cookies</h1><p>\n");
        resp.getWriter().append("<pre>\n");
        dumpCookies(req, resp);
        resp.getWriter().append("</pre>\n");
    }


    private void dumpCookies(HttpServletRequest req, HttpServletResponse resp) {
        Cookie cookies[] = req.getCookies();
        if( cookies == null ){
            return;
        }
        Arrays.stream(cookies).forEach(c -> {
                try {
                    final String name = c.getName();
                    final String value = c.getValue();
                    resp.getWriter().append(name + ": " + value + "\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        );
    }


}
