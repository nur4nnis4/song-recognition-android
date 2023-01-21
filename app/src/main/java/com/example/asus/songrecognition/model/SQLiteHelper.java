package com.example.asus.songrecognition.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class SQLiteHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "song_recognition.db";
    public static final String TABLE_NAME = "tb_history";
    public static final String COL_1 = "id";
    public static final String COL_2 = "title";
    public static final String COL_3 = "date";

    public SQLiteHelper(Context context){
        super(context,DATABASE_NAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTable = "CREATE TABLE "+ TABLE_NAME + " ("+
                COL_1 +" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                COL_2 +" TEXT, "+
                COL_3 +" TEXT)" ;
        sqLiteDatabase.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        if(newVersion>oldVersion){
            // Drop older table if exists
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            // Create table again
            onCreate(sqLiteDatabase);
        }
    }

}
