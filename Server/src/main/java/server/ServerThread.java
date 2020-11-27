package server;

import database.SQLOperations;
import model.Topic;
import model.User;
import util.MessageUser;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerThread extends Thread {
    private final Socket socket;
    private ObjectInputStream in = null;
    private ObjectOutputStream out = null;
    private final SQLOperations sqlOperations = new SQLOperations();
    private Integer id_user;

    /**
     * Constructor that sets ObjectInputStream and ObjectOutputStream
     * @param socket belongs to each client that connects to the server
     */
    public ServerThread(Socket socket) {
        this.socket = socket;
        try {
            System.out.println("\n~ New client connected to the server with "+socket.getPort()+" port.");

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
                    System.out.println("\nThe command for user with "+socket.getPort() +" port is: " + command);
                    if (command.equals("quit")) {
                        break;
                    } else {
                        check(command);
                    }
                } catch (Exception ex1) {
                    ex1.getMessage();
                    break;
                }
            }
        } finally {
            try {
                System.out.println("Socket is closing.");
                this.in.close();
                this.out.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Function that depends on the command given from the Client
     * @param line - string that contains command received from the client
     * @throws IOException used for the methods called
     * @throws ClassNotFoundException used for the methods called
     */
    private void check(String line) throws IOException, ClassNotFoundException {
        if (line.toLowerCase().equalsIgnoreCase("login")) {
            id_user = login();
        } else if (line.toLowerCase().equalsIgnoreCase("register")) {
            id_user = register();
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
     * @param username - the string that contains the client's username
     * @param password - the string that contains the client's password
     * @return the user
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
     * @return current client id
     * @throws IOException used for readObject()
     * @throws ClassNotFoundException used for writeObject()
     */
    private Integer login() throws IOException, ClassNotFoundException {
        String username = (String) in.readObject();
        String password = (String) in.readObject();
        User user = checkLogIn(username, password);
        Integer id;
        if (user != null && !user.getUsername().isEmpty() && !user.getPassword().isEmpty()) {
            out.writeObject("Log in successful.");
            System.out.println(user.toString());
            out.writeObject(user);
            id = user.getId_user();
            System.out.println("Client with "+ socket.getPort() +" port managed to log in\n");
        } else {
            out.writeObject("Log in failed.");
            id = 0;
            System.out.println("Client with "+ socket.getPort() +" port don't managed to log in\n");
        }

        out.flush();
        return id;
    }

    /**
     * Register function that uses the registerSQL() function from SQLOperations
     * @return id_user for the recognition of the current client
     * @throws IOException used for readObject()
     * @throws ClassNotFoundException used for writeObject()
     */
    private Integer register() throws IOException, ClassNotFoundException {
        String username = (String) in.readObject();
        String password = (String) in.readObject();

        ArrayList<User> userArrayList = sqlOperations.getUserList();
        User user = new User(username, password);
        Integer id = 0;
        for (User user1 : userArrayList) {
            if (!user1.getUsername().equals(user.getUsername())) {
                out.writeObject("Register successful.");
                sqlOperations.registerSQL(username, password);
                id = user1.getId_user();
                System.out.println("Client with "+ socket.getPort() +" port managed to register\n");
            } else {
                out.writeObject("The user already exists.");
                System.out.println("Client with "+ socket.getPort() +" port don't managed to register because already exists\n");
            }
            break;
        }

        out.flush();
        return id;
    }

    /**
     * Show all topics for user function that uses the getTopicList()
     * function from SQLOperations
     * @throws IOException used for writeObject()
     */
    private void showAllTopics() throws IOException {
        ArrayList<Topic> topics = sqlOperations.getTopicList();
        if (topics.isEmpty()) {
            out.writeObject("There are no topics!");
        }
        else {
            out.writeObject(topics);
        }

        out.flush();
    }

    /**
     * Show if a user is subscribed to a topic using the showTopicsForUserSQL()
     * function from SQLOperations
     * @throws IOException used for readObject() and writeObject()
     * @throws ClassNotFoundException used for readObject()
     */
    private void showTopic() throws IOException, ClassNotFoundException {
        String id = (String) in.readObject();
        if(1<=Integer.parseInt(id) && Integer.parseInt(id)<=10) {

            ArrayList<Integer> idList = sqlOperations.showTopicsForUserSQL(id_user, Integer.parseInt(id));

            if (idList.isEmpty()) {
                out.writeObject("You are not subscribed to this topic!");
                System.out.println("Client with "+ socket.getPort() +" port isn't subscribed to this topic\n");
            } else {
                out.writeObject("Access");
                System.out.println("Client with "+ socket.getPort() +" port have access to this topic\n");
            }
        }
        else out.writeObject("The number you entered is invalid");

        out.flush();
    }

    /**
     * Function that subscribed the user to a topic using subscribeTopicSQL()
     * function from SQLOperations
     * @throws IOException used for readObject() and writeObject()
     * @throws ClassNotFoundException used for readObject()
     */
    private void subscribeTopic () throws IOException, ClassNotFoundException {
        String id = (String) in.readObject();

        boolean ok = sqlOperations.subscribeTopicSQL(id_user,Integer.parseInt(id));

        if(ok){
            out.writeObject("From now, you are subscribed to this topic!");
            System.out.println("Client with "+ socket.getPort() +" port has subscribed to a topic\n");
        }else{
            out.writeObject("You can't subscribe!");
        }
        out.flush();
    }

    /**
     * Show all messages in a topic using the getMessagesAndUsersSQL()
     * function from SQLOperations
     * @throws IOException used for readObject() and writeObject()
     * @throws ClassNotFoundException used for readObject()
     */
    private void showMessages() throws IOException, ClassNotFoundException {
        String id_topic = (String) in.readObject();
        ArrayList<MessageUser> messageUsers = sqlOperations.getMessagesAndUsersSQL(Integer.parseInt(id_topic));

        out.writeObject(messageUsers);
        System.out.println("Client with port "+ socket.getPort() +" wants to see the messages in a topic.");
        out.flush();
    }

    /**
     * Function that add message in a topic using addMessageSQL()
     * function from SQLOperations
     * @throws IOException used for readObject() and writeObject()
     *  @throws ClassNotFoundException used for readObject()
     */
    private void addMessages() throws IOException, ClassNotFoundException {
        Integer id_topic = (Integer) in.readObject();
        String message = (String) in.readObject();

        boolean ok = sqlOperations.addMessageSQL(id_user,id_topic,message);

        if(ok){
            out.writeObject("Your message has been successfully added!");
            System.out.println("Client with port "+ socket.getPort() +" added a message to a topic.");
        }else{
            out.writeObject("You can't add message!");
        }
        out.flush();
    }
}