package com.chelsenok.translator.database;

public class FavoritesDb {

    private static final FavoritesDb ourInstance = new FavoritesDb();

    static FavoritesDb getInstance() {
        return ourInstance;
    }

    private FavoritesDb() {
    }
}
