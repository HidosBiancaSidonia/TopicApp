package server;

import com.sun.org.apache.xpath.internal.operations.Bool;
import database.SQLOperations;
import model.*;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerThread extends Thread {
    private Socket socket;
    private ObjectInputStream in = null;
    private ObjectOutputStream out = null;
    private SQLOperations sqlOperations = new SQLOperations();
    private static User user;
    private static ArrayList<Topic> topics = new ArrayList<Topic>();
    private static ArrayList<MessageUser> messageUser = new ArrayList<MessageUser>();

    public ServerThread(Socket socket) {
        this.socket = socket;
        try {
            in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        String command;
        try {
            while (true) {
                try {
                    command = (String) in.readObject();
                    System.out.println("The command is: " + command);
                    if (command.equals("quit")) {
                        break;
                    } else {
                        check(command);
                    }
                } catch (EOFException ex1) {
                    break;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                System.out.println("Socket is closing.");
                in.close();
                out.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Function that depends on the command given from the Client
     *
     * @param line
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private void check(String line) throws IOException, ClassNotFoundException {
        if (line.toLowerCase().equalsIgnoreCase("login")) {
            login();
        } else if (line.toLowerCase().equalsIgnoreCase("showTopic")) {
            showTopic();
        } else if (line.toLowerCase().equalsIgnoreCase("subscribeTopic")) {
            subscribeTopic();
        }
        else if (line.toLowerCase().equalsIgnoreCase("showMessages")) {
            showMessages();
        }
    }

    /**
     * Function that checks if the user is already in the database
     *
     * @param username
     * @param password
     * @return
     */
    private User checkLogIn(String username, String password) {
        List<User> userList = sqlOperations.getUserList();

        for (User user : userList) {
            if (username.equals(user.getUsername())) {
                if (password.equals(user.getPassword()))
                    return user;
            }
        }
        return null;
    }

    /**
     * Login function that uses the loginSQL() function from SQLOperations
     *
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private void login() throws IOException, ClassNotFoundException {
        String username = (String) in.readObject();
        String password = (String) in.readObject();
        User user = checkLogIn(username, password);

        if (user != null && !user.getUsername().isEmpty() && !user.getPassword().isEmpty()) {
            out.writeObject("Log in successful.");
            ServerThread.user = new User(user.getUsername(), user.getPassword());
            System.out.println(user.toString());
            out.writeObject(user);
            ServerThread.user = user;
        } else {
            out.writeObject("Log in failed.");
        }

        out.flush();
    }

    private void showTopic() throws IOException, ClassNotFoundException {
        User user = ServerThread.user;
        String id = (String) in.readObject();
        ArrayList<Integer> idList = sqlOperations.showTopicsForUserSQL(user.getId_user(),Integer.parseInt(id));

        if (idList.isEmpty()) {
            out.writeObject("You are not subscribed to this topic!");
        }
        else {
            out.writeObject("Acces");
        }

        out.flush();
    }

    private void subscribeTopic () throws IOException, ClassNotFoundException {
        User user = ServerThread.user;
        String id = (String) in.readObject();

        Boolean ok = sqlOperations.subscribeTopic(user.getId_user(),Integer.parseInt(id));

        if(ok == true){
            out.writeObject("From now, you are subscribed to this topic!");
        }else{
            out.writeObject("You can't subscribe!");
        }
        out.flush();
    }

    private void showMessages() throws IOException, ClassNotFoundException {
        String id = (String) in.readObject();
        messageUser = sqlOperations.messageUser(Integer.parseInt(id));
        if (messageUser.isEmpty()) {
            out.writeObject("There are no posts in this topic.");
        } else {
            out.writeObject(messageUser.toString());
        }

        out.flush();
    }
}
