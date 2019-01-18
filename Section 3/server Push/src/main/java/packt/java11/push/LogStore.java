package packt.java11.push;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class LogStore {

    static LogStore INSTANCE = new LogStore();
    private List<String> messages;
    private long start;
    private AtomicInteger pendingMessages = new AtomicInteger(0);

    void reset() {
        messages = new LinkedList<>();
        start = System.currentTimeMillis();
    }

    void pending() {
        pending(1);
    }

    void pending(int j) {
        pendingMessages.addAndGet(j);
    }


    void _debug(String message) {
        pending();
        debug(message);
    }

    void debug(String message) {
        long delta = System.currentTimeMillis() - start;
        messages.add("" + String.format("%05d", delta) + "ms: " + message);
        pendingMessages.decrementAndGet();
    }

    List<String> getMessages() {
        while (pendingMessages.get() > 0) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return messages;
    }
}
