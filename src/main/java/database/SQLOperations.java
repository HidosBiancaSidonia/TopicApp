package database;

import model.Topic;
import model.User;
import model.UserTopic;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class SQLOperations {
    private ConnectionDB connectionDB = new ConnectionDB();
    private Statement statement;
    private ResultSet resultSet = null;

    public ArrayList<User> getUserList() {
        return connectionDB.getUsers();
    }

    public ArrayList<Topic> getTopicList() {
        return connectionDB.getTopics();
    }

    public ArrayList<UserTopic> getUserTopicList() {
        return connectionDB.getUserTopics();
    }

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

    public Boolean subscribeTopic (Integer id_user, Integer id_topic){
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
}
