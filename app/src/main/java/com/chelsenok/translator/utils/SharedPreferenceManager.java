package com.chelsenok.translator.utils;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class SharedPreferenceManager {

    private static SharedPreferences sPref;

    public SharedPreferenceManager(final Context context,
                                   final String tableName) {
        sPref = context.getSharedPreferences(tableName, MODE_PRIVATE);
    }

    public String getString(final String key) {
        return sPref.getString(key, null);
    }

    public void putString(final String key, final String value) {
        sPref.edit().putString(key, value).apply();
    }
}
