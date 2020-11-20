package client;

import java.io.IOException;

public class MainApp {
    public static void main(String[] args)  throws IOException {
        Client client = new Client();
        Client client2 = new Client();

        try {
            client.menu();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
