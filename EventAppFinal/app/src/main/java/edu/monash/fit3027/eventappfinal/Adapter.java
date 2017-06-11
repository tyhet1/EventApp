package edu.monash.fit3027.eventappfinal;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class Adapter extends RecyclerView.Adapter<ViewHolder>{
        private ArrayList<Event> m_cEventsList;
        private DatabaseHelper m_cDBHelper;
        public Adapter(ArrayList<Event> Data){
            this.m_cEventsList = Data;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_card, parent, false);
            ViewHolder holder = new ViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            final Context context = holder.favButtonoff.getContext();
            //set all textViews
            holder.eventNameTextView.setText(m_cEventsList.get(position).getName());
            holder.locationTextView.setText(m_cEventsList.get(position).getLocation());
            holder.dateTextView.setText(m_cEventsList.get(position).getStartDate());
            holder.timeTextView.setText(m_cEventsList.get(position).getStartTime());
            holder.priceTextView.setText(m_cEventsList.get(position).getPrice());
            //holder.databasenumTextView.setText(m_cEventsList.size());

            holder.favButtonoff.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.favButtonoff.setVisibility(View.INVISIBLE);    //change the button to be pressed
                    holder.favButtonOn.setVisibility(View.VISIBLE);

                    m_cDBHelper = new DatabaseHelper(context);

                    Event event = new Event(m_cEventsList.get(position).getId(),
                            m_cEventsList.get(position).getName(),
                            m_cEventsList.get(position).getDescription(),
                            m_cEventsList.get(position).getStartDate(),
                            m_cEventsList.get(position).getEndDate(),
                            m_cEventsList.get(position).getStartTime(),
                            m_cEventsList.get(position).getEndTime(),
                            m_cEventsList.get(position).getPrice(),
                            m_cEventsList.get(position).getType(),
                            m_cEventsList.get(position).getLocation(),
                            m_cEventsList.get(position).getLatitude(),
                            m_cEventsList.get(position).getLongitude());

                    m_cDBHelper.AddEventToFavourites(event);
                    HashMap<Long, Event> favs = m_cDBHelper.favourites;          // add to favourites list
                    Toast.makeText(context, String.valueOf(favs.size()), Toast.LENGTH_SHORT).show();


                }
            });
            holder.favButtonOn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.favButtonOn.setVisibility(View.INVISIBLE);
                    holder.favButtonoff.setVisibility(View.VISIBLE);
                }
            });



        }

        @Override
        public int getItemCount() {
            return (m_cEventsList == null) ? 0 : m_cEventsList.size();
        }

        @Override
        public void onAttachedToRecyclerView(RecyclerView recyclerView){
            super.onAttachedToRecyclerView(recyclerView);
        }


    }
