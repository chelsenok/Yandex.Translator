package com.chelsenok.translator.language;

import android.content.Context;

import com.chelsenok.translator.utils.SharedPreferenceManager;
import com.chelsenok.translator.utils.YandexApiManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;

public final class LanguageManager {

    private static LanguageManager ourInstance;

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
        final JSONObject langsJson = new YandexApiManager().getLangsJson(Locale.getDefault().getLanguage());
        try {
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

    public static boolean initialize(final Context context) {
        sContext = context;
        ourInstance = new LanguageManager(sContext);
        return mSuccessed;
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
