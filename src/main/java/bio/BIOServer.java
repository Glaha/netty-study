package bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class BIOServer {

    public void run() throws IOException {
        Socket socket;
        //Bind,Listen
        ServerSocket ss = new ServerSocket(7777);
        while (true) {
            //Accept
            socket = ss.accept();
            //一般新建一个线程执行读写
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(socket .getInputStream()));
            System.out.println("you input is : " + br.readLine());
        }
    }

    public static void main(String[] args) throws IOException {
        BIOServer bioServer = new BIOServer();
        bioServer.run();
    }
}
