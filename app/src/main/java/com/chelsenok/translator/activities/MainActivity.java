package com.chelsenok.translator.activities;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.chelsenok.translator.R;
import com.chelsenok.translator.adapters.pagers.MainPagerAdapter;
import com.chelsenok.translator.utils.ActivityViewManager;
import com.chelsenok.translator.utils.SharedPreferenceManager;

public class MainActivity extends AppCompatActivity implements Handler.Callback {

    private final String MAIN_TAB_LAYOUT = "main_tab_layout";
    private final String SELECTED_TAB = "selected_tab";
    private SharedPreferenceManager mManager;
    private TabLayout mTabLayout;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mManager = new SharedPreferenceManager(this, MAIN_TAB_LAYOUT);
        ActivityViewManager.setTaskDescription(this);
        final MainPagerAdapter mainPagerAdapter = new MainPagerAdapter(
                getSupportFragmentManager(), new Handler(this)
        );
        mTabLayout = getTabLayout(R.id.main_viewpager, R.id.main_tabs,
                mainPagerAdapter);
        for (int i = 0; i < mainPagerAdapter.getCount(); i++) {
            setTabColorFilter(mTabLayout.getTabAt(i).setIcon(mainPagerAdapter.getContentAt(i)),
                    R.color.tabUnselectedColor);
        }
        setTabColorFilter(mTabLayout.getTabAt(mManager.getInt(SELECTED_TAB, 0)),
                R.color.tabSelectedColor).select();
    }

    private TabLayout getTabLayout(final int viewPagerId, final int tabLayoutId,
                                   final PagerAdapter pagerAdapter) {
        final ViewPager viewPager = (ViewPager) findViewById(viewPagerId);
        viewPager.setAdapter(pagerAdapter);
        final TabLayout tabLayout = (TabLayout) findViewById(tabLayoutId);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setOnTabSelectedListener(
                new TabLayout.ViewPagerOnTabSelectedListener(viewPager) {

                    @Override
                    public void onTabSelected(final TabLayout.Tab tab) {
                        super.onTabSelected(tab);
                        setTabColorFilter(tab, R.color.tabSelectedColor);
                        mManager.putInt(SELECTED_TAB, tab.getPosition());
                    }

                    @Override
                    public void onTabUnselected(final TabLayout.Tab tab) {
                        super.onTabUnselected(tab);
                        setTabColorFilter(tab, R.color.tabUnselectedColor);
                    }

                    @Override
                    public void onTabReselected(final TabLayout.Tab tab) {
                        super.onTabReselected(tab);
                    }
                }
        );
        return tabLayout;
    }

    private TabLayout.Tab setTabColorFilter(final TabLayout.Tab tab, final int colorId) {
        tab.getIcon().setColorFilter(ContextCompat.getColor(this, colorId),
                PorterDuff.Mode.SRC_IN);
        return tab;
    }

    @Override
    public boolean handleMessage(final Message msg) {
        switch (msg.what) {
            case 0:
                startActivity((Intent) msg.obj);
                finish();
        }
        return true;
    }
}