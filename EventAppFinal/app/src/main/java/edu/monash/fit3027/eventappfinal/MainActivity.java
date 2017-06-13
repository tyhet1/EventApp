package edu.monash.fit3027.eventappfinal;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;


/*
Links used:
    http://www.truiton.com/2015/06/android-tabs-example-fragments-viewpager/
    http://www.androidhive.info/2015/09/android-material-design-working-with-tabs/
*/

public class MainActivity extends AppCompatActivity {
    private TabLayout m_cTabLayout;
    private ViewPager m_cViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //set up tabs
        m_cViewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(m_cViewPager);
        m_cTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        m_cTabLayout.setupWithViewPager(m_cViewPager);
    }

    //set up tabs with fragments
    private void setupViewPager(ViewPager viewPager){
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new HomeFragment(), "Home");
        adapter.addFragment(new FavouritesFragment(), "Favourites");
        adapter.addFragment(new SearchFragment(), "Search");
        adapter.addFragment(new AboutFragment(), "About");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter{
        private final List<Fragment> m_fragmentList = new ArrayList<>();
        private final List<String> m_fragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager){
            super(manager);
        }

        @Override
        public Fragment getItem(int position){
            return m_fragmentList.get(position);
        }

        @Override
        public int getCount(){
            return m_fragmentList.size();
        }

        public void addFragment(Fragment fragment, String title){
            m_fragmentList.add(fragment);
            m_fragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position){
            return m_fragmentTitleList.get(position);
        }
    }
}