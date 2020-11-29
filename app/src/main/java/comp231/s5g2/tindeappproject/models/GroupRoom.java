package comp231.s5g2.tindeappproject.models;

import java.util.Random;

public class GroupRoom {
    private String name, code, location;
    String groupID;

    public GroupRoom(String name, String location, String code) {
        this.name = name;
        this.location = location;
        this.code = code;
    }

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public String getCode() {
        return code;
    }
}
