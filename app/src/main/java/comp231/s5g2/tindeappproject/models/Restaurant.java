package comp231.s5g2.tindeappproject.models;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class Restaurant {

    private String restaurantName;
    private String restaurantPhone;
    private String restaurantAddress;
    private List<Dish> dishes = new ArrayList<Dish>();
    private String pictureToken;


    public Restaurant(String restaurantName, String restaurantPhone, String restauranteAddress, List<Dish> dishes) {
        this.restaurantName = restaurantName;
        this.restaurantPhone = restaurantPhone;
        this.restaurantAddress = restauranteAddress;
        this.dishes = dishes;
    }

    public String getPictureToken() {
        return pictureToken;
    }

    public void setPictureToken(String pictureToken) {
        this.pictureToken = pictureToken;
    }

    public Restaurant() {
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getRestaurantPhone() {
        return restaurantPhone;
    }

    public void setRestaurantPhone(String restaurantPhone) {
        this.restaurantPhone = restaurantPhone;
    }

    public String getRestaurantAddress() {
        return restaurantAddress;
    }

    public void setRestaurantAddress(String restaurantAddress) {
        this.restaurantAddress = restaurantAddress;
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    public void setDishes(List<Dish> dishes) {
        this.dishes = dishes;
    }

}
