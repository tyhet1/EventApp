package edu.monash.fit3027.eventappfinal;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Thamale on 12/05/2017.
 */
//https://www.androidtutorialpoint.com/material-design/android-cardview-tutorial

public class HomeFragment extends Fragment {

    public HomeFragment(){

    }

    public  static final int ADD_EVENT_REQUEST = 1;
    public static final  String JSON_DOWNLOAD_LOCATION = "http://api.eventfinda.com.au/v2/events.xml?fields=event:(url,name,sessions,location,location_summary,description,datetime_start,datetime_end,location:(id,url,name),session:(datetime_summary,session_tickets),session_ticket:(id,name,price))";

    RecyclerView MyRecyclerView;
    private ArrayList<Event> m_cEventList;
    DatabaseHelper m_cDBHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        m_cDBHelper = new DatabaseHelper(getActivity());

        if(m_cDBHelper.getAllEvents().size() == 0){
            //m_cDBHelper.CreateDefaultEvents();
            new SetupEventDataSetTask().execute(JSON_DOWNLOAD_LOCATION);
        }
        m_cEventList = new ArrayList<>(m_cDBHelper.getAllEvents().values());
        View view = inflater.inflate(R.layout.fragment_card, container, false);
        MyRecyclerView = (RecyclerView) view.findViewById(R.id.cardView);
        MyRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        if(m_cEventList.size() > 0 & MyRecyclerView != null){
            MyRecyclerView.setAdapter(new Adapter(m_cEventList));
        }
        MyRecyclerView.setLayoutManager(layoutManager);


        return view;


    }

    private class SetupEventDataSetTask extends AsyncTask<String, Void, String>{
        @Override
        protected String doInBackground(String... strings){
            try{
                URL downloadURL = new URL(strings[0]);
                HttpURLConnection connection = (HttpURLConnection) downloadURL.openConnection();
                InputStream input = connection.getInputStream();
                String result = "";
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                StringBuilder sb = new StringBuilder();
                while ((result = reader.readLine()) != null){
                    sb.append(result);
                }
                return sb.toString();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected  void onPostExecute(String result){
        if (result != null){
            try {
                JSONArray eventsContents = new JSONArray(result);

                for (int i=0; i < eventsContents.length(); i++){
                    JSONObject tempJSON = eventsContents.getJSONObject(i);

                    //get category name
                    JSONObject category = tempJSON.getJSONObject("category");
                    String categoryName = category.getString("name");

                    //get price
                    String price;
                    if (tempJSON.getInt("is_free") == 1) {
                        price = "0";
                    }
                    else{
                        JSONArray session = tempJSON.getJSONArray("sessions");
                        JSONObject session_ticket = session.getJSONObject(0);
                        price = session_ticket.getString("price");
                    }

                    //Get latitude and longitude
                    JSONObject point = tempJSON.getJSONObject("point");
                    String lat = point.getString("lat");
                    String lng = point.getString("lng");

                    //Get the image url from JSON
                    JSONArray images = tempJSON.getJSONArray("images");
                    JSONArray transforms = images.getJSONArray(4);
                    for ( int j = 0; j < transforms.length() ; j++){
                        JSONObject transform = (JSONObject)transforms.get(j);
                        int transformationID = transform.getInt("transformation_id");
                        if (transformationID == 8){
                            String imageURL = transform.getString("url");
                            break;
                        }
                    }

                    //get datetime_start
                    String datetime_start = tempJSON.getString("datetime_start");
                        //get the start date
                    Date start_date = getDateFromString(datetime_start);
                    String string_start_date = getStringFromDate(start_date);
                        //get the start time
                    Date time_start = getTimeFromString(datetime_start);
                    String string_start_time = getStringFromTime(time_start);

                    //get datetime_end
                    String datetime_end = tempJSON.getString("datetime_end");
                        //get the end date
                    Date end_date = getDateFromString(datetime_end);
                    String string_end_date = getStringFromDate(end_date);
                        //get end time
                    Date end_time = getTimeFromString(datetime_end);
                    String string_end_time = getStringFromTime(end_time);


                    Event event = new Event(
                            tempJSON.getLong("id"),
                            tempJSON.getString("name"),
                            tempJSON.getString("description"),
                            string_start_date,
                            string_end_date,
                            string_start_time,
                            string_end_time,
                            price,
                            categoryName,
                            tempJSON.getString("location_summary"),
                            lat,
                            lng);
                    m_cDBHelper.AddEvent(event);
                }

                m_cEventList.addAll(m_cDBHelper.getAllEvents().values());
                UpdateListCount();


            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
    }
    }





    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView eventNameTextView;
        TextView locationTextView;
        TextView dateTextView;
        TextView timeTextView;
        TextView priceTextView;
        ImageView eventImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            eventNameTextView = (TextView) itemView.findViewById(R.id.event_textView);
            locationTextView = (TextView)itemView.findViewById(R.id.location_textView);
            dateTextView = (TextView) itemView.findViewById(R.id.date_textView);
            timeTextView = (TextView) itemView.findViewById(R.id.time_textView);
            priceTextView = (TextView) itemView.findViewById(R.id.price_textView);
            eventImageView = (ImageView) itemView.findViewById(R.id.event_imageView);
        }
    }

    public class Adapter extends RecyclerView.Adapter<ViewHolder>{
        private ArrayList<Event> m_cEventsList;

        public Adapter(ArrayList<Event> Data){
            m_cEventsList = Data;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_card, parent, false);
            ViewHolder holder = new ViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.eventNameTextView.setText(m_cEventsList.get(position).getName());
            holder.locationTextView.setText(m_cEventsList.get(position).getLocation());
            holder.dateTextView.setText(m_cEventsList.get(position).getStartDate());
            holder.timeTextView.setText(m_cEventsList.get(position).getStartTime());
            holder.priceTextView.setText(m_cEventsList.get(position).getPrice());

        }

        @Override
        public int getItemCount() {
            return m_cEventList.size();
        }
    }

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

    public String getStringFromDate(Date date){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        return dateFormat.format(date);
    }

    private  void UpdateListCount(){
        int numEvents = m_cEventList.size();
    }





}

