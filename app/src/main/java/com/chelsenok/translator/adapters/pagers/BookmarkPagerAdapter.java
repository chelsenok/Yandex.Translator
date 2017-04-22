package com.chelsenok.translator.adapters.pagers;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.chelsenok.translator.fragments.pagers.BookmarkPagerFragment;

public class BookmarkPagerAdapter extends PagerAdapter {

    public BookmarkPagerAdapter(FragmentManager fm) {
        super(fm, new String[] {
                "History",
                "Favorites"
        });
    }

    @Override
    public Fragment getItem(int position) {
        return BookmarkPagerFragment.newInstance(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        //can differ here
        return getContentAt(position);
    }

    @Override
    public String getContentAt(int i) {
        return (String) super.getContentAt(i);
    }

//    @Override
//    public void finishUpdate(ViewGroup container) {
//        try{
//            super.finishUpdate(container);
//        } catch (NullPointerException nullPointerException){
//            Log.d("TAG", "Catch the NullPointerException in FragmentPagerAdapter.finishUpdate");
//        }
//    }
}
