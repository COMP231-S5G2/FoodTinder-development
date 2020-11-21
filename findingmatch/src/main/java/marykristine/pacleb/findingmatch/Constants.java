package marykristine.pacleb.findingmatch;

public class Constants {

    //DB name
    public static final String DB_NAME = "FOOD_DB";
    //DB version
    public static final int DB_VERSION = 1;
    //DB table
    public static final  String TABLE_NAME = "FOOD_INFO_TABLE";
    //DB Table columns
    public static final String C_ID ="ID";
    public static final String C_FOOD_NAME ="FOOD_NAME";
    public static final String C_RESTAU_LOC ="RESTAU_LOC";
    public static final String C_PEANUT ="PEANUT_RES";
    public static final String C_SEAFOOD ="SEAFOOD_RES";
    public static final String C_DAIRY ="DAIRY_RES";
    public static final String C_MEAT ="MEAT_RES";
    public static final String C_GLUTEN ="GLUTEN_RES";
    public static final String C_IMAGE ="FOOD_IMAGE";
    public static final String C_ADD_TIMESTAMP ="ADD_TIMESTAMP";
    public static final String C_UPDATE_TIMESTAMP ="UPDATE_TIMESTAMP";

    //create query for table
    public static final String CREATE_TABLE = "CREATE TABLE "
            + TABLE_NAME
            + " ("
            + C_ID +" INTEGER PRIMARY KEY AUTOINCREMENT,"
            + C_FOOD_NAME + " TEXT,"
            + C_RESTAU_LOC + " TEXT,"
            + C_PEANUT + " TEXT,"
            + C_SEAFOOD + " TEXT,"
            + C_DAIRY + " TEXT,"
            + C_MEAT + " TEXT,"
            + C_GLUTEN + " TEXT,"
            + C_IMAGE + " TEXT,"
            + C_ADD_TIMESTAMP + " TEXT,"
            + C_UPDATE_TIMESTAMP + " TEXT"
            +");";
}
//Create Database helper class
