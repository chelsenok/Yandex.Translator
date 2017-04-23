package com.chelsenok.translator.ui;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.DecelerateInterpolator;
import android.widget.Scroller;

import java.lang.reflect.Field;

public class NonSwipeableViewPager extends ViewPager {

    public NonSwipeableViewPager(final Context context) {
        super(context);
        setMyScroller();
    }

    public NonSwipeableViewPager(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        setMyScroller();
    }

    @Override
    public boolean onInterceptTouchEvent(final MotionEvent event) {
        return false;
    }

    @Override
    public boolean onTouchEvent(final MotionEvent event) {
        return false;
    }

    private void setMyScroller() {
        try {
            final Class<?> viewpager = ViewPager.class;
            final Field scroller = viewpager.getDeclaredField("mScroller");
            scroller.setAccessible(true);
            scroller.set(this, new MyScroller(getContext()));
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    public class MyScroller extends Scroller {

        private static final int DURATION = 175;

        public MyScroller(final Context context) {
            super(context, new DecelerateInterpolator());
        }

        @Override
        public void startScroll(final int startX, final int startY, final int dx,
                                final int dy, final int duration) {
            super.startScroll(startX, startY, dx, dy, DURATION);
        }
    }
}