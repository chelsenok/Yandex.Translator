package com.chelsenok.translator.utils;

import android.content.Context;
import android.support.v4.util.Pair;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class LanguageManager {

    private static LanguageManager ourInstance;

    public static LanguageManager getInstance() {
        if (ourInstance == null) {
            initialize(sContext);
        }
        return ourInstance;
    }

    private Pair[] mAvailableLanguagePairs;
    private Pair<Language, Language> mCurrentPair = new Pair<>(null, null);
    private List<Language> mLanguageMap;
    private static boolean mSuccessed;
    private static Context sContext;
    private static final String AVAILABLE_ARRAY = "dirs";
    private static final String LANGS_MAP = "langs";
    private static final String LANGUAGE = "language";
    private static final String NATIVE_LANGUAGE = "native_language";
    private static final String FOREIGN_LANGUAGE = "foreign_language";

    private LanguageManager(final Context context) {
        final JSONObject langsJson = new YandexApiManager().getLangsJson(Locale.getDefault().getLanguage());
        try {
            initializeAvailableLanguagePairs(langsJson);
            initializeLanguageMap(langsJson);
            initializeCurrentPair(context);
            mSuccessed = true;
        } catch (final Exception ignored) {
            mSuccessed = false;
        }
    }

    private void initializeAvailableLanguagePairs(final JSONObject langsJson) throws JSONException {
        final JSONArray array = langsJson.getJSONArray(AVAILABLE_ARRAY);
        mAvailableLanguagePairs = new Pair[array.length()];
        for (int i = 0; i < array.length(); i++) {
            final String current = (String) array.get(i);
            final String[] langs = current.split("-");
            mAvailableLanguagePairs[i] = new Pair<>(langs[0], langs[1]);
        }
    }

    private void initializeLanguageMap(final JSONObject langsJson) throws JSONException {
        mLanguageMap = new ArrayList<>();
        final JSONObject langs = langsJson.getJSONObject(LANGS_MAP);
        final Iterator<String> keys = langs.keys();
        while (keys.hasNext()) {
            final String currentKey = keys.next();
            mLanguageMap.add(new Language(currentKey, (String) langs.get(currentKey)));
        }
    }

    private void initializeCurrentPair(final Context context) {
        final SharedPreferenceManager manager = new SharedPreferenceManager(context, LANGUAGE);
        mCurrentPair = new Pair<>(
                getLanguageByFullName(manager.getString(NATIVE_LANGUAGE,
                        getLanguageByShortName("ru").fullName)),
                getLanguageByFullName(manager.getString(FOREIGN_LANGUAGE,
                        getLanguageByShortName("en").fullName))
        );
    }

    private Language getLanguageByShortName(final String shortName) {
        for (final Language language :
                mLanguageMap) {
            if (language.shortName.equals(shortName)) {
                return language;
            }
        }
        return null;
    }

    public static boolean initialize(final Context context) {
        sContext = context;
        ourInstance = new LanguageManager(sContext);
        return mSuccessed;
    }

    public void setNativeLanguage(final String fullName) {
        final Language language = getLanguageByFullName(fullName);
        if (language.equals(mCurrentPair.second)) {
            swapLanguages();
        } else {
            mCurrentPair = new Pair<>(language, mCurrentPair.second);
        }
    }

    public void setForeignLanguage(final String fullName) {
        final Language language = getLanguageByFullName(fullName);
        if (language.equals(mCurrentPair.first)) {
            swapLanguages();
        } else {
            mCurrentPair = new Pair<>(mCurrentPair.first, getLanguageByFullName(fullName));
        }
    }

    public void swapLanguages() {
        mCurrentPair = new Pair<>(mCurrentPair.second, mCurrentPair.first);
    }

    public Language getNativeLanguage() {
        return getLanguage(mCurrentPair.first, "ru", "Russian");
    }

    public Language getForeignLanguage() {
        return getLanguage(mCurrentPair.second, "en", "English");
    }

    private Language getLanguage(final Language current, final String shortName,
                                 final String fullName) {
        if (current != null) {
            return current;
        }
        return new Language(shortName, fullName);
    }

    private Language getLanguageByFullName(final String fullName) {
        for (final Language language :
                mLanguageMap) {
            if (language.fullName.equals(fullName)) {
                return language;
            }
        }
        return null;
    }
}
