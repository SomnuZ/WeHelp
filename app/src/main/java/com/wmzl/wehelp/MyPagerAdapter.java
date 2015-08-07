package com.wmzl.wehelp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by leo on 15-7-8.
 */
public class MyPagerAdapter extends FragmentPagerAdapter {

    private final String[] TITLES = { "帮帮好友", "帮帮我", "财产" };

    public MyPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return TITLES[position];
    }

    @Override
    public int getCount() {
        return TITLES.length;
    }

    @Override
    public Fragment getItem(int position) {
        return HomePageFragment.newInstance(position);
    }

}