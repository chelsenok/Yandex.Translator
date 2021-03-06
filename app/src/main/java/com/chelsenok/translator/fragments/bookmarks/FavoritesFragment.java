package com.chelsenok.translator.fragments.bookmarks;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chelsenok.translator.DaoApplication;
import com.chelsenok.translator.R;
import com.chelsenok.translator.adapters.TranslationsAdapter;
import com.chelsenok.translator.backend.TranslationResultObservable;
import com.chelsenok.translator.ui.DividerItemDecoration;

public class FavoritesFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private TranslationsAdapter mAdapter;


    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_favorites, container, false);
        mAdapter = getAdapter(DaoApplication.getFavoriteObservable());
        DaoApplication.getFavoriteObservable().addObserver(mAdapter);
        mRecyclerView = getRecyclerView(container.getContext(), view, mAdapter);
        return view;
    }

    public TranslationsAdapter getAdapter(final TranslationResultObservable dao) {
        final TranslationsAdapter adapter = new TranslationsAdapter(dao);
        return adapter;
    }

    private RecyclerView getRecyclerView(final Context context, final View parent, final TranslationsAdapter adapter) {
        final RecyclerView recyclerView = (RecyclerView) parent.findViewById(R.id.recycler_view_favorites);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.addItemDecoration(new DividerItemDecoration(context));
        return recyclerView;
    }
}
