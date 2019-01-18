package packt.java10.network.servlet;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

@ServerEndpoint(value = "/echo", encoders = MessageEncoder.class, decoders = MessageDecoder.class)
public final class EchoServer {

    @OnOpen
    public void onOpen(final Session session) {
    }

    @OnError
    public void onError(final Session session, final Throwable throwable) {
    }

    @OnMessage
    public void onMessage(final Message message, final Session session) {
        System.out.println("Message received: " + message.getMessage());
        final Message outMessage = new Message("[ECHO] " + message.getMessage());
        try {
            session.getBasicRemote().sendObject(outMessage);
        } catch (IOException | EncodeException e) {
            e.printStackTrace();
        }
    }

    @OnClose
    public void onClose(final Session session) {
    }

}
