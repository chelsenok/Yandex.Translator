package com.chelsenok.translator.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.chelsenok.translator.R;
import com.chelsenok.translator.activities.SourceLanguageActivity;
import com.chelsenok.translator.utils.Language;
import com.chelsenok.translator.utils.LanguageManager;

public class TranslatorFragment extends Fragment {

    private LanguageManager mManager;
    private TextView mTextNative;
    private TextView mTextForeign;
    private View mView;
    private Context mContext;
    private EditText mEditText;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_translator, container, false);
        mContext = mView.getContext();
        mManager = LanguageManager.getInstance();
        mTextNative = initializeLanguageText(R.id.tv_native, mManager.getNativeLanguage());
        mTextForeign = initializeLanguageText(R.id.tv_foreign, mManager.getForeignLanguage());
        initializeSwapButton();
        setOnLanguageFrameClickListener(R.id.fl_native);
        setOnLanguageFrameClickListener(R.id.fl_foreign);
        mEditText = (EditText) mView.findViewById(R.id.edit_text);
        mEditText.setSingleLine(true);
//        mEditText.setOnFocusChangeListener((v, hasFocus) -> {
//            final InputMethodManager imm = (InputMethodManager) mContext
//                    .getSystemService(Context.INPUT_METHOD_SERVICE);
//            if (hasFocus) {
//                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,
//                        InputMethodManager.HIDE_IMPLICIT_ONLY);
//            } else {
//
//            }
//        });
//        mEditText.requestFocus();
        return mView;
    }

    private void setOnLanguageFrameClickListener(final int id) {
        mView.findViewById(id).setOnClickListener(v -> mContext.startActivity(
                new Intent(mContext, SourceLanguageActivity.class))
        );
    }

    private void initializeSwapButton() {
        final View view = mView.findViewById(R.id.btn_swap);
        view.setOnClickListener(v -> {
            mManager.swapLanguages();
            final CharSequence swap = mTextNative.getText();
            mTextNative.setText(mTextForeign.getText());
            mTextForeign.setText(swap);
        });
    }

    private TextView initializeLanguageText(final int id, final Language language) {
        final TextView textView = (TextView) mView.findViewById(id);
        textView.setText(language.fullName);
        return textView;
    }
}