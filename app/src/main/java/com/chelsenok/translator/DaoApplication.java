package com.chelsenok.translator;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.chelsenok.translator.dao.DaoMaster;
import com.chelsenok.translator.dao.TranslationResultDao;

public class DaoApplication extends Application {

    private static TranslationResultDao sHistoryDao;
    private static TranslationResultDao sFavoritesDao;

    public static TranslationResultDao getHistoryDao() {
        return sHistoryDao;
    }

    public static TranslationResultDao getFavoritesDao() {
        return sFavoritesDao;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sHistoryDao = getTranslationResultDao("history");
        sFavoritesDao = getTranslationResultDao("favorites");
    }

    private TranslationResultDao getTranslationResultDao(final String dbName) {
        final DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, dbName);
        final SQLiteDatabase db = helper.getWritableDatabase();
        final DaoMaster master = new DaoMaster(db);
        return master.newSession().getTranslationResultDao();
    }

}
