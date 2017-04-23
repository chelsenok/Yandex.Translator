package com.chelsenok.translator.adapters.pagers;

import android.support.v4.app.FragmentManager;

abstract class PagerAdapter extends android.support.v4.app.FragmentPagerAdapter {

    private final Object[] CONTENT;

    PagerAdapter(final FragmentManager fm, final Object[] content) {
        super(fm);
        CONTENT = content;
    }

    @Override
    public int getCount() {
        return CONTENT.length;
    }

    protected Object getContentAt(final int i) {
        return CONTENT[i];
    }
}
