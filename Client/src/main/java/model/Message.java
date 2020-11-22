package model;

import java.io.Serializable;

public class Message implements Serializable {
    private static final long serialVersionUID = 6529685098267757690L;
    private Integer id_message;
    private String message;
    private Integer id_user;
    private Integer id_topic;

    /**
     * Constructor without parameters that sets all null
     */
    public Message(){
        this.id_message= 0;
        this.message = null;
        this.id_user = 0;
        this.id_topic = 0;
    }

    /**
     * Constructor that sets all class variables
     * @param id_message
     * @param message
     * @param id_user
     * @param id_topic
     */
    public Message(Integer id_message, String message, Integer id_user,Integer id_topic) {
        this.id_message = id_message;
        this.message = message;
        this.id_user = id_user;
        this.id_topic = id_topic;
    }

    /**
     * @return id_message
     */
    public Integer getId_message() {
        return id_message;
    }

    /**
     * Sets the id_message variable
     * @param id_message
     */
    public void setId_message(Integer id_message) {
        this.id_message = id_message;
    }

    /**
     * @return message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the message variable
     * @param message
     */
    public void setMessage(String message) {
        this.message = message;
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
     * Converts the Message object to a string
     * @return
     */
    @Override
    public String toString() {
        return "Message{" +
                "message='" + message + '\'' +
                '}';
    }
}
