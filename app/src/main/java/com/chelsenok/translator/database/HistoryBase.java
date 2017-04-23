package com.chelsenok.translator.database;

public class HistoryBase {

    private static final HistoryBase ourInstance = new HistoryBase();

    static HistoryBase getInstance() {
        return ourInstance;
    }

    private HistoryBase() {
    }
}
