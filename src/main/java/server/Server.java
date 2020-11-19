package server;

import database.ConnectionDB;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static final int PORT = 4133;

    /**
     * Function that instantiate the server thread
     */
    private void start() {

        new Thread( () -> {
            System.out.println("Starting the socket server at port: " + PORT);
            ServerSocket serverSocket = null;
            try {
                serverSocket = new ServerSocket(PORT);
            } catch (IOException e) {
                e.printStackTrace();
            }

            while (true) {
                System.out.println("You are connected to the server.");
                Socket socket = null;
                try {
                    assert serverSocket != null;
                    socket = serverSocket.accept();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                assert socket != null;
                new Thread((Runnable) new ServerThread(socket)).start();
            }

        }).start();
    }

    public static void main(String[] args) {
        try {
            ConnectionDB conn = new ConnectionDB(1);
            new Server().start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
