package comp231.s5g2.tindeappproject.models;

public class ItemModel {
    private String image;
    private String name, location,
            radius, resHalal, resNuts,
            resVegan, resVegetarian, resPetSafe;

    public ItemModel(String imageAcessToken, String name, String restaurantAddress){

    }
    public ItemModel(String image, String name, String location,
                     String radius, String resHalal, String resNuts,
                     String resVegan, String resVegetarian, String resPetSafe){

        this.image = image;
        this.name = name;
        this.location = location;
        this.radius =radius;
        this.resHalal = resHalal;
        this.resNuts = resNuts;
        this.resVegan = resVegan;
        this.resVegetarian = resVegetarian;
        this.resPetSafe = resPetSafe;

    }

    public String getImage() {return image;}

    public String getName(){return name;}

    public String getLocation(){return location;}

    //added getter methods for new members
    public String getRadius() {return radius;}

    public String getResHalal(){return resHalal;}

    public String getResNuts(){return resNuts;}

    public String getResVegan() {return resVegan;}

    public String getResVegetarian(){return resVegetarian;}

    public String getResPetSafe(){return resPetSafe;}

}
