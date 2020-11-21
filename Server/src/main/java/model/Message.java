package model;

import java.io.Serializable;

public class Message implements Serializable {
    private static final long serialVersionUID = 6529685098267757690L;
    private Integer id_message;
    private String message;
    private Integer id_user;
    private Integer id_topic;

    public Message(Integer id_message, String message, Integer id_user,Integer id_topic) {
        this.id_message = id_message;
        this.message = message;
        this.id_user = id_user;
        this.id_topic = id_topic;
    }

    public Integer getId_message() {
        return id_message;
    }

    public void setId_message(Integer id_message) {
        this.id_message = id_message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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
        return "Message{" +
                "message='" + message + '\'' +
                '}';
    }
}
