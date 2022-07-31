package model;

/**
 * Users model class.
 */
public class Users {

    private int userID;
    private String username, password;

    /**
     * Users class constructor. Instantiates a new user object.
     * @param userID
     * @param username
     * @param password
     */
    public Users(int userID, String username, String password) {
        this.userID = userID;
        this.username = username;
        this.password = password;
    }

    /**
     * Method gets User ID.
     * @return
     */
    public int getUserID() {
        return userID;
    }

    /**
     * Method sets User ID.
     * @param userID
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /**
     * Method gets user Name.
     * @return
     */
    public String getUsername() {
        return username;
    }

    /**
     * Method sets user Name.
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Method gets user Password.
     * @return
     */
    public String getPassword() {
        return password;
    }

    /**
     * Method sets user Password.
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Method overrides toString method.
     * @return
     */
    @Override
    public String toString() {
        return userID + " - " + username;
    }

}
