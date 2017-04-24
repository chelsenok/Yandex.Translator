package com.chelsenok.translator.language;

import android.content.Context;

import com.chelsenok.translator.api.YandexApiManager;
import com.chelsenok.translator.utils.SharedPreferenceManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;

public final class LanguageManager {

    private static LanguageManager ourInstance;
    private boolean mDetectLanguagesEnabled;

    public static LanguageManager getInstance() {
        if (ourInstance == null) {
            initialize(sContext);
        }
        return ourInstance;
    }

    private ArrayList<Language> mLanguageMap;
    private static boolean mSuccessed;
    private static Context sContext;
    private static final String LANGS_MAP = "langs";
    private static final String LANGUAGE = "language";

    private LanguageManager(final Context context) {
        try {
            final JSONObject langsJson = new YandexApiManager().getLangsJson(Locale.getDefault().getLanguage());
            initializeLanguageMap(langsJson.getJSONObject(LANGS_MAP));
            initializeCurrentPair(context);
            mSuccessed = true;
        } catch (final Exception ignored) {
            mSuccessed = false;
        }
    }

    public ArrayList<Language> getAvailableLanguages() {
        return mLanguageMap;
    }

    public boolean allow(final Language language) {
        for (final LanguageTypes type :
                LanguageTypes.values()) {
            if (language.equals(type.getLanguage())) {
                return false;
            }
        }
        return true;
    }

    public void setDetectLanguagesEnabled(final boolean detectLanguagesEnabled) {
        mDetectLanguagesEnabled = detectLanguagesEnabled;
    }

    public boolean getDetectLanguagesEnabled() {
        return mDetectLanguagesEnabled;
    }

    private void initializeLanguageMap(final JSONObject langs) throws JSONException {
        mLanguageMap = new ArrayList<>();
        final Iterator<String> keys = langs.keys();
        while (keys.hasNext()) {
            final String currentKey = keys.next();
            mLanguageMap.add(new Language(currentKey, (String) langs.get(currentKey)));
        }
    }

    private void initializeCurrentPair(final Context context) {
        final SharedPreferenceManager manager = new SharedPreferenceManager(context, LANGUAGE);
        for (final LanguageTypes type :
                LanguageTypes.values()) {
            type.setLanguage(
                    getLanguageByShortName(manager.getString(
                            type.getName(),
                            type.getDefaultShortName()
                    ))
            );
        }
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

    public static boolean getSuccessed() {
        return mSuccessed;
    }

    public static void initialize(final Context context) {
        if (!mSuccessed) {
            sContext = context;
            ourInstance = new LanguageManager(sContext);
        }
    }

    public void setLanguage(final LanguageTypes type, final Language language) {
        type.setLanguage(language);
        final SharedPreferenceManager manager = new SharedPreferenceManager(sContext, LANGUAGE);
        manager.putString(type.getName(), type.getLanguage().shortName);
    }

    public void swapLanguages() {
        final Language foreign = LanguageTypes.Foreign.getLanguage();
        setLanguage(LanguageTypes.Foreign, LanguageTypes.Native.getLanguage());
        setLanguage(LanguageTypes.Native, foreign);
    }

    public Language getLanguage(final LanguageTypes type) {
        return type.getLanguage();
    }
}
