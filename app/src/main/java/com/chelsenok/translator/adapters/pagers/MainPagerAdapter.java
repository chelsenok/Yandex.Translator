package com.chelsenok.translator.adapters.pagers;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.chelsenok.translator.R;
import com.chelsenok.translator.fragments.pagers.MainPagerFragment;

public class MainPagerAdapter extends PagerAdapter {

    private final FragmentManager mFragmentManager;

    public MainPagerAdapter(final FragmentManager fm) {
        super(fm, new Integer[]{
                R.drawable.ic_one,
                R.drawable.ic_two,
                R.drawable.ic_three
        });
        mFragmentManager = fm;
    }

    @Override
    public Integer getContentAt(final int i) {
        return (Integer) super.getContentAt(i);
    }

    @Override
    public Fragment getItem(final int position) {
        return MainPagerFragment.newInstance(position, mFragmentManager);
    }
}
