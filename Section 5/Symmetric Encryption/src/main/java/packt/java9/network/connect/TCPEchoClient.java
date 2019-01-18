package packt.java9.network.connect;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import static packt.java9.network.connect.TCPEchoServer.BUFFLEN;
import static packt.java9.network.connect.TCPEchoServer.TELNET_PORT;

public class TCPEchoClient {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket();
        socket.connect(new InetSocketAddress("localhost", TELNET_PORT));
        if (!socket.isConnected()) {
            throw new RuntimeException("Can not connect socket");
        }
        OutputStream os = socket.getOutputStream();
        String out = Encryptor.encrypt("Hello, World");
        os.write(out.getBytes(StandardCharsets.UTF_8));
        os.flush();
        InputStream is = socket.getInputStream();
        byte[] buffer = new byte[BUFFLEN];
        int len = is.read(buffer);
        System.out.print(new String(buffer, StandardCharsets.UTF_8).substring(0, len));
    }
}
