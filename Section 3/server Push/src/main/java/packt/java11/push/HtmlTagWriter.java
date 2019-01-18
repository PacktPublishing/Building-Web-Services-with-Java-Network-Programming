package packt.java11.push;

import java.io.Closeable;
import java.io.PrintWriter;
import java.util.Optional;

/**
 * A simple HTML output producer class that uses the Java 7+ try with resources autoclose feature.
 * You start to write a HTML tag in a try block with a resource, like
 *
 * <pre>
 * try (var html = htmlWriter(resp.getWriter())) {
 *     try (var h1 = html.writer("h1")) {
 *                 h1.append(titleText);
 *         }
 *     }
 * </pre>
 * <p>
 * and it will write the starrting tag and the closing tag into the writer that
 * you pass to the {@link #htmlWriter(PrintWriter)}.
 */
public class HtmlTagWriter implements Closeable {
    private final PrintWriter writer;
    private final String tag;
    private boolean closed = false;
    private Optional<HtmlTagWriter> lastSubTag = Optional.empty();

    private HtmlTagWriter(PrintWriter writer, String tag) {
        this.writer = writer;
        this.tag = tag;
    }

    /**
     * Start a new HTML writer for the whole HTML structure.
     *
     * @param w where the output will be sent.
     */
    static HtmlTagWriter htmlWriter(PrintWriter w) {
        w.write("<html>");
        return new HtmlTagWriter(w, "html");
    }

    /**
     * Start a new tag into the writer a new HtmlTagWriter. The code between the try and the closing
     * bracket should use this tag to open new sub tags or to output and textual code.
     *
     * @param tag the new tag that we start
     * @return the new writer that can write between this tag and the closing tag.
     */
    HtmlTagWriter writer(String tag) {
        assertActive();
        writer.write("<" + tag + ">");
        return (lastSubTag = Optional.of(new HtmlTagWriter(writer, tag))).get();
    }

    /**
     * Write out a simple HTML tag.
     *
     * @param s          the tag itself
     * @param attributes the names and the values of the attributes, always the name first followed by the attribute.
     * @return returns {@code this} to allow chaining
     */
    HtmlTagWriter tag(String s, String... attributes) {
        assertActive();
        writer.write("<" + s);
        var key = true;
        for (var attr : attributes) {
            if (key) {
                writer.write(" " + attr);
            } else {
                writer.write("=\"" + attr + "\"");
            }
            key = !key;
        }
        writer.write(">");
        return this;
    }

    /**
     * Append text to the output
     *
     * @param s the text to append
     * @return returns {@code this} to allow chaining
     */
    HtmlTagWriter append(String s) {
        assertActive();
        writer.write(s);
        return this;
    }

    HtmlTagWriter flush() {
        assertActive();
        writer.flush();
        return this;
    }

    private void assertActive() {
        if (closed) {
            throw new IllegalStateException();
        }
        if (lastSubTag.isPresent() && lastSubTag.get().isOpen()) {
            throw new IllegalStateException();
        }
    }

    private boolean isOpen() {
        return !closed;
    }

    /**
     * This method closes the tag and it is called automatically by the try block when it is closed.
     */
    @Override
    public void close() {
        writer.write("</" + tag + ">");
        closed = true;
    }
}
