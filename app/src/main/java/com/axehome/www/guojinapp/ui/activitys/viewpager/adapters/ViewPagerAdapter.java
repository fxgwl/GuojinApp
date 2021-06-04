package com.axehome.www.guojinapp.ui.activitys.viewpager.adapters;


import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

/**
 * Created by Axehome_Mr.z on 2020/3/11 16:46
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {
    ArrayList<Fragment> fragments;

    public ViewPagerAdapter(FragmentManager fm , ArrayList<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int i) {
        return fragments.get(i);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
