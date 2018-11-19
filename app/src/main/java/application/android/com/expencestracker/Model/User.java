package application.android.com.expencestracker.Model;

public class User {

    public static final String DBNAME = "extrack.db";
    public static final String USER_TABLE_NAME = "user_record";
    public static final String USER_TABLE_INFO_COLUM_ID = "userid";
    public static final String USER_TABLE_INFO_COLUM_USERNAME = "username";
    public static final String USER_TABLE_INFO_COLUM_PASSWORD = "password";
    public static final String USER_TABLE_INFO_COLUM_EMAIL = "email";
    public static final String USER_TABLE_INFO_COLUM_STATUS = "status";

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

    public void setEmail(String username) {
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

