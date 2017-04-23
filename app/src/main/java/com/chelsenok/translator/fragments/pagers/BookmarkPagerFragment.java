package com.chelsenok.translator.fragments.pagers;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chelsenok.translator.fragments.bookmarks.FavoritesFragment;
import com.chelsenok.translator.fragments.bookmarks.HistoryFragment;

public class BookmarkPagerFragment extends PagerFragment {

    public static BookmarkPagerFragment newInstance(final int page) {
        final BookmarkPagerFragment fragment = new BookmarkPagerFragment();
        fragment.setArguments(getArguments(page));
        return fragment;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        switch (getArguments().getInt(ARG_PAGE)) {
            case 0:
                return new HistoryFragment().onCreateView(inflater,
                        container, savedInstanceState);
            case 1:
                return new FavoritesFragment().onCreateView(inflater,
                        container, savedInstanceState);
            default:
                return null;
        }
    }
}