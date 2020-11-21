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
        db.execSQL("DROP TABLE IF EXISTS "+ Constants.TABLE_NAME);
        onCreate(db);
    }

    //method for inserting info to table
    public long insertInfo(String foodName, String restauName, String restauLocation, String foodImage)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constants.C_FOOD_NAME, foodName);
        values.put(Constants.C_RESTAU_NAME, restauName);
        values.put(Constants.C_RESTAU_LOC, restauLocation);
        values.put(Constants.C_IMAGE, foodImage);

        long id = db.insert(Constants.CREATE_TABLE, null, values);
        db.close();
        return id;
    }
    //unto UploadImageActivity
}
