package packt.java9.network.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.OptionalInt;

public class ResponseDemo extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        final int responseCode;
        if (req.getParameterMap().containsKey("code")) {
            final String responseCodeString = req.getParameterMap().get("code")[0];
            responseCode = parseInt(responseCodeString).orElse(HttpServletResponse.SC_NOT_FOUND);
        } else {
            responseCode = HttpServletResponse.SC_OK;
        }
        resp.setStatus(responseCode);
    }

    private OptionalInt parseInt(String s) {
        try {
            return OptionalInt.of(Integer.parseInt(s));
        } catch (NumberFormatException nfe) {
            return OptionalInt.empty();
        }
    }
}
