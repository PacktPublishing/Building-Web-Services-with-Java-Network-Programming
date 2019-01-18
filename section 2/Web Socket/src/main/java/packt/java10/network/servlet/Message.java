package packt.java10.network.servlet;

import java.util.Objects;

public final class Message {

    private final String message;

    public Message(final String message) {
        Objects.requireNonNull(message);

        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
