package com.example.asus.songrecognition.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import java.util.ArrayList;

public class HistoryDAOImpl implements HistoryDAO {

    SQLiteHelper helper;

    public HistoryDAOImpl(Context context){
        helper = new SQLiteHelper(context);
    }

    @Override
    public boolean insert(History history) {
        SQLiteDatabase db = helper.getWritableDatabase();
        try {
            ContentValues cv = new ContentValues();
            cv.put(helper.COL_2,history.getSongTitle());
            cv.put(helper.COL_3,history.getDate());
            db.insert(helper.TABLE_NAME,null,cv);
            return true;
        }catch (Exception e){
            return false;
        }finally {
            db.close();
        }
    }

    @Override
    public ArrayList<History> read() {
        SQLiteDatabase db = helper.getReadableDatabase();
        try {
            ArrayList<History> dataList = new ArrayList<>();

            Cursor cursor = db.rawQuery("SELECT * FROM "+helper.TABLE_NAME,null);

            cursor.moveToLast();
            do{
                int index;
                History history = new History();
                index = cursor.getColumnIndexOrThrow(helper.COL_1);
                history.setId(cursor.getInt(index));
                index = cursor.getColumnIndexOrThrow(helper.COL_2);
                history.setSongTitle(cursor.getString(index));
                index =  cursor.getColumnIndexOrThrow(helper.COL_3);
                history.setDate(cursor.getString(index));
                dataList.add(history);
            }while(cursor.moveToPrevious());

            return dataList;
        }catch (Exception e){
            return null;
        }finally {
            db.close();
        }
    }


    @Override
    public boolean delete(int[] id) {
        SQLiteDatabase db = helper.getWritableDatabase();
        try{
            for(int i=0; i<id.length ; i++){
                db.delete(helper.TABLE_NAME, helper.COL_1 +"= ?",new String[]{Integer.toString(id[0])});
            }
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
