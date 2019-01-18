package packt.java9.network.connect.rsa;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.security.PublicKey;

public class TCPEchoClient {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket();
        socket.connect(new InetSocketAddress("localhost", TCPEchoServer.TELNET_PORT));
        if (!socket.isConnected()) {
            throw new RuntimeException("Can not connect socket");
        }
        OutputStream os = socket.getOutputStream();
        PublicKey publicKey = Encryptor.loadPublicKey("target/public.key", "RSA");
        byte[] out = Encryptor.encrypt(publicKey, "Hello, World".getBytes(StandardCharsets.UTF_8));
        os.write(out);
        os.flush();
        InputStream is = socket.getInputStream();
        byte[] buffer = new byte[TCPEchoServer.BUFFLEN];
        int len = is.read(buffer);
        System.out.print(new String(buffer, StandardCharsets.UTF_8).substring(0, len));
    }
}
