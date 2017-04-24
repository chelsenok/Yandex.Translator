package com.chelsenok.translator.utils;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class SharedPreferenceManager {

    private final SharedPreferences sPref;

    public SharedPreferenceManager(final Context context,
                                   final String tableName) {
        sPref = context.getSharedPreferences(tableName, MODE_PRIVATE);
    }

    public String getString(final String key, final String defaultValue) {
        return sPref.getString(key, defaultValue);
    }

    public Integer getInt(final String key, final int defaultValue) {
        return sPref.getInt(key, defaultValue);
    }

    public void putString(final String key, final String value) {
        sPref.edit().putString(key, value).apply();
    }

    public boolean getBoolean(final String key, final boolean defaultValue) {
        return sPref.getBoolean(key, defaultValue);
    }

    public void putBoolean(final String key, final boolean value) {
        sPref.edit().putBoolean(key, value).apply();
    }

    public void putInt(final String key, final int value) {
        sPref.edit().putInt(key, value).apply();
    }
}
