package edu.monash.fit3027.eventappfinal;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewHolder extends RecyclerView.ViewHolder{
    TextView eventNameTextView;
    TextView locationTextView;
    TextView dateTextView;
    TextView timeTextView;
    TextView priceTextView;
    ImageView eventImageView;
    //TextView databasenumTextView;

    public ViewHolder(View itemView) {
        super(itemView);
        eventNameTextView = (TextView) itemView.findViewById(R.id.event_textView);
        locationTextView = (TextView)itemView.findViewById(R.id.location_textView);
        dateTextView = (TextView) itemView.findViewById(R.id.date_textView);
        timeTextView = (TextView) itemView.findViewById(R.id.time_textView);
        priceTextView = (TextView) itemView.findViewById(R.id.price_textView);
        eventImageView = (ImageView) itemView.findViewById(R.id.event_imageView);
        //databasenumTextView = (TextView) itemView.findViewById(R.id.textView_num);

    }
}
