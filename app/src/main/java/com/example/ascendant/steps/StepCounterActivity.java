package com.example.ascendant.steps;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.ascendant.R;
import com.example.ascendant.achievements.AchievementUnlockedActivity;
import com.example.ascendant.database.PlayerDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class StepCounterActivity extends AppCompatActivity implements SensorEventListener {

    private static final int GOAL = 5000;
    private static final int PERMISSION_REQUEST_CODE = 101;

    private SensorManager sensorManager;
    private Sensor stepSensor;
    private PlayerDatabase db;

    private TextView tvStepCount, tvProgressPercent, tvStatus, tvSensorWarning;
    private ProgressBar progressBar;

    private boolean sensorAvailable = false;
    private long lastDisplayedSteps = -1;
    private boolean showingAchievementFrame = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_FULLSCREEN |
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        );

        setContentView(R.layout.activity_step_counter);

        db = PlayerDatabase.getInstance(this);

        tvStepCount      = findViewById(R.id.tvStepCount);
        tvProgressPercent = findViewById(R.id.tvProgressPercent);
        tvStatus          = findViewById(R.id.tvStatus);
        tvSensorWarning   = findViewById(R.id.tvSensorWarning);
        progressBar       = findViewById(R.id.stepProgressBar);

        Button btnBack = findViewById(R.id.btnBackSteps);
        btnBack.setOnClickListener(v -> finish());

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        stepSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        sensorAvailable = stepSensor != null;

        if (!sensorAvailable) {
            tvSensorWarning.setText("[ SENSOR NO DISPONIBLE EN ESTE DISPOSITIVO ]");
        }

        updateUI(db.getStepsToday());

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACTIVITY_RECOGNITION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACTIVITY_RECOGNITION},
                    PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        showingAchievementFrame = false;
        if (sensorAvailable &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACTIVITY_RECOGNITION)
                        == PackageManager.PERMISSION_GRANTED) {
            sensorManager.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_UI);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (sensorAvailable) {
            sensorManager.unregisterListener(this);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE &&
                grantResults.length > 0 &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (sensorAvailable) {
                sensorManager.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_UI);
            }
        } else {
            tvSensorWarning.setText("[ PERMISO DE ACTIVIDAD DENEGADO ]");
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() != Sensor.TYPE_STEP_COUNTER) return;

        long totalSteps = (long) event.values[0];
        String today = getToday();

        long initialSteps = db.getInitialSteps();
        String stepDate   = db.getStepDate();

        if (stepDate == null || stepDate.isEmpty() || !today.equals(stepDate)) {
            db.saveStepData(totalSteps, today);
            initialSteps = totalSteps;
        }

        if (totalSteps < initialSteps) {
            db.saveStepData(0L, today);
            initialSteps = 0;
        }

        long stepsToday = totalSteps - initialSteps;
        db.saveCurrentSteps(totalSteps, stepsToday);
        db.updateDailySteps(today, stepsToday);

        if (stepsToday >= GOAL && !db.getMissionState(5)) {
            db.saveMissionForToday(5, true, today);
        }

        ArrayList<String> unlockedNow = db.evaluateAndUnlockAchievements(today, db.getCurrentLevel(), stepsToday);
        if (!unlockedNow.isEmpty() && !showingAchievementFrame) {
            showingAchievementFrame = true;
            Intent intent = new Intent(this, AchievementUnlockedActivity.class);
            intent.putStringArrayListExtra(AchievementUnlockedActivity.EXTRA_ACHIEVEMENT_NAMES, unlockedNow);
            intent.putExtra(AchievementUnlockedActivity.EXTRA_START_INDEX, 0);
            intent.putExtra(AchievementUnlockedActivity.EXTRA_GO_TO_MAIN, false);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }

        updateUI(stepsToday);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}

    private String getToday() {
        return new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
    }

    private void updateUI(long stepsToday) {
        int progress = (int) Math.min(stepsToday * 100L / GOAL, 100);

        tvStepCount.setText(String.valueOf(stepsToday));
        tvProgressPercent.setText(progress + "%");
        progressBar.setProgress(progress);

        boolean missionDone = stepsToday >= GOAL;
        if (missionDone) {
            tvStatus.setText("✓  MISION COMPLETADA");
            tvStatus.setTextColor(0xFF00FF88);
            tvStepCount.setTextColor(0xFF00FF88);
            tvStepCount.setShadowLayer(35, 0, 0, 0xFF00FF88);
        } else {
            tvStatus.setText("▶  EN PROGRESO");
            tvStatus.setTextColor(0xFF00D4FF);
            tvStepCount.setTextColor(0xFF00D4FF);
            tvStepCount.setShadowLayer(35, 0, 0, 0xFF00D4FF);
        }

        if (lastDisplayedSteps >= 0 && stepsToday > lastDisplayedSteps) {
            ScaleAnimation pulse = new ScaleAnimation(
                    1f, 1.08f, 1f, 1.08f,
                    Animation.RELATIVE_TO_SELF, 0.5f,
                    Animation.RELATIVE_TO_SELF, 0.5f);
            pulse.setDuration(200);
            pulse.setRepeatMode(Animation.REVERSE);
            pulse.setRepeatCount(1);
            tvStepCount.startAnimation(pulse);
        }
        lastDisplayedSteps = stepsToday;
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_FULLSCREEN |
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            );
        }
    }
}
