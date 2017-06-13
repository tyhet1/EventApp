package edu.monash.fit3027.eventappfinal;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class ViewEvent extends AppCompatActivity implements OnMapReadyCallback {
    //https://stackoverflow.com/questions/6407324/how-to-display-image-from-url-on-android
    private GoogleMap m_cGoogleMap;
    private Location m_cCurrentLocation;
    private String str_latitude;
    private String str_longitude;
    private LatLng EVENT_LOCATION;
    private String event_name;

    private TextView m_cName_TextView;
    private TextView m_cDateTime_TextView;
    private TextView m_cDecription_TextView;
    private ImageView m_cImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_event);
        //get incoming intent
        Intent intent = getIntent();
        Event m_cEvent = intent.getParcelableExtra("event");

        m_cName_TextView = (TextView) findViewById(R.id.name_textView);
        m_cDateTime_TextView = (TextView) findViewById(R.id.dateTime_textView);
        m_cDecription_TextView = (TextView) findViewById(R.id.description_textView);
        m_cImageView = (ImageView) findViewById(R.id.imageView);

        if (m_cEvent != null) {

            String m_cDateTime = m_cEvent.getStartDate() + "  " + m_cEvent.getStartTime();
            m_cName_TextView.setText(m_cEvent.getName());
            m_cDateTime_TextView.setText(m_cDateTime);
            m_cDecription_TextView.setText(m_cEvent.getDescription());

            //get the latitude and logitude from the event object
            str_latitude = m_cEvent.getLatitude();
            str_longitude = m_cEvent.getLongitude();
            event_name = m_cEvent.getName();
            EVENT_LOCATION = new LatLng(Double.parseDouble(str_latitude), Double.parseDouble(str_longitude));
            new ImageDownloader(m_cImageView).execute(m_cEvent.getImage());

            //set the map fragment
            MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map_fragment);
            mapFragment.getMapAsync(this);

        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        m_cGoogleMap = googleMap;
        m_cGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(EVENT_LOCATION,15));
        m_cGoogleMap.addMarker(new MarkerOptions().position(EVENT_LOCATION).title(event_name));

    }

}
