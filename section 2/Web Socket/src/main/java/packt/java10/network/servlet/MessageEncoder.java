package packt.java10.network.servlet;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

public final class MessageEncoder implements Encoder.Text<Message> {

    @Override
    public void destroy() {
    }

    @Override
    public void init(final EndpointConfig arg0) {
    }

    @Override
    public String encode(final Message message) {
        System.out.println("Encoding message " + message.getMessage());
        var encodedMessageText = message.getMessage().replaceAll("\"", "\\\"");
        System.out.println("Encoded message text " + encodedMessageText);
        return "{ \"message\" : \"" + encodedMessageText + "\" }";
    }
}
