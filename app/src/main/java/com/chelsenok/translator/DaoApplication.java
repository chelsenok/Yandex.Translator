package com.chelsenok.translator;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.chelsenok.translator.backend.TranslationResultObservable;
import com.chelsenok.translator.backend.dao.DaoMaster;
import com.chelsenok.translator.backend.dao.TranslationResultDao;

public class DaoApplication extends Application {

    private static TranslationResultObservable sHistoryDao;
    private static TranslationResultObservable sFavoritesDao;

    public static TranslationResultObservable getHistoryObservable() {
        return sHistoryDao;
    }

    public static TranslationResultObservable getFavoriteObservable() {
        return sFavoritesDao;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sHistoryDao = new TranslationResultObservable(getTranslationResultDao("history"));
        sFavoritesDao = new TranslationResultObservable(getTranslationResultDao("favorites"));
    }

    private TranslationResultDao getTranslationResultDao(final String dbName) {
        final DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, dbName);
        final SQLiteDatabase db = helper.getWritableDatabase();
        final DaoMaster master = new DaoMaster(db);
        return master.newSession().getTranslationResultDao();
    }

}
