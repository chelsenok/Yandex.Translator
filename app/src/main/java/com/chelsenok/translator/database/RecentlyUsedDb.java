package com.chelsenok.translator.database;

public class RecentlyUsedDb {

    private static final RecentlyUsedDb ourInstance = new RecentlyUsedDb();

    static RecentlyUsedDb getInstance() {
        return ourInstance;
    }

    private RecentlyUsedDb() {
    }

    public boolean isEmpty() {
        return true;
    }
}
