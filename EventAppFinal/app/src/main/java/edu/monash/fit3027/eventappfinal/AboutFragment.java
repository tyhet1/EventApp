package edu.monash.fit3027.eventappfinal;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


//Page that shows all external libaries
public class AboutFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View homeView = inflater.inflate(R.layout.about_fragment, container, false);
        return homeView;
    }
}
