package es.westcod.android.fooddb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by ampedPF on 29/03/2015.
 */
public class SQLiteDatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "FoodDB";
    private static final String TABLE_NAME = "FoodsTable";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_PRICE = "price";
    private static final String KEY_QUANTITY = "quantity";
    private static final String[] COLUMNS = { KEY_ID, KEY_NAME, KEY_PRICE, KEY_QUANTITY };

    public SQLiteDatabaseHandler(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.i("SQLite DB : Constructor", "Constructor");
    }

    @Override
    public void onCreate(SQLiteDatabase arg0) {

        String CREATION_TABLE_EXAMPLE = "CREATE TABLE FoodsTable ( "
                        + "id INTEGER PRIMARY KEY AUTOINCREMENT, " + "name TEXT, "
                        + "price TEXT, " + "quantity INTEGER )";
        arg0.execSQL(CREATION_TABLE_EXAMPLE);
        Log.i("SQLite DB", "Creation");
    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {

        arg0.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        this.onCreate(arg0);
        Log.i("SQLite DB", "Upgrade");
    }

    public void deleteOne(Food food) {

        //1. Get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, "id = ?", new String[] { String.valueOf(food.getId())});
        db.close();
        Log.i("SQLite DB : Delete : ", food.toString());
    }

    public Food showOne(int id) {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, // a. Table
                        COLUMNS, // b. Column names
                        " id = ?", //c. Selections
                        new String[] {String.valueOf(id) }, // d. selections args
                        null, // e. group by
                        null, // f. having
                        null, // g. order by
                        null); // h. limit
        if (cursor != null)
                cursor.moveToFirst();
        Food food = new Food();
        food.setId(Integer.parseInt(cursor.getString(0)));
        food.setName(cursor.getString(1));
        food.setPrice(Float.parseFloat(cursor.getString(2)));
        food.setQuantity(Integer.parseInt(cursor.getString(3)));
        // Log
        Log.i("SQLite DB : Show one : id= " + id, food.toString());

        return food;
    }

    public List<Food> showAll() {

        List<Food> foods = new LinkedList<Food>();
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Food food = null;
        if (cursor.moveToFirst()){
            do {
                food = new Food();
                food.setId(Integer.parseInt(cursor.getString(0)));
                food.setName(cursor.getString(1));
                food.setPrice(Float.parseFloat(cursor.getString(2)));
                food.setQuantity(Integer.parseInt(cursor.getString(3)));
                foods.add(food);
            } while (cursor.moveToNext());
        }
        Log.i("SQLite DB : Show All : ", foods.toString());
        return foods;
    }

    public void addOne(Food food) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, food.getName());
        values.put(KEY_PRICE, food.getPrice());
        values.put(KEY_QUANTITY, food.getQuantity());
        // Insertion
        db.insert(TABLE_NAME, // Table
                        null, values );

        db.close();
        Log.i("SQLite DB : Add one : id= " + food.getId(), food.toString());
    }

    public int updateOne(Food food) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, food.getName());
        values.put(KEY_PRICE, food.getPrice());
        values.put(KEY_QUANTITY, food.getQuantity());

        int i = db.update(TABLE_NAME, // Table
                        values, // column/value
                        "id = ?", // selections
                        new String[] { String.valueOf(food.getId()) });

        db.close();
        Log.i("SQLite DB : Update one : id= " + food.getId(), food.toString());

        return i;
    }
}
