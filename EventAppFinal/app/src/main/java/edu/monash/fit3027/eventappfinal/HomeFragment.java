package edu.monash.fit3027.eventappfinal;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.IntentCompat;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewGroup;

import android.webkit.HttpAuthHandler;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpHost;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.StringTokenizer;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;



/**
 * Created by Thamale on 12/05/2017.
 */
//https://www.androidtutorialpoint.com/material-design/android-cardview-tutorial

public class HomeFragment extends Fragment {
    private String TAG = HomeFragment.class.getSimpleName();

    public HomeFragment(){


        // leave empty

    }
    //?username=myusers&password=mypassword



    public static final  String JSON_DOWNLOAD_LOCATION = "http://eventfindingapp:wyr77q77gx9k@api.eventfinda.com.au/v2/events.json?fields=event:(url,name,sessions,location,location_summary,description,datetime_start,datetime_end,location:(id,url,name),session:(datetime_summary,session_tickets),session_ticket:(id,name,price))";
    public  static final int ADD_EVENT_REQUEST = 1;





    private RecyclerView MyRecyclerView;
    private ArrayList<Event> m_cEventList;
    private DatabaseHelper m_cDBHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        // get database handler
        m_cDBHelper = new DatabaseHelper(getActivity());

        if(m_cDBHelper.getAllEvents().size() == 0){
            m_cDBHelper.CreateDefaultEvents();
            //new SetupEventDataSetTask().execute(JSON_DOWNLOAD_LOCATION);

        }
        //populate the list with valuts from the database
        m_cEventList = new ArrayList<>(m_cDBHelper.getAllEvents().values());
        View view = inflater.inflate(R.layout.fragment_card, container, false);
        MyRecyclerView = (RecyclerView) view.findViewById(R.id.cardView);
        MyRecyclerView.setHasFixedSize(false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        if(m_cEventList.size() > 0 & MyRecyclerView != null){
            MyRecyclerView.setAdapter(new Adapter(m_cEventList));
        }
        MyRecyclerView.setLayoutManager(layoutManager);

        int numevents = m_cEventList.size();
        View homeView = inflater.inflate(R.layout.home_fragment, container, false);
        TextView databasenum = (TextView) homeView.findViewById(R.id.textView2);
        databasenum.setText(String.valueOf(numevents));


        return view;

        //return homeView;


    }







    private class SetupEventDataSetTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            HttpHandler sh = new HttpHandler();
            // Make a request to url and getting response
            String url = "http://eventfindingapp:wyr77q77gx9k@api.eventfinda.com.au/v2/events.json?fields=event:(url,name,sessions,location,location_summary,description,datetime_start,datetime_end,location:(id,url,name),session:(datetime_summary,session_tickets),session_ticket:(id,name,price))";
            String jsonStr = sh.makeServiceCall(url);

            Log.e(TAG, "Response from url: " + jsonStr);
            if (jsonStr != null) {
                try {
                    JSONObject eventsContents = new JSONObject(jsonStr);
                    JSONArray events = eventsContents.getJSONArray("events");


                    for (int i = 0; i < events.length(); i++) {
                        JSONObject tempJSON = events.getJSONObject(i);

                        //get category name
                        JSONObject category = tempJSON.getJSONObject("category");
                        String categoryName = category.getString("name");

                        //get price
                        String price;
                        if (tempJSON.getInt("is_free") == 1) {
                            price = "0";
                        } else {
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
                        for (int j = 0; j < transforms.length(); j++) {
                            JSONObject transform = (JSONObject) transforms.get(j);
                            int transformationID = transform.getInt("transformation_id");
                            if (transformationID == 8) {
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
                        m_cEventList.add(event);
                    }

                    //m_cEventList.addAll(m_cDBHelper.getAllEvents().values());
                    UpdateListCount();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            return null;
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



            /*
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
            */
        }


