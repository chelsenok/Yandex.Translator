package com.chelsenok.translator.api;

abstract class ResponseComponents {

    static final String TRANSLATE_PATH = "https://translate.yandex.net/api/v1.5/tr.json/translate?";
    static final String LANGS_PATH = "https://translate.yandex.net/api/v1.5/tr.json/getLangs?";
    static final String DETECT_PATH = "https://translate.yandex.net/api/v1.5/tr.json/detect?";
    static final String KEY = "&key=trnsl.1.1.20170420T184415Z.75b001edaf6d1442.0822f0d810c93cfff4d0b6a6b1dd8260f25a9c89";
    static final String UI = "&ui=";
    static final String TEXT = "&text=";
    static final String LANG = "&lang=";
}


//Translate
//https://translate.yandex.net/api/v1.5/tr.json/translate?key=trnsl.1.1.20170420T184415Z.75b001edaf6d1442.0822f0d810c93cfff4d0b6a6b1dd8260f25a9c89&lang=en-ru&text=i will work hard

//List of available languages
//https://translate.yandex.net/api/v1.5/tr.json/getLangs?key=trnsl.1.1.20170420T184415Z.75b001edaf6d1442.0822f0d810c93cfff4d0b6a6b1dd8260f25a9c89&ui=en