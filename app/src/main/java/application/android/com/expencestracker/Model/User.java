package application.android.com.expencestracker.Model;

/**
 * The User class represents a user item with several properties, such as
 * user id, user name, user password, user email, etc.
 *
 * @author
 * @version 1.0
 *
 */
public class User {

    private int userId;
    private String username;
    private String password;
    private String email;
    private boolean status;

    public User(String username, String password, String email, boolean status) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.status = status;
    }

    public User(int userId, String username, String password, String email, boolean status) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.email = email;
        this.status = status;
    }

    public User() {
        this.username="";
        this.password="";
        this.email = "";
        this.status=true;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getUserId() {
        return userId;
    }

}

