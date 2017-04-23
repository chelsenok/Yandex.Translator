package com.chelsenok.translator.language;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.chelsenok.translator.R;

public enum LanguageTypes {

    Native {

        private Language mLanguage;

        @Override
        public String getName() {
            return "NATIVE";
        }

        @Override
        public TextView getTextView(final View viewParent) {
            final TextView textView = (TextView) viewParent.findViewById(R.id.tv_native);
            textView.setText(mLanguage.fullName);
            return textView;
        }

        @Override
        Language getLanguage() {
            return new Language(mLanguage.shortName, mLanguage.fullName);
        }

        @Override
        void setLanguage(final Language language) {
            mLanguage = language;
        }

        @Override
        String getDefaultShortName() {
            return "en";
        }

        @Override
        public FrameLayout getFrameLayout(final View viewParent) {
            return (FrameLayout) viewParent.findViewById(R.id.fl_native);
        }
    },

    Foreign {

        private Language mLanguage;

        @Override
        public String getName() {
            return "FOREIGN";
        }

        @Override
        public TextView getTextView(final View viewParent) {
            final TextView textView = (TextView) viewParent.findViewById(R.id.tv_foreign);
            textView.setText(mLanguage.fullName);
            return textView;
        }

        @Override
        Language getLanguage() {
            return new Language(mLanguage.shortName, mLanguage.fullName);
        }

        @Override
        void setLanguage(final Language language) {
            mLanguage = language;
        }

        @Override
        String getDefaultShortName() {
            return "ru";
        }

        @Override
        public FrameLayout getFrameLayout(final View viewParent) {
            return (FrameLayout) viewParent.findViewById(R.id.fl_foreign);
        }

    };

    public abstract String getName();

    public abstract TextView getTextView(View viewParent);

    abstract Language getLanguage();

    abstract void setLanguage(Language language);

    abstract String getDefaultShortName();

    public abstract FrameLayout getFrameLayout(View viewParent);

    public static final String EXTRA = "extra";

    public static LanguageTypes getTypeByName(final String name) {
        for (final LanguageTypes type :
                LanguageTypes.values()) {
            if (type.getName().equals(name)) {
                return type;
            }
        }
        return null;
    }
}
