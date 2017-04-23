package com.chelsenok.translator.fragments.pagers;

import android.os.Bundle;
import android.support.v4.app.Fragment;

abstract class PagerFragment extends Fragment {

    protected static final String ARG_PAGE = "arg_page";
    protected static Bundle getArguments(final int page) {
        final Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        return args;
    }

}
