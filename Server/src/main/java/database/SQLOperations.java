package database;

import model.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class SQLOperations {
    private final ConnectionDB connectionDB = new ConnectionDB();
    private Statement statement;
    private ResultSet resultSet = null;

    /**
     * Function to get the users from DB using the getUsers function from ConnectionDB
     * @return users list
     */
    public ArrayList<User> getUserList() {
        return connectionDB.getUsers();
    }

    /**
     * Function to get the topics from DB using the getTopics function from ConnectionDB
     * @return topics list
     */
    public ArrayList<Topic> getTopicList() {
        return connectionDB.getTopics();
    }

    /**
     * Function to register an user into DB
     * @param username
     * @param password
     */
    public void registerSQL(String username, String password) {
        try {
            statement = connectionDB.connect().createStatement();
            statement.executeUpdate("INSERT INTO user ( username, password) VALUES ('" + username + "', '" + password  + "')",Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = statement.getGeneratedKeys();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Function that gets all the topics from DB in which a client is subscribed
     * @param id_user
     * @param id
     * @return
     */
    public ArrayList<Integer> showTopicsForUserSQL(Integer id_user, Integer id) {
        ArrayList<Integer> id_user_topic = new ArrayList<Integer>();

        try {
            statement = connectionDB.connect().createStatement();

            String query = "SELECT id_user_topic FROM user_topic WHERE id_user='"+id_user+"' AND id_topic='"+id+"';";
            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                int id_find = resultSet.getInt("id_user_topic");

                id_user_topic.add(id_find);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return id_user_topic;
    }

    /**
     * Function that add a subscribed to a topic in DB
     * @param id_user
     * @param id_topic
     * @return
     */
    public Boolean subscribeTopicSQL(Integer id_user, Integer id_topic){
        Boolean ok=false;
        try {
            statement = connectionDB.connect().createStatement();
            statement.executeUpdate("INSERT INTO user_topic(id_user,id_topic) VALUES  ('"+id_user+"','"+id_topic+"');",Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = statement.getGeneratedKeys();

            ok=true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ok;
    }

    /**
     * Function to get the messages and user from a topic from DB
     * @param id_topic
     * @return
     */
    public ArrayList<MessageUser> getMessagesAndUsersSQL(Integer id_topic){
        ArrayList<MessageUser> messageUsersFirst = new ArrayList<MessageUser>();
        ArrayList<MessageUser> messageUsers = new ArrayList<MessageUser>();

        try {
            statement = connectionDB.connect().createStatement();

            String query = "SELECT message,id_user FROM message WHERE id_topic='"+id_topic+"';";
            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                Integer id_user = resultSet.getInt("id_user");
                String message = resultSet.getString("message");
                MessageUser messageUser = new MessageUser(message,id_user);
                messageUsersFirst.add(messageUser);
            }

            for (MessageUser messageUser:messageUsersFirst) {

                String second_query = "SELECT username FROM user WHERE id_user='"+messageUser.getId_user()+"';";
                resultSet = statement.executeQuery(second_query);

                while (resultSet.next()) {
                    String username = resultSet.getString("username");

                    MessageUser messageUser2 = new MessageUser(messageUser.getMessage(),username);
                    messageUsers.add(messageUser2);
                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return messageUsers;
    }

    /**
     * Function that add message in a DB
     * @param id_user
     * @param id_topic
     * @param message
     * @return
     */
    public Boolean addMessageSQL(Integer id_user,Integer id_topic, String message){
        Boolean ok=false;

        try {
            statement = connectionDB.connect().createStatement();
            statement.executeUpdate("INSERT INTO message(message,id_user,id_topic) VALUES  ('"+message+"','"+id_user+"','"+id_topic+"');",Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = statement.getGeneratedKeys();

            ok=true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ok;
    }
}
