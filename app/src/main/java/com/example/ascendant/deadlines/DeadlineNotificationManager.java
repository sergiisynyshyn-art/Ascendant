package com.example.ascendant.deadlines;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import com.example.ascendant.R;

public final class DeadlineNotificationManager {

    private static final String CHANNEL_DAY_START = "deadline_day_start";
    private static final String CHANNEL_WARNING = "deadline_warning";
    private static final String CHANNEL_CRITICAL = "deadline_critical";
    private static final String CHANNEL_SILENT = "deadline_silent";

    private static final int NOTIFICATION_DAY_START = 8101;
    private static final int NOTIFICATION_WARNING = 8102;
    private static final int NOTIFICATION_CRITICAL = 8103;
    private static final int NOTIFICATION_DEADLINE = 8104;

    private DeadlineNotificationManager() {
    }

    public static void ensureChannels(Context context) {
        NotificationManager manager = context.getSystemService(NotificationManager.class);
        if (manager == null) return;

        NotificationChannel dayStart = new NotificationChannel(CHANNEL_DAY_START, "Inicio del día", NotificationManager.IMPORTANCE_DEFAULT);
        dayStart.enableLights(true);
        dayStart.setLightColor(Color.BLUE);
        dayStart.enableVibration(true);
        dayStart.setVibrationPattern(new long[]{0, 120, 80, 120});
        dayStart.setSound(defaultNotificationSound(), audioAttributes(false));
        manager.createNotificationChannel(dayStart);

        NotificationChannel warning = new NotificationChannel(CHANNEL_WARNING, "Advertencia del Sistema", NotificationManager.IMPORTANCE_HIGH);
        warning.enableLights(true);
        warning.setLightColor(Color.rgb(200, 40, 40));
        warning.enableVibration(true);
        warning.setVibrationPattern(new long[]{0, 220, 180, 220});
        warning.setSound(defaultNotificationSound(), audioAttributes(false));
        manager.createNotificationChannel(warning);

        NotificationChannel critical = new NotificationChannel(CHANNEL_CRITICAL, "Peligro crítico", NotificationManager.IMPORTANCE_HIGH);
        critical.enableLights(true);
        critical.setLightColor(Color.RED);
        critical.enableVibration(true);
        critical.setVibrationPattern(new long[]{0, 320, 140, 320, 140, 420});
        // Solo notificación, sin sonido de alarma
        critical.setSound(defaultNotificationSound(), audioAttributes(false));
        manager.createNotificationChannel(critical);

        NotificationChannel silent = new NotificationChannel(CHANNEL_SILENT, "Cierre silencioso", NotificationManager.IMPORTANCE_LOW);
        silent.enableVibration(false);
        silent.setSound(null, null);
        manager.createNotificationChannel(silent);
    }

    public static void showDayStart(Context context) {
        ensureChannels(context);
        NotificationCompat.Builder builder = baseBuilder(context, CHANNEL_DAY_START, "El Sistema te recuerda tus misiones. El día ha comenzado.")
                .setColor(Color.BLUE)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setLights(Color.BLUE, 1000, 1000)
                .setVibrate(new long[]{0, 120, 80, 120});
        notify(context, NOTIFICATION_DAY_START, builder);
    }

    public static void showWarning(Context context) {
        ensureChannels(context);
        NotificationCompat.Builder builder = baseBuilder(context, CHANNEL_WARNING, "El tiempo se agota. Completa tus misiones antes de que el Sistema cierre el día.")
                .setColor(Color.rgb(200, 50, 50))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setLights(Color.rgb(200, 50, 50), 1000, 1000)
                .setVibrate(new long[]{0, 220, 180, 220});
        notify(context, NOTIFICATION_WARNING, builder);
    }

    public static void showCritical(Context context) {
        ensureChannels(context);
        NotificationCompat.Builder builder = baseBuilder(context, CHANNEL_CRITICAL, "PELIGRO: El día está a punto de perderse. Tienes 5 minutos para sobrevivir.")
                .setColor(Color.RED)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setLights(Color.RED, 1000, 500)
                .setVibrate(new long[]{0, 320, 140, 320, 140, 420});
        notify(context, NOTIFICATION_CRITICAL, builder);
    }

    public static void showDeadlineClosed(Context context) {
        ensureChannels(context);
        NotificationCompat.Builder builder = baseBuilder(context, CHANNEL_SILENT, "El Sistema ha cerrado el día. No sobreviviste hoy.")
                .setColor(Color.rgb(150, 60, 60))
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setSilent(true)
                .setOngoing(false);
        notify(context, NOTIFICATION_DEADLINE, builder);
    }

    public static void showDayResetStarted(Context context) {
        ensureChannels(context);
        NotificationCompat.Builder builder = baseBuilder(context, CHANNEL_DAY_START, "Nuevo dia iniciado. El Sistema ha reiniciado tus misiones.")
                .setColor(Color.BLUE)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setLights(Color.BLUE, 1000, 1000)
                .setVibrate(new long[]{0, 120, 80, 120});
        notify(context, NOTIFICATION_DEADLINE, builder);
    }

    private static NotificationCompat.Builder baseBuilder(Context context, String channelId, String message) {
        return new NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Ascendant")
                .setContentText(message)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                .setAutoCancel(true)
                .setOnlyAlertOnce(true)
                .setCategory(NotificationCompat.CATEGORY_ALARM);
    }

    private static void notify(Context context, int notificationId, NotificationCompat.Builder builder) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU
                && ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        try {
            NotificationManagerCompat.from(context).notify(notificationId, builder.build());
        } catch (SecurityException ignored) {
            // If the user revoked notifications, fail silently.
        }
    }

    private static Uri defaultNotificationSound() {
        return RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
    }

    private static Uri defaultAlarmSound() {
        return RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
    }

    private static AudioAttributes audioAttributes(boolean alarm) {
        int usage = alarm ? AudioAttributes.USAGE_ALARM : AudioAttributes.USAGE_NOTIFICATION;
        return new AudioAttributes.Builder()
                .setUsage(usage)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();
    }
}
