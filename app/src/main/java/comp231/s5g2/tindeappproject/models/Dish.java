package comp231.s5g2.tindeappproject.models;

public class Dish {
    private String name;
    private double price;
    private String description;
    private String imageAcessToken;
    private boolean petSafe,vegan,vegetarian,nutsFree,halal;
    private int dishID;
    public Dish(String nome, double price, String description) {
        this.name = nome;
        this.price = price;
        this.description = description;
    }

    public int getDishID() {
        return dishID;
    }

    public void setDishID(int dishID) {
        this.dishID = dishID;
    }

    public boolean isPetSafe() {
        return petSafe;
    }

    public void setPetSafe(boolean petSafe) {
        this.petSafe = petSafe;
    }

    public boolean isVegan() {
        return vegan;
    }

    public void setVegan(boolean vegan) {
        this.vegan = vegan;
    }

    public boolean isVegetarian() {
        return vegetarian;
    }

    public void setVegetarian(boolean vegetarian) {
        this.vegetarian = vegetarian;
    }

    public boolean isNutsFree() {
        return nutsFree;
    }

    public void setNutsFree(boolean nutsFree) {
        this.nutsFree = nutsFree;
    }

    public boolean isHalal() {
        return halal;
    }

    public void setHalal(boolean halal) {
        this.halal = halal;
    }

    public String getImageAcessToken() {
        return imageAcessToken;
    }

    public void setImageAcessToken(String imageAcessToken) {
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
}
