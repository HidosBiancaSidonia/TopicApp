package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
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

    private static String showTopic(Integer id) throws IOException, ClassNotFoundException {
        out.writeObject("showTopic");
        out.writeObject(id.toString());

        try {
            Object user = in.readObject();
            String message = (String) in.readObject();
            return  message;
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
            System.out.println(message);
            return message;
        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return "";
    }

    private static void showMessages(Integer id) throws IOException, ClassNotFoundException {
        out.writeObject("showMessages");
        out.writeObject(id.toString());

        String message = (String) in.readObject();
        if (message.equals("There are no posts in this topic.")) {
            System.out.println(message);
        } else {
            System.out.println(message);
        }
    }

    /**
     * Function that creates the menu, the choices in the menu and that calls the functions for each option in the menu
     */
    public static void menu() {
        Menu mainMenu = new Menu("Main");
        Menu subMenuTopics = new Menu("Topics");
        Menu subMenuSubscribe = new Menu("Do you want to subscribe to this topic?");
        Menu subMenuTopicMessage = new Menu("");

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
                    activateMenu(subMenuTopics);
                }
            } catch (IOException | ClassNotFoundException e) {
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

        subMenuTopics.putAction("Sport", () -> {
            try {
                String message = showTopic(1);
                if(message.equals("Acces")){
                    activateMenu(subMenuTopicMessage);
                }else if(message.equals("You are not subscribed to this topic!")){
                    System.out.println("You are not subscribed to this topic!");
                    id_topic=1;
                    activateMenu(subMenuSubscribe);
                }


            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        subMenuTopics.putAction("Movies", () -> {
            try {
                String message = showTopic(2);
                if(message.equals("Acces")){
                    activateMenu(subMenuTopicMessage);
                }else if(message.equals("You are not subscribed to this topic!")){
                    System.out.println("You are not subscribed to this topic!");
                    id_topic=2;
                    activateMenu(subMenuSubscribe);
                }


            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        subMenuTopics.putAction("TV Series", () -> {
            try {
                String message = showTopic(3);
                if(message.equals("Acces")){
                    activateMenu(subMenuTopicMessage);
                }else if(message.equals("You are not subscribed to this topic!")){
                    System.out.println("You are not subscribed to this topic!");
                    id_topic=3;
                    activateMenu(subMenuSubscribe);
                }


            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        subMenuTopics.putAction("Books", () -> {
            try {
                String message = showTopic(4);
                if(message.equals("Acces")){
                    activateMenu(subMenuTopicMessage);
                }else if(message.equals("You are not subscribed to this topic!")){
                    System.out.println("You are not subscribed to this topic!");
                    id_topic=4;
                    activateMenu(subMenuSubscribe);
                }


            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        subMenuTopics.putAction("Animals", () -> {
            try {
                String message = showTopic(5);
                if(message.equals("Acces")){
                    activateMenu(subMenuTopicMessage);
                }else if(message.equals("You are not subscribed to this topic!")){
                    System.out.println("You are not subscribed to this topic!");
                    id_topic=5;
                    activateMenu(subMenuSubscribe);
                }


            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        subMenuTopics.putAction("Music", () -> {
            try {
                String message = showTopic(6);
                if(message.equals("Acces")){
                    activateMenu(subMenuTopicMessage);
                }else if(message.equals("You are not subscribed to this topic!")){
                    System.out.println("You are not subscribed to this topic!");
                    id_topic=6;
                    activateMenu(subMenuSubscribe);
                }


            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        subMenuTopics.putAction("Art", () -> {
            try {
                String message = showTopic(7);
                if(message.equals("Acces")){
                    activateMenu(subMenuTopicMessage);
                }else if(message.equals("You are not subscribed to this topic!")){
                    System.out.println("You are not subscribed to this topic!");
                    id_topic=7;
                    activateMenu(subMenuSubscribe);
                }


            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        subMenuTopics.putAction("Gaming", () -> {
            try {
                String message = showTopic(8);
                if(message.equals("Acces")){
                    activateMenu(subMenuTopicMessage);
                }else if(message.equals("You are not subscribed to this topic!")){
                    System.out.println("You are not subscribed to this topic!");
                    id_topic=8;
                    activateMenu(subMenuSubscribe);
                }


            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        subMenuTopics.putAction("Food", () -> {
            try {
                String message = showTopic(9);
                if(message.equals("Acces")){
                    activateMenu(subMenuTopicMessage);
                }else if(message.equals("You are not subscribed to this topic!")){
                    System.out.println("You are not subscribed to this topic!");
                    id_topic=9;
                    activateMenu(subMenuSubscribe);
                }


            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        subMenuTopics.putAction("Travel", () -> {
            try {
                String message = showTopic(10);
                if(message.equals("Acces")){
                    activateMenu(subMenuTopicMessage);
                }else if(message.equals("You are not subscribed to this topic!")){
                    System.out.println("You are not subscribed to this topic!");
                    id_topic=10;
                    activateMenu(subMenuSubscribe);
                }


            } catch (IOException | ClassNotFoundException e) {
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

        subMenuSubscribe.putAction("Yes", () -> {
            try {
                subscribeTopic(id_topic);
                activateMenu(subMenuTopicMessage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        subMenuSubscribe.putAction("No", () -> activateMenu(subMenuTopics));

        subMenuSubscribe.putAction("Quit", () -> {
            try {
                quit();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        subMenuTopicMessage.putAction("See all posts in this topic",() -> {
            try {
                showMessages(id_topic);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        subMenuTopicMessage.putAction("Add a message to this topic",() -> {

        });

        subMenuTopicMessage.putAction("Back", () -> activateMenu(subMenuTopics));

        activateMenu(mainMenu);
    }



    /**
     * Function that shows the options of a menu and which let the user select a certain option if it's correct
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
