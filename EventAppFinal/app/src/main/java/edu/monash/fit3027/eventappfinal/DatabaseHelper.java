package edu.monash.fit3027.eventappfinal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;


public class DatabaseHelper extends SQLiteOpenHelper {
    //SET DATABASE PROPERTIES
    public static final String DATABASE_NAME = "EventsDB";
    public static final int DATABASE_VERSION = 1;
    public HashMap <Long, Event> favourites = new HashMap <>();

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Event.CREATE_STATEMENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ Event.TABLE_NAME);
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
        values.put(Event.COLUMN_LAT, event.getLatitude());
        values.put(Event.COLUMN_LONG, event.getLongitude());
        values.put(Event.COLUMN_IMAGE, event.getImage());
        db.insert(Event.TABLE_NAME, null, values);
        db.close();
    }

    public void AddEventToFavourites(Event event){
        favourites.put(event.getId(), event);
    }

    public HashMap<Long, Event> getAllFavourites(){
        return favourites;
    }



    public ArrayList<String> getColumnData(String colmun){
        ArrayList<String> array = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + Event.TABLE_NAME, null);

        if(cursor.moveToFirst()){
            do{
                String NAME = cursor.getString(1);
                String DESCRIPTION = cursor.getString(2);
                String STARTDATE = cursor.getString(3);
                String ENDDATE = cursor.getString(4);
                String STARTTIME = cursor.getString(5);
                String ENDTIME = cursor.getString(6);
                String PRICE = cursor.getString(7);
                String TYPE = cursor.getString(8);
                String LOCATION = cursor.getString(9);
                String LAT = cursor.getString(10);
                String LONG = cursor.getString(11);
                String IMAGE = cursor.getString(12);
                if (colmun.equals("NAME")){
                    array.add(NAME);
                }
                else if (colmun.equals("DESCRIPTION")){
                    array.add(DESCRIPTION);
                }
                else if (colmun.equals("STARTDATE")){
                    array.add(STARTDATE);
                }
                else if (colmun.equals("ENDDATE")){
                    array.add(ENDDATE);
                }
                else if (colmun.equals("STARTTIME")){
                    array.add(STARTTIME);
                }
                else if (colmun.equals("ENDTIME")){
                    array.add(ENDTIME);
                }
                else if (colmun.equals("PRICE")){
                    array.add(PRICE);
                }
                else if (colmun.equals("TYPE")){
                    array.add(TYPE);
                }
                else if (colmun.equals("LOCATION")){
                    array.add(LOCATION);
                }
                else if (colmun.equals("LAT")){
                    array.add(LAT);
                }
                else if (colmun.equals("LONG")){
                    array.add(LONG);
                }
                else if(colmun.equals("IMAGE")){
                    array.add(IMAGE);
                }
                else{
                    array.add("No result");
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return array;

    }




    public  HashMap<Long, Event> getSearchedEvents(String location, String budget, String type){
        HashMap<Long, Event> events = new LinkedHashMap<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Event.TABLE_NAME, null);



        String [] partsString;
        partsString = budget.split(" ");
        String bounds = partsString[0];
        String[] boundsList;
        boundsList = bounds.split("-");

        int budgetLowerBound = Integer.parseInt(boundsList[0]);
        int budgetUpperBound = Integer.parseInt(boundsList[1]);


        //Add each person to the hashmap
        if(cursor.moveToFirst()){
            do {Event event = new Event(cursor.getLong(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getString(6),
                    cursor.getString(7),
                    cursor.getString(8),
                    cursor.getString(9),
                    cursor.getString(10),
                    cursor.getString(11),
                    cursor.getString(12));

                String [] eventBudget;
                eventBudget = cursor.getString(7).split(" ");
                String budgetBounds = partsString[0];
                String[] eventBoundsList;
                eventBoundsList = budgetBounds.split("-");

                int LowerBound = Integer.parseInt(eventBoundsList[0]);
                int UpperBound = Integer.parseInt(eventBoundsList[1]);

                if (cursor.getString(9).equals(location)){ //9
                    if (cursor.getString(8).equals(type)) { //8
                        if(Integer.parseInt(cursor.getString(7)) >= budgetLowerBound && Integer.parseInt(cursor.getString(7)) <= budgetUpperBound ){ //7
                            events.put(event.getId(), event);
                        }

                    }
                }

            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return events;
    }

    public HashMap<Long, Event> getAllEvents(){
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
                        cursor.getString(9),
                        cursor.getString(10),
                        cursor.getString(11),
                        cursor.getString(12));
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
        AddEvent(new Event(0, "Night Market", "All the food", "06/06/2017", "16/07/2017", "18:00", "22:00", "15-50", "Food ","Melbourne CBD", "-37.807578", "144.956928","http://www.qvm.com.au/wp-content/uploads/2016/06/QVM-WNM-2016-114-768x512.jpg"));
        AddEvent(new Event(1, "blah", "blah1desc", "blah2datestart", "blah3dateend", "blah4timestart", "blahtimeend", "blah-price", "blahType", "location", "lat", "lomg","https://www.star.com.au/sydney/sites/default/files/thumbnails/image/The%20Star%20-%20The%20Event%20Centre%20-%20Banquet%20setup%20view.JPG" ));
        AddEvent(new Event(2, "Testing", "this is great", "3/11/17", "4/11/17", "05:00", "10:00", "4", "stuff", "Melbourne CBD", "65", "56", "data:image/jpeg;base64,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"));
    }


}
