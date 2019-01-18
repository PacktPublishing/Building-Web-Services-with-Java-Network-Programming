package packt.java9.network.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.OptionalInt;

public class HeaderDemo extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final int responseCode = HttpServletResponse.SC_OK;
        resp.setStatus(responseCode);
        resp.setHeader("Server","MySpecialServer v1.0");
        resp.setHeader("Age","9000");
    }
}
