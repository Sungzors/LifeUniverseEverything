package com.example.sungwon.lifeuniverseeverything;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by SungWon on 9/6/2016.
 */
public class SQLHelper extends SQLiteOpenHelper{
    public static final String DB_NAME = "everything_db";

    public SQLHelper(Context context) {
        super(context, DB_NAME, null, 13);
    }

    private static SQLHelper INSTANCE;
    private int ii = 1;

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
        addDataToDb(db);
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
        public static final String COLUMN_TAGSTHING = "tags";
        public static final String COLUMN_RATINGS = "rating";
        public static final String COLUMN_CATEGORY_ID = "category_id";
        public static final String COLUMN_REVIEW = "review";
        public static final String COLUMN_PICTURE = "picture";
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
            everythingTable.COLUMN_TAGSTHING + " TEXT," +
            everythingTable.COLUMN_RATINGS + " INTEGER," +
            everythingTable.COLUMN_CATEGORY_ID + " INTEGER," +
            everythingTable.COLUMN_REVIEW + " TEXT,"+
            everythingTable.COLUMN_PICTURE + " TEXT" + ")";

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


    public int insertthing(Everything thing) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(everythingTable.COLUMN_EVERYTHING, thing.getmName());
        values.put(everythingTable.COLUMN_TAGSTHING, thing.getmTag(true));
        ii++;
        values.put(everythingTable.COLUMN_RATINGS, thing.getmRating());
        values.put(everythingTable.COLUMN_CATEGORY_ID, thing.getmCat_id());
        values.put(everythingTable.COLUMN_REVIEW, thing.getmReview());
        db.insertOrThrow(everythingTable.TABLE_NAME, null, values);
        int af = getLastID();
        return getLastID();
    }

    public void insertthing(SQLiteDatabase db, Everything thing) {
        ContentValues values = new ContentValues();

        values.put(everythingTable.COLUMN_EVERYTHING, thing.getmName());
        values.put(everythingTable.COLUMN_TAGSTHING, thing.getmTag(true));
        ii++;
        values.put(everythingTable.COLUMN_RATINGS, thing.getmRating());
        values.put(everythingTable.COLUMN_CATEGORY_ID, thing.getmCat_id());
        values.put(everythingTable.COLUMN_REVIEW, thing.getmReview());
        values.put(everythingTable.COLUMN_PICTURE, thing.getmPicURL());
        db.insertOrThrow(everythingTable.TABLE_NAME, null, values);

    }

    public int insertEverything(Everything thing) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        int x = getLastIDET();
        values.put(everythingTable.COLUMN_EVERYTHING, thing.getmName());
        values.put(everythingTable.COLUMN_TAGSTHING, thing.getmTag(true));
        values.put(everythingTable.COLUMN_RATINGS, thing.getmRating());
        values.put(everythingTable.COLUMN_CATEGORY_ID, thing.getmCat_id());
        values.put(everythingTable.COLUMN_REVIEW, thing.getmReview());
        values.put(everythingTable.COLUMN_PICTURE, thing.getmPicURL());
        db.insertOrThrow(everythingTable.TABLE_NAME, null, values);
        return x;
    }

    /**
     * Insert a company into the database.
     * @param
     */
    public void insertCategory(SQLiteDatabase db, Category cat) {
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
        List<String> taglist = thing.getmTag();

        for (int i = 0; i < taglist.size(); i++) {
            ContentValues values = new ContentValues();
            values.put(TagTable.COLUMN_TAG, taglist.get(i));
            values.put(TagTable.COLUMN_THING_ID, id + 1);
            db.insertOrThrow(TagTable.TABLE_NAME, null, values);
        }
    }

    public void insertTag(SQLiteDatabase db, Everything thing) {
        List<String> taglist = thing.getmTag();

        for (int i = 0; i < taglist.size(); i++) {
            ContentValues values = new ContentValues();
            values.put(TagTable.COLUMN_TAG, taglist.get(i));
            values.put(TagTable.COLUMN_THING_ID, ii-1);
            db.insertOrThrow(TagTable.TABLE_NAME, null, values);
        }
    }

    /**
     * Add some default data to the database.
     */
    public void addDataToDb(SQLiteDatabase db) {
// first cat initialize, hopefully cat id matches variable number (it's actually +1...)


        Category cat0 = new Category("Action");
        Category cat1 = new Category("Animal");
        Category cat2 = new Category("Belief");
        Category cat3 = new Category("Beverage");
        Category cat4 = new Category("Book");
        Category cat5 = new Category("Emotion");
        Category cat6 = new Category("Event");
        Category cat7 = new Category("Fiction");
        Category cat8 = new Category("Food");
        Category cat9 = new Category("Fruit");
        Category cat10 = new Category("Game");
        Category cat11 = new Category("Movie");
        Category cat12 = new Category("Music");
        Category cat13 = new Category("Person");
        Category cat14 = new Category("Place");
        Category cat15 = new Category("Show");
        Category cat16 = new Category("State of Being");
        Category cat17 = new Category("Time");
        Category cat18 = new Category("Tool");
        Category cat19 = new Category("Vegetable");
        Category cat20 = new Category("18+");
        Category cat21 = new Category("Other");


        insertCategory(db, cat0);
        insertCategory(db, cat1);
        insertCategory(db, cat2);
        insertCategory(db, cat3);
        insertCategory(db, cat4);
        insertCategory(db, cat5);
        insertCategory(db, cat6);
        insertCategory(db, cat7);
        insertCategory(db, cat8);
        insertCategory(db, cat9);
        insertCategory(db, cat10);
        insertCategory(db, cat11);
        insertCategory(db, cat12);
        insertCategory(db, cat13);
        insertCategory(db, cat14);
        insertCategory(db, cat15);
        insertCategory(db, cat16);
        insertCategory(db, cat17);
        insertCategory(db, cat18);
        insertCategory(db, cat19);
        insertCategory(db, cat20);
        insertCategory(db, cat21);


//        2nd db dummy initialize Everything format cat id, name, rating, review, tags
        Everything thing0 = new Everything(2, "Milkie, a Shi-tzu", 7, "She's pretty much pretty good", "great, pet, nice, fluffy");
        Everything thing1 = new Everything(11, "Sanic", 2, "Just not a good Sanic", "poor, horrible, murder, crime");
        Everything thing2 = new Everything(16, "Star Trek: The Next Generation", 10, "Pretty much the best show pretty much", "sweet, I, love, Picard");
        thing0.setmPicURL("http://cdn2-www.dogtime.com/assets/uploads/gallery/shih-tzu-dog-breed-pictures/shih-tzu-breed-picture-8.jpg");
        thing1.setmPicURL("http://vignette1.wikia.nocookie.net/meme/images/4/42/1385136139955.png/revision/latest?cb=20150207013804");
        thing2.setmPicURL("http://www.blastr.com/sites/blastr/files/styles/blog_post_in_content_image/public/PicardRikerTNGfacepalm1_1.jpg?itok=h6_Lcsaq");
        int i = 0;
        insertthing(db, thing0);
        insertTag(db, thing0);
        insertthing(db, thing1);
        insertTag(db, thing1);
        insertthing(db, thing2);
        insertTag(db, thing2);


    }

    /**
     * Should return the entire "Everythings" in the entire db... hopefully
     * @return cursor
     */

    public Cursor getEverything(){
        SQLiteDatabase db = this.getReadableDatabase();
//        LinkedList<Everything> list = new LinkedList<>();
        Cursor cursor = db.query(everythingTable.TABLE_NAME, // a. table
                new String[]{everythingTable._ID, everythingTable.COLUMN_CATEGORY_ID, everythingTable.COLUMN_EVERYTHING, everythingTable.COLUMN_RATINGS, everythingTable.COLUMN_REVIEW, everythingTable.COLUMN_TAGSTHING, everythingTable.COLUMN_PICTURE}, // b. column names
                null, // c. selections
                null, // d. selections args
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit
//        cursor.moveToFirst();
//        while(!cursor.isAfterLast()){
//            int tagid = cursor.getInt(cursor.getColumnIndex(everythingTable.COLUMN_TAGSTHING));
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
    public Cursor getEverythingByID(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        String sid = String.valueOf(id);
//        LinkedList<Everything> list = new LinkedList<>();
        Cursor cursor = db.query(everythingTable.TABLE_NAME, // a. table
                new String[]{everythingTable.COLUMN_CATEGORY_ID, everythingTable.COLUMN_EVERYTHING, everythingTable.COLUMN_RATINGS, everythingTable.COLUMN_REVIEW, everythingTable.COLUMN_TAGSTHING, everythingTable.COLUMN_PICTURE}, // b. column names
                everythingTable._ID + " = ?", // c. selections
                new String[]{sid}, // d. selections args
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit
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
    /*
    Returns a sorted thing based on inputted column
     */
    public Cursor getEverythingSort(String column, boolean asc){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor;
        if (asc) {
            cursor = db.query(everythingTable.TABLE_NAME, // a. table
                    new String[]{everythingTable._ID, everythingTable.COLUMN_CATEGORY_ID, everythingTable.COLUMN_EVERYTHING, everythingTable.COLUMN_RATINGS, everythingTable.COLUMN_REVIEW, everythingTable.COLUMN_TAGSTHING, everythingTable.COLUMN_PICTURE}, // b. column names
                    null, // c. selections
                    null, // d. selections args
                    null, // e. group by
                    null, // f. having
                    column + " ASC", // g. order by
                    null); // h. limit
            asc = false;
        } else {
            cursor = db.query(everythingTable.TABLE_NAME, // a. table
                    new String[]{everythingTable._ID, everythingTable.COLUMN_CATEGORY_ID, everythingTable.COLUMN_EVERYTHING, everythingTable.COLUMN_RATINGS, everythingTable.COLUMN_REVIEW, everythingTable.COLUMN_TAGSTHING, everythingTable.COLUMN_PICTURE}, // b. column names
                    null, // c. selections
                    null, // d. selections args
                    null, // e. group by
                    null, // f. having
                    column + " DESC", // g. order by
                    null); // h. limit
            asc = true;
        }
        cursor.moveToFirst();
        return cursor;
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

    public int getLastIDET(){
        String query = "SELECT MAX("+ everythingTable._ID+") FROM " + everythingTable.TABLE_NAME;
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

        db.delete(TagTable.TABLE_NAME, selection2, selectionArgs2);
    }

    public Cursor getSpecificThing(String query, String choice){ //needs search entry as well as choice of which query to search
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        switch (choice) {
            case "everything":

                cursor = db.query(everythingTable.TABLE_NAME, // a. table
                        new String[]{everythingTable._ID, everythingTable.COLUMN_CATEGORY_ID, everythingTable.COLUMN_EVERYTHING, everythingTable.COLUMN_RATINGS, everythingTable.COLUMN_REVIEW, everythingTable.COLUMN_TAGSTHING, everythingTable.COLUMN_PICTURE}, // b. column names
                        everythingTable.COLUMN_EVERYTHING + " LIKE ?", // c. selections
                        new String[]{'%' + query + '%'}, // d. selections args
                        null, // e. group by
                        null, // f. having
                        null, // g. order by
                        null); // h. limit
                cursor.moveToFirst();
                break;

            case "tag":
                cursor = db.rawQuery("SELECT DISTINCT "+ TagTable.COLUMN_THING_ID + ", " + TagTable.COLUMN_TAG + " FROM " + TagTable.TABLE_NAME + " WHERE UPPER("+TagTable.COLUMN_TAG+") LIKE UPPER("+"'%" + query+"%')", null);
                if(cursor.getCount()==0){cursor = null; break;}
                ArrayList<String> tidlist = new ArrayList<>();
                tidlist.add(0, "");
                int x = 1;
                while(cursor.moveToNext()){
                    String s = String.valueOf(cursor.getInt(cursor.getColumnIndex(TagTable.COLUMN_THING_ID)));
                    if(!tidlist.get(x-1).equals(s)){
                        tidlist.add(x, s);
                        x++;
                    }
                }
                String[] tidnodupe = new String[tidlist.size()-1];
                for (int j = 1; j <= tidlist.size()-1; j++) {
                    tidnodupe[j-1] = tidlist.get(j);
                }

//                Set<String> tidlist = new HashSet<>();
//                if(cursor.getCount()==0){cursor=null; break;}
//                cursor.moveToFirst();
//                while(cursor.moveToNext()) {
//                    tidlist.add(String.valueOf(cursor.getInt(cursor.getColumnIndex(TagTable.COLUMN_THING_ID))));
//                }
//                String[] tidnodupe = tidlist.toArray(new String[tidlist.size()]);
                cursor = db.query(everythingTable.TABLE_NAME, // a. table
                        new String[]{everythingTable._ID, everythingTable.COLUMN_CATEGORY_ID, everythingTable.COLUMN_EVERYTHING, everythingTable.COLUMN_RATINGS, everythingTable.COLUMN_REVIEW, everythingTable.COLUMN_TAGSTHING, everythingTable.COLUMN_PICTURE}, // b. column names
                        everythingTable._ID + " = ?", // c. selections
                        tidnodupe, // d. selections args
                        null, // e. group by
                        null, // f. having
                        null, // g. order by
                        null); // h. limit
                cursor.moveToFirst();
                break;

            case "cat":
                cursor = db.rawQuery("SELECT DISTINCT "+ CategoryTable._ID + " FROM " + CategoryTable.TABLE_NAME + " WHERE UPPER("+CategoryTable.COLUMN_CATEGORY+") LIKE UPPER("+"'%" + query+"%')", null);
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
                        new String[]{everythingTable._ID, everythingTable.COLUMN_CATEGORY_ID, everythingTable.COLUMN_EVERYTHING, everythingTable.COLUMN_RATINGS, everythingTable.COLUMN_REVIEW, everythingTable.COLUMN_TAGSTHING, everythingTable.COLUMN_PICTURE}, // b. column names
                        everythingTable.COLUMN_CATEGORY_ID + " = ?", // c. selections
                        cidargs, // d. selections args
                        null, // e. group by
                        null, // f. having
                        null, // g. order by
                        null); // h. limit
                cursor.moveToFirst();
                break;
        }

        return cursor;
    }
}
