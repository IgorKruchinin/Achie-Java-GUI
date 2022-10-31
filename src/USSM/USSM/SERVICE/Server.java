package USSM.USSM.SERVICE;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public void connect() throws IOException {
        ServerSocket server = new ServerSocket(101);
        Socket client = server.accept();
        try {
            client.getInputStream();
        } catch (IOException e) {

        }
    }
}
