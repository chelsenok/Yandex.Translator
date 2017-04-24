package com.chelsenok.translator.api;

import android.support.annotation.Nullable;

import com.chelsenok.translator.asynctasks.RetrieveJsonTask;

import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

import static com.chelsenok.translator.api.ResponseComponents.DETECT_PATH;
import static com.chelsenok.translator.api.ResponseComponents.KEY;
import static com.chelsenok.translator.api.ResponseComponents.LANG;
import static com.chelsenok.translator.api.ResponseComponents.LANGS_PATH;
import static com.chelsenok.translator.api.ResponseComponents.TEXT;
import static com.chelsenok.translator.api.ResponseComponents.TRANSLATE_PATH;
import static com.chelsenok.translator.api.ResponseComponents.UI;

public class YandexApiManager {
    private String mQueryUrl;

    public JSONObject getLangsJson(final String lang) {
        mQueryUrl = LANGS_PATH + KEY + UI + lang;
        return getExecution();
    }

    public JSONObject getDetectedJson(final String text) {
        mQueryUrl = DETECT_PATH + KEY + TEXT + text;
        return getExecution();
    }

    public JSONObject getTranslationJson(@Nullable final String nativeLang,
                                         final String foreignLang, final String text) {
        mQueryUrl = TRANSLATE_PATH + KEY + TEXT + text + LANG + foreignLang;
        if (nativeLang != null) {
            mQueryUrl += "-" + nativeLang;
        }
        return getExecution();
    }

    private JSONObject getExecution() {
        try {
            return new RetrieveJsonTask().execute(mQueryUrl).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return null;
        }
    }
}