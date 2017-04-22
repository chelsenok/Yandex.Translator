package com.chelsenok.translator.adapters.pagers;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.chelsenok.translator.R;
import com.chelsenok.translator.fragments.pagers.MainPagerFragment;

public class MainPagerAdapter extends PagerAdapter {

    private static FragmentManager sFragmentManager;

    public MainPagerAdapter(FragmentManager fm) {
        super(fm, new Integer[]{
                R.drawable.ic_one,
                R.drawable.ic_two,
                R.drawable.ic_three
        });
        sFragmentManager = fm;
    }

    @Override
    public Integer getContentAt(int i) {
        return (Integer) super.getContentAt(i);
    }

    @Override
    public Fragment getItem(int position) {
        return MainPagerFragment.newInstance(position, sFragmentManager);
    }
}
