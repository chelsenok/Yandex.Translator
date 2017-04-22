package com.chelsenok.translator.fragments.pagers;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chelsenok.translator.R;

public class BookmarkPagerFragment extends PagerFragment {

    public static BookmarkPagerFragment newInstance(int page) {
        BookmarkPagerFragment fragment = new BookmarkPagerFragment();
        fragment.setArguments(getArguments(page));
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        switch (getArguments().getInt(ARG_PAGE)) {
            case 0: {
                return inflater.inflate(R.layout.fragment_history, container, false);
            }
            case 1: {
                return inflater.inflate(R.layout.fragment_favorites, container, false);
            }
        }
        return null;
    }
}