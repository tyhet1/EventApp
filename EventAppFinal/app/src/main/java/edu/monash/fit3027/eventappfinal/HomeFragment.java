package edu.monash.fit3027.eventappfinal;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Thamale on 12/05/2017.
 */
//https://www.androidtutorialpoint.com/material-design/android-cardview-tutorial

public class HomeFragment extends Fragment {

    public HomeFragment(){

    }

    public  static final int ADD_EVENT_REQUEST = 1;
    public static final  String JSON_DOWNLOAD_LOCATION = "http://api.eventfinda.com.au/v2/events.json?location=4";

    RecyclerView MyRecyclerView;
    private ArrayList<Event> m_cEventList;
    DatabaseHelper m_cDBHelper;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        m_cDBHelper = new DatabaseHelper(getActivity());

        if(m_cDBHelper.getAllEvents().size() == 0){
            m_cDBHelper.CreateDefaultEvents();
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

}

