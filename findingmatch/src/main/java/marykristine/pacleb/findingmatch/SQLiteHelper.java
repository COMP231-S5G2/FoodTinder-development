package marykristine.pacleb.findingmatch;


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SQLiteHelper extends SQLiteOpenHelper {


    public SQLiteHelper(@Nullable Context context) {
        super(context, Constants.DB_NAME, null, Constants.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(Constants.CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_NAME);
        onCreate(db);
    }

    //method for inserting info to table
    public long insertInfo(String strFoodName, String strRestauLocation, String strFoodImage,
                           String strPeanut, String strSeafood, String strDairy, String strMeat,  String strGluten,
                           String strAddTimeStamp, String strUpdateTimeStamp)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constants.C_FOOD_NAME, strFoodName);
        values.put(Constants.C_RESTAU_LOC, strRestauLocation);
        values.put(Constants.C_IMAGE, strFoodImage);

        values.put(Constants.C_PEANUT, strFoodImage);
        values.put(Constants.C_SEAFOOD, strFoodImage);
        values.put(Constants.C_DAIRY, strFoodImage);
        values.put(Constants.C_MEAT, strFoodImage);
        values.put(Constants.C_GLUTEN, strFoodImage);

        values.put(Constants.C_ADD_TIMESTAMP, strAddTimeStamp);
        values.put(Constants.C_UPDATE_TIMESTAMP, strUpdateTimeStamp);

        long id = db.insert(Constants.CREATE_TABLE, null, values);
        db.close();
        return id;
    }
    //unto UploadImageActivity
}
