package client;

import java.io.IOException;

public class ClientMain {
    public static void main(String[] args)  throws IOException {
        Client client = new Client();

        try {
            client.menu();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
