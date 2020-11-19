package database;

import model.Topic;
import model.User;
import model.UserTopic;

import java.sql.*;
import java.util.ArrayList;

public class ConnectionDB {

    private static Connection connection = null;

    public ConnectionDB(Integer i){
        connect();
    }
    public ConnectionDB(){
    }

    /**
     * Function that connect the application to the DB using JDBC
     * @return
     */
    Connection connect() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost/example", "root", "1q2w3e");
            System.out.println("Connected to database.");

            return connection;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Couldn't connect to database.");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Function that gets the users from DB
     * @return
     */
    ArrayList<User> getUsers() {
        ArrayList<User> users = new ArrayList<User>();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM user");

            while(resultSet.next()) {
                int id = resultSet.getInt("id_user");
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");

                User user = new User(id, username, password);
                users.add(user);
            }

            if(users.isEmpty()) {
                System.out.println("There is no user in the database.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    ArrayList<Topic> getTopics() {
        ArrayList<Topic> topics = new ArrayList<Topic>();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM topic");

            while(resultSet.next()) {
                int id = resultSet.getInt("id_topic");
                String topicName = resultSet.getString("topic_name");

                Topic topic = new Topic(id, topicName);
                topics.add(topic);
            }

            if(topics.isEmpty()) {
                System.out.println("There is no topic in the database.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return topics;
    }

    ArrayList<UserTopic> getUserTopics() {
        ArrayList<UserTopic> userTopics = new ArrayList<UserTopic>();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM user_topic");

            while(resultSet.next()) {
                int id = resultSet.getInt("id_topic");
                int id_user = resultSet.getInt("id_user");
                int id_topic = resultSet.getInt("id_topic");

                UserTopic userTopic = new UserTopic(id, id_user, id_topic);
                userTopics.add(userTopic);
            }

            if(userTopics.isEmpty()) {
                System.out.println("There is no user-topic in the database.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userTopics;
    }
}
