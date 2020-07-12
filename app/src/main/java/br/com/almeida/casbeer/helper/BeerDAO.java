package br.com.almeida.casbeer.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.com.almeida.casbeer.model.Beer;

public class BeerDAO implements iBeerDAO {

    private SQLiteDatabase write;
    private SQLiteDatabase read;

    public BeerDAO(Context context) {
        DBHelper db = new DBHelper(context);
        write = db.getWritableDatabase();
        read = db.getReadableDatabase();
    }

    @Override
    public boolean insert(Beer beer) {

        ContentValues cv = new ContentValues();
        cv.put("name", beer.getName());
        cv.put("tagline", beer.getTagline());
        cv.put("description", beer.getDescription());
        cv.put("image_url", beer.getImage_url());

        try {
            write.insert(DBHelper.TABLE_BEERS, null, cv);
            Log.i("INFODB", "Beer successfully saved!");
        } catch (Exception e) {
            Log.e("INFO", "Erro on insert database: " + e.getMessage());
            return false;
        }


        return true;
    }

    @Override
    public boolean update(Beer beer) {
        return false;
    }

    @Override
    public boolean delete(Beer beer) {
        return false;
    }

    @Override
    public List<Beer> list() {

        List<Beer> beers = new ArrayList<>();
        String sql = "SELECT * FROM " + DBHelper.TABLE_BEERS + " ;";
        Cursor c = read.rawQuery(sql, null);

        while (c.moveToNext()) {
            Beer beer = new Beer();

            Long id = c.getLong( c.getColumnIndex("id"));
            String name = c.getString( c.getColumnIndex("name"));
            String tagline = c.getString( c.getColumnIndex("tagline"));
            String description = c.getString( c.getColumnIndex("description"));
            String image_url = c.getString( c.getColumnIndex("image_url"));

            beer.setId(id.toString());
            beer.setName(name);
            beer.setImage_url(image_url);
            beer.setDescription(description);
            beer.setTagline(tagline);

            beers.add(beer);
        }

        return beers;
    }
}
