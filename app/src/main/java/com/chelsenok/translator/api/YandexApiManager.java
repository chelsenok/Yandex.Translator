package com.chelsenok.translator.api;

import android.os.AsyncTask;

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
    private static RetrieveJsonTask mJsonTask;

    public static AsyncTask.Status getStatus() {
        return mJsonTask.getStatus();
    }

    public JSONObject getLangsJson(final String lang) {
        mQueryUrl = LANGS_PATH + KEY + UI + lang;
        return getExecution();
    }

    public JSONObject getDetectedJson(final String text) {
        mQueryUrl = DETECT_PATH + KEY + TEXT + text;
        return getExecution();
    }

    public JSONObject getTranslationJson(final String nativeLang,
                                         final String foreignLang, final String text) {
        mQueryUrl = TRANSLATE_PATH + KEY + TEXT + text + LANG + nativeLang + "-" + foreignLang;
        return getExecution();
    }

    public JSONObject getTranslationJson(final String foreignLang, final String text) {
        mQueryUrl = TRANSLATE_PATH + KEY + TEXT + text + LANG + foreignLang;
        return getExecution();
    }

    private JSONObject getExecution() {
        try {
            mJsonTask = new RetrieveJsonTask();
            return mJsonTask.execute(mQueryUrl).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return null;
        }
    }
}