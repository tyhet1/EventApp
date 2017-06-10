package edu.monash.fit3027.eventappfinal;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thamale on 21/05/2017.
 */

public class SearchFragment extends Fragment {
    private DatabaseHelper m_cDBHelper;
    private ArrayList<Event> m_cEventList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.search_fragment, container, false);
        Spinner location_Spinner = (Spinner) view.findViewById(R.id.location_spinner);
        Spinner budget_spinner = (Spinner) view.findViewById(R.id.budget_spinner);
        Spinner type_spinner = (Spinner) view.findViewById(R.id.type_spinner);
        ArrayList<String> array = new ArrayList<String>();

        setSpinnerDatabaseData(location_Spinner, "LOCATION");
        setSpinnerDatabaseData(type_spinner, "TYPE");

        array.add("0");
        array.add("0-50");
        array.add("50-100");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, array);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        budget_spinner.setAdapter(adapter);


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





}

