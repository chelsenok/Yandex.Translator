package com.chelsenok.translator.utils;

import android.support.annotation.Nullable;

import com.chelsenok.translator.asynctasks.RetrieveJsonTask;

import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class YandexApiManager {

    private final String TRANSLATE_PATH = "https://translate.yandex.net/api/v1.5/tr.json/translate?";
    private final String LANGS_PATH = "https://translate.yandex.net/api/v1.5/tr.json/getLangs?";
    private final String DETECT_PATH = "https://translate.yandex.net/api/v1.5/tr.json/detect?";
    private final String KEY = "&key=trnsl.1.1.20170420T184415Z.75b001edaf6d1442.0822f0d810c93cfff4d0b6a6b1dd8260f25a9c89";
    private final String UI = "&ui=";
    private final String TEXT = "&text=";
    private final String LANG = "&lang=";
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
