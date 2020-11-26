package model;

import java.io.Serializable;

public class MessageUser implements Serializable {
    private static final long serialVersionUID = 6529685098267757690L;
    private String message;
    private String user;
    private int id_user;

    /**
     * Constructor without parameters that sets all null
     */
    public MessageUser(String message, String user) {
        this.message = message;
        this.user = user;
    }

    /**
     * Constructor that sets all class variables
     */
    public MessageUser(String message, Integer id_user) {
        this.message = message;
        this.id_user = id_user;
    }

    /**
     * @return message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the message variable
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return user
     */
    public String getUser() {
        return user;
    }

    /**
     * Sets the user variable
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * @return id_user
     */
    public int getId_user() {
        return id_user;
    }

    /**
     * Sets the id_user variable
     */
    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    /**
     * Converts the MessageUser object to a string
     */
    @Override
    public String toString() {
        return "\r\nUser: "  + user + " Message: " + message +" \r\n" ;
    }
}
