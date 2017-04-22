package com.chelsenok.translator.fragments.pagers;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chelsenok.translator.R;
import com.chelsenok.translator.adapters.pagers.BookmarkPagerAdapter;

public class MainPagerFragment extends PagerFragment {
    private static FragmentManager sFragmentManager;

    public static MainPagerFragment newInstance(int page, FragmentManager fragmentManager) {
        sFragmentManager = fragmentManager;
        MainPagerFragment fragment = new MainPagerFragment();
        fragment.setArguments(getArguments(page));
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        switch (getArguments().getInt(ARG_PAGE)) {
            case 0: {
                return inflater.inflate(R.layout.fragment_translator, container, false);
            }
            case 1: {
                final View view = inflater.inflate(R.layout.fragment_bookmarks, container, false);
                final ViewPager viewPager = (ViewPager) view.findViewById(R.id.bookmark_viewpager);
                viewPager.setAdapter(new BookmarkPagerAdapter(sFragmentManager));
                final TabLayout tabLayout = (TabLayout) view.findViewById(R.id.bookmark_tabs);
                tabLayout.setupWithViewPager(viewPager);
                return view;
            }
            case 2: {
                return inflater.inflate(R.layout.fragment_settings, container, false);
            }
        }
        return null;
    }
}