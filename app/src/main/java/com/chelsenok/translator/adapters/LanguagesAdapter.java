package com.chelsenok.translator.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chelsenok.translator.R;
import com.chelsenok.translator.language.Language;
import com.chelsenok.translator.language.LanguageManager;
import com.chelsenok.translator.language.LanguageTypes;

import java.util.List;

public class LanguagesAdapter extends RecyclerView.Adapter<LanguagesAdapter.ViewHolder> {

    private final List<Language> mLanguages;
    private static LanguageTypes sType;
    private static Context sContext;
    private static LanguagesAdapter.ViewHolder sCurrentVh;

    public LanguagesAdapter(final Context context, final List<Language> languages,
                            final LanguageTypes type) {
        mLanguages = languages;
        sType = type;
        sContext = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private Language language;
        private final TextView languageTextView;
        private final ImageView acceptIcon;
        private final View itemView;

        public ViewHolder(final View itemView) {
            super(itemView);
            this.itemView = itemView;
            languageTextView = (TextView) itemView.findViewById(R.id.tv);
            acceptIcon = (ImageView) itemView.findViewById(R.id.content);
            this.itemView.setOnClickListener(v -> {
                LanguageManager.getInstance().setLanguage(sType, language);
                unSelectItem(sCurrentVh);
                sCurrentVh = this;
                selectItem();
            });
        }

        public void setLanguage(final Language language) {
            this.language = language;
            this.languageTextView.setText(this.language.fullName);
        }
    }

    @Override
    public LanguagesAdapter.ViewHolder onCreateViewHolder(final ViewGroup parent,
                                                          final int viewType) {
        final Context context = parent.getContext();
        final LayoutInflater inflater = LayoutInflater.from(context);
        final View languageView = inflater.inflate(R.layout.item_language, parent, false);
        return new ViewHolder(languageView);
    }

    @Override
    public void onBindViewHolder(final LanguagesAdapter.ViewHolder viewHolder, final int position) {
        final Language language = mLanguages.get(position);
        viewHolder.setLanguage(language);
        if (language.equals(LanguageManager.getInstance().getLanguage(sType))) {
            sCurrentVh = viewHolder;
            selectItem();
        }
    }

    private static void selectItem() {
        sCurrentVh.acceptIcon.setVisibility(View.VISIBLE);
        sCurrentVh.itemView.setBackgroundColor(sContext
                .getResources().getColor(R.color.rv_item));
    }

    private static void unSelectItem(final LanguagesAdapter.ViewHolder viewHolder) {
        viewHolder.acceptIcon.setVisibility(View.INVISIBLE);
        final TypedValue outValue = new TypedValue();
        sContext.getTheme().resolveAttribute(android.R.attr.selectableItemBackground, outValue, true);
        viewHolder.itemView.setBackgroundResource(outValue.resourceId);
    }

    @Override
    public void onViewDetachedFromWindow(final LanguagesAdapter.ViewHolder viewHolder) {
        super.onViewDetachedFromWindow(viewHolder);
        unSelectItem(viewHolder);
    }

    @Override
    public int getItemCount() {
        return mLanguages.size();
    }
}