package com.chelsenok.translator.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
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

import com.chelsenok.translator.DaoApplication;
import com.chelsenok.translator.R;
import com.chelsenok.translator.activities.SourceLanguageActivity;
import com.chelsenok.translator.api.YandexApiManager;
import com.chelsenok.translator.backend.TranslationResultObservable;
import com.chelsenok.translator.backend.dao.TranslationResult;
import com.chelsenok.translator.language.LanguageManager;
import com.chelsenok.translator.language.LanguageTypes;
import com.chelsenok.translator.utils.SharedPreferenceManager;

import org.json.JSONObject;

public class TranslatorFragment extends Fragment {

    private LanguageManager mManager;
    private TextView mTextNative;
    private TextView mTextForeign;
    private View mView;
    private Context mContext;
    private EditText mEditText;
    private ImageButton mBookmarkImageButton;
    private ImageButton mClearButton;
    private View mConnectionErrorView;
    private View mTranslationView;
    private TextView mTranslateText;
    private final Handler mHandler;
    private static final String TABLE = "translator";
    private static final String EDIT_FIELD = "edit_field";
    private SharedPreferenceManager mSharedManager;

    public TranslatorFragment(final Handler handler) {
        mHandler = handler;
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_translator, container, false);
        mContext = mView.getContext();
        mManager = LanguageManager.getInstance();
        mEditText = initializeEditText();
        mConnectionErrorView = mView.findViewById(R.id.ll_connection_error);
        mTranslationView = mView.findViewById(R.id.fl_translation);
        mBookmarkImageButton = (ImageButton) mView.findViewById(R.id.btn_bookmark);
        mBookmarkImageButton.setOnClickListener(v -> {
            if (!"".equals(mEditText.getText().toString()) && !"".equals(mTranslateText.getText().toString())) {
                DaoApplication.getFavoriteObservable().getTranslationResultDao().insert(
                        new TranslationResult(
                                null,
                                mEditText.getText().toString(),
                                mTranslateText.getText().toString(),
                                mManager.getLanguage(LanguageTypes.Native).shortName,
                                mManager.getLanguage(LanguageTypes.Foreign).shortName
                        )
                );
                DaoApplication.getFavoriteObservable().notifyObservers();
            }
        });
        mTranslateText = (TextView) mTranslationView.findViewById(R.id.tv_translation);
        LanguageManager.initialize(mContext);
        if (LanguageManager.getSuccessed()) {
            initializeLanguagesFields();
        } else {
            setErrorVisible();
            mView.findViewById(R.id.btn_retry).setOnClickListener(v -> {
                LanguageManager.initialize(mContext);
                if (LanguageManager.getSuccessed()) {
                    initializeLanguagesFields();
                    mConnectionErrorView.setVisibility(View.INVISIBLE);
                }
            });
        }
        initializeSwapButton();
        mClearButton = (ImageButton) mView.findViewById(R.id.btn_clear);
        mClearButton.setOnClickListener(v -> {
            if (!"".equals(mEditText.getText().toString()) && !"".equals(mTranslateText.getText().toString())) {
                final TranslationResultObservable observable = DaoApplication.getHistoryObservable();
                final TranslationResult result = new TranslationResult(
                        null,
                        mEditText.getText().toString(),
                        mTranslateText.getText().toString(),
                        mManager.getLanguage(LanguageTypes.Native).shortName,
                        mManager.getLanguage(LanguageTypes.Foreign).shortName
                );
                observable.getTranslationResultDao().insert(result);
                observable.notifyObservers();
            }
            mEditText.setText("");
            mTranslateText.setText("");
        });
        mSharedManager = new SharedPreferenceManager(mContext, TABLE);
        mEditText.setText(mSharedManager.getString(EDIT_FIELD, ""));
        translateText(mEditText.getText().toString());
        return mView;
    }

    public void setTranslationText(final CharSequence translationText) {
        mConnectionErrorView.setVisibility(View.INVISIBLE);
        mTranslationView.setVisibility(View.VISIBLE);
        mTranslateText.setText(translationText);
    }

    private void initializeLanguagesFields() {
        mTextNative = LanguageTypes.Native.getTextView(mView);
        mTextForeign = LanguageTypes.Foreign.getTextView(mView);
        setOnLanguageFrameClickListener(LanguageTypes.Native);
        setOnLanguageFrameClickListener(LanguageTypes.Foreign);
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
                final String string = s.toString();
                translateText(string);
                mSharedManager.putString(EDIT_FIELD, string);
            }
        });
        return editText;
    }

    private void translateText(final String s) {
        if (!LanguageManager.getSuccessed()) {
            setErrorVisible();
            return;
        }
        if (s.isEmpty()) {
            mClearButton.setVisibility(View.INVISIBLE);
            return;
        } else {
            mClearButton.setVisibility(View.VISIBLE);
        }
        if (YandexApiManager.getStatus() != AsyncTask.Status.RUNNING) {
            final LanguageManager manager = LanguageManager.getInstance();
            final JSONObject translate;
            final String sNative = s.replace(" ", "%20");
            if (manager.getDetectLanguagesEnabled()) {
                translate = new YandexApiManager().getTranslationJson(
                        manager.getLanguage(LanguageTypes.Foreign).shortName,
                        sNative
                );
            } else {
                translate = new YandexApiManager().getTranslationJson(
                        manager.getLanguage(LanguageTypes.Native).shortName,
                        manager.getLanguage(LanguageTypes.Foreign).shortName,
                        sNative
                );
            }
            if (translate == null) {
                setErrorVisible();
                return;
            }
            try {
                final String cleanTranslation = translate.getJSONArray("text").getString(0);
                setTranslationText(cleanTranslation);
            } catch (final Exception ignored) {
            }
        }
    }

    private void setErrorVisible() {
        mTranslationView.setVisibility(View.INVISIBLE);
        mConnectionErrorView.setVisibility(View.VISIBLE);
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
            mHandler.obtainMessage(0, intent).sendToTarget();
        });
    }

    private void initializeSwapButton() {
        final View view = mView.findViewById(R.id.btn_swap);
        view.setOnClickListener(v -> {
            if (LanguageManager.getSuccessed()) {
                mManager.swapLanguages();
                this.swapLanguages();
            }
        });
    }

    private void swapLanguages() {
        final CharSequence swap = mTextNative.getText();
        mTextNative.setText(mTextForeign.getText());
        mTextForeign.setText(swap);
        translateText(mEditText.getText().toString());
    }
}