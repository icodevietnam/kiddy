package com.coursework.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.coursework.com.coursework.domain.Event;
import com.coursework.com.coursework.domain.iDiscovery;
import com.coursework.com.coursework.domain.Report;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper{

    public static final String DATABASE_NAME = "IDiscoveryCW.db";

    //IDISCOVERY
    public static final String IDISCOVERY_TABLE = "kiddy";
    public static final String IDISCOVERY_ID = "id";
    public static final String IDISCOVERY_ACTIVITY_NAME = "activityName";
    public static final String IDISCOVERY_LOCATION = "location";
    public static final String IDISCOVERY_DATE = "date";
    public static final String IDISCOVERY_TIME = "time";
    public static final String IDISCOVERY_REPORTER_NAME = "reporterName";

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
    public static final String REPORT_IDISCOVERY_ID="kiddyId";

    public DBHelper(Context context){
        super(context, DATABASE_NAME, null, 1);
    }


    //Create SQLite
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + IDISCOVERY_TABLE + " ( " + IDISCOVERY_ID + " integer primary key autoincrement not null ," + IDISCOVERY_ACTIVITY_NAME + " text," + IDISCOVERY_LOCATION + " text," + IDISCOVERY_DATE + " text," + IDISCOVERY_TIME + " text," + IDISCOVERY_REPORTER_NAME + " text)");
        db.execSQL("create table " + EVENT_TABLE + " ( " + EVENT_ID + " integer primary key autoincrement not null ," + EVENT_NAME + " text," + EVENT_DESC + " text)");
        db.execSQL("create table " + REPORT_TABLE + " ( " + REPORT_ID + " integer primary key autoincrement not null ," + REPORT_NAME + " text," + REPORT_DESC + " text,"+REPORT_IDISCOVERY_ID +" text)");
        Log.d("Init database:", "Init two tables successfully");
    }

    // OnUpgrade SQLite
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXIST " +IDISCOVERY_TABLE);
        db.execSQL("DROP TABLE IF EXIST " +REPORT_TABLE);
        db.execSQL("DROP TABLE IF EXIST " +EVENT_TABLE);
        onCreate(db);
    }

    public boolean insertIDiscovery(iDiscovery iDiscovery){
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(IDISCOVERY_ACTIVITY_NAME, iDiscovery.getActivityName());
            contentValues.put(IDISCOVERY_LOCATION, iDiscovery.getLocation());
            contentValues.put(IDISCOVERY_DATE, iDiscovery.getDate());
            contentValues.put(IDISCOVERY_TIME, iDiscovery.getTime());
            contentValues.put(IDISCOVERY_REPORTER_NAME, iDiscovery.getReporterName());
            db.insert(IDISCOVERY_TABLE,null,contentValues);
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
            contentValues.put(REPORT_IDISCOVERY_ID,report.getIDiscoveryIdId());
            db.insert(REPORT_TABLE,null,contentValues);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateIDiscovery(iDiscovery iDiscovery){
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(IDISCOVERY_ACTIVITY_NAME, iDiscovery.getActivityName());
            contentValues.put(IDISCOVERY_LOCATION, iDiscovery.getLocation());
            contentValues.put(IDISCOVERY_DATE, iDiscovery.getDate());
            contentValues.put(IDISCOVERY_TIME, iDiscovery.getTime());
            contentValues.put(IDISCOVERY_ACTIVITY_NAME, iDiscovery.getReporterName());
            db.update(IDISCOVERY_TABLE, contentValues, IDISCOVERY_ID + " = ? ", new String[]{Integer.toString(iDiscovery.getId())});
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public Integer deleteIDiscovery(Integer id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(IDISCOVERY_TABLE,IDISCOVERY_ID + " = ? ",new String[]{Integer.toString(id)});
    }


    public iDiscovery getIDiscoveryData(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(" select * from " + IDISCOVERY_TABLE + " where " + IDISCOVERY_ID + " = " + id + "", null);
        cursor.moveToFirst();
        iDiscovery iDiscovery = new iDiscovery();
        iDiscovery.setId(cursor.getInt(cursor.getColumnIndex(IDISCOVERY_ID)));
        iDiscovery.setActivityName(cursor.getString(cursor.getColumnIndex(IDISCOVERY_ACTIVITY_NAME)));
        iDiscovery.setLocation(cursor.getString(cursor.getColumnIndex(IDISCOVERY_LOCATION)));
        iDiscovery.setDate(cursor.getString(cursor.getColumnIndex(IDISCOVERY_DATE)));
        iDiscovery.setTime(cursor.getString(cursor.getColumnIndex(IDISCOVERY_TIME)));
        iDiscovery.setReporterName(cursor.getString(cursor.getColumnIndex(IDISCOVERY_REPORTER_NAME)));
        return iDiscovery;
    }

    public List<Report> getAllReports(String kiddyId){
        List<Report> listReports = new ArrayList<Report>();
        String query = "SELECT * FROM " + REPORT_TABLE + " WHERE " +REPORT_IDISCOVERY_ID + " ='" +kiddyId+"'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                Report report = new Report();
                report.setId(cursor.getInt(cursor.getColumnIndex(REPORT_ID)));
                report.setName(cursor.getString(cursor.getColumnIndex(REPORT_NAME)));
                report.setDescription(cursor.getString(cursor.getColumnIndex(REPORT_DESC)));
                report.setIDiscoveryId(cursor.getString(cursor.getColumnIndex(REPORT_IDISCOVERY_ID)));
                // Adding contact to list
                listReports.add(report);
            } while (cursor.moveToNext());
        }
        return listReports;
    }

    public Boolean checkIDiscoveryDuplicate(String activityName){
        List<iDiscovery> listKiddies = new ArrayList<iDiscovery>();
        String query = "SELECT * FROM " + IDISCOVERY_TABLE + " WHERE " + IDISCOVERY_ACTIVITY_NAME + " ='" +activityName +"'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                iDiscovery iDiscovery = new iDiscovery();
                iDiscovery.setId(cursor.getInt(cursor.getColumnIndex(IDISCOVERY_ID)));
                iDiscovery.setActivityName(cursor.getString(cursor.getColumnIndex(IDISCOVERY_ACTIVITY_NAME)));
                iDiscovery.setLocation(cursor.getString(cursor.getColumnIndex(IDISCOVERY_LOCATION)));
                iDiscovery.setDate(cursor.getString(cursor.getColumnIndex(IDISCOVERY_DATE)));
                iDiscovery.setTime(cursor.getString(cursor.getColumnIndex(IDISCOVERY_TIME)));
                iDiscovery.setReporterName(cursor.getString(cursor.getColumnIndex(IDISCOVERY_REPORTER_NAME)));
                // Adding contact to list
                listKiddies.add(iDiscovery);
            } while (cursor.moveToNext());
        }
        if(listKiddies.size() > 0){
            return true;
        }else
            return  false;
    }

    public List<iDiscovery> searchByName(String name){
        List<iDiscovery> listKiddies = new ArrayList<iDiscovery>();
        if(name.trim().isEmpty()){
            listKiddies = getAllKiddies();
        }
        else {
            String query = "SELECT * FROM " + IDISCOVERY_TABLE + " WHERE " + IDISCOVERY_ACTIVITY_NAME + " LIKE '%" + name + "%'";
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                do {
                    iDiscovery iDiscovery = new iDiscovery();
                    iDiscovery.setId(cursor.getInt(cursor.getColumnIndex(IDISCOVERY_ID)));
                    iDiscovery.setActivityName(cursor.getString(cursor.getColumnIndex(IDISCOVERY_ACTIVITY_NAME)));
                    iDiscovery.setLocation(cursor.getString(cursor.getColumnIndex(IDISCOVERY_LOCATION)));
                    iDiscovery.setDate(cursor.getString(cursor.getColumnIndex(IDISCOVERY_DATE)));
                    iDiscovery.setTime(cursor.getString(cursor.getColumnIndex(IDISCOVERY_TIME)));
                    iDiscovery.setReporterName(cursor.getString(cursor.getColumnIndex(IDISCOVERY_REPORTER_NAME)));
                    // Adding contact to list
                    listKiddies.add(iDiscovery);
                } while (cursor.moveToNext());
            }
        }
        return listKiddies;
    }

    public List<iDiscovery> getAllKiddies(){
        List<iDiscovery> listKiddies = new ArrayList<iDiscovery>();
        String query = "SELECT * FROM " + IDISCOVERY_TABLE;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                iDiscovery iDiscovery = new iDiscovery();
                iDiscovery.setId(cursor.getInt(cursor.getColumnIndex(IDISCOVERY_ID)));
                iDiscovery.setActivityName(cursor.getString(cursor.getColumnIndex(IDISCOVERY_ACTIVITY_NAME)));
                iDiscovery.setLocation(cursor.getString(cursor.getColumnIndex(IDISCOVERY_LOCATION)));
                iDiscovery.setDate(cursor.getString(cursor.getColumnIndex(IDISCOVERY_DATE)));
                iDiscovery.setTime(cursor.getString(cursor.getColumnIndex(IDISCOVERY_TIME)));
                iDiscovery.setReporterName(cursor.getString(cursor.getColumnIndex(IDISCOVERY_REPORTER_NAME)));
                // Adding report to list
                listKiddies.add(iDiscovery);
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
