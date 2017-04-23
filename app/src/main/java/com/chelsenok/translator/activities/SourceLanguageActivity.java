package com.chelsenok.translator.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.chelsenok.translator.R;

public class SourceLanguageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_source_language);
        initializeOfflineTranslation();
        initializeDetectLanguages();
    }

    private void initializeDetectLanguages() {
        final FrameLayout viewDetect = (FrameLayout) findViewById(R.id.detect_language);
        ((TextView) viewDetect.findViewById(R.id.tv))
                .setText("Detect languages");
        viewDetect.addView(new Switch(this),
                new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        Gravity.RIGHT | Gravity.CENTER_VERTICAL));
    }

    private void initializeOfflineTranslation() {
        final ImageView viewTranslation = (ImageView) findViewById(R.id.offline_translation)
                .findViewById(R.id.content);
        viewTranslation.setImageDrawable(getResources().getDrawable(R.drawable.ic_flight));
        viewTranslation.setVisibility(View.VISIBLE);
        ((TextView) findViewById(R.id.offline_translation).findViewById(R.id.tv))
                .setText("Offline translation");
    }

    public void onBackClick(final View view) {
        finish();
    }
}
