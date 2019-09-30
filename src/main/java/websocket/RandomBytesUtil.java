package websocket;

import io.netty.buffer.*;
import io.netty.util.internal.PlatformDependent;

import java.util.Random;

public class RandomBytesUtil {

    public static byte[] random(int length) {
        // create random object
        Random randomno = new Random();
      /*  UnpooledByteBufAllocator allocator = new UnpooledByteBufAllocator(false);
        ByteBuf byteBuf = allocator.buffer(length, length * 2);
        // create byte array
        byte[] nbyte = byteBuf.array();
        allocator.*/
        byte[] nbyte = new byte[length];
        // put the next byte in the array
        randomno.nextBytes(nbyte);
        return nbyte;
    }

    public static void releaseBytes(byte[] bytes) {
    }

    public static void main(String[] args) {
        // create random object
        Random randomno = new Random();

        // create byte array
        byte[] nbyte = new byte[30];

        // put the next byte in the array
        randomno.nextBytes(nbyte);

        // check the value of array
        System.out.println("Value of byte array: " + new String(nbyte));
    }
}
