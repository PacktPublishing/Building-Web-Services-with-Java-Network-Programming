package packt.java9.byteorder;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class DoubleDemo {

    public static void main(String[] args) {
        double demo = 3.14159267;
        System.out.println("" + demo);
        byte[] b = ByteBuffer.allocate(Double.BYTES).putDouble(demo).array();
        System.out.println(String.format("b[0]=%02X, b[1]=%02X, b[2]=%02X, b[3]=%02X, b[4]=%02X, b[5]=%02X, b[6]=%02X, b[7]=%02X, ",
                b[0], b[1], b[2], b[3], b[4], b[5], b[6], b[7]));
        double reversed = ByteBuffer.allocate(Double.BYTES).put(b).flip().getDouble();
        System.out.println("" + reversed);
    }
}
