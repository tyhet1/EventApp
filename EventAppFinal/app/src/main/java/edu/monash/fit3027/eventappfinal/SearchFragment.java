package edu.monash.fit3027.eventappfinal;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thamale on 21/05/2017.
 */

public class SearchFragment extends Fragment implements View.OnClickListener {
    private DatabaseHelper m_cDBHelper;
    private ArrayList<Event> m_cEventList;
    private Button searchButton;
    private Spinner location_Spinner;
    private Spinner budget_spinner;
    private Spinner type_spinner;
    private TextView text5;
    private TextView text6;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.search_fragment, container, false);
        location_Spinner = (Spinner) view.findViewById(R.id.location_spinner);
        budget_spinner = (Spinner) view.findViewById(R.id.budget_spinner);
        type_spinner = (Spinner) view.findViewById(R.id.type_spinner);
        searchButton = (Button) view.findViewById(R.id.search_button);
        ArrayList<String> array = new ArrayList<String>();

        setSpinnerDatabaseData(location_Spinner, "LOCATION");
        setSpinnerDatabaseData(type_spinner, "TYPE");

        array.add("0");
        array.add("1-50");
        array.add("50-100");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, array);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        budget_spinner.setAdapter(adapter);

        searchButton.setOnClickListener(this);


        return view;

    }

    public void setSpinnerDatabaseData(Spinner spinner, String column){
        m_cDBHelper = new DatabaseHelper(getActivity());
        ArrayList<String> array;
        array = m_cDBHelper.getColumnData(column);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, array);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);
    }


    @Override
    public void onClick(View v) {
        m_cDBHelper = new DatabaseHelper(getActivity());
        ArrayList<Event> m_cEventList;

        String type = (String) type_spinner.getSelectedItem();
        String location = (String) location_Spinner.getSelectedItem();
        String budget = (String) budget_spinner.getSelectedItem();




        m_cEventList = new ArrayList<>(m_cDBHelper.getSearchedEvents(location, budget, type).values());

        //set up intent
        Intent intent = new Intent(getActivity(), SearchEvent.class);
        intent.putExtra("EVENTS_RESULT", m_cEventList);
        startActivity(intent);
        getActivity().finish();




    }
}

