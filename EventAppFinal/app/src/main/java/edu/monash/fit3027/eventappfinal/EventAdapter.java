package edu.monash.fit3027.eventappfinal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Thamale on 17/05/2017.
 */

public class EventAdapter extends BaseAdapter {

    private Context m_cCurrentContext;
    private ArrayList<Event> m_cEventsList;

    public EventAdapter(Context context, ArrayList<Event> events){
        m_cCurrentContext = context;
        m_cEventsList = events;
    }

    public static class ViewHolder{
        TextView eventNameTextView;
        TextView locationTextView;
        TextView dateTextView;
        TextView timeTextView;
        TextView priceTextView;
        ImageView eventImageView;
    }

    @Override
    public int getCount() {
        return m_cEventsList.size();
    }

    @Override
    public Object getItem(int position) {
        return m_cEventsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView ==null){
            LayoutInflater inflater = (LayoutInflater) m_cCurrentContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_row, null);

            viewHolder = new ViewHolder();
            viewHolder.eventNameTextView = (TextView) convertView.findViewById(R.id.event_textView);
            viewHolder.locationTextView = (TextView)convertView.findViewById(R.id.location_textView);
            viewHolder.dateTextView = (TextView) convertView.findViewById(R.id.date_textView);
            viewHolder.timeTextView = (TextView) convertView.findViewById(R.id.time_textView);
            viewHolder.priceTextView = (TextView) convertView.findViewById(R.id.price_textView);
            viewHolder.eventImageView = (ImageView) convertView.findViewById(R.id.event_imageView);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder =(ViewHolder) convertView.getTag();
        }

        //Assign values to TextViews using Event object
        viewHolder.eventNameTextView.setText(m_cEventsList.get(position).getName());
        viewHolder.locationTextView.setText(m_cEventsList.get(position).getLocation());
        viewHolder.dateTextView.setText(m_cEventsList.get(position).getStartDate());
        viewHolder.timeTextView.setText(m_cEventsList.get(position).getStartTime());
        viewHolder.priceTextView.setText(m_cEventsList.get(position).getPrice());

        return convertView;



    }
}
