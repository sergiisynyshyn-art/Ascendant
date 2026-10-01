package com.example.ascendant.deadlines;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

import com.example.ascendant.deadlines.DailyDeadlineReceiver;

public final class DeadlineScheduler {

    public static final String ACTION_DAY_START = "com.example.ascendant.deadlines.ACTION_DAY_START";
    public static final String ACTION_WARNING = "com.example.ascendant.deadlines.ACTION_WARNING";
    public static final String ACTION_CRITICAL = "com.example.ascendant.deadlines.ACTION_CRITICAL";
    public static final String ACTION_DEADLINE = "com.example.ascendant.deadlines.ACTION_DEADLINE";

    private static final int REQUEST_DAY_START = 8001;
    private static final int REQUEST_WARNING = 8002;
    private static final int REQUEST_CRITICAL = 8003;
    private static final int REQUEST_DEADLINE = 8004;

    private DeadlineScheduler() {
    }

    public static void scheduleAll(Context context) {
        schedule(context, ACTION_DAY_START, 8, 0, REQUEST_DAY_START);
        schedule(context, ACTION_WARNING, 20, 0, REQUEST_WARNING);
        // Alarma a las 23:55 cancelada - solo se muestra notificación
        // schedule(context, ACTION_CRITICAL, 23, 55, REQUEST_CRITICAL);
        schedule(context, ACTION_DEADLINE, 0, 0, REQUEST_DEADLINE);
    }

    public static void scheduleNext(Context context, String action) {
        if (ACTION_DAY_START.equals(action)) {
            schedule(context, ACTION_DAY_START, 8, 0, REQUEST_DAY_START);
        } else if (ACTION_WARNING.equals(action)) {
            schedule(context, ACTION_WARNING, 20, 0, REQUEST_WARNING);
        } else if (ACTION_CRITICAL.equals(action)) {
            // Alarma a las 23:55 cancelada - solo se muestra notificación
            // schedule(context, ACTION_CRITICAL, 23, 55, REQUEST_CRITICAL);
        } else if (ACTION_DEADLINE.equals(action)) {
            schedule(context, ACTION_DEADLINE, 0, 0, REQUEST_DEADLINE);
        }
    }

    private static void schedule(Context context, String action, int hourOfDay, int minute, int requestCode) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (alarmManager == null) return;

        Intent intent = new Intent(context, DailyDeadlineReceiver.class);
        intent.setAction(action);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context,
                requestCode,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT | immutableFlag()
        );

        alarmManager.cancel(pendingIntent);

        Calendar trigger = Calendar.getInstance();
        trigger.set(Calendar.SECOND, 0);
        trigger.set(Calendar.MILLISECOND, 0);
        trigger.set(Calendar.HOUR_OF_DAY, hourOfDay);
        trigger.set(Calendar.MINUTE, minute);

        if (trigger.getTimeInMillis() <= System.currentTimeMillis()) {
            trigger.add(Calendar.DAY_OF_YEAR, 1);
        }

        try {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, trigger.getTimeInMillis(), pendingIntent);
        } catch (SecurityException ignored) {
            alarmManager.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, trigger.getTimeInMillis(), pendingIntent);
        }
    }

    private static int immutableFlag() {
        return PendingIntent.FLAG_IMMUTABLE;
    }
}



