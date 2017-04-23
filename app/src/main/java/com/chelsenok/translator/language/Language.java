package com.chelsenok.translator.language;

public class Language {
    public String shortName;
    public String fullName;

    public Language(final String shortName, final String fullName) {
        this.shortName = shortName;
        this.fullName = fullName;
    }

    @Override
    public boolean equals(final Object obj) {
        try {
            final Language language = (Language) obj;
            if (this.fullName.equals(language.fullName)
                    && this.shortName.equals(language.shortName)) {
                return true;
            }
        } catch (final Exception ignored) {
        }
        return false;
    }
}
