package com.chelsenok.translator.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.chelsenok.translator.R;
import com.chelsenok.translator.fragments.MainPagerFragment;

public class MainPagerAdapter extends android.support.v4.app.FragmentPagerAdapter {

    public static final int[] ICONS = {
            R.drawable.ic_one,
            R.drawable.ic_two,
            R.drawable.ic_three
    };

    public MainPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return ICONS.length;
    }

    @Override
    public Fragment getItem(int position) {
        return MainPagerFragment.newInstance(position + 1);
    }

}
