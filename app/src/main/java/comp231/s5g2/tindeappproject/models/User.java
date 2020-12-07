package comp231.s5g2.tindeappproject.models;

public class User {

    public String userName;
    public String name;
    public String email;
    public String userPhoneNumber;
    public String userId;

    //default constructor
    public User(){

    }

    public User(String userName, String name, String email, String userPhoneNumber) {
        this.userName = userName;
        this.name = name;
        this.email = email;
        this.userPhoneNumber = userPhoneNumber;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUserPhoneNumber(String userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
    }

    public String getUserName() {
        return userName;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getUserPhoneNumber() {
        return userPhoneNumber;
    }
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
