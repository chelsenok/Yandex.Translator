package com.chelsenok.translator.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chelsenok.translator.R;
import com.chelsenok.translator.dao.TranslationResult;
import com.chelsenok.translator.dao.TranslationResultDao;

import java.util.List;

public class TranslationsAdapter extends RecyclerView.Adapter<TranslationsAdapter.ViewHolder> {

    private final List<TranslationResult> mResults;
    private final TranslationResultDao mDao;

    public TranslationsAdapter(final TranslationResultDao dao) {
        mDao = dao;
        mResults = dao.loadAll();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TranslationResult translationResult;
        private final TextView nativeSentTextView;
        private final TextView foreignSentTextView;
        private final TextView nativeShortcutTextView;
        private final TextView foreignShortcutTextView;

        public ViewHolder(final View itemView) {
            super(itemView);
            itemView.setOnLongClickListener(v -> {

                return false;
            });
            nativeSentTextView = (TextView) itemView.findViewById(R.id.tv_native_sent);
            nativeShortcutTextView = (TextView) itemView.findViewById(R.id.tv_native_shortcut);
            foreignSentTextView = (TextView) itemView.findViewById(R.id.tv_foreign_sent);
            foreignShortcutTextView = (TextView) itemView.findViewById(R.id.tv_foreign_shortcut);
        }

        public void setTranslationResult(final TranslationResult translationResult) {
            this.translationResult = translationResult;
            this.nativeSentTextView.setText(this.translationResult.getNativeSentence());
            this.foreignSentTextView.setText(this.translationResult.getForeignSentence());
            this.nativeShortcutTextView.setText(this.translationResult.getNativeShortcut());
            this.foreignShortcutTextView.setText(this.translationResult.getForeignShortcut());
        }
    }

    @Override
    public TranslationsAdapter.ViewHolder onCreateViewHolder(final ViewGroup parent,
                                                             final int viewType) {
        final Context context = parent.getContext();
        final LayoutInflater inflater = LayoutInflater.from(context);
        final View translationResultView = inflater.inflate(R.layout.item_translation_result, parent, false);
        return new ViewHolder(translationResultView);
    }

    @Override
    public void onBindViewHolder(final TranslationsAdapter.ViewHolder viewHolder, final int position) {
        final TranslationResult translationResult = mResults.get(position);
        viewHolder.setTranslationResult(translationResult);
    }

    @Override
    public int getItemCount() {
        return mResults.size();
    }
}