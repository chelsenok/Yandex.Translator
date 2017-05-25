package com.chelsenok.translator.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.chelsenok.translator.R;

public class SplashActivity extends AppCompatActivity {

    private static final long DURATION = 500;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        final Context context = this;
        final Class aClass = MainActivity.class;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                final Intent intent = new Intent(context, aClass);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
            }
        }, DURATION);
    }
}

//fade_in.xml
//<?xml version="1.0" encoding="utf-8"?>
//<alpha xmlns:android="http://schemas.android.com/apk/res/android"
//        android:interpolator="@android:anim/accelerate_interpolator"
//        android:fromAlpha="0.0" android:toAlpha="1.0"
//        android:duration="500" />

//fade_out.xml
//<?xml version="1.0" encoding="utf-8"?>
//<alpha xmlns:android="http://schemas.android.com/apk/res/android"
//        android:interpolator="@android:anim/accelerate_interpolator"
//        android:fromAlpha="1.0" android:toAlpha="0.0"
//        android:fillAfter="true"
//        android:duration="500" />
