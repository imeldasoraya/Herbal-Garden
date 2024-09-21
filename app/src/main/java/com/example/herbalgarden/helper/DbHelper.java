package com.example.herbalgarden.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

public class DbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "herbalgarden.db";
    public static final String TABLE_SQLITE = "herbal";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAMA = "nama";
    public static final String COLUMN_MANFAAT = "manfaat";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE = "CREATE TABLE " + TABLE_SQLITE + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAMA + " TEXT NOT NULL, " +
                COLUMN_MANFAAT + " TEXT NOT NULL)";
        db.execSQL(SQL_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        final String SQL_DROP = "DROP TABLE IF EXISTS " + TABLE_SQLITE;
        db.execSQL(SQL_DROP);
        onCreate(db);
    }

    public ArrayList<HashMap<String, String>> getAllData() {
        ArrayList<HashMap<String, String>> wordList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_SQLITE;
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<>();
                map.put(COLUMN_ID, cursor.getString(0));
                map.put(COLUMN_NAMA, cursor.getString(1));
                map.put(COLUMN_MANFAAT, cursor.getString(2));
                wordList.add(map);
            } while (cursor.moveToNext());
        }
        Log.e("SELECT SQLITE", "" + wordList);
        database.close();
        return wordList;
    }

    public boolean isIdExists(int id) {
        SQLiteDatabase database = this.getReadableDatabase();
        String selectQuery = "SELECT 1 FROM " + TABLE_SQLITE + " WHERE " + COLUMN_ID + " = ?";
        Cursor cursor = database.rawQuery(selectQuery, new String[]{String.valueOf(id)});
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        database.close();
        return exists;
    }

    public void insert(String nama, String manfaat) {
        SQLiteDatabase database = this.getWritableDatabase();
        String queryValues = "INSERT INTO " + TABLE_SQLITE + " (" + COLUMN_NAMA + ", " + COLUMN_MANFAAT + ") VALUES ('" + nama + "', '" + manfaat + "')";
        Log.e("insert sqlite", "" + queryValues);
        database.execSQL(queryValues);
        database.close();
    }

    public void update(int id, String nama, String manfaat) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAMA, nama);
        contentValues.put(COLUMN_MANFAAT, manfaat);

        int result = database.update(TABLE_SQLITE, contentValues, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});

        if (result > 0) {
            Log.e("update sqlite", "Update successful for ID: " + id);
        } else {
            Log.e("update sqlite", "Update failed for ID: " + id);
        }

        database.close();
    }

    public void delete(int id) {
        SQLiteDatabase database = this.getWritableDatabase();

        int result = database.delete(TABLE_SQLITE, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});

        if (result > 0) {
            Log.e("delete sqlite", "Delete successful for ID: " + id);
        } else {
            Log.e("delete sqlite", "Delete failed for ID: " + id);
        }

        database.close();
    }
}
