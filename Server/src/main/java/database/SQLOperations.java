package database;

import model.*;
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

            String query = "SELECT id_user FROM user ";
            resultSet = statement.executeQuery(query);
            int id_user=0;
            while (resultSet.next()) {
                id_user = resultSet.getInt(1);
            }
            id_user++;
            String sql = "INSERT INTO user (id_user, username, password) VALUES ('"+ id_user+"', '" + username + "', '" + password  + "')";
            statement.executeUpdate(sql);
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

            String query = "SELECT id_user_topic FROM user_topic ";
            resultSet = statement.executeQuery(query);
            int id_user_topic=0;
            while (resultSet.next()) {
                id_user_topic = resultSet.getInt(1);
            }
            id_user_topic++;
            String query1 = "INSERT INTO user_topic(id_user_topic,id_user,id_topic) VALUES  ('"+id_user_topic+"','"+id_user+"','"+id_topic+"');";
            statement.executeUpdate(query1);

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

            String query = "SELECT id_message FROM message ";
            resultSet = statement.executeQuery(query);
            int id_message=0;
            while (resultSet.next()) {
                id_message = resultSet.getInt(1);
            }
            id_message++;
            String query1 = "INSERT INTO message(id_message,message,id_user,id_topic) VALUES  ('"+id_message+"','"+message+"','"+id_user+"','"+id_topic+"');";
            statement.executeUpdate(query1);
            ok=true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ok;
    }
}
