package com.example.vankien.dating.Views.Adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.vankien.dating.Views.Fragment.AroundFragment;
import com.example.vankien.dating.Views.Fragment.ChatFragment;
import com.example.vankien.dating.Views.Fragment.MapFragment;
import com.example.vankien.dating.Views.Fragment.ProfileFragment;

/**
 * Created by vanki on 2/1/2018.
 */

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    private static int NUM_ITEMS = 4;
    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new AroundFragment();
            case 1:
                return new MapFragment();
            case 2:
                return new ChatFragment();
            case 3:
                return new ProfileFragment();
        }
        return null;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        switch (position){
            case 0:
                return "Around";
            case 1:
                return "Map";
            case 2:
                return "Chat";
            case 3:
                return "Profile";
            default:
                return "";
        }
    }

    @Override
    public int getCount() {
        return NUM_ITEMS;
    }
}
