package edu.monash.fit3027.eventappfinal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.HashMap;
import java.util.LinkedHashMap;


public class DatabaseHelper extends SQLiteOpenHelper {
    //SET DATABASE PROPERTIES
    public static final String DATABASE_NAME = "EventsDB";
    public static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Event.CREATE_STATEMENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ Event.CREATE_STATEMENT);
        onCreate(db);
    }

    public void AddEvent(Event event){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Event.COLUMN_NAME, event.getName());
        values.put(Event.COLUMN_DESCRIPTION, event.getDescription());
        values.put(Event.COLUMN_STARTDATE, event.getStartDate());
        values.put(Event.COLUMN_ENDDATE, event.getEndDate());
        values.put(Event.COLUMN_STARTTIME, event.getStartTime());
        values.put(Event.COLUMN_ENDTIME, event.getEndTime());
        values.put(Event.COLUMN_PRICE, event.getPrice());
        values.put(Event.COLUMN_TYPE, event.getType());
        values.put(Event.COLUMN_LOCATION, event.getLocation());
        db.insert(Event.TABLE_NAME, null, values);
        db.close();
    }

    public HashMap<Long, Event> GetAllEvents(){
        HashMap<Long, Event> events = new LinkedHashMap<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Event.TABLE_NAME, null);

        //Add each person to the hashmap
        if(cursor.moveToFirst()){
            do {
                Event event = new Event(cursor.getLong(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getString(7),
                        cursor.getString(8),
                        cursor.getString(9));
                events.put(event.getId(), event);
            }while(cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return events;
    }

    public void RemoveEvent(Event event){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Event.TABLE_NAME, Event.COLUMN_ID + " = ?",
                new String[] {String.valueOf(event.getId())});
    }

    public void CreateDefaultEvents(){
        AddEvent(new Event(0, "Night Market", "All the food", "06/06/2017", "16/07/2017", "18:00", "22:00", "15-50", "Food ","Melbourne CBD"));
    }


}
