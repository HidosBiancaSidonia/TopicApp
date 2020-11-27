package server;

import database.ConnectionDB;

public class ServerMain {

    /**
     * Main method
     * @param args is a collection of Strings, separated by a space,
     *            which can be typed into the program on the terminal.
     */
    public static void main(String[] args) {
        try {
            ConnectionDB connectionDB = new ConnectionDB();
            connectionDB.connect();
            Server server = new Server();
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
