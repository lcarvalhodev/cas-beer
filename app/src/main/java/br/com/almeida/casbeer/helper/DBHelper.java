package br.com.almeida.casbeer.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    public static int VERSION = 1;
    public static String NAME_DB = "DB_BEERS";
    public static String TABLE_BEERS = "beers";


    public DBHelper(@Nullable Context context) {
        super(context, NAME_DB, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_BEERS
                + " ( id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                " name TEXT NOT NULL, tagline TEXT NOT NULL, description TEXT NOT NULL, image_url TEXT NOT NULL ); ";

        try {
            sqLiteDatabase.execSQL(sql);
            Log.i("INFODB", "Success to create table");
        }catch (Exception e){
            Log.i("INFODB", "Error to create table " + e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
