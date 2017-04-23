package com.chelsenok.translator.database;

public class TranslatorFabric {

    private static final TranslatorFabric ourInstance = new TranslatorFabric();

    public static TranslatorFabric getInstance() {
        return ourInstance;
    }

    public RecentlyUsedBase getRecentlyUsedInstance() {
        return RecentlyUsedBase.getInstance();
    }

    public HistoryBase getHistoryInstance() {
        return HistoryBase.getInstance();
    }

    public FavoritesBase getFavoritesInstance() {
        return FavoritesBase.getInstance();
    }

    private TranslatorFabric() {
    }
}
