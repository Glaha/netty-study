package bio;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class BIOClient {

    private Socket client;

    public BIOClient() throws IOException {
        client = new Socket("127.0.0.1",7777);
    }

    public void write() throws IOException {
        try (
                PrintWriter pw = new PrintWriter(client.getOutputStream());
        ) {
                pw.write("BIO-test");
        } finally {
            client.close();
        }
    }

    public static void main(String[] args) throws IOException {
        for (int i = 0; i < 10000; i++) {
            BIOClient bioClient = new BIOClient();
            bioClient.write();
        }

    }
}
