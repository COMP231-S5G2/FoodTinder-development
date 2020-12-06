package comp231.s5g2.tindeappproject.models;

public class User {

    private String userName;
    private String name;
    private String email;
    private String userPhoneNumber;

    public User(){

    }

    public User(String userName, String name, String email, String userPhoneNumber) {
        this.userName = userName;
        this.name = name;
        this.email = email;
        this.userPhoneNumber = userPhoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhoneNumber() {
        return userPhoneNumber;
    }

    public void setUserPhoneNumber(String userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
    }
}
