package packt.java10.network.servlet;

import org.junit.Assert;
import org.junit.Test;

import javax.websocket.DecodeException;
import javax.websocket.EncodeException;

public class TestMessageEncoderDecoder {

    @Test
    public void canEncodeMessage() throws EncodeException {
        var me = new MessageEncoder();
        var encoded = me.encode(new Message("test message"));
        Assert.assertEquals("{ \"message\" : \"test message\" }", encoded);
    }

    @Test
    public void canDecodeMessage() throws DecodeException {
        var md = new MessageDecoder();
        var message = md.decode("{ \"message\" : \"test message\" }");
        Assert.assertEquals("test message", message.getMessage());
    }

}
