package com.example.sungwon.lifeuniverseeverything;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import java.util.LinkedList;

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
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES_TODOLIST);
        db.execSQL(SQL_DELETE_ENTRIES_CATEGORY);
        onCreate(db);
    }

    /**
     * Inner class which represents the columns in to-do mTask table.
     */
    public static abstract class TodoTable implements BaseColumns {
        public static final String TABLE_NAME = "todotable";
        public static final String COLUMN_TODO = "mTask";
        public static final String COLUMN_CREATED = "date_created";
        public static final String COLUMN_DUE = "due_date";
        public static final String COLUMN_CATEGORY_ID = "category_id";
    }

    /**
     * Inner class which represents the columns in mCategory_id table.
     */
    public static abstract class CategoryTable implements BaseColumns {
        public static final String TABLE_NAME = "categorytable";
        public static final String COLUMN_CATEGORY = "category_name";
    }

    /**
     * SQL command to create to-do table.
     */
    private static final String SQL_CREATE_ENTRIES_EVERYTHING = "CREATE TABLE " +
            TodoTable.TABLE_NAME + " (" +
            TodoTable._ID + " INTEGER PRIMARY KEY," +
            TodoTable.COLUMN_TODO + " TEXT," +
            TodoTable.COLUMN_CREATED + " DATE," +
            TodoTable.COLUMN_DUE + " DATE," +
            TodoTable.COLUMN_CATEGORY_ID + " INTEGER" + ")";

    /**
     * SQL command to delete our employee table.
     */
    private static final String SQL_DELETE_ENTRIES_TODOLIST = "DROP TABLE IF EXISTS " +
            TodoTable.TABLE_NAME;

    /**
     * SQL command to create our company table.
     */
    private static final String SQL_CREATE_ENTRIES_CATEGORY = "CREATE TABLE " +
            CategoryTable.TABLE_NAME + " (" +
            CategoryTable._ID + " INTEGER PRIMARY KEY," +
            CategoryTable.COLUMN_CATEGORY + " TEXT" + ")";

    /**
     * SQL command to delete our company table.
     */
    private static final String SQL_DELETE_ENTRIES_CATEGORY = "DROP TABLE IF EXISTS " +
            CategoryTable.TABLE_NAME;

    /**
     * Insert a mTask into the database.
     * @param
     */
    public void insertTodo(ToDoDoDa todo, int year, int month, int day) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        todo.setDueDateNumbah(year, month, day);
        values.put(TodoTable.COLUMN_TODO, todo.getmTask());
        values.put(TodoTable.COLUMN_CREATED, todo.getDateNumbah());
        values.put(TodoTable.COLUMN_DUE, todo.getDueDateNumbah());
        values.put(TodoTable.COLUMN_CATEGORY_ID, todo.getmCategory_id());
        db.insertOrThrow(TodoTable.TABLE_NAME, null, values);
    }

    public void insertTodo(ToDoDoDa todo) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(TodoTable.COLUMN_TODO, todo.getmTask());
        values.put(TodoTable.COLUMN_CREATED, todo.getDateNumbah());
        values.put(TodoTable.COLUMN_DUE, todo.getDueDateNumbah());
        values.put(TodoTable.COLUMN_CATEGORY_ID, todo.getmCategory_id());
        db.insertOrThrow(TodoTable.TABLE_NAME, null, values);
    }

    /**
     * Insert a company into the database.
     * @param
     */
    public void insertCategory(ToDoDoDa todo) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CategoryTable.COLUMN_CATEGORY, todo.getmCategory());
        db.insertOrThrow(CategoryTable.TABLE_NAME, null, values);
    }

    /**
     * Add some default data to the database.
     */
    public void addDataToDb() {
        ToDoDoDa one = new ToDoDoDa("Dongo", 0 , "California");
        ToDoDoDa two = new ToDoDoDa("Bongo", 1 , "Nevada");
        ToDoDoDa three = new ToDoDoDa("Dongo", 0, "Banga");
        ToDoDoDa four = new ToDoDoDa("Bongo", 1 , "Dododo");
        ToDoDoDa phive = new ToDoDoDa("Dongo", 0, "Doodoodoo");


        insertCategory(one);
        insertCategory(two);

        insertTodo(one, 2017, 10, 8);
        insertTodo(two, 2018, 1, 18);
        insertTodo(three, 2017, 3, 19);
        insertTodo(four, 2016, 10, 28);
        insertTodo(phive, 2016, 12, 26);
    }

    /**
     * @return List of tasks within one category.
     */
    public LinkedList<ToDoDoDa> getRightTasks(String category) {
        SQLiteDatabase db = getReadableDatabase();
        LinkedList<ToDoDoDa> goodlist = new LinkedList<>();
        String query = "SELECT "+ TodoTable.COLUMN_TODO + ", " + TodoTable.COLUMN_CREATED + ", " + TodoTable.COLUMN_DUE + ", " + TodoTable.COLUMN_CATEGORY_ID + " FROM "+ TodoTable.TABLE_NAME + " INNER JOIN " + CategoryTable.TABLE_NAME + " ON " + TodoTable.TABLE_NAME + "." + TodoTable.COLUMN_CATEGORY_ID + " = " + CategoryTable.TABLE_NAME + "." + CategoryTable._ID + " WHERE " + CategoryTable.COLUMN_CATEGORY + " Like ?";
        Cursor cursor = db.rawQuery(query, new String[]{category});
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            ToDoDoDa toDoDo = new ToDoDoDa(category,cursor.getInt(3),cursor.getString(0)); //get category from param, id and tododoo task from cursor
            toDoDo.setDueDateNumbah(cursor.getString(2));
            goodlist.add(toDoDo);
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return goodlist;
    }

    /**
     * TODO: Get category id
     * @return List of employee names.
     */
    public int getCategoryid(String category) {
        SQLiteDatabase db = getReadableDatabase();
        int goodlist = 0;
        String query = "SELECT "+ CategoryTable._ID + ", " + CategoryTable.COLUMN_CATEGORY + " FROM "+ CategoryTable.TABLE_NAME + " WHERE " + CategoryTable.COLUMN_CATEGORY + " like ?";
        Cursor cursor = db.rawQuery(query, new String[]{category});
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            goodlist = cursor.getInt(0);
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return goodlist;
    }

    /**
     * TODO: Get category id
     * @return List of employee names.
     */
    public LinkedList<String> getCategory() {
        SQLiteDatabase db = getReadableDatabase();
        LinkedList<String> goodlist = new LinkedList<>();
        String query = "SELECT "+ CategoryTable._ID + ", " + CategoryTable.COLUMN_CATEGORY + " FROM "+ CategoryTable.TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            goodlist.add(cursor.getString(1));
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return goodlist;
    }


}
