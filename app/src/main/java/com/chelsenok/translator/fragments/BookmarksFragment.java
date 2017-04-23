package com.chelsenok.translator.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.chelsenok.translator.R;
import com.chelsenok.translator.adapters.pagers.BookmarkPagerAdapter;
import com.chelsenok.translator.utils.SharedPreferenceManager;

public class BookmarksFragment extends Fragment {

    private final FragmentManager mFragmentManager;
    private ImageButton mButtonDelete;
    private TabLayout mTabLayout;
    private SharedPreferenceManager mManager;
    private final String TAB_LAYOUT = "bookmarks_tab_layout";
    private final String SELECTED_TAB = "selected_tab";

    public BookmarksFragment(final FragmentManager fragmentManager) {
        mFragmentManager = fragmentManager;
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_bookmarks, container, false);
        mButtonDelete = (ImageButton) view.findViewById(R.id.btn_delete);
        final ViewPager viewPager = (ViewPager) view.findViewById(R.id.bookmark_viewpager);
        mManager = new SharedPreferenceManager(view.getContext(), TAB_LAYOUT);
        viewPager.setAdapter(new BookmarkPagerAdapter(mFragmentManager));
        mTabLayout = (TabLayout) view.findViewById(R.id.bookmark_tabs);
        mTabLayout.setupWithViewPager(viewPager);
        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(final TabLayout.Tab tab) {
                setDeleteButtonVisibility(tab, View.VISIBLE);
                mManager.putInt(SELECTED_TAB, tab.getPosition());
            }

            @Override
            public void onTabUnselected(final TabLayout.Tab tab) {
                setDeleteButtonVisibility(tab, View.INVISIBLE);
            }

            @Override
            public void onTabReselected(final TabLayout.Tab tab) {

            }
        });
        mTabLayout.getTabAt(mManager.getInt(SELECTED_TAB, 0)).select();
        return view;
    }

    private void setDeleteButtonVisibility(final TabLayout.Tab tab, final int visibility) {
        if (tab == mTabLayout.getTabAt(0)) {
            mButtonDelete.setVisibility(visibility);
        }
    }
}
