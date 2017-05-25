package com.chelsenok.translator.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chelsenok.translator.R;
import com.chelsenok.translator.backend.TranslationResultObservable;
import com.chelsenok.translator.backend.dao.TranslationResult;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class TranslationsAdapter extends RecyclerView.Adapter<TranslationsAdapter.ViewHolder>
        implements Observer {

    private List<TranslationResult> mResults;
    private final TranslationResultObservable mTranslationResultObservable;

    public TranslationsAdapter(final TranslationResultObservable observable) {
        mTranslationResultObservable = observable;
        mResults = observable.getTranslationResultDao().loadAll();
    }

    @Override
    public void update(final Observable o, final Object arg) {
        mResults = mTranslationResultObservable.getTranslationResultDao().loadAll();
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TranslationResult translationResult;
        private final TextView nativeSentTextView;
        private final TextView foreignSentTextView;
        private final TextView nativeShortcutTextView;
        private final TextView foreignShortcutTextView;
        private final View mView;

        public ViewHolder(final View itemView) {
            super(itemView);
            mView = itemView;
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

        public void setListener(final View.OnLongClickListener listener) {
            mView.setOnLongClickListener(listener);
        }
    }

    @Override
    public TranslationsAdapter.ViewHolder onCreateViewHolder(final ViewGroup parent,
                                                             final int viewType) {
        final Context context = parent.getContext();
        final LayoutInflater inflater = LayoutInflater.from(context);
        final View translationResultView = inflater.inflate(R.layout.item_translation_result, parent, false);
        final TranslationsAdapter adapter = this;
        return new ViewHolder(translationResultView);
    }

    @Override
    public void onBindViewHolder(final TranslationsAdapter.ViewHolder viewHolder, final int position) {
        final TranslationResult translationResult = mResults.get(position);
        viewHolder.setTranslationResult(translationResult);
        viewHolder.setListener(v -> {
            mResults.remove(translationResult);
            mTranslationResultObservable.getTranslationResultDao().delete(translationResult);
            notifyDataSetChanged();
            return false;
        });
    }

    @Override
    public int getItemCount() {
        return mResults.size();
    }
}