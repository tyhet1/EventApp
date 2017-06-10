package edu.monash.fit3027.eventappfinal;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

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
            //holder.databasenumTextView.setText(m_cEventsList.size());


        }

        @Override
        public int getItemCount() {
            return m_cEventsList.size();
        }
    }
