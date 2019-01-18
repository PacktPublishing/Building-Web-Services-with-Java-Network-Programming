package packt.java9.network.httpclient;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static java.lang.System.Logger.Level.INFO;

public class App9 {
    private static System.Logger LOG = System.getLogger(App9.class.getName());

    public static void main(String[] args) throws IOException, URISyntaxException, InterruptedException {
        var location = "http://www.packtpub.com/";
        LOG.log(INFO, String.format("fetching from %s", location));
        var req = HttpRequest.newBuilder()
            .uri(new URI(location))
            .GET()
            .build();
        var client = HttpClient.newBuilder().followRedirects(HttpClient.Redirect.NORMAL).build();
        var response = client.send(req, HttpResponse.BodyHandlers.ofString());
        LOG.log(INFO, "number of lines is " +
            response.body().split("\n").length);
    }
}
