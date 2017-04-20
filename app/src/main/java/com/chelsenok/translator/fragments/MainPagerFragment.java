package com.chelsenok.translator.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chelsenok.translator.R;

public class MainPagerFragment extends Fragment {

    public static final String ARG_PAGE = "ARG_PAGE";

    public static MainPagerFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        MainPagerFragment fragment = new MainPagerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        switch (getArguments().getInt(ARG_PAGE)) {
            case 1: {
                return inflater.inflate(R.layout.fragment_translator, container, false);
            }
            case 2: {
                return inflater.inflate(R.layout.fragment_bookmarks, container, false);
            }
            case 3: {
                return inflater.inflate(R.layout.fragment_settings, container, false);
            }
        }
        return null;
    }
}