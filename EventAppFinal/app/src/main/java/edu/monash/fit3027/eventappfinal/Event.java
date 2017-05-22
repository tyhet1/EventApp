package edu.monash.fit3027.eventappfinal;


import android.os.Parcel;
import android.os.Parcelable;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Calendar;

/**
 * Created by Thamale on 04/05/2017.
 */

public class Event implements Parcelable {
    //DATABASE CONSTANTS//
    public static final String TABLE_NAME = "events";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_STARTDATE = "startDate";
    public static final String COLUMN_ENDDATE = "endDate";
    public static final String COLUMN_STARTTIME = "startTime";
    public static final String COLUMN_ENDTIME = "endTime";
    public static final String COLUMN_PRICE = "price";
    public static final String COLUMN_TYPE = "type";
    public static final String COLUMN_LOCATION = "location";

    //TABLE CREATE STATEMENT
    public static final String CREATE_STATEMENT = "CREATE TABLE "
            + TABLE_NAME + "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
            COLUMN_NAME + " TEXT NOT NULL," +
            COLUMN_DESCRIPTION + " TEXT NOT NULL," +
            COLUMN_STARTDATE + " TEXT NOT NULL," +
            COLUMN_ENDDATE + " TEXT NOT NULL," +
            COLUMN_STARTTIME + " TEXT NOT NULL," +
            COLUMN_ENDTIME + " TEXT NOT NULL," +
            COLUMN_PRICE + " INTEGER," +
            COLUMN_TYPE + " TEXT NOT NULL," +
            COLUMN_LOCATION + " TEXT NOT NULL" + ")";

    // VARIABLES //
    private long m_nID;
    private String m_sName;
    private String m_sDescription;
    private String m_dStartDate;
    private String m_dEndDate;
    private String m_dStartTime;
    private String m_dEndTime;
    private String m_nPrice;
    private String m_sType;
    private String m_sLocation;

    // DUMMY EVENT //
    public Event(){
        m_sName = "Night Market";
        m_sDescription = "All the food";
        m_dStartDate = "06/06/2017";
        m_dEndDate = "16/07/2017";
        m_dStartTime = "18:00";
        m_dEndTime = "22:00";
        m_nPrice = "15-50";
        m_sLocation = "Melbourne CBD";
    }

    protected Event(Parcel in){
        m_nID = in.readLong();
        m_sName = in.readString();
        m_sDescription = in.readString();
        m_dStartDate = in.readString();
        m_dEndDate = in.readString();
        m_dStartTime = in.readString();
        m_dEndTime = in.readString();
        m_nPrice = in.readString();
        m_sType = in.readString();
        m_sLocation = in.readString();

    }

    public static final Creator<Event> CREATOR = new Creator<Event>() {
        @Override
        public Event createFromParcel(Parcel source) {
            return new Event(source);
        }

        @Override
        public Event[] newArray(int size) {
            return new Event[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(m_nID);
        dest.writeString(m_sName);
        dest.writeString(m_sDescription);
        dest.writeString(m_dStartDate);
        dest.writeString(m_dEndDate);
        dest.writeString(m_dStartTime);
        dest.writeString(m_dEndTime);
        dest.writeString(m_nPrice);
        dest.writeString(m_sType);
        dest.writeString(m_sLocation);
    }



   // CONSTRUCTORS //
    public Event(long id, String name, String description, String startDate, String endDate, String startTime, String endTime, String price, String type, String location ){
        this.m_nID = id;
        this.m_sName = name;
        this.m_sDescription = description;
        this.m_dStartDate = startDate;
        this.m_dEndDate = endDate;
        this.m_dStartTime = startTime;
        this.m_dEndTime = endTime;
        this.m_nPrice = price;
        this.m_sType = type;
        this.m_sLocation = location;
    }

    // ACCESSORS & MUTATORS //
    public long getId(){ return m_nID; }
    public void setID(long id) { this.m_nID = id; }

    public String getName() { return m_sName; }
    public void setName(String name) { this.m_sName = name; }

    public String getDescription() { return m_sDescription; }
    public void setM_sDescription(String description) {this.m_sDescription = description; }

    public String getStartDate() {return m_dStartDate; }
    public void setStartDate(String dateTime){
        Date date = getDateFromString(dateTime);
        this.m_dStartDate = getStringFromDate(date);
    }

    public String getEndDate() {return m_dEndDate; }
    public void setEndDate(String dateTime){
        Date date = getDateFromString(dateTime);
        this.m_dEndDate = getStringFromDate(date);
    }

    public String getStartTime() {return m_dStartTime; }
    public void setStartTime(String dateTime){
        Date time = getTimeFromString(dateTime);
        this.m_dStartTime = getStringFromTime(time);
    }

    public String getEndTime() {return m_dEndTime; }
    public void setEndTime(String dateTime){
        Date time = getTimeFromString(dateTime);
        this.m_dEndTime = getStringFromTime(time);
    }


    public String getPrice() {return m_nPrice; }
    public void setPrice(String price){
        this.m_nPrice = price;
    }

    public String getType() {return m_sType; }
    public void setType(String type){
        this.m_sType = type;
    }

    public String getLocation(){return m_sLocation; }
    public void  setLocation(String location){
        this.m_sLocation = location;
    }

    public String getStringFromDate(Date date){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        return dateFormat.format(date);
    }

    // s_date in the format "2017-06-08 19:00:00"
    public Date getDateFromString(String s_date){
        String[] dateList;
        dateList = s_date.split(" ");
        String date = dateList[0];
        String[] currDate;
        currDate = date.split("-");

        Date myDate;
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DATE, Integer.parseInt(currDate[0]));
        cal.set(Calendar.MONTH, Integer.parseInt(currDate[1]));
        cal.set(Calendar.YEAR, Integer.parseInt(currDate[2]));
        myDate = cal.getTime();
        return myDate;

    }


    // s_date in the format "2017-06-08 19:00:00"
    public Date getTimeFromString(String s_date){
        String[] dateList;
        dateList = s_date.split(" ");
        String time = dateList[1];
        String[] currTime;
        currTime = time.split(":");

        Date myTime;
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(currTime[0]));
        cal.set(Calendar.MINUTE, Integer.parseInt(currTime[1]));
        cal.set(Calendar.SECOND, Integer.parseInt(currTime[2]));
        myTime = cal.getTime();
        return myTime;
    }

    public String getStringFromTime(Date time){
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH);
        return dateFormat.format(time);
    }
}
