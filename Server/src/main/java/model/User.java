package model;

import java.io.Serializable;

/**
 * Model class for the user table in the DB
 */
public class User implements Serializable {
    private static final long serialVersionUID = 6529685098267757690L;
    private Integer id_user;
    private String username;
    private String password;

    /**
     * Constructor without parameters that sets null all variables
     */
    public User(){
        this.id_user = 0;
        this.username = null;
        this.password = null;
    }

    /**
     * Constructor that sets username and passwords variables
     * @param username
     * @param password
     */
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Constructor that sets all class variables
     * @param id_user
     * @param username
     * @param password
     */
    public User(Integer id_user, String username, String password) {
        this.id_user = id_user;
        this.username = username;
        this.password = password;
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
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username variable
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password variable
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Converts the User object to a string
     * @return
     */
    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
