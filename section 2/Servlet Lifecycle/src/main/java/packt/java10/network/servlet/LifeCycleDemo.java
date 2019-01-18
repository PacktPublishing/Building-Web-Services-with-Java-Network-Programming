package packt.java10.network.servlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class LifeCycleDemo extends HttpServlet {
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config); // need to call in case getServletConfig() is used later
        createStampFile("initWasCalled.stamp");
    }

    @Override
    public void destroy() {
        super.destroy();
        createStampFile("destroyWasCalled.stamp");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        final int responseCode = HttpServletResponse.SC_OK;
        resp.setStatus(responseCode);
        final var out = resp.getWriter();
        var config = getServletConfig();
        out.write(config.getInitParameter("name"));
    }

    private void createStampFile(final String name) {
        try {
            var writer = new PrintWriter(name, "UTF-8");
            writer.println("created\n");
            writer.close();
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            //we would log here, but this is a demo
        }
    }
}
