package com.chelsenok.translator.fragments.pagers;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chelsenok.translator.fragments.BookmarksFragment;
import com.chelsenok.translator.fragments.SettingsFragment;
import com.chelsenok.translator.fragments.TranslatorFragment;

public class MainPagerFragment extends PagerFragment {
    private static FragmentManager sFragmentManager;
    private static Handler sHandler;

    public static MainPagerFragment newInstance(final int page, final FragmentManager fragmentManager,
                                                final Handler handler) {
        sFragmentManager = fragmentManager;
        sHandler = handler;
        final MainPagerFragment fragment = new MainPagerFragment();
        fragment.setArguments(getArguments(page));
        return fragment;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        final Bundle bundle = getArguments();
        switch (bundle.getInt(ARG_PAGE)) {
            case 0:
                return new TranslatorFragment(sHandler).onCreateView(inflater,
                        container, savedInstanceState);
            case 1:
                return new BookmarksFragment(sFragmentManager).onCreateView(inflater,
                        container, savedInstanceState);
            case 2:
                return new SettingsFragment().onCreateView(inflater,
                        container, savedInstanceState);
            default:
                return null;
        }
    }
}