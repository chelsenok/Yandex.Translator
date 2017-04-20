package com.chelsenok.translator.activities;

import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.chelsenok.translator.R;
import com.chelsenok.translator.adapters.MainPagerAdapter;

public class MainActivity extends AppCompatActivity {

    private Context mContext;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;

        final TabLayout mainTabLayout = getTabLayout(R.id.main_viewpager, R.id.main_tabs,
                new MainPagerAdapter(getSupportFragmentManager()));
        for (int i = 0; i < MainPagerAdapter.ICONS.length; i++) {
            setTabColorFilter(mainTabLayout.getTabAt(i).setIcon(MainPagerAdapter.ICONS[i]),
                    R.color.tabUnselectedIconColor);
        }
        setTabColorFilter(mainTabLayout.getTabAt(0), R.color.tabSelectedIconColor);

//        final TabLayout bookmarkTabLayout = getTabLayout(R.id.bookmark_viewpager, R.id.bookmark_tabs,
//                new BookmarkPagerAdapter(getSupportFragmentManager()));
//        for (int i = 0; i < BookmarkPagerAdapter.NAMES.length; i++) {
//            setTabColorFilter(bookmarkTabLayout.getTabAt(i),
//                    R.color.tabUnselectedIconColor);
//        }
//        setTabColorFilter(bookmarkTabLayout.getTabAt(0), R.color.tabSelectedIconColor);
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
                        setTabColorFilter(tab, R.color.tabSelectedIconColor);
                    }

                    @Override
                    public void onTabUnselected(final TabLayout.Tab tab) {
                        super.onTabUnselected(tab);
                        setTabColorFilter(tab, R.color.tabUnselectedIconColor);
                    }

                    @Override
                    public void onTabReselected(final TabLayout.Tab tab) {
                        super.onTabReselected(tab);
                    }
                }
        );
        return tabLayout;
    }

    private void setTabColorFilter(final TabLayout.Tab pTab, final int colorId) {
        pTab.getIcon().setColorFilter(ContextCompat.getColor(mContext, colorId),
                PorterDuff.Mode.SRC_IN);
    }
}

//Translate
//https://translate.yandex.net/api/v1.5/tr.json/translate?key=trnsl.1.1.20170420T184415Z.75b001edaf6d1442.0822f0d810c93cfff4d0b6a6b1dd8260f25a9c89&lang=en-ru&text=i will work hard

//List of available languages
//https://translate.yandex.net/api/v1.5/tr.json/getLangs?key=trnsl.1.1.20170420T184415Z.75b001edaf6d1442.0822f0d810c93cfff4d0b6a6b1dd8260f25a9c89&ui=en

//Detect language by inputted text
//https://translate.yandex.net/api/v1.5/tr.json/detect?key=trnsl.1.1.20170420T184415Z.75b001edaf6d1442.0822f0d810c93cfff4d0b6a6b1dd8260f25a9c89&text=работает