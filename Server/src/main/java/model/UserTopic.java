package model;

import java.io.Serializable;

public class UserTopic implements Serializable {
    private static final long serialVersionUID = 6529685098267757690L;
    private Integer id_user_topic;
    private Integer id_user;
    private Integer id_topic;

    /**
     * Constructor that sets all class variables to 0
     */
    public UserTopic(){
        this.id_user_topic = 0;
        this.id_user = 0;
        this.id_topic = 0;
    }

    /**
     * Constructor that sets all class variables
     * @param id_user_topic
     * @param id_user
     * @param id_topic
     */
    public UserTopic(Integer id_user_topic, Integer id_user, Integer id_topic) {
        this.id_user_topic = id_user_topic;
        this.id_user = id_user;
        this.id_topic = id_topic;
    }

    /**
     * @return id_user_topic
     */
    public Integer getId_user_topic() {
        return id_user_topic;
    }

    /**
     * Sets the id_user_topic variable
     * @param id_user_topic
     */
    public void setId_user_topic(Integer id_user_topic) {
        this.id_user_topic = id_user_topic;
    }

    /**
     * @return id_user
     */
    public Integer getId_user() {
        return id_user;
    }

    /**
     * Sets the id_user variable
     * @param id_user
     */
    public void setId_user(Integer id_user) {
        this.id_user = id_user;
    }

    /**
     * @return id_topic
     */
    public Integer getId_topic() {
        return id_topic;
    }

    /**
     * Sets the id_topic variable
     * @param id_topic
     */
    public void setId_topic(Integer id_topic) {
        this.id_topic = id_topic;
    }

    /**
     * Converts the UserTopic object to a string
     * @return
     */
    @Override
    public String toString() {
        return "UserTopic{" +
                "id_user=" + id_user +
                ", id_topic=" + id_topic +
                '}';
    }
}
