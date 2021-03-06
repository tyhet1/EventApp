package edu.monash.fit3027.eventappfinal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class SearchEvent extends AppCompatActivity {
    public static final int ADD_PERSON_REQUEST = 1;
    private RecyclerView MyRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_event);

        //get incoming intent
        Intent intent = getIntent();
        ArrayList<Event> m_cEventList = intent.getParcelableArrayListExtra("EVENTS_RESULT");


        // Display the events in the m_cEventList in cardView in RecyclerView in activity
        MyRecyclerView = (RecyclerView) findViewById(R.id.cardView);
        MyRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getBaseContext());
        MyRecyclerView.setLayoutManager(layoutManager);
        if(m_cEventList.size() > 0 & MyRecyclerView != null){
            RVAdapter adapter = new RVAdapter(m_cEventList);
            MyRecyclerView.setAdapter(adapter);
        }



    }
}
