package comp231.s5g2.tindeappproject.models;

public class ItemModel {
    private String image;
    private String name, location;
    private String restriction;
    private int dishId;

    public ItemModel(){

    }
    public ItemModel(String image, String name, String location, int dishId, String restriction){
        this.image = image;
        this.name = name;
        this.location = location;
        this.dishId = dishId;
        this.restriction = restriction;

    }
    public String getImage() {return image;}

    public String getName(){return name;}

    public String getLocation(){return location;}
    public String getRestriction() {return restriction;}
    public int getDishId() {return dishId;}

}
