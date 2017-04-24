package com.chelsenok.translator.database;

public class HistoryDb {

    private static final HistoryDb ourInstance = new HistoryDb();

    static HistoryDb getInstance() {
        return ourInstance;
    }

    private HistoryDb() {
    }
}
