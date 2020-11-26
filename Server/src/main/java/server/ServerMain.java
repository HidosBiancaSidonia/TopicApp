package server;

import database.ConnectionDB;

public class ServerMain {

    /**
     * Main method
     */
    public static void main(String[] args) {
        try {
            new ConnectionDB(1);
            Server server = new Server();
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
