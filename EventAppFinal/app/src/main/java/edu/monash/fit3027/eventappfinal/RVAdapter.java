package edu.monash.fit3027.eventappfinal;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.EventViewHolder> {
    ArrayList<Event> m_cEventsList;
    private DatabaseHelper m_cDBHelper;
    URL url;
//https://code.tutsplus.com/tutorials/getting-started-with-recyclerview-and-cardview-on-android--cms-23465

    public class EventViewHolder extends RecyclerView.ViewHolder {
        public TextView eventNameTextView;
        public TextView locationTextView;
        public TextView dateTextView;
        public TextView timeTextView;
        public TextView priceTextView;
        public ImageView eventImageView;
        public ImageButton favButtonoff;
        public ImageButton favButtonOn;
        public CardView cardView;


        public EventViewHolder(View itemView) {
            super(itemView);
            final Context context = itemView.getContext();

            eventNameTextView = (TextView) itemView.findViewById(R.id.event_textView);
            locationTextView = (TextView) itemView.findViewById(R.id.location_textView);
            dateTextView = (TextView) itemView.findViewById(R.id.dateTime_textView);
            timeTextView = (TextView) itemView.findViewById(R.id.time_textView);
            priceTextView = (TextView) itemView.findViewById(R.id.price_textView);
            eventImageView = (ImageView) itemView.findViewById(R.id.event_imageView);
            favButtonoff = (ImageButton) itemView.findViewById(R.id.favourite_imageButton);
            favButtonOn = (ImageButton) itemView.findViewById(R.id.favourite_imageButton_pressed);
            favButtonOn.setVisibility(itemView.INVISIBLE);
            cardView = (CardView) itemView.findViewById(R.id.card_view);

            //itemView.setClickable(true);

            itemView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ViewEvent.class);
                    int clickPosition = getAdapterPosition();
                    Event event = m_cEventsList.get(clickPosition);
                    intent.putExtra("event", event);
                    context.startActivity(intent);


                }
            });

        }
    }

    public RVAdapter(ArrayList<Event> eventsList){
        this.m_cEventsList = eventsList;
    }



    @Override
    public EventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_card, parent, false);
        EventViewHolder eventViewHolder = new EventViewHolder(view);
        return eventViewHolder;

    }

    @Override
    public void onBindViewHolder(final EventViewHolder holder, final int position) {
        final Context context = holder.favButtonoff.getContext();

        String price = "$" + m_cEventsList.get(position).getPrice();

        //set all textView

        holder.eventNameTextView.setText(m_cEventsList.get(position).getName());
        holder.locationTextView.setText(m_cEventsList.get(position).getLocation());
        holder.dateTextView.setText(m_cEventsList.get(position).getStartDate());
        holder.timeTextView.setText(m_cEventsList.get(position).getStartTime());
        holder.priceTextView.setText(price);
        new ImageDownloader(holder.eventImageView).execute(m_cEventsList.get(position).getImage());


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
                            m_cEventsList.get(position).getLongitude(),
                            m_cEventsList.get(position).getImage());

                    m_cDBHelper.AddEventToFavourites(event);
                    HashMap<Long, Event> favs = m_cDBHelper.favourites;          // add to favourites list
                    //Toast.makeText(context, String.valueOf(favs.size()), Toast.LENGTH_SHORT).show();


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


        public Event getItem(int position){
            return m_cEventsList.get(position);
        }
        @Override
        public void onAttachedToRecyclerView(RecyclerView recyclerView){
            super.onAttachedToRecyclerView(recyclerView);
        }


    }
