package com.chelsenok.translator.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.chelsenok.translator.database.TranslatorFabric;
import com.chelsenok.translator.language.Language;
import com.chelsenok.translator.language.LanguageManager;
import com.chelsenok.translator.language.LanguageTypes;
import com.chelsenok.translator.ui.DividerItemDecoration;

import java.util.ArrayList;

public class SourceLanguageActivity extends AppCompatActivity {

    ArrayList<Language> mLanguages;
    private String mTypeName;
    private RecyclerView mRvAllLanguages;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_source_language);
        initializeOfflineTranslation();
        initializeDetectLanguages();
        checkRecentlyUsedLanguages();
        mRvAllLanguages = initializeRvAllLanguages();
    }

    private RecyclerView initializeRvAllLanguages() {
        final RecyclerView rvAllLanguages = (RecyclerView) findViewById(R.id.rv_all_langs);
        mLanguages = LanguageManager.getInstance().getAvailableLanguages();
        final LanguagesAdapter languagesAdapter = new LanguagesAdapter(this, mLanguages,
                LanguageTypes.getTypeByName(mTypeName));
        rvAllLanguages.setAdapter(languagesAdapter);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvAllLanguages.setLayoutManager(layoutManager);
//        rvAllLanguages.setNestedScrollingEnabled(false);
        rvAllLanguages.addItemDecoration(new DividerItemDecoration(this));
        return rvAllLanguages;
    }

    private void checkRecentlyUsedLanguages() {
        if (TranslatorFabric.getInstance().getRecentlyUsedInstance().isEmpty()) {
            findViewById(R.id.ll_recently_used).setVisibility(View.GONE);
        }
    }

    private void initializeDetectLanguages() {
        final LinearLayout viewDetect = (LinearLayout) findViewById(R.id.detect_language);
        mTypeName = getIntent().getStringExtra(LanguageTypes.EXTRA);
        if (mTypeName.equals(LanguageTypes.Foreign.getName())) {
            viewDetect.setVisibility(View.GONE);
            return;
        }
        viewDetect.findViewById(R.id.content).setVisibility(View.GONE);
        ((TextView) viewDetect.findViewById(R.id.tv))
                .setText(R.string.detect_languages);
        viewDetect.addView(new Switch(this));
    }

    private void initializeOfflineTranslation() {
        final ImageView viewTranslation = (ImageView) findViewById(R.id.offline_translation)
                .findViewById(R.id.content);
        viewTranslation.setImageDrawable(getResources().getDrawable(R.drawable.ic_flight));
        viewTranslation.setVisibility(View.VISIBLE);
        ((TextView) findViewById(R.id.offline_translation).findViewById(R.id.tv))
                .setText(R.string.offline_translation);
    }

    public void onBackClick(final View view) {
        finish();
    }
}
