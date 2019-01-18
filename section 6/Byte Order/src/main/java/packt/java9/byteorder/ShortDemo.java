package packt.java9.byteorder;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class ShortDemo {

    public static void main(String[] args) {
        short demo = 0x53AF;
        System.out.println("" + demo);
        byte[] b = ByteBuffer.allocate(Short.BYTES).putShort(demo).array();
        System.out.println(String.format("b[0]=%02X, b[1]=%02X",b[0],b[1]));
        short reversed = ByteBuffer.allocate(Short.BYTES).put(b).flip().getShort();
        System.out.println("short value reversed in decimal " + reversed);
    }
}
