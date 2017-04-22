package com.chelsenok.translator.utils;

import android.support.v4.util.Pair;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class LanguageManager {

    private static LanguageManager ourInstance;

    public static LanguageManager getInstance() {
        if (ourInstance == null) {
            initialize();
        }
        return ourInstance;
    }

    private Pair[] mAvailableLanguagePairs;
    private Pair<Language, Language> mCurrentPair = new Pair<>(null, null);
    private List<Language> mLanguageMap;
    private static boolean mSuccessed;
    private static final String AVAILABLE_ARRAY = "dirs";
    private static final String LANGS_MAP = "langs";
    private static final String LANGUAGE = "language";
    private static final String NATIVE_LANGUAGE = "native_language";
    private static final String FOREIGN_LANGUAGE = "foreign_language";

    private LanguageManager() {
        final JSONObject langsJson = new YandexApiManager().getLangsJson(Locale.getDefault().getLanguage());
        try {
            final JSONArray array = langsJson.getJSONArray(AVAILABLE_ARRAY);
            mAvailableLanguagePairs = new Pair[array.length()];
            for (int i = 0; i < array.length(); i++) {
                final String current = (String) array.get(i);
                final String[] langs = current.split("-");
                mAvailableLanguagePairs[i] = new Pair<>(langs[0], langs[1]);
            }
            mLanguageMap = new ArrayList<>();
            final JSONObject langs = langsJson.getJSONObject(LANGS_MAP);
            final Iterator<String> keys = langs.keys();
            while (keys.hasNext()) {
                final String currentKey = keys.next();
                mLanguageMap.add(new Language(currentKey, (String) langs.get(currentKey)));
            }
            mSuccessed = true;
        } catch (final Exception ignored) {
            mSuccessed = false;
        }
    }

    public static boolean initialize() {
        ourInstance = new LanguageManager();
        Language nativeLang = getLanguage(new SharedPreferenceManager(, LANGUAGE)
                .getString(NATIVE_LANGUAGE));
        return mSuccessed;
    }

    public void setNativeLanguage(final String fullName) {
        mCurrentPair = new Pair<>(getLanguage(fullName), mCurrentPair.second);
    }

    public void setForeignLanguage(final String fullName) {
        mCurrentPair = new Pair<>(mCurrentPair.first, getLanguage(fullName));
    }

    private static Language getLanguage(final String fullName) {
        for (Language language :
                mLanguageMap) {
            if (language.fullName.equals(fullName)) {
                return language;
            }
        }
        return null;
    }
}
