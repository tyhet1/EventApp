package edu.monash.fit3027.eventappfinal;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by Thamale on 12/05/2017.
 */

public class PagerAdapter extends FragmentStatePagerAdapter{
    int m_nNumberOfTabs;

    public PagerAdapter(FragmentManager fm, int numOfTabs){
        super(fm);
        this.m_nNumberOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                TabFragment1 tab1 = new TabFragment1();
                return tab1;

            case 1:
                TabFragment2 tab2 = new TabFragment2();
                return tab2;

            case 2:
                TabFragment3 tab3 = new TabFragment3();
                return tab3;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return m_nNumberOfTabs;
    }

}
