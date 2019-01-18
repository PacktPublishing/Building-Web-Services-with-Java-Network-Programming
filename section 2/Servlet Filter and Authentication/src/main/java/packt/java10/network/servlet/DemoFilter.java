package packt.java10.network.servlet;

import javax.servlet.*;
import java.io.IOException;

public class DemoFilter implements Filter {
    @Override
    public void doFilter(ServletRequest req,
                         ServletResponse resp,
                         FilterChain chain) throws IOException, ServletException {
        if (req.getParameter("allow") != null) {
            chain.doFilter(req, resp);
        } else {
            final var out = resp.getWriter();
            out.write("<html>" +
                "<head>" +
                "</head>" +
                "<body>" +
                "<h1><font color=\"red\">" +
                "Access is not allowed. Use the get parameter <tt>?allow</tt>" +
                "</font></h1>" +
                "</body>" +
                "</html>"
            );
        }

    }
}
