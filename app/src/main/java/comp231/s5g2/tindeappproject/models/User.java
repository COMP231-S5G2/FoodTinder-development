package comp231.s5g2.tindeappproject.models;

public class User {

    public String userName;
    public String name;
    public String email;
    public String userPhoneNumber;

    //default constructor
    public User(){

    }

    public User(String userName, String name, String email, String userPhoneNumber) {
        this.userName = userName;
        this.name = name;
        this.email = email;
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
}
