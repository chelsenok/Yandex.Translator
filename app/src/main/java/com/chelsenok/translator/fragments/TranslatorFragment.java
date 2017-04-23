package com.chelsenok.translator.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.chelsenok.translator.R;
import com.chelsenok.translator.activities.SourceLanguageActivity;
import com.chelsenok.translator.language.LanguageManager;
import com.chelsenok.translator.language.LanguageTypes;

public class TranslatorFragment extends Fragment {

    private LanguageManager mManager;
    private TextView mTextNative;
    private TextView mTextForeign;
    private View mView;
    private Context mContext;
    private EditText mEditText;
    private ImageButton mClearButton;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_translator, container, false);
        mContext = mView.getContext();
        mManager = LanguageManager.getInstance();
        mTextNative = LanguageTypes.Native.getTextView(mView);
        mTextForeign = LanguageTypes.Foreign.getTextView(mView);
        initializeSwapButton();
        setOnLanguageFrameClickListener(LanguageTypes.Native);
        setOnLanguageFrameClickListener(LanguageTypes.Foreign);
        mEditText = initializeEditText();
        mClearButton = (ImageButton) mView.findViewById(R.id.btn_clear);
        mClearButton.setOnClickListener(v -> mEditText.setText(""));
        return mView;
    }

    private EditText initializeEditText() {
        final EditText editText = (EditText) mView.findViewById(R.id.edit_text);
        editText.setOnFocusChangeListener((v, hasFocus) -> setEditTextEnabled(hasFocus));
        editText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(final CharSequence s, final int start,
                                          final int count, final int after) {

            }

            @Override
            public void onTextChanged(final CharSequence s, final int start,
                                      final int count, final int after) {

            }

            @Override
            public void afterTextChanged(final Editable s) {
                if (s.toString().isEmpty()) {
                    mClearButton.setVisibility(View.INVISIBLE);
                } else {
                    mClearButton.setVisibility(View.VISIBLE);
                }
            }
        });
        return editText;
    }

    private void setEditTextEnabled(final boolean b) {
        final View view = mView.findViewById(R.id.border);
        final int drawableId;
        if (b) {
            drawableId = R.drawable.border_active;
        } else {
            drawableId = R.drawable.border_passive;
        }
        view.setBackground(mContext.getResources().getDrawable(drawableId));

    }

    private void setOnLanguageFrameClickListener(final LanguageTypes type) {
        final View view = type.getFrameLayout(mView);
        view.setTag(type.getName());
        view.setOnClickListener(v -> {
            final Intent intent = new Intent(mContext, SourceLanguageActivity.class);
            intent.putExtra(LanguageTypes.EXTRA, (String) v.getTag());
            mContext.startActivity(intent);
        });
    }

    private void initializeSwapButton() {
        final View view = mView.findViewById(R.id.btn_swap);
        view.setOnClickListener(v -> {
            mManager.swapLanguages();
            this.swapLanguages();
        });
    }

    private void swapLanguages() {
        final CharSequence swap = mTextNative.getText();
        mTextNative.setText(mTextForeign.getText());
        mTextForeign.setText(swap);
    }
}