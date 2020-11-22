package server;

import database.ConnectionDB;

public class ServerMain {

    /**
     * Main method
     * @param args
     */
    public static void main(String[] args) {
        try {
            ConnectionDB conn = new ConnectionDB(1);
            Server server = new Server();
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
