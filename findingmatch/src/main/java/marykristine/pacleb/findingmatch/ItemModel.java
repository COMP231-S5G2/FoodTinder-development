package marykristine.pacleb.findingmatch;

public class ItemModel {
    private int image;
    private String name, location;

    public ItemModel(){

    }
    public ItemModel(int image, String name, String location){
        this.image = image;
        this.name = name;
        this.location = location;

    }
    public int getImage() {return image;}

    public String getName(){return name;}

    public String getLocation(){return location;}

}
