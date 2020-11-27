package client;

import model.Topic;
import util.MessageUser;

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
    private static final Scanner scanner = new Scanner(System.in);
    private static final Scanner scannerWithDelimiter = new Scanner(System.in).useDelimiter("\n");
    private static Integer id_topic=0;

    /**
     * Constructor 
     */
    public Client()  {
        try {
            Socket socketClient = new Socket("localhost", 4133);
            out = new ObjectOutputStream(socketClient.getOutputStream());
            in = new ObjectInputStream(socketClient.getInputStream());
            System.out.println("Client is connected.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Force quit the active Client class
     */
    private static void quit() throws IOException {
        out.writeObject("quit");
        System.exit(0);
    }

    /**
     * Function that gets the username and password to perform the login in the Server class
     * @param username - the username with which the client identifies himself
     * @param password - the password with which the client identifies himself
     * @throws IOException this exception occurs when the username and the password are empty
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
     * @param i used to differentiate when accessing this method
     *          i = 1 - when accessing topics after logging in
     *          i = 2 - accessing the method  second time, third time, and so on
     * @throws IOException used for writeObject()
     * @throws ClassNotFoundException used for readObject()
     */
    private static void showAllTopics(int i) throws IOException, ClassNotFoundException {
        out.writeObject("showAllTopics");
        if(i==1){
            in.readObject();
        }

        //Can't deserialize object collections without "unchecked cast" warnings
        ArrayList<Topic> topics = (ArrayList<Topic>) in.readObject();
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
     * @param id represents the id of the chosen topic
     * @return the string received from the server
     * @throws IOException used for writeObject()
     * @throws ClassNotFoundException used for readObject()
     */
    private static String showTopic(Integer id) throws IOException, ClassNotFoundException {
        out.writeObject("showTopic");
        out.writeObject(id.toString());

        try {
            return (String) in.readObject();
        }catch (IOException  e){
            e.printStackTrace();
        }
        return "";
    }

    /**
     * Function that subscribes the user to a certain topic identified
     * by the id parameter using the function from the Server class
     * @param id represents the id of the chosen topic
     * @return the string received from the server
     * @throws IOException used for writeObject()
     */
    private static String subscribeTopic(Integer id) throws IOException {
        out.writeObject("subscribeTopic");
        out.writeObject(id.toString());
        try {
            return (String) in.readObject();
        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return "";
    }

    /**
     * Function that show all messages in a topic identified
     * by the id parameter using the function from the Server class
     * @param id represents the id of the chosen topic
     * @throws IOException used for writeObject()
     * @throws ClassNotFoundException used for readObject()
     */
    private static void showMessages(Integer id) throws IOException, ClassNotFoundException {
        out.writeObject("showMessages");
        out.writeObject(id.toString());

        //Can't deserialize object collections without "unchecked cast" warnings
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
     * @param id represents the id of the chosen topic
     * @param message - message to add to the chosen topic
     * @return the string received from the server
     * @throws IOException used for writeObject()
     */
    private static String addMessages(Integer id, String message) throws IOException {
        out.writeObject("addMessages");
        out.writeObject(id);
        out.writeObject(message);

        try {
            return (String) in.readObject();
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
                System.out.println("Enter the number of the topic you want to add a message: ");
                id_topic = scanner.nextInt();
                String message = showTopic(id_topic);
                switch(message) {
                    case "The number you entered is invalid":
                        System.out.println(message);
                        showAllTopics(2);
                        activateMenu(subMenuTopics);
                        break;
                    case "Access":
                        showMessages(id_topic);
                        activateMenu(subMenuTopics);
                        break;
                    case "You are not subscribed to this topic!":
                        System.out.println(message);
                        activateMenu(subscribeTopic);
                        break;
                    default:
                        break;
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

                switch(server2) {
                    case "The number you entered is invalid":
                        System.out.println(server2);
                        showAllTopics(2);
                        activateMenu(subMenuTopics);
                        break;
                    case "Access":
                        System.out.println("\nAdd the message: ");
                        String message = scannerWithDelimiter.next();
                        String server = addMessages(id_topic,message);
                        if(server.equals("Your message has been successfully added!")) {
                            System.out.println(server);
                            activateMenu(subMenuTopics);
                        }
                        break;
                    case "You are not subscribed to this topic!":
                        System.out.println(server2);
                        activateMenu(subscribeTopic);
                        break;
                    default:
                        break;

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
        subMenuTopics.putAction("Quit", () -> {
            try {
                quit();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

       activateMenu(mainMenu);
    }

    /**
     * Function that shows the options of a menu and which let the user select
     * a certain option if it's correct.
     * @param newMenu - the menu that is activated
     */
    private static void activateMenu(Menu newMenu) {
        System.out.println(newMenu.generateText());
        Scanner scannerForMenu = new Scanner(System.in);
        while (true) {
            if (scannerForMenu.hasNextInt()) {
                int actionNumber = scannerForMenu.nextInt();
                newMenu.executeAction(actionNumber);
            } else {
                System.out.println("Please enter only numbers!");
                break;
            }
        }
    }

}
