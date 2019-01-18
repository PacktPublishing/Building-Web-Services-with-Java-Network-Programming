package packt.java10.network.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

@WebServlet(name = "upload", urlPatterns = "/up")
@MultipartConfig
public class FileUpload extends HttpServlet {
    private final static System.Logger LOGGER =
        System.getLogger(FileUpload.class.getCanonicalName());

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
        throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        final String path = request.getParameter("destination");
        final Part filePart = request.getPart("file");
        final String fileName = getFileName(filePart);


        try (final var out = new FileOutputStream(new File(path + File.separator + fileName));
             final var filecontent = filePart.getInputStream();
             final var writer = response.getWriter();) {

            filecontent.transferTo(out);

            writer.println("New file " + fileName + " created at " + path);
        } catch (FileNotFoundException fne) {
            LOGGER.log(System.Logger.Level.ERROR, "Problems during file upload. Error: {0}",
                new Object[]{fne.getMessage()});
        }
    }

    private String getFileName(final Part part) {
        for (final var content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content
                    .substring(content.indexOf('=') + 1)
                    .trim()
                    .replace("\"", "");
            }
        }
        return null;
    }
}
