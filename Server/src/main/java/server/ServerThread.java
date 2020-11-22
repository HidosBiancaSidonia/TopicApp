package server;

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
    private static ArrayList<MessageUser> messageUsers = new ArrayList<MessageUser>();

    /**
     * Constructor that sets ObjectInputStream and ObjectOutputStream
     * @param socket
     */
    public ServerThread(Socket socket) {
        this.socket = socket;
        try {
            in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Function through which the command is received from the client
     * and sends this command to the server execution
     */
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
     * @param line
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private void check(String line) throws IOException, ClassNotFoundException {
        if (line.toLowerCase().equalsIgnoreCase("login")) {
            login();
        } else if (line.toLowerCase().equalsIgnoreCase("register")) {
            register();
        }  else if (line.toLowerCase().equalsIgnoreCase("showAllTopics")) {
            showAllTopics();
        } else if (line.toLowerCase().equalsIgnoreCase("showTopic")) {
            showTopic();
        } else if (line.toLowerCase().equalsIgnoreCase("subscribeTopic")) {
            subscribeTopic();
        }
        else if (line.toLowerCase().equalsIgnoreCase("showMessages")) {
            showMessages();
        } else if (line.toLowerCase().equalsIgnoreCase("addMessages")) {
            addMessages();
        }
    }

    /**
     * Function that checks if the user is already in the database
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

    /**
     * Register function that uses the registerSQL() function from SQLOperations
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private void register() throws IOException, ClassNotFoundException {
        String username = (String) in.readObject();
        String password = (String) in.readObject();

        ArrayList<User> userArrayList = sqlOperations.getUserList();
        User user = new User(username, password);

        for (User user1 : userArrayList) {
            if (!user1.getUsername().equals(user.getUsername())) {
                ServerThread.user = user1;
                out.writeObject("Register successful.");
                sqlOperations.registerSQL(username, password);
                break;
            } else {
                out.writeObject("The user already exists.");
                break;
            }
        }

        out.flush();
    }

    /**
     * Show all topics for user function that uses the getTopicList()
     * function from SQLOperations
     * @throws IOException
     */
    private void showAllTopics() throws IOException {

        topics = sqlOperations.getTopicList();
        if (topics.isEmpty()) {
            out.writeUnshared("There are no topics!");
        }
        else {
            out.writeUnshared(topics);
        }

        out.flush();
    }

    /**
     * Show if a user is subscribed to a topic using the showTopicsForUserSQL()
     * function from SQLOperations
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private void showTopic() throws IOException, ClassNotFoundException {
        User user = ServerThread.user;
        String id = (String) in.readObject();
        if(1<=Integer.parseInt(id) && Integer.parseInt(id)<=10) {

            ArrayList<Integer> idList = sqlOperations.showTopicsForUserSQL(user.getId_user(), Integer.parseInt(id));

            if (idList.isEmpty()) {
                out.writeObject("You are not subscribed to this topic!");
            } else {
                out.writeObject("Acces");
            }
        }
        else out.writeObject("The number you entered is invalid");

        out.flush();
    }

    /**
     * Function that subscribed the user to a topic using subscribeTopicSQL()
     * function from SQLOperations
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private void subscribeTopic () throws IOException, ClassNotFoundException {
        User user = ServerThread.user;
        String id = (String) in.readObject();

        Boolean ok = sqlOperations.subscribeTopicSQL(user.getId_user(),Integer.parseInt(id));

        if(ok == true){
            out.writeObject("From now, you are subscribed to this topic!");
        }else{
            out.writeObject("You can't subscribe!");
        }
        out.flush();
    }

    /**
     * Show all messages in a topic using the getMessagesAndUsersSQL()
     * function from SQLOperations
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private void showMessages() throws IOException, ClassNotFoundException {
        String id_topic = (String) in.readObject();
        System.out.println(id_topic);
        messageUsers = sqlOperations.getMessagesAndUsersSQL(Integer.parseInt(id_topic));

        out.writeObject(messageUsers);
        out.flush();
    }

    /**
     * Function that add message in a topic using addMessageSQL()
     * function from SQLOperations
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private void addMessages() throws IOException, ClassNotFoundException {
        User user = ServerThread.user;
        Integer id_topic = (Integer) in.readObject();
        String message = (String) in.readObject();

        Boolean ok = sqlOperations.addMessageSQL(user.getId_user(),id_topic,message);

        if(ok == true){
            out.writeObject("Your message has been successfully added!");
        }else{
            out.writeObject("You can't add message!");
        }
        out.flush();
    }
}
