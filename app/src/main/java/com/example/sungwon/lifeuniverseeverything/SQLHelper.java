package com.example.sungwon.lifeuniverseeverything;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by SungWon on 9/6/2016.
 */
public class SQLHelper extends SQLiteOpenHelper{
    public static final String DB_NAME = "everything_db";

    public SQLHelper(Context context) {
        super(context, DB_NAME, null, 5);
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
        public static final String COLUMN_REVIEW = "review";
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
            everythingTable.COLUMN_CATEGORY_ID + " INTEGER," +
            everythingTable.COLUMN_REVIEW + " TEXT"+")";

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
            CategoryTable.COLUMN_CATEGORY + " TEXT" + ")";

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
            TagTable.COLUMN_TAG + " TEXT," +
            TagTable.COLUMN_THING_ID + " INTEGER" + ")";

    /**
     * SQL command to delete our cate table.
     */
    private static final String SQL_DELETE_ENTRIES_TAG = "DROP TABLE IF EXISTS " +
            TagTable.TABLE_NAME;

    /**
     * Insert a mTask into the database.
     * @param
     */


    public int insertEverything(Everything thing) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(everythingTable.COLUMN_EVERYTHING, thing.getmName());
        values.put(everythingTable.COLUMN_TAGS_ID, getLastID());
        values.put(everythingTable.COLUMN_RATINGS, thing.getmRating());
        values.put(everythingTable.COLUMN_CATEGORY_ID, thing.getmCat_id());
        values.put(everythingTable.COLUMN_REVIEW, thing.getmReview());
        db.insertOrThrow(everythingTable.TABLE_NAME, null, values);
        return getLastID();
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
     * Insert a tag into the database.  Must directly follow inserteverything
     * @param
     */
    public void insertTag(Everything thing, int id) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        String taglist = thing.getmTag(true);

        values.put(TagTable.COLUMN_TAG, taglist);
        values.put(TagTable.COLUMN_THING_ID, id );


        db.insertOrThrow(TagTable.TABLE_NAME, null, values);
    }

    /**
     * Add some default data to the database.
     */
    public void addDataToDb() {
// first cat initialize, hopefully cat id matches variable number (it's actually +1...)
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
        Category cat11 = new Category("Beverage");

        insertCategory(cat0);
        insertCategory(cat1);
        insertCategory(cat2);
        insertCategory(cat3);
        insertCategory(cat4);
        insertCategory(cat5);
        insertCategory(cat6);
        insertCategory(cat7);
        insertCategory(cat8);
        insertCategory(cat9);
        insertCategory(cat10);
        insertCategory(cat11);

        //2nd db dummy initialize Everything format cat id, name, rating, review, tags
        Everything thing0 = new Everything(1, "Milkie, a Shi-tzu", 7, "She's pretty much pretty good" , "great, pet, nice, fluffy");
        Everything thing1 = new Everything(8, "Sanic", 2, "Just not a good Sanic" , "poor, horrible, murder, crime");
        Everything thing2 = new Everything(9, "Star Trek: The Next Generation", 10, "Pretty much the best show pretty much" , "sweet, I, love, Picard, all, of, the, homo");
        int i = 0;
        i = insertEverything(thing0);
        insertTag(thing0, i);
        i = insertEverything(thing1);
        insertTag(thing1, i);
        i = insertEverything(thing2);
        insertTag(thing2, i);

    }

    /**
     * Should return the entire "Everythings" in the entire db... hopefully
     * @return cursor
     */

    public Cursor getEverything(){
        SQLiteDatabase db = this.getReadableDatabase();
//        LinkedList<Everything> list = new LinkedList<>();
        Cursor cursor = db.query(everythingTable.TABLE_NAME, // a. table
                new String[]{everythingTable._ID, everythingTable.COLUMN_CATEGORY_ID, everythingTable.COLUMN_EVERYTHING, everythingTable.COLUMN_RATINGS, everythingTable.COLUMN_REVIEW, everythingTable.COLUMN_TAGS_ID}, // b. column names
                null, // c. selections
                null, // d. selections args
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit
//        cursor.moveToFirst();
//        while(!cursor.isAfterLast()){
//            int tagid = cursor.getInt(cursor.getColumnIndex(everythingTable.COLUMN_TAGS_ID));
//            String tags = getTag(tagid);
//            Everything thing = new Everything(cursor.getInt(cursor.getColumnIndex(everythingTable.COLUMN_CATEGORY_ID)),
//                    cursor.getString(cursor.getColumnIndex(everythingTable.COLUMN_EVERYTHING)),
//                    cursor.getInt(cursor.getColumnIndex(everythingTable.COLUMN_RATINGS)),
//                    cursor.getString(cursor.getColumnIndex(everythingTable.COLUMN_REVIEW)),
//                    tags
//                    );
//            list.add(thing);
//        }
//        cursor.close();
//        db.close();
        return cursor;
    }

    /**
     * Gets the ID of an everything
     * @param thing
     * @return
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
        int i = cursor.getInt(cursor.getColumnIndex(everythingTable._ID));
        cursor.close();
        db.close();
        return i;
    }

    /**
     * method to get all the categories in an arraylist to use in drop down menuuuuu
     * @return
     */
    public ArrayList<String> getAllCats(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> bongo = new ArrayList<>();
        Cursor cursor = db.query(CategoryTable.TABLE_NAME, // a. table
                new String[]{CategoryTable.COLUMN_CATEGORY}, // b. column names
                null, // c. selections
                null, // d. selections args
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit
        while(cursor.moveToNext()){
            bongo.add(cursor.getString(cursor.getColumnIndex(CategoryTable.COLUMN_CATEGORY)));
        }
        return bongo;
    }

    public int getLastID(){
        String query = "SELECT last_insert_rowid()";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        int i = cursor.getInt(0);
        return i;
    }

    /**
     *
     * @return get Category depending on le id.
     */
    public String getCategory(int id) {
        SQLiteDatabase db = getReadableDatabase();
        String ids = String.valueOf(id);

        String query = "SELECT * FROM "+ CategoryTable.TABLE_NAME + " WHERE " + CategoryTable._ID + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{ids});
//        Cursor cursor = db.rawQuery("SELECT * FROM category_table", null);
        cursor.moveToFirst();
        String s = cursor.getString(cursor.getColumnIndex(CategoryTable.COLUMN_CATEGORY));
        cursor.close();
        db.close();
        return s;
    }

    /**
     *
     * @return List of employee names.
     */
    public int getCategoryid(String cat) {
        SQLiteDatabase db = getReadableDatabase();
        LinkedList<String> goodlist = new LinkedList<>();
        String query = "SELECT "+ CategoryTable._ID + " FROM "+ CategoryTable.TABLE_NAME + " WHERE " + CategoryTable.COLUMN_CATEGORY + " like ?";
        Cursor cursor = db.rawQuery(query, new String[]{cat});
        cursor.moveToFirst();
        int i = cursor.getInt(cursor.getColumnIndex(CategoryTable._ID));
        cursor.close();
        db.close();
        return i;
    }

    /**
     * Gets Tag based on id
     * @return get Category depending on le id.
     */
    public String getTag(int id) {
        SQLiteDatabase db = getReadableDatabase();
        String ids = String.valueOf(id);

        String query = "SELECT "+ TagTable.COLUMN_TAG + " FROM "+ TagTable.TABLE_NAME + " WHERE " + TagTable._ID + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{ids});
        cursor.moveToFirst();
        String s = cursor.getString(cursor.getColumnIndex(TagTable.COLUMN_TAG));
        return s;
    }

    /**
     * Gets Tag ID based on tag name
     * @return List of employee names.
     */
    public int getTagid(String cat) {
        SQLiteDatabase db = getReadableDatabase();
        LinkedList<String> goodlist = new LinkedList<>();
        String query = "SELECT "+ TagTable._ID + " FROM "+ TagTable.TABLE_NAME + " WHERE " + TagTable.COLUMN_TAG + " like ?";
        Cursor cursor = db.rawQuery(query, new String[]{cat});
        cursor.moveToFirst();
        int i = cursor.getInt(cursor.getColumnIndex(TagTable._ID));
        cursor.close();
        db.close();
        return i;
    }

    /**
     * Deletes an Everything as well as a tag associated with it
     * @param id
     */
    public void deleteOneEverything(int id){
        SQLiteDatabase db = getWritableDatabase();

        String selection = everythingTable._ID + " = ?";

        String[] selectionArgs = new String[]{String.valueOf(id)};

        db.delete(everythingTable.TABLE_NAME, selection, selectionArgs);

        String selection2 = TagTable.COLUMN_THING_ID + " = ?";

        String[] selectionArgs2 = new String[]{String.valueOf(id)};

        db.delete(TagTable.TABLE_NAME, selection, selectionArgs);
    }

    public Cursor getSpecificThing(String query, String choice){ //needs search entry as well as choice of which query to search
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        switch (choice) {
            case "everything":

                cursor = db.query(everythingTable.TABLE_NAME, // a. table
                        new String[]{everythingTable._ID, everythingTable.COLUMN_CATEGORY_ID, everythingTable.COLUMN_EVERYTHING, everythingTable.COLUMN_RATINGS, everythingTable.COLUMN_REVIEW, everythingTable.COLUMN_TAGS_ID}, // b. column names
                        everythingTable.COLUMN_EVERYTHING + " LIKE ?", // c. selections
                        new String[]{'%' + query + '%'}, // d. selections args
                        null, // e. group by
                        null, // f. having
                        null, // g. order by
                        null); // h. limit

                break;

            case "tag":
                ArrayList<Integer> idlist = new ArrayList<>();
                cursor = db.query(everythingTable.TABLE_NAME, // a. table
                        new String[]{everythingTable.COLUMN_TAGS_ID}, // b. column names
                        null, // c. selections
                        null, // d. selections args
                        null, // e. group by
                        null, // f. having
                        null, // g. order by
                        null); // h. limit

                DatabaseUtils.dumpCursor(cursor);
                while(cursor.moveToNext()){

                    idlist.add(cursor.getInt(cursor.getColumnIndex(everythingTable.COLUMN_TAGS_ID)));

                }
                if(idlist.size()==0) {cursor.close(); break;}
                String[] idargs = new String[idlist.size()];
                for (int i = 0; i < idlist.size(); i++) {
                    String tag = getTag(idlist.get(i));
                    List<String> items = Arrays.asList(tag.split("\\s*,\\s*"));
                    idargs[i] = (items.contains(query)) ? String.valueOf(idlist.get(i)):"z";
                }
                cursor.close();
                cursor = db.query(everythingTable.TABLE_NAME, // a. table
                        new String[]{everythingTable._ID, everythingTable.COLUMN_CATEGORY_ID, everythingTable.COLUMN_EVERYTHING, everythingTable.COLUMN_RATINGS, everythingTable.COLUMN_REVIEW, everythingTable.COLUMN_TAGS_ID}, // b. column names
                        everythingTable.COLUMN_TAGS_ID + " = ?", // c. selections
                        idargs, // d. selections args
                        null, // e. group by
                        null, // f. having
                        null, // g. order by
                        null); // h. limit
                break;
            case "cat":
                cursor = db.query(CategoryTable.TABLE_NAME, // a. table
                        new String[]{CategoryTable._ID}, // b. column names
                        CategoryTable.COLUMN_CATEGORY + " = ?", // c. selections
                        new String[]{query}, // d. selections args
                        null, // e. group by
                        null, // f. having
                        null, // g. order by
                        null); // h. limit
                ArrayList<Integer> cidlist = new ArrayList<>();
                while(cursor.moveToNext()){
                    cidlist.add(cursor.getInt(cursor.getColumnIndex(CategoryTable._ID)));
                }
                if(cidlist.size()==0) {cursor.close(); break;}
                String[] cidargs = new String[cidlist.size()];
                for (int i = 0; i < cidlist.size(); i++) {
                    cidargs[i]=String.valueOf(cidlist.get(i));
                }
                cursor = db.query(everythingTable.TABLE_NAME, // a. table
                        new String[]{everythingTable._ID, everythingTable.COLUMN_CATEGORY_ID, everythingTable.COLUMN_EVERYTHING, everythingTable.COLUMN_RATINGS, everythingTable.COLUMN_REVIEW, everythingTable.COLUMN_TAGS_ID}, // b. column names
                        everythingTable.COLUMN_CATEGORY_ID + " = ?", // c. selections
                        cidargs, // d. selections args
                        null, // e. group by
                        null, // f. having
                        null, // g. order by
                        null); // h. limit
                break;
        }

        return cursor;
    }
}
