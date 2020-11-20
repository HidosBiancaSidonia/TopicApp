package model;

import java.io.Serializable;

public class MessageUser implements Serializable {
    private static final long serializeVersionUID = 1L;
    private String message;
    private String user;

    public MessageUser(String message, String user) {
        this.message = message;
        this.user = user;
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

    @Override
    public String toString() {
        return "User: "  + user + '\'' +
                "Message: '" + message + '\'' +
                "/n";
    }
}
