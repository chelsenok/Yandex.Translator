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
import com.chelsenok.translator.language.LanguageTypes;

import java.util.List;

public class LanguagesAdapter extends
        RecyclerView.Adapter<LanguagesAdapter.ViewHolder> {

    private static final int DEF_VALUE = 0xFF00FF;
    private final List<Language> mLanguages;
    private final LanguageTypes mType;
    private final Context mContext;

    public LanguagesAdapter(final Context context, final List<Language> languages,
                            final LanguageTypes type) {
        mLanguages = languages;
        mType = type;
        mContext = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView languageTextView;
        public ImageView acceptIcon;
        public View itemView;

        public ViewHolder(final View itemView) {
            super(itemView);
            this.itemView = itemView;
            languageTextView = (TextView) itemView.findViewById(R.id.tv);
            acceptIcon = (ImageView) itemView.findViewById(R.id.content);
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
        final TextView textView = viewHolder.languageTextView;
        textView.setText(language.fullName);
        if (language.equals(mType.getLanguage())) {
            viewHolder.acceptIcon.setVisibility(View.VISIBLE);
            viewHolder.itemView.setBackgroundColor(mContext
                    .getResources().getColor(R.color.rv_item));
        }
    }

    @Override
    public void onViewDetachedFromWindow(final LanguagesAdapter.ViewHolder viewHolder) {
        super.onViewDetachedFromWindow(viewHolder);
        viewHolder.acceptIcon.setVisibility(View.INVISIBLE);
        final TypedValue outValue = new TypedValue();
        mContext.getTheme().resolveAttribute(android.R.attr.selectableItemBackground, outValue, true);
        viewHolder.itemView.setBackgroundResource(outValue.resourceId);
    }

    @Override
    public int getItemCount() {
        return mLanguages.size();
    }
}