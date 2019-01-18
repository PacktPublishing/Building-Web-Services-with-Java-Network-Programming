package packt.java10.network.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class SessionDemo extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        final int responseCode = HttpServletResponse.SC_OK;
        resp.setStatus(responseCode);
        final var session = req.getSession();
        final var out = resp.getWriter();
        if (req.getParameter("logout") != null){
            session.invalidate();
            out.write("You are logged out");
            return;
        }
        if (req.getParameter("secret") != null) {
            if ("changeme".equals(req.getParameter("secret"))) {
                session.setAttribute("authenticated", true);
            }
        }
        final var authenticated = ((Boolean) session.getAttribute("authenticated")) == Boolean.TRUE;
        if (!authenticated) {
            resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
            out.write("You are NOT authenticated.");
            return;
        }
        out.write("You are authenticated.");

    }
}
