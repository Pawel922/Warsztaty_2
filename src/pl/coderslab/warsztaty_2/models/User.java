package pl.coderslab.warsztaty_2.models;

import org.mindrot.jbcrypt.BCrypt;

public class User {

    private int id;
    private String userName;
    private String email;
    private String password;
    private int userGroupId;

    public User() {}

    public User(String userName, String email, String password) {
        this.userName = userName;
        this.email = email;
        this.hashPassword(password);
    }

    public int getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public int getUserGroupId() {
        return userGroupId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserGroupId(int userGroupId) {
        this.userGroupId = userGroupId;
    }

    public void hashPassword(String password) {
        this.password = BCrypt.hashpw(password, BCrypt.gensalt());
    }


}
