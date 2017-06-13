package edu.monash.fit3027.eventappfinal;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Thamale on 12/05/2017.
 */

public class FavouritesFragment extends Fragment{
    private  DatabaseHelper m_cDBHelper;
    private RecyclerView MyRecyclerView;
    private ArrayList<Event> favourites;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        m_cDBHelper = new DatabaseHelper(getActivity());
        favourites = new ArrayList<>(m_cDBHelper.favourites.values());

        // display all the events in favourites, cardview in recyclerView in a fragment
        View view = inflater.inflate(R.layout.fragment_card, container, false);
        MyRecyclerView = (RecyclerView) view.findViewById(R.id.cardView);
        MyRecyclerView.setHasFixedSize(false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        MyRecyclerView.setAdapter(new RVAdapter(favourites));
        MyRecyclerView.setLayoutManager(layoutManager);


        // set up other view to see how many objects are in the favourites
        int numevents = favourites.size();
        View homeView = inflater.inflate(R.layout.favourites_fragment, container, false);
        TextView databasenum = (TextView) homeView.findViewById(R.id.textView3);
        databasenum.setText(String.valueOf(numevents));
        return homeView;

       // return view;

    }


}
