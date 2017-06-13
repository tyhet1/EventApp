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


    //create the database
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Event.CREATE_STATEMENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ Event.TABLE_NAME);
        onCreate(db);
    }

    //add a new object to the database
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

    // add an event object to arrayList of events that have been marked favourite
    public void AddEventToFavourites(Event event){
        favourites.put(event.getId(), event);
    }

    public HashMap<Long, Event> getAllFavourites(){
        return favourites;
    }


    //Get every entry in a particular coloum in the database
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



    // reutrn an arrayList of events that satisfy the given location, budget and type
    public  HashMap<Long, Event> getSearchedEvents(String location, String budget, String type){
        HashMap<Long, Event> events = new LinkedHashMap<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Event.TABLE_NAME, null);


        // slipt the budget to individual integers
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


    // get all the events in the data base
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
        AddEvent(new Event(0, "Night Market", "All the food", "06/06/2017", "16/07/2017", "18:00", "22:00", "15", "Food ","Melbourne CBD", "-37.807578", "144.956928","http://www.qvm.com.au/wp-content/uploads/2016/06/QVM-WNM-2016-114-768x512.jpg"));
        AddEvent(new Event(1, "Salsation Dance Fitness Class",
                "Join us for a fun SALSATION® Dance Fitness Class.\n" +
                        " \n" +
                        " SALSATION® is a dance workout that converts functional training protocols into innovative dance moves. Let the dancing do the talking",
                "02-05-2017",
                "20-06-2017",
                "17:30",
                "18:20",
                "4",
                "Fitness",
                "City Danse Studio, Sydney CBD, New South Wales",
                "-33.874", "151.2056",
                "https://cdn.eventfinda.com.au/uploads/events/transformed/546687-242333-14.jpg" ));
        AddEvent(new Event(2, "Bodhi and Ride – Ride Like a Royal", "Get your blood pumping as Bodhi & Ride turn up the heat at Kingpin Crown for a once-off, royal cycling experience", "14-06-2017", "14-06-2017", "18:00", "19:30", "0", "Fitness", "Kingpin Crown, Southbank, Victoria", "-37.8239", "144.9587", "https://scontent.cdninstagram.com/t51.2885-15/s640x640/e15/18950168_301868553572496_2574492427599478784_n.jpg"));
        AddEvent(new Event(3, "Food Festival", "More food", "06/06/2017", "16/07/2017", "15:00", "22:00", "10", "Food ","Melbourne CBD", "-37.846105", "144.991861","http://marketlane.com.au/wp/wp-content/uploads/2015/07/MG_7952-copy.jpg"));
    }


}
