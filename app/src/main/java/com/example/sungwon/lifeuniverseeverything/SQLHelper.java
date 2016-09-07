package com.example.sungwon.lifeuniverseeverything;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by SungWon on 9/6/2016.
 */
public class SQLHelper extends SQLiteOpenHelper{
    public static final String DB_NAME = "everything_db";

    public SQLHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }

    private static SQLHelper INSTANCE;

    public static synchronized SQLHelper getInstance(Context context) {
        if (INSTANCE == null)
            INSTANCE = new SQLHelper(context.getApplicationContext());
        return INSTANCE;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES_EVERYTHING);
        db.execSQL(SQL_CREATE_ENTRIES_CATEGORY);
        db.execSQL(SQL_CREATE_ENTRIES_TAG);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES_EVERYTHING);
        db.execSQL(SQL_DELETE_ENTRIES_CATEGORY);
        db.execSQL(SQL_DELETE_ENTRIES_TAG);
        onCreate(db);
    }

    /**
     * Inner class which represents the columns in everything table.
     */
    public static abstract class everythingTable implements BaseColumns {
        public static final String TABLE_NAME = "everything_table";
        public static final String COLUMN_EVERYTHING = "everything_name";
        public static final String COLUMN_TAGS_ID = "tags_id";
        public static final String COLUMN_RATINGS = "rating";
        public static final String COLUMN_CATEGORY_ID = "category_id";
    }

    /**
     * Inner class which represents the columns in categoruy table.
     */
    public static abstract class CategoryTable implements BaseColumns {
        public static final String TABLE_NAME = "category_table";
        public static final String COLUMN_CATEGORY = "category_name";
    }

    /**
     * Inner class which represents the columns in tag table.
     */
    public static abstract class TagTable implements BaseColumns {
        public static final String TABLE_NAME = "tag_table";
        public static final String COLUMN_TAG = "tag";
        public static final String COLUMN_THING_ID = "everything_id";
    }

    /**
     * SQL command to create everything table.
     */
    private static final String SQL_CREATE_ENTRIES_EVERYTHING = "CREATE TABLE " +
            everythingTable.TABLE_NAME + " (" +
            everythingTable._ID + " INTEGER PRIMARY KEY," +
            everythingTable.COLUMN_EVERYTHING + " TEXT," +
            everythingTable.COLUMN_TAGS_ID + " INTEGER," +
            everythingTable.COLUMN_RATINGS + " INTEGER," +
            everythingTable.COLUMN_CATEGORY_ID + " INTEGER" + ")";

    /**
     * SQL command to delete our everything table.
     */
    private static final String SQL_DELETE_ENTRIES_EVERYTHING = "DROP TABLE IF EXISTS " +
            everythingTable.TABLE_NAME;

    /**
     * SQL command to create our cate table.
     */
    private static final String SQL_CREATE_ENTRIES_CATEGORY = "CREATE TABLE " +
            CategoryTable.TABLE_NAME + " (" +
            CategoryTable._ID + " INTEGER PRIMARY KEY," +
            CategoryTable.COLUMN_CATEGORY + " TEXT" +
            "FOREIGN KEY(" + CategoryTable._ID + ") REFERENCES "+everythingTable._ID +")";

    /**
     * SQL command to delete our cate table.
     */
    private static final String SQL_DELETE_ENTRIES_CATEGORY = "DROP TABLE IF EXISTS " +
            CategoryTable.TABLE_NAME;

    /**
     * SQL command to create our tag table.
     */
    private static final String SQL_CREATE_ENTRIES_TAG = "CREATE TABLE " +
            TagTable.TABLE_NAME + " (" +
            TagTable._ID + " INTEGER PRIMARY KEY," +
            TagTable.COLUMN_TAG + " TEXT" +
            TagTable.COLUMN_THING_ID + " INTEGER" +
            "FOREIGN KEY(" + TagTable._ID + ") REFERENCES "+everythingTable._ID +")";

    /**
     * SQL command to delete our cate table.
     */
    private static final String SQL_DELETE_ENTRIES_TAG = "DROP TABLE IF EXISTS " +
            TagTable.TABLE_NAME;

    /**
     * Insert a mTask into the database.
     * @param
     */


    public void insertEverything(Everything thing) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(everythingTable.COLUMN_EVERYTHING, thing.getmName());
        values.put(everythingTable.COLUMN_TAGS_ID, everythingTable._ID);
        values.put(everythingTable.COLUMN_RATINGS, thing.getmRating());
        values.put(everythingTable.COLUMN_CATEGORY_ID, thing.getmCat_id());
        db.insertOrThrow(everythingTable.TABLE_NAME, null, values);
    }

    /**
     * Insert a company into the database.
     * @param
     */
    public void insertCategory(Category cat) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CategoryTable.COLUMN_CATEGORY, cat.getmCatTitle());
        db.insertOrThrow(CategoryTable.TABLE_NAME, null, values);
    }
    /**
     * Insert a tag into the database.
     * @param
     */
    public void insertTag(Everything thing, int id) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        List<String> taglist = thing.getmTag();
        for (int i = 0; i < taglist.size(); i++) {
            values.put(TagTable.COLUMN_TAG, taglist.get(i));
            values.put(TagTable.COLUMN_THING_ID, id );
        }

        db.insertOrThrow(CategoryTable.TABLE_NAME, null, values);
    }

    /**
     * Add some default data to the database.
     */
    public void addDataToDb() {
        Category cat0 = new Category("Animal");
        Category cat1 = new Category("Book");
        Category cat2 = new Category("Fruit");
        Category cat3 = new Category("Vegetable");
        Category cat4 = new Category("Person");
        Category cat5 = new Category("State of Being");
        Category cat6 = new Category("18+");
        Category cat7 = new Category("Game");
        Category cat8 = new Category("Show");
        Category cat9 = new Category("Movie");
        Category cat10 = new Category("Food");
    }

    /**
     * @return List of tasks within one category.
     */


    public int getEverythingID(String thing){

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(everythingTable.TABLE_NAME, // a. table
                new String[]{everythingTable._ID}, // b. column names
                everythingTable.COLUMN_EVERYTHING + " LIKE ?", // c. selections
                new String[]{thing}, // d. selections args
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit
        cursor.moveToFirst();
        cursor.close();
        db.close();
        return cursor.getInt(0);
    }

    public int getLastID(){
        String query = "SELECT last_insert_rowid()";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        int i = cursor.getInt(0);
        cursor.close();
        db.close();
        return i;
    }

    /**
     * TODO: Get category
     * @return get Category depending on le id.
     */
    public String getCategory(int id) {
        SQLiteDatabase db = getReadableDatabase();
        String ids = String.valueOf(id);

        String query = "SELECT "+ CategoryTable.COLUMN_CATEGORY + " FROM "+ CategoryTable.TABLE_NAME + " WHERE " + CategoryTable._ID + " like ?";
        Cursor cursor = db.rawQuery(query, new String[]{ids});
        cursor.moveToFirst();
        String s = cursor.getString(0);
        cursor.close();
        db.close();
        return s;
    }

    /**
     * TODO: Get category id
     * @return List of employee names.
     */
    public int getCategoryid(String cat) {
        SQLiteDatabase db = getReadableDatabase();
        LinkedList<String> goodlist = new LinkedList<>();
        String query = "SELECT "+ CategoryTable._ID + " FROM "+ CategoryTable.TABLE_NAME + " WHERE " + CategoryTable._ID + " like ?";
        Cursor cursor = db.rawQuery(query, new String[]{cat});
        cursor.moveToFirst();
        int i = cursor.getInt(0);
        cursor.close();
        db.close();
        return i;
    }


}
