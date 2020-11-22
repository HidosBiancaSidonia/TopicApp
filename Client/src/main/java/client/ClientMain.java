package client;

import java.io.IOException;

public class ClientMain {
    /**
     * Main method
     * @param args 
     * @throws IOException
     */
    public static void main(String[] args)  throws IOException {
        Client client = new Client();

        try {
            client.menu();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
