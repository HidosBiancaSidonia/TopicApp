package model;

import java.io.Serializable;

public class MessageUser implements Serializable {
    private static final long serialVersionUID = 6529685098267757690L;
    private String message;
    private String user;
    private int id_user;

    public MessageUser(String message, String user) {
        this.message = message;
        this.user = user;
    }

    public MessageUser(String message, int id_user) {
        this.message = message;
        this.id_user = id_user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    @Override
    public String toString() {
        return "\r\nUser: "  + user + " Message: " + message +" \r\n" ;
    }
}
