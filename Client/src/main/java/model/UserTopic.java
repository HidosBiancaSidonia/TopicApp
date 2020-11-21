package model;

import java.io.Serializable;

public class UserTopic implements Serializable {
    private static final long serialVersionUID = 6529685098267757690L;
    private Integer id_user_topic;
    private Integer id_user;
    private Integer id_topic;

    public UserTopic(){

    }

    public UserTopic(Integer id_user_topic, Integer id_user, Integer id_topic) {
        this.id_user_topic = id_user_topic;
        this.id_user = id_user;
        this.id_topic = id_topic;
    }

    public Integer getId_user_topic() {
        return id_user_topic;
    }

    public void setId_user_topic(Integer id_user_topic) {
        this.id_user_topic = id_user_topic;
    }

    public Integer getId_user() {
        return id_user;
    }

    public void setId_user(Integer id_user) {
        this.id_user = id_user;
    }

    public Integer getId_topic() {
        return id_topic;
    }

    public void setId_topic(Integer id_topic) {
        this.id_topic = id_topic;
    }

    @Override
    public String toString() {
        return "UserTopic{" +
                "id_user=" + id_user +
                ", id_topic=" + id_topic +
                '}';
    }
}
