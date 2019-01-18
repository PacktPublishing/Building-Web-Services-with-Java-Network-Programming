package packt.java9.network.httpclient;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.ExecutionException;

import static java.lang.System.Logger.Level.INFO;

public class App9Async {
    private static System.Logger LOG = System.getLogger(App9Async.class.getName());

    public static void main(String[] args) throws IOException, URISyntaxException, InterruptedException, ExecutionException {
        var location = "http://www.packtpub.com/";
        LOG.log(INFO, String.format("fetching from %s", location));
        var req = HttpRequest.newBuilder()
            .uri(new URI(location))
            .GET()
            .build();
        var client = HttpClient.newBuilder().followRedirects(HttpClient.Redirect.NORMAL).build();
        var response = client.sendAsync(req, HttpResponse.BodyHandlers.ofString());
        LOG.log(INFO, "we have returned from async");
        while (!response.isDone()) {
            LOG.log(INFO, "waiting for response...");
            Thread.sleep(100);
        }
        LOG.log(INFO, "number of lines is " +
            response.get().body().split("\n").length);
    }
}
