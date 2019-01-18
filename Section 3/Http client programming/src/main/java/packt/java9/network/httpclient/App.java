package packt.java9.network.httpclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static java.lang.System.Logger.Level.INFO;


public class App {
    private static System.Logger LOG = System.getLogger(App.class.getName());

    public static void main(String[] args) throws IOException {
        int status = 0;
        var location = "http://www.packtpub.com/";
        int maxRedirect = 10;
        for (; ; ) {
            final var url = new URL(location);
            LOG.log(INFO, String.format("fetching from %s", url.toString()));
            final var con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setDoOutput(false);
            status = con.getResponseCode();
            if (status != HttpURLConnection.HTTP_MOVED_PERM &&
                status != HttpURLConnection.HTTP_MOVED_TEMP) {
                var is = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String line;
                var count = 0;
                while ((line = is.readLine()) != null) {
                    count++;
                }
                LOG.log(INFO, "number of lines is " + count);
                break;
            }
            location = con.getHeaderField("location");
            if (maxRedirect == 0) {
                LOG.log(INFO, "Too many redirects.");
                break;
            }
            LOG.log(INFO, "redirecting to " + location);
            maxRedirect--;
        }
    }
}
