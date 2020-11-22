package client;

import model.MessageUser;
import model.Topic;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Client implements Serializable {

    private static ObjectInputStream in = null;
    private static ObjectOutputStream out = null;
    private static String username, password;
    private static Scanner scanner = new Scanner(System.in);
    private static Integer id_topic=0;

    public Client()  {
        try {
            Socket socketClient = new Socket("localhost", 4134);
            out = new ObjectOutputStream(socketClient.getOutputStream());
            in = new ObjectInputStream(socketClient.getInputStream());
            System.out.println("Client is connected.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Force quit the active Client class
     * @throws IOException
     */
    private static void quit() throws IOException {
        out.writeObject("quit");
        System.exit(0);
    }

    /**
     * Function that gets the username and password to perform the login in the Server class
     * @param username
     * @param password
     * @throws IOException
     */
    private static void doLogin(String username, String password) throws IOException {
        out.writeObject("login");
        if (!username.isEmpty() && !password.isEmpty()) {
            try {
                out.writeObject(username);
                out.writeObject(password);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Function that gets the username and password to perform the register of a new user
     * in the Server class
     * @param username
     * @param password
     * @throws IOException
     */
    private static void doRegister(String username, String password) throws IOException {
        out.writeObject("register");
        if (!username.isEmpty() && !password.isEmpty()) {
            try {
                out.writeObject(username);
                out.writeObject(password);
                String message = (String) in.readObject();
                System.out.println(message);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Function that show all topics using the function from the Server class
     * @param i
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private static void showAllTopics(int i) throws IOException, ClassNotFoundException {
        out.writeObject("showAllTopics");
        if(i==1){
        Object obj =  in.readObject();
        }
        ArrayList<Topic> topics = (ArrayList<Topic>) in.readUnshared();
        System.out.println("\nTopics:");
        int nr = 0;
        for (Topic topic : topics) {
            nr++;
            System.out.println(nr + ". " + topic.getTopicName());
        }

        System.out.println();
    }

    /**
     * Function that checks if the user is subscribed to a certain topic identified
     * by the id parameter using the function from the Server class
     * @param id
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private static String showTopic(Integer id) throws IOException, ClassNotFoundException {
        out.writeObject("showTopic");
        out.writeObject(id.toString());

        try {
            //Object user =  in.readObject();
            String message = (String) in.readObject();

                return message;
        }catch (IOException  e){
            e.printStackTrace();
        }
        return "";
    }

    /**
     * Function that subscribes the user to a certain topic identified
     *  by the id parameter using the function from the Server class
     * @param id
     * @return
     * @throws IOException
     */
    private static String subscribeTopic(Integer id) throws IOException {
        out.writeObject("subscribeTopic");
        out.writeObject(id.toString());
        try {
            String message = (String) in.readObject();
            return message;
        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return "";
    }

    /**
     * Function that show all messages in a topic identified
     * by the id parameter using the function from the Server class
     * @param id
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private static void showMessages(Integer id) throws IOException, ClassNotFoundException {
        out.writeObject("showMessages");
        out.writeObject(id.toString());

        ArrayList<MessageUser> message = (ArrayList<MessageUser>) in.readObject();
        if (message.isEmpty()) {
            System.out.println("There are no posts in this topic.");
        } else {
            for (MessageUser messageUser: message) {
                System.out.println("User: "+messageUser.getUser()+" - Message: "+messageUser.getMessage());
            }
        }
    }

    /**
     * Function that gets the id_topic and message to perform the add of a new message
     * in a certain topic using Server class
     * @param id
     * @param message
     * @return
     * @throws IOException
     */
    private static String addMessages(Integer id, String message) throws IOException {
        out.writeObject("addMessages");
        out.writeObject(id);
        out.writeObject(message);

        try {
            String server = (String) in.readObject();
            return server;
        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return "";
    }

    /**
     * Function that creates the menu, the choices in the menu and that calls the
     * functions for each option in the menu
     */
    public static void menu() {
        Menu mainMenu = new Menu("\nChoose an option");
        Menu subMenuTopics = new Menu("I want to :");
        Menu subscribeTopic = new Menu("Do you want to subscribe to this topic?");

        mainMenu.putAction("Log in", () -> {
            System.out.println("Please enter username and password.");
            System.out.println("Username: ");
            username = scanner.next();
            System.out.println("Password: ");
            password = scanner.next();
            try {
                doLogin(username, password);
                String message = (String) in.readObject();
                System.out.println(message);
                if (message.equals("Log in failed.")) {
                    System.out.println("Wrong credentials. Please try again!");
                    activateMenu(mainMenu);
                } else if (message.equals("Log in successful.")) {
                    showAllTopics(1);
                    activateMenu(subMenuTopics);
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        mainMenu.putAction("Register", () -> {
            System.out.println("Please enter username and password.");
            System.out.println("Username: ");
            username = scanner.next();
            System.out.println("Password: ");
            password = scanner.next();
            try {
                doRegister(username, password);
                activateMenu(mainMenu);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        mainMenu.putAction("Quit", () -> {
            try {
                quit();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        subMenuTopics.putAction("See all the posts in a topic", () -> {
            try {
                System.out.println("Enter the number of the topic you want to see all the posts: ");
                id_topic = scanner.nextInt();
                String message = showTopic(id_topic);
                if(message.equals("The number you entered is invalid")){
                    System.out.println(message);
                    showAllTopics(2);
                    activateMenu(subMenuTopics);
                } else if(message.equals("Acces")){
                    showMessages(id_topic);
                    activateMenu(subMenuTopics);
                } else if(message.equals("You are not subscribed to this topic!")){
                    System.out.println(message);
                    activateMenu(subscribeTopic);
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        subMenuTopics.putAction("Add a message to a topic", () -> {
            try {
                System.out.println("Enter the number of the topic you want to add a message: ");
                id_topic = scanner.nextInt();
                String server2 = showTopic(id_topic);
                if(server2.equals("The number you entered is invalid")){
                    System.out.println(server2);
                    showAllTopics(2);
                    activateMenu(subMenuTopics);
                } else if(server2.equals("Acces")){
                    System.out.println("\nAdd the message: ");
                    String message = scanner.next();
                    String server = addMessages(id_topic,message);
                    if(server.equals("Your message has been successfully added!")) {
                        System.out.println(server);
                        activateMenu(subMenuTopics);
                    }
                } else if(server2.equals("You are not subscribed to this topic!")){
                    System.out.println(server2);
                    activateMenu(subscribeTopic);
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }

        });

        subMenuTopics.putAction("Back to topics", () -> {
            try {
                showAllTopics(2);
                activateMenu(subMenuTopics);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        subscribeTopic.putAction("Yes", () ->{
            try {
                System.out.println(subscribeTopic(id_topic));
                activateMenu(subMenuTopics);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        subscribeTopic.putAction("No", () ->{
            try {
                showAllTopics(2);
                activateMenu(subMenuTopics);
            } catch (IOException | ClassNotFoundException  e ) {
                e.printStackTrace();
            }

        });

       activateMenu(mainMenu);
    }

    /**
     * Function that shows the options of a menu and which let the user select
     * a certain option if it's correct.
     * @param newMenu
     */
    private static void activateMenu(Menu newMenu) {
        System.out.println(newMenu.generateText());
        int actionNumber ;
        while (true) {
            if (scanner.hasNextInt()) {
                actionNumber = scanner.nextInt();
                newMenu.executeAction(actionNumber);
            } else {
                System.out.println("Please enter only numbers!");
                break;
            }
        }
    }

}
