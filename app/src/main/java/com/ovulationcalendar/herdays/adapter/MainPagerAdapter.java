package com.ovulationcalendar.herdays.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.ovulationcalendar.herdays.fragment.CalendarFragment;
import com.ovulationcalendar.herdays.fragment.HomeFragment;
import com.ovulationcalendar.herdays.fragment.InfoFragment;

/**
 * Created by mleano on 3/10/2016.
 */
public class MainPagerAdapter extends FragmentStatePagerAdapter {

    CharSequence titles[];
    int count;

    public MainPagerAdapter(FragmentManager fm, CharSequence[] titles) {
        super(fm);
        this.titles = titles;
        this.count = titles.length;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new HomeFragment();
            case 1:
                return new CalendarFragment();
            case 2:
                return new InfoFragment();
        }

        return null;
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
