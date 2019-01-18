package packt.java10.network.servlet;

import com.google.gson.Gson;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

public final class MessageDecoder implements Decoder.Text<Message> {

    @Override
    public void destroy() {
        Runnable z = () -> {
        };
    }

    @Override
    public void init(final EndpointConfig arg0) {
    }

    @Override
    public Message decode(final String jsonMessage) throws DecodeException {
        return new Gson().fromJson(jsonMessage, Message.class);
    }

    @Override
    public boolean willDecode(final String arg0) {
        return true;
    }
}
