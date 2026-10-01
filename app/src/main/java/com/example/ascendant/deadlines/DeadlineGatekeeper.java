package com.example.ascendant.deadlines;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.view.View;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public final class DeadlineGatekeeper {

    private static final String PREFS = "deadline_gatekeeper";
    private static final String KEY_LAST_SHOWN_DATE = "critical_last_shown_date";

    private DeadlineGatekeeper() {
    }

    public static void maybeShowCriticalPanel(Activity activity) {
        if (!isCriticalWindow()) {
            return;
        }

        String today = getToday();
        SharedPreferences preferences = activity.getSharedPreferences(PREFS, Activity.MODE_PRIVATE);
        String lastShownDate = preferences.getString(KEY_LAST_SHOWN_DATE, "");
        if (today.equals(lastShownDate)) {
            return;
        }

        preferences.edit().putString(KEY_LAST_SHOWN_DATE, today).apply();
        Intent intent = new Intent(activity, CriticalDeadlineActivity.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.startActivity(intent);
        } else {
            activity.startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        }
        activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    public static void closeMissedDeadlineIfNeeded(Activity activity) {
        DailyResetCoordinator.executeMidnightReset(activity);
    }

    public static boolean isCriticalWindow() {
        Calendar now = Calendar.getInstance();
        int hour = now.get(Calendar.HOUR_OF_DAY);
        int minute = now.get(Calendar.MINUTE);
        return hour == 23 && minute >= 55;
    }

    public static String getToday() {
        return new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
    }

    public static void applyImmersiveMode(Activity activity) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return;
        }
        View decorView = activity.getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        );
    }
}
