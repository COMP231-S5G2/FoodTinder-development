package comp231.s5g2.tindeappproject.models;

import comp231.s5g2.tindeappproject.models.Restaurant;

public class Owner {

    String ownerID;
    Restaurant restaurant;
    String ownerGmail;


    public Owner() {
    }

    public String getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(String ownerID) {
        this.ownerID = ownerID;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
}
