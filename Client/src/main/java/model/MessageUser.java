package model;

import java.io.Serializable;

public class MessageUser implements Serializable {
    private static final long serialVersionUID = 6529685098267757690L;
    private String message;
    private String user;

    /**
     * Constructor without parameters that sets all null
     */
    public MessageUser(){
        this.message = null;
        this.user = null;
    }

    /**
     * Constructor that sets all class variables
     * @param message
     * @param user
     */
    public MessageUser(String message, String user) {
        this.message = message;
        this.user = user;
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
     * @return user
     */
    public String getUser() {
        return user;
    }

    /**
     * Sets the user variable
     * @param user
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * Converts the MessageUser object to a string
     * @return
     */
    @Override
    public String toString() {
        return "\r\nUser: "  + user + " Message: " + message +" \r\n" ;
    }
}
