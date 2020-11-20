package server;

import database.ConnectionDB;

public class MainApp {
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
