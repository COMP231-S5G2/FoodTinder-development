package comp231.s5g2.tindeappproject.models;

public class Dish {
    private String name;
    private double price;
    private String description;
    private String imageAcessToken;
    private int dishID;
    private String restriction;

    public Dish(String nome, double price, String description, String restriction)
    {
        this.name = nome;
        this.price = price;
        this.description = description;
        this.restriction = restriction;

    }

    public int getDishID() {
        return dishID;
    }

    public void setDishID(int dishID) {
        this.dishID = dishID;
    }

    public String getImageAcessToken() {
        return imageAcessToken;
    }

    public void setImageAccessToken(String imageAcessToken) {
        this.imageAcessToken = imageAcessToken;
    }

    public Dish() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRestriction() {return restriction;}

    public void setRestriction(String restriction) {this.restriction = restriction;}

}
