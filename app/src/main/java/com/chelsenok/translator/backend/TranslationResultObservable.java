package com.chelsenok.translator.backend;

import com.chelsenok.translator.backend.dao.TranslationResultDao;

import java.util.Observable;

public class TranslationResultObservable extends Observable {

    private final TranslationResultDao mTranslationResultDao;

    public TranslationResultObservable(final TranslationResultDao dao) {
        super();
        mTranslationResultDao = dao;
    }

    public TranslationResultDao getTranslationResultDao() {
        setChanged();
        return mTranslationResultDao;
    }
}
