package com.example.ascendant.deadlines;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class DailyDeadlineReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null || intent.getAction() == null) return;

        DeadlineNotificationManager.ensureChannels(context);

        String action = intent.getAction();
        if (DeadlineScheduler.ACTION_DAY_START.equals(action)) {
            DeadlineNotificationManager.showDayStart(context);
            DeadlineScheduler.scheduleNext(context, action);
            return;
        }

        if (DeadlineScheduler.ACTION_WARNING.equals(action)) {
            DeadlineNotificationManager.showWarning(context);
            DeadlineScheduler.scheduleNext(context, action);
            return;
        }

        if (DeadlineScheduler.ACTION_CRITICAL.equals(action)) {
            DeadlineNotificationManager.showCritical(context);
            DeadlineScheduler.scheduleNext(context, action);
            return;
        }

        if (DeadlineScheduler.ACTION_DEADLINE.equals(action)) {
            DailyResetCoordinator.executeMidnightReset(context);
            DeadlineScheduler.scheduleNext(context, action);
        }
    }
}
