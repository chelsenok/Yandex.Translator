package com.chelsenok.translator.database;

public class DbFabric {

    private static final DbFabric ourInstance = new DbFabric();

    public static DbFabric getInstance() {
        return ourInstance;
    }

    public RecentlyUsedDb getRecentlyUsedDb() {
        return RecentlyUsedDb.getInstance();
    }

    public HistoryDb getHistoryDb() {
        return HistoryDb.getInstance();
    }

    public FavoritesDb getFavoritesDb() {
        return FavoritesDb.getInstance();
    }

    private DbFabric() {
    }
}
