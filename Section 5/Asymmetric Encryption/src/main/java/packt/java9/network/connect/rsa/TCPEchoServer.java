package packt.java9.network.connect.rsa;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.KeyPair;
import java.util.Arrays;

public class TCPEchoServer {

    static final int TELNET_PORT = 8080;
    static final int BUFFLEN = 1024;

    public static void main(String[] args) throws Exception {
        KeyPair keyPair = Encryptor.buildKeyPair("RSA", 2048);
        Encryptor.savePublicKey("target/public.key", keyPair.getPublic());
        System.out.println("Public.key was created.");
        ServerSocket serverSocket = new ServerSocket(TELNET_PORT);
        Socket clientSocket = serverSocket.accept();
        InetSocketAddress remote = (InetSocketAddress) clientSocket.getRemoteSocketAddress();
        System.out.println("connection from port=" + remote.getPort() + " host=" + remote.getHostName());
        InputStream in = clientSocket.getInputStream();
        OutputStream out = clientSocket.getOutputStream();
        byte[] buffer = new byte[BUFFLEN];
        while (clientSocket.isConnected()) {
            int len = in.read(buffer);
            byte[] encrypted = Arrays.copyOf(buffer, len);
            if (len > 0) {
                byte[] plainText = Encryptor.decrypt(keyPair.getPrivate(), encrypted);
                out.write(plainText, 0, plainText.length);
                out.flush();
            }
        }
    }
}
