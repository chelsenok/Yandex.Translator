package com.chelsenok.translator.database;

public class FavoritesBase {

    private static final FavoritesBase ourInstance = new FavoritesBase();

    static FavoritesBase getInstance() {
        return ourInstance;
    }

    private FavoritesBase() {
    }
}
