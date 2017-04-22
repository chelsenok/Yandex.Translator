package com.chelsenok.translator.activities;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.chelsenok.translator.R;
import com.chelsenok.translator.adapters.pagers.MainPagerAdapter;
import com.chelsenok.translator.utils.Language;
import com.chelsenok.translator.utils.LanguageManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final MainPagerAdapter mainPagerAdapter = new MainPagerAdapter(getSupportFragmentManager());
        final TabLayout mainTabLayout = getTabLayout(R.id.main_viewpager, R.id.main_tabs,
                mainPagerAdapter);
        for (int i = 0; i < mainPagerAdapter.getCount(); i++) {
            setTabColorFilter(mainTabLayout.getTabAt(i).setIcon(mainPagerAdapter.getContentAt(i)),
                    R.color.tabUnselectedColor);
        }
        setTabColorFilter(mainTabLayout.getTabAt(0), R.color.tabSelectedColor).select();
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

    public void onLanguageClick(View view) {
    }

    public void onSwapLanguageClick(View view) {
    }
}

//Translate
//https://translate.yandex.net/api/v1.5/tr.json/translate?key=trnsl.1.1.20170420T184415Z.75b001edaf6d1442.0822f0d810c93cfff4d0b6a6b1dd8260f25a9c89&lang=en-ru&text=i will work hard

//List of available languages
//https://translate.yandex.net/api/v1.5/tr.json/getLangs?key=trnsl.1.1.20170420T184415Z.75b001edaf6d1442.0822f0d810c93cfff4d0b6a6b1dd8260f25a9c89&ui=en

//Detect language by inputted text
//https://translate.yandex.net/api/v1.5/tr.json/detect?key=trnsl.1.1.20170420T184415Z.75b001edaf6d1442.0822f0d810c93cfff4d0b6a6b1dd8260f25a9c89&text=работает