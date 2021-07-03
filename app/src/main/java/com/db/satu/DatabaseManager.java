package com.db.satu;

import  java.util.ArrayList;
import java.util.Objects;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import  android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseManager {
    private static String Row_ID = "_id";
    private static String Row_NAMA  = "_nama";
    private static String Row_HOBI = "_hobi";

    private static String NAMA_DB = "DatabaseAndroidSatu";
    private static String NAMA_TABEL = "hobiku";
    private static int DB_VERSION = 1;

    private static final String CREATE_TABLE = "create table "+NAMA_TABEL+" ("+Row_ID+" integer PRIMARY KEY autoincrement,"+Row_NAMA+" text,"+Row_HOBI+" text) ";

    private final Context context;
    private DatabaseOpenHelper dbHelper;
    private SQLiteDatabase db;

    public DatabaseManager(Context ctx){
        this.context = ctx;
        dbHelper = new DatabaseOpenHelper(context);
        db = dbHelper.getWritableDatabase();
    }
    private static class DatabaseOpenHelper extends SQLiteOpenHelper{
        public DatabaseOpenHelper(Context context){
            super(context, NAMA_DB,null,DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS "+NAMA_DB);
            onCreate(db);
        }
    }

    public void close(){
        dbHelper.close();
    }

    public void  addRow(String nama, String hobi){
        ContentValues values = new ContentValues();
        values.put(Row_NAMA, nama);
        values.put(Row_HOBI, hobi);

        try {
            db.insert(NAMA_TABEL,null,values);
        } catch (Exception e){
            Log.e("DB ERROR", e.toString());
            e.printStackTrace();
        }
    }

    public ArrayList<ArrayList<Object>> ambilSemuaBaris() {
        ArrayList<ArrayList<Object>> dataArray = new ArrayList<ArrayList<Object>>();
        Cursor cur;

        try {
         cur = db.query (NAMA_TABEL, new String[] {Row_ID,Row_NAMA,Row_HOBI},null,null,null,null,null);
         cur.moveToFirst();

         if (!cur.isAfterLast()){
             do{
                 ArrayList<Object> dataList = new ArrayList<Object>();
                 dataList.add(cur.getLong(0));
                 dataList.add(cur.getString(1));
                 dataList.add(cur.getString(2));
                 dataArray.add(dataList);
             } while (cur.moveToNext());
         }
        } catch (Exception e){
            e.printStackTrace();
            Log.e("DEBE ERROR",e.toString());
        } return dataArray;
    }
}
