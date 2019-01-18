package packt.java9.network.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.OptionalInt;

public class RedirectDemo extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final int responseCode = HttpServletResponse.SC_FOUND;
        resp.setStatus(responseCode);
        resp.setHeader("Location","https://www.packtpub.com/");
    }
}
