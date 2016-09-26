package com.coursework.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.coursework.com.coursework.domain.Bird;
import com.coursework.com.coursework.domain.Event;
import com.coursework.com.coursework.domain.Report;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper{

    public static final String DATABASE_NAME = "ADMD.db";

    //Bird
    public static final String BIRD_TABLE = "bird";
    public static final String BIRD_ID = "id";
    public static final String BIRD_NAME = "bird_name";
    public static final String BIRD_LOCATION = "bird_location";
    public static final String BIRD_DATE = "bird_date";
    public static final String BIRD_TIME = "bird_time";
    public static final String BIRD_WATCHER_NAME = "bird_watcher_name";

    //Event

    public static final String EVENT_TABLE = "event";
    public static final String EVENT_ID = "id";
    public static final String EVENT_NAME ="name";
    public static final String EVENT_DESC="description";

    //Report
    public static final String REPORT_TABLE = "report";
    public static final String REPORT_ID = "id";
    public static final String REPORT_NAME ="name";
    public static final String REPORT_DESC="description";
    public static final String REPORT_BIRD_ID="birdId";

    public DBHelper(Context context){
        super(context, DATABASE_NAME, null, 1);
    }


    //Create SQLite
    @Override
    public void onCreate(SQLiteDatabase db) {
        //Create table bird
        db.execSQL("create table " + BIRD_TABLE + " ( " + BIRD_ID + " integer primary key autoincrement not null ," + BIRD_NAME + " text," + BIRD_LOCATION + " text," + BIRD_DATE + " text," + BIRD_TIME + " text," + BIRD_WATCHER_NAME + " text)");
        db.execSQL("create table " + EVENT_TABLE + " ( " + EVENT_ID + " integer primary key autoincrement not null ," + EVENT_NAME + " text," + EVENT_DESC + " text)");
        db.execSQL("create table " + REPORT_TABLE + " ( " + REPORT_ID + " integer primary key autoincrement not null ," + REPORT_NAME + " text," + REPORT_DESC + " text,"+REPORT_BIRD_ID +" text)");
        Log.d("Init database:", "Init two tables successfully");
    }

    // OnUpgrade SQLite
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXIST " +BIRD_TABLE);
        db.execSQL("DROP TABLE IF EXIST " +REPORT_TABLE);
        db.execSQL("DROP TABLE IF EXIST " +EVENT_TABLE);
        onCreate(db);
    }

    //Insert Bird
    public boolean insertBird(Bird bird){
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(BIRD_NAME,bird.getBirdName());
            contentValues.put(BIRD_LOCATION,bird.getLocation());
            contentValues.put(BIRD_DATE,bird.getDate());
            contentValues.put(BIRD_TIME,bird.getTime());
            contentValues.put(BIRD_WATCHER_NAME,bird.getWatcherName());
            db.insert(BIRD_TABLE,null,contentValues);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    //Insert Event
    public boolean insertEvent(Event event){
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(EVENT_NAME,event.getName());
            contentValues.put(EVENT_DESC,event.getDescription());
            db.insert(EVENT_TABLE,null,contentValues);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    //Insert Event
    public boolean insertReport(Report report){
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(REPORT_NAME,report.getName());
            contentValues.put(REPORT_DESC,report.getDescription());
            contentValues.put(REPORT_BIRD_ID,report.getBirdId());
            db.insert(REPORT_TABLE,null,contentValues);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateBird(Bird bird){
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(BIRD_NAME,bird.getBirdName());
            contentValues.put(BIRD_LOCATION,bird.getLocation());
            contentValues.put(BIRD_DATE,bird.getDate());
            contentValues.put(BIRD_TIME,bird.getTime());
            contentValues.put(BIRD_WATCHER_NAME, bird.getWatcherName());
            db.update(BIRD_TABLE, contentValues, BIRD_ID + " = ? ", new String[]{Integer.toString(bird.getId())});
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public Integer deleteBird(Integer id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(BIRD_TABLE,BIRD_ID + " = ? ",new String[]{Integer.toString(id)});
    }


    public Bird getBirdData(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(" select * from " + BIRD_TABLE + " where " + BIRD_ID + " = " + id + "", null);
        cursor.moveToFirst();
        Bird bird = new Bird();
        bird.setId(cursor.getInt(cursor.getColumnIndex(BIRD_ID)));
        bird.setBirdName(cursor.getString(cursor.getColumnIndex(BIRD_NAME)));
        bird.setLocation(cursor.getString(cursor.getColumnIndex(BIRD_LOCATION)));
        bird.setDate(cursor.getString(cursor.getColumnIndex(BIRD_DATE)));
        bird.setTime(cursor.getString(cursor.getColumnIndex(BIRD_TIME)));
        bird.setWatcherName(cursor.getString(cursor.getColumnIndex(BIRD_WATCHER_NAME)));
        return bird;
    }

    public List<Report> getAllReports(String birdId){
        List<Report> listReports = new ArrayList<Report>();
        // Select all bird query
        String query = "SELECT * FROM " + REPORT_TABLE + " WHERE " +REPORT_BIRD_ID + " ='" +birdId+"'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                Report report = new Report();
                report.setId(cursor.getInt(cursor.getColumnIndex(REPORT_ID)));
                report.setName(cursor.getString(cursor.getColumnIndex(REPORT_NAME)));
                report.setDescription(cursor.getString(cursor.getColumnIndex(REPORT_DESC)));
                report.setBirdId(cursor.getString(cursor.getColumnIndex(REPORT_BIRD_ID)));
                // Adding contact to list
                listReports.add(report);
            } while (cursor.moveToNext());
        }
        return listReports;
    }

    public Boolean checkDuplicate(String birdName){
        List<Bird> listBirds = new ArrayList<Bird>();
        // Select all bird query
        String query = "SELECT * FROM " + BIRD_TABLE + " WHERE " + BIRD_NAME + " ='" +birdName +"'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                Bird bird = new Bird();
                bird.setId(cursor.getInt(cursor.getColumnIndex(BIRD_ID)));
                bird.setBirdName(cursor.getString(cursor.getColumnIndex(BIRD_NAME)));
                bird.setLocation(cursor.getString(cursor.getColumnIndex(BIRD_LOCATION)));
                bird.setDate(cursor.getString(cursor.getColumnIndex(BIRD_DATE)));
                bird.setTime(cursor.getString(cursor.getColumnIndex(BIRD_TIME)));
                bird.setWatcherName(cursor.getString(cursor.getColumnIndex(BIRD_WATCHER_NAME)));
                // Adding contact to list
                listBirds.add(bird);
            } while (cursor.moveToNext());
        }
        if(listBirds.size() > 0){
            return true;
        }else
            return  false;
    }

    public List<Bird> searchByName(String name){
        List<Bird> listBirds = new ArrayList<Bird>();
        // Select all bird query
        if(name.trim().isEmpty()){
            listBirds = getAllBirds();
        }
        else {
            String query = "SELECT * FROM " + BIRD_TABLE + " WHERE " + BIRD_NAME + " LIKE '%" + name + "%'";
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                do {
                    Bird bird = new Bird();
                    bird.setId(cursor.getInt(cursor.getColumnIndex(BIRD_ID)));
                    bird.setBirdName(cursor.getString(cursor.getColumnIndex(BIRD_NAME)));
                    bird.setLocation(cursor.getString(cursor.getColumnIndex(BIRD_LOCATION)));
                    bird.setDate(cursor.getString(cursor.getColumnIndex(BIRD_DATE)));
                    bird.setTime(cursor.getString(cursor.getColumnIndex(BIRD_TIME)));
                    bird.setWatcherName(cursor.getString(cursor.getColumnIndex(BIRD_WATCHER_NAME)));
                    // Adding contact to list
                    listBirds.add(bird);
                } while (cursor.moveToNext());
            }
        }
        return listBirds;
    }

    public List<Bird> getAllBirds(){
        List<Bird> listBirds = new ArrayList<Bird>();
        // Select all bird query
        String query = "SELECT * FROM " + BIRD_TABLE;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                Bird bird = new Bird();
                bird.setId(cursor.getInt(cursor.getColumnIndex(BIRD_ID)));
                bird.setBirdName(cursor.getString(cursor.getColumnIndex(BIRD_NAME)));
                bird.setLocation(cursor.getString(cursor.getColumnIndex(BIRD_LOCATION)));
                bird.setDate(cursor.getString(cursor.getColumnIndex(BIRD_DATE)));
                bird.setTime(cursor.getString(cursor.getColumnIndex(BIRD_TIME)));
                bird.setWatcherName(cursor.getString(cursor.getColumnIndex(BIRD_WATCHER_NAME)));
                // Adding report to list
                listBirds.add(bird);
            } while (cursor.moveToNext());
        }
        return listBirds;
    }

    public List<Event> getAllEvents(){
        List<Event> listEvents = new ArrayList<Event>();
        // Select all bird query
        String query = "SELECT * FROM " + EVENT_TABLE;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                Event event = new Event();
                event.setId(cursor.getInt(cursor.getColumnIndex(EVENT_ID)));
                event.setName(cursor.getString(cursor.getColumnIndex(EVENT_NAME)));
                event.setDescription(cursor.getString(cursor.getColumnIndex(EVENT_DESC)));
                // Adding event to list
                listEvents.add(event);
            } while (cursor.moveToNext());
        }
        return listEvents;
    }

}
