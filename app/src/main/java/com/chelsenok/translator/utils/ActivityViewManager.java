package com.chelsenok.translator.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.view.Window;
import android.view.WindowManager;

import com.chelsenok.translator.R;

public abstract class ActivityViewManager {

    public static void setStatusBarColor(final Activity activity, final int colorId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            final Window window = activity.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(activity.getResources().getColor(colorId));
        }
    }

    public static void setTaskDescription(final Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            final ActivityManager.TaskDescription taskDescription = new ActivityManager.TaskDescription(
                    activity.getResources().getString(R.string.app_name),
                    BitmapFactory.decodeResource(activity.getResources(), R.drawable.task_icon),
                    ContextCompat.getColor(activity, R.color.toolbarColor)
            );
            activity.setTaskDescription(taskDescription);
        }
    }
}
