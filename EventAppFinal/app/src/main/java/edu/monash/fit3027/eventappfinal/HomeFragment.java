package edu.monash.fit3027.eventappfinal;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import org.json.JSONArray;
import org.json.JSONObject;
import android.os.AsyncTask;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Thamale on 12/05/2017.
 */

public class HomeFragment extends Fragment implements AdapterView.OnItemLongClickListener {

    public  static final int ADD_EVENT_REQUEST = 1;
    public static final  String JSON_DOWNLOAD_LOCATION = "http://api.eventfinda.com.au/v2/events.json?location=4";

    private ListView m_cListView;
    RecyclerView MyRecyclerView;
    private ArrayList<Event> m_cEventList;
    private DatabaseHelper m_cDBHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_card, container, false);
        MyRecyclerView = (RecyclerView)  view.findViewById(R.id.cardView);
        MyRecyclerView.setHasFixedSize(true);
        LinearLayoutManager myLayoutManager = new LinearLayoutManager(getActivity());


        return inflater.inflate(R.layout.home_fragment, container, false);


    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        return false;
    }
}

