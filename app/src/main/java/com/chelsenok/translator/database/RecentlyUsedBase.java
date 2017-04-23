package com.chelsenok.translator.database;

public class RecentlyUsedBase {

    private static final RecentlyUsedBase ourInstance = new RecentlyUsedBase();

    static RecentlyUsedBase getInstance() {
        return ourInstance;
    }

    private RecentlyUsedBase() {
    }

    public boolean isEmpty() {
        return true;
    }
}
