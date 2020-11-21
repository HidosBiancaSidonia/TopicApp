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

    public Client() throws IOException {
        try {
            Socket socketClient = new Socket("localhost", 4134);
            out = new ObjectOutputStream(socketClient.getOutputStream());
            in = new ObjectInputStream(socketClient.getInputStream());
            System.out.println("Client is connected.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void quit() throws IOException {
        out.writeObject("quit");
        System.exit(0);
    }

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

    public static void menu() {
        Menu mainMenu = new Menu("\nChoose an option");
        Menu subMenuTopics = new Menu("I want to :");
        Menu subscribeTopic = new Menu("Do you want to subscribe to this topic?");
        Menu subsubMenuTopics = new Menu("\nI want to :");

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
                    activateMenu(subsubMenuTopics);
                } else if(message.equals("You are not subscribed to this topic!")){
                    System.out.println(message);
                    activateMenu(subscribeTopic);
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        subMenuTopics.putAction("Add a message to a topic", () -> {

        });

        subMenuTopics.putAction("Exit", () -> {
            try {
                quit();
            } catch (IOException e) {
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

        subsubMenuTopics.putAction("Add a message to a topic", () -> {

        });

        subsubMenuTopics.putAction("Back to topics", () -> {
            try {
                showAllTopics(2);
                activateMenu(subMenuTopics);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

       activateMenu(mainMenu);
    }

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
