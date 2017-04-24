package com.chelsenok.translator.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.chelsenok.translator.R;
import com.chelsenok.translator.adapters.LanguagesAdapter;
import com.chelsenok.translator.database.DbFabric;
import com.chelsenok.translator.language.Language;
import com.chelsenok.translator.language.LanguageManager;
import com.chelsenok.translator.language.LanguageTypes;
import com.chelsenok.translator.ui.DividerItemDecoration;
import com.chelsenok.translator.utils.ActivityViewManager;
import com.chelsenok.translator.utils.SharedPreferenceManager;

import java.util.ArrayList;

public class SourceLanguageActivity extends AppCompatActivity
        implements Handler.Callback {

    private String mTypeName;
    private boolean mEnabled;
    private final String LANG_TABLE = "source_lang";
    private final String SWITCH_ENABLED = "switch_enabled";
    private SharedPreferenceManager mPreferenceManager;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_source_language);
        ActivityViewManager.setTaskDescription(this);
        mPreferenceManager = new SharedPreferenceManager(this, LANG_TABLE);
        mEnabled = mPreferenceManager.getBoolean(SWITCH_ENABLED, false);
        initializeOfflineTranslation();
        initializeDetectLanguages();
        initializeRecentlyUsedLanguages();
        initializeRvAllLanguages(LanguageManager.getInstance().getAvailableLanguages());
        final NestedScrollView scrollView = (NestedScrollView) findViewById(R.id.scroll_view);
        scrollView.smoothScrollTo(0, 0);
    }

    private RecyclerView initializeRvAllLanguages(final ArrayList<Language> availableLanguages) {
        final RecyclerView rvAllLanguages = (RecyclerView) findViewById(R.id.rv_all_langs);
        final LanguagesAdapter languagesAdapter = new LanguagesAdapter(
                this, availableLanguages,
                LanguageTypes.getTypeByName(mTypeName),
                new Handler(this)
        );
        rvAllLanguages.setAdapter(languagesAdapter);
        rvAllLanguages.setLayoutManager(new LinearLayoutManager(this));
        rvAllLanguages.addItemDecoration(new DividerItemDecoration(this));
        rvAllLanguages.setNestedScrollingEnabled(false);
        return rvAllLanguages;
    }

    @Override
    public boolean handleMessage(final Message msg) {
        switch (msg.what) {
            case 0:
                startActivity(new Intent(this, SplashActivity.class));
                finish();
        }
        return true;
    }

    private LinearLayout initializeRecentlyUsedLanguages() {
        final LinearLayout layout = (LinearLayout) findViewById(R.id.ll_recently_used);
        if (DbFabric.getInstance().getRecentlyUsedDb().isEmpty()) {
            layout.setVisibility(View.GONE);
        }
        return layout;
    }

    private LinearLayout initializeDetectLanguages() {
        final LinearLayout viewDetect = (LinearLayout) findViewById(R.id.detect_language);
        mTypeName = getIntent().getStringExtra(LanguageTypes.EXTRA);
        if (mTypeName.equals(LanguageTypes.Native.getName())) {
            viewDetect.findViewById(R.id.content).setVisibility(View.GONE);
            ((TextView) viewDetect.findViewById(R.id.tv))
                    .setText(R.string.detect_languages);
            final Switch detectSwitch = new Switch(this);
            detectSwitch.setChecked(mEnabled);
            viewDetect.addView(detectSwitch);
            viewDetect.setOnClickListener(v -> {
                mEnabled = !mEnabled;
                mPreferenceManager.putBoolean(SWITCH_ENABLED, mEnabled);
                detectSwitch.setChecked(mEnabled);
                mPreferenceManager.putBoolean(SWITCH_ENABLED, mEnabled);
                LanguageManager.getInstance().setDetectLanguagesEnabled(mEnabled);
            });
        } else {
            viewDetect.setVisibility(View.GONE);
        }
        return viewDetect;
    }

    private LinearLayout initializeOfflineTranslation() {
        final LinearLayout layout = (LinearLayout) findViewById(R.id.offline_translation);
        final ImageView viewTranslation = (ImageView) layout.findViewById(R.id.content);
        viewTranslation.setImageDrawable(getResources().getDrawable(R.drawable.ic_flight));
        viewTranslation.setVisibility(View.VISIBLE);
        ((TextView) findViewById(R.id.offline_translation).findViewById(R.id.tv))
                .setText(R.string.offline_translation);
        return layout;
    }

    @Override
    public void onBackPressed() {
        onBackClick(null);
    }

    public void onBackClick(final View view) {
        final Message message = new Message();
        message.what = 0;
        handleMessage(message);
    }
}
