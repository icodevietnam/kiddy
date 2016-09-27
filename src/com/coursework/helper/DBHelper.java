package com.coursework.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.coursework.com.coursework.domain.Event;
import com.coursework.com.coursework.domain.Kiddy;
import com.coursework.com.coursework.domain.Report;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper{

    public static final String DATABASE_NAME = "KiddyCW.db";

    //KIDDY
    public static final String KIDDY_TABLE = "kiddy";
    public static final String KIDDY_ID = "id";
    public static final String KIDDY_ACTIVITY_NAME = "activityName";
    public static final String KIDDY_LOCATION = "location";
    public static final String KIDDY_DATE = "date";
    public static final String KIDDY_TIME = "time";
    public static final String KIDDY_REPORTER_NAME = "reporterName";

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
    public static final String REPORT_KIDDY_ID="kiddyId";

    public DBHelper(Context context){
        super(context, DATABASE_NAME, null, 1);
    }


    //Create SQLite
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + KIDDY_TABLE + " ( " + KIDDY_ID + " integer primary key autoincrement not null ," + KIDDY_ACTIVITY_NAME + " text," + KIDDY_LOCATION + " text," + KIDDY_DATE + " text," + KIDDY_TIME + " text," + KIDDY_REPORTER_NAME + " text)");
        db.execSQL("create table " + EVENT_TABLE + " ( " + EVENT_ID + " integer primary key autoincrement not null ," + EVENT_NAME + " text," + EVENT_DESC + " text)");
        db.execSQL("create table " + REPORT_TABLE + " ( " + REPORT_ID + " integer primary key autoincrement not null ," + REPORT_NAME + " text," + REPORT_DESC + " text,"+REPORT_KIDDY_ID +" text)");
        Log.d("Init database:", "Init two tables successfully");
    }

    // OnUpgrade SQLite
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXIST " +KIDDY_TABLE);
        db.execSQL("DROP TABLE IF EXIST " +REPORT_TABLE);
        db.execSQL("DROP TABLE IF EXIST " +EVENT_TABLE);
        onCreate(db);
    }

    public boolean insertKiddy(Kiddy kiddy){
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(KIDDY_ACTIVITY_NAME,kiddy.getActivityName());
            contentValues.put(KIDDY_LOCATION,kiddy.getLocation());
            contentValues.put(KIDDY_DATE,kiddy.getDate());
            contentValues.put(KIDDY_TIME,kiddy.getTime());
            contentValues.put(KIDDY_REPORTER_NAME,kiddy.getReporterName());
            db.insert(KIDDY_TABLE,null,contentValues);
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
            contentValues.put(REPORT_KIDDY_ID,report.getKiddyId());
            db.insert(REPORT_TABLE,null,contentValues);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateKiddy(Kiddy kiddy){
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(KIDDY_ACTIVITY_NAME,kiddy.getActivityName());
            contentValues.put(KIDDY_LOCATION,kiddy.getLocation());
            contentValues.put(KIDDY_DATE,kiddy.getDate());
            contentValues.put(KIDDY_TIME,kiddy.getTime());
            contentValues.put(KIDDY_ACTIVITY_NAME, kiddy.getReporterName());
            db.update(KIDDY_TABLE, contentValues, KIDDY_ID + " = ? ", new String[]{Integer.toString(kiddy.getId())});
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public Integer deleteKiddy(Integer id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(KIDDY_TABLE,KIDDY_ID + " = ? ",new String[]{Integer.toString(id)});
    }


    public Kiddy getKiddyData(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(" select * from " + KIDDY_TABLE + " where " + KIDDY_ID + " = " + id + "", null);
        cursor.moveToFirst();
        Kiddy kiddy = new Kiddy();
        kiddy.setId(cursor.getInt(cursor.getColumnIndex(KIDDY_ID)));
        kiddy.setActivityName(cursor.getString(cursor.getColumnIndex(KIDDY_ACTIVITY_NAME)));
        kiddy.setLocation(cursor.getString(cursor.getColumnIndex(KIDDY_LOCATION)));
        kiddy.setDate(cursor.getString(cursor.getColumnIndex(KIDDY_DATE)));
        kiddy.setTime(cursor.getString(cursor.getColumnIndex(KIDDY_TIME)));
        kiddy.setReporterName(cursor.getString(cursor.getColumnIndex(KIDDY_REPORTER_NAME)));
        return kiddy;
    }

    public List<Report> getAllReports(String kiddyId){
        List<Report> listReports = new ArrayList<Report>();
        String query = "SELECT * FROM " + REPORT_TABLE + " WHERE " +REPORT_KIDDY_ID + " ='" +kiddyId+"'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                Report report = new Report();
                report.setId(cursor.getInt(cursor.getColumnIndex(REPORT_ID)));
                report.setName(cursor.getString(cursor.getColumnIndex(REPORT_NAME)));
                report.setDescription(cursor.getString(cursor.getColumnIndex(REPORT_DESC)));
                report.setKiddyId(cursor.getString(cursor.getColumnIndex(REPORT_KIDDY_ID)));
                // Adding contact to list
                listReports.add(report);
            } while (cursor.moveToNext());
        }
        return listReports;
    }

    public Boolean checkKiddyDuplicate(String activityName){
        List<Kiddy> listKiddies = new ArrayList<Kiddy>();
        String query = "SELECT * FROM " + KIDDY_TABLE + " WHERE " + KIDDY_ACTIVITY_NAME + " ='" +activityName +"'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                Kiddy kiddy = new Kiddy();
                kiddy.setId(cursor.getInt(cursor.getColumnIndex(KIDDY_ID)));
                kiddy.setActivityName(cursor.getString(cursor.getColumnIndex(KIDDY_ACTIVITY_NAME)));
                kiddy.setLocation(cursor.getString(cursor.getColumnIndex(KIDDY_LOCATION)));
                kiddy.setDate(cursor.getString(cursor.getColumnIndex(KIDDY_DATE)));
                kiddy.setTime(cursor.getString(cursor.getColumnIndex(KIDDY_TIME)));
                kiddy.setReporterName(cursor.getString(cursor.getColumnIndex(KIDDY_REPORTER_NAME)));
                // Adding contact to list
                listKiddies.add(kiddy);
            } while (cursor.moveToNext());
        }
        if(listKiddies.size() > 0){
            return true;
        }else
            return  false;
    }

    public List<Kiddy> searchByName(String name){
        List<Kiddy> listKiddies = new ArrayList<Kiddy>();
        if(name.trim().isEmpty()){
            listKiddies = getAllKiddies();
        }
        else {
            String query = "SELECT * FROM " + KIDDY_TABLE + " WHERE " + KIDDY_ACTIVITY_NAME + " LIKE '%" + name + "%'";
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                do {
                    Kiddy kiddy = new Kiddy();
                    kiddy.setId(cursor.getInt(cursor.getColumnIndex(KIDDY_ID)));
                    kiddy.setActivityName(cursor.getString(cursor.getColumnIndex(KIDDY_ACTIVITY_NAME)));
                    kiddy.setLocation(cursor.getString(cursor.getColumnIndex(KIDDY_LOCATION)));
                    kiddy.setDate(cursor.getString(cursor.getColumnIndex(KIDDY_DATE)));
                    kiddy.setTime(cursor.getString(cursor.getColumnIndex(KIDDY_TIME)));
                    kiddy.setReporterName(cursor.getString(cursor.getColumnIndex(KIDDY_REPORTER_NAME)));
                    // Adding contact to list
                    listKiddies.add(kiddy);
                } while (cursor.moveToNext());
            }
        }
        return listKiddies;
    }

    public List<Kiddy> getAllKiddies(){
        List<Kiddy> listKiddies = new ArrayList<Kiddy>();
        String query = "SELECT * FROM " + KIDDY_TABLE;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                Kiddy kiddy = new Kiddy();
                kiddy.setId(cursor.getInt(cursor.getColumnIndex(KIDDY_ID)));
                kiddy.setActivityName(cursor.getString(cursor.getColumnIndex(KIDDY_ACTIVITY_NAME)));
                kiddy.setLocation(cursor.getString(cursor.getColumnIndex(KIDDY_LOCATION)));
                kiddy.setDate(cursor.getString(cursor.getColumnIndex(KIDDY_DATE)));
                kiddy.setTime(cursor.getString(cursor.getColumnIndex(KIDDY_TIME)));
                kiddy.setReporterName(cursor.getString(cursor.getColumnIndex(KIDDY_REPORTER_NAME)));
                // Adding report to list
                listKiddies.add(kiddy);
            } while (cursor.moveToNext());
        }
        return listKiddies;
    }

    public List<Event> getAllEvents(){
        List<Event> listEvents = new ArrayList<Event>();
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
