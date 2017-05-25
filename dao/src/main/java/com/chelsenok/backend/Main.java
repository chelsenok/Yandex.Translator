package com.chelsenok.backend;

import org.greenrobot.greendao.generator.DaoGenerator;
import org.greenrobot.greendao.generator.Entity;
import org.greenrobot.greendao.generator.Schema;

public final class Main {

    public static void main(final String... args) throws Exception {
        final Schema schema = new Schema(1, "com.chelsenok.translator.dao");
        schema.enableKeepSectionsByDefault();
        createDatabase(schema);
        final DaoGenerator generator = new DaoGenerator();
        generator.generateAll(schema, args[0]);
    }

    private static void createDatabase(final Schema schema) {
        final Entity request = schema.addEntity("TranslationResult");
        request.addIdProperty();
        request.addStringProperty("nativeSentence");
        request.addStringProperty("foreignSentence");
        request.addStringProperty("nativeShortcut");
        request.addStringProperty("foreignShortcut");
    }
}
