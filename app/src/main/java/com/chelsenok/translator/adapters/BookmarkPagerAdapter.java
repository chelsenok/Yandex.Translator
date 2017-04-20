package com.chelsenok.translator.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.chelsenok.translator.fragments.MainPagerFragment;

public class BookmarkPagerAdapter extends android.support.v4.app.FragmentPagerAdapter {

    public static final String[] NAMES = {
            "History",
            "Favorites"
    };

    public BookmarkPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return NAMES.length;
    }

    @Override
    public Fragment getItem(int position) {
        return MainPagerFragment.newInstance(position + 1);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return NAMES[position];
    }
}
