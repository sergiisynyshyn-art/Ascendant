package com.example.ascendant.deadlines;

import android.content.Context;

import com.example.ascendant.database.PlayerDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public final class DailyResetCoordinator {

    private static final int MAX_LEVEL = 100;

    private DailyResetCoordinator() {
    }

    public static void executeMidnightReset(Context context) {
        PlayerDatabase db = PlayerDatabase.getInstance(context);
        String today = getToday();
        String yesterday = getYesterday();

        if (!db.hasDailyProgressForDate(yesterday)) {
            db.prepareRuntimeForNewDay(today);
            return;
        }

        if (db.isDeadlineReached(yesterday)) {
            db.prepareRuntimeForNewDay(today);
            return;
        }

        String status = db.getDailyStatus(yesterday);
        boolean completedByState = "completed".equals(status);
        boolean completedByMissions = db.areDailyMissionsCompletedForDate(yesterday);

        if (completedByState || completedByMissions) {
            int completionHour = db.getDailyCompletionHour(yesterday);
            if (completionHour < 0) {
                completionHour = 23;
            }

            long steps = db.getDailySteps(yesterday);
            db.finalizeDailyProgress(yesterday, completionHour, steps, true);

            if (!db.isDailyLevelApplied(yesterday)) {
                int newLevel = db.incrementLevel(MAX_LEVEL);
                db.setDailyLevelApplied(yesterday, true);
                db.unlockAdvancedClassIfEligible(newLevel, yesterday);
            }

            db.evaluateAndUnlockAchievements(yesterday, db.getCurrentLevel(), steps);
            DeadlineNotificationManager.showDayResetStarted(context);
        } else {
            db.markDailyFailed(yesterday);
            DeadlineNotificationManager.showDeadlineClosed(context);
        }

        db.prepareRuntimeForNewDay(today);
    }

    private static String getToday() {
        return new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
    }

    private static String getYesterday() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        return new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(calendar.getTime());
    }
}
