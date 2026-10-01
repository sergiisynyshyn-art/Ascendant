package com.example.ascendant.missions;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ascendant.R;
import com.example.ascendant.achievements.AchievementUnlockedActivity;
import com.example.ascendant.database.PlayerDatabase;
import com.example.ascendant.deadlines.CriticalDeadlineActivity;
import com.example.ascendant.deadlines.DailyResetCoordinator;
import com.example.ascendant.deadlines.DeadlineGatekeeper;
import com.example.ascendant.deadlines.DeadlineNotificationManager;
import com.example.ascendant.deadlines.DeadlineScheduler;
import com.example.ascendant.deadlines.NotificationPermissionHelper;
import com.example.ascendant.missionscomplete.MissionCompleteVideoActivity;
import com.example.ascendant.progress.ProgressActivity;
import com.example.ascendant.steps.StepCounterActivity;

import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MissionsActivity extends AppCompatActivity {

    private static final String DEFAULT_MISSION_COMPLETE_VIDEO = "lvlup3";
    private static final int RUN_GOAL_KM = 5;

    private CheckBox cb1, cb2, cb3, cb4, cb5, cb6, cb7, cb8;
    private TextView tvStepsMini;
    private ProgressBar pbStepsMini;
    private Button btnMissionDone, btnOpenSteps;
    private PlayerDatabase db;
    private boolean isLoadingMissionStates = false;
    private boolean showingAchievementFrame = false;
    private boolean isDayLocked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_FULLSCREEN |
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        );

        setContentView(R.layout.activity_missions);

        NotificationPermissionHelper.requestIfNeeded(this);
        DeadlineScheduler.scheduleAll(this);
        DeadlineGatekeeper.closeMissedDeadlineIfNeeded(this);
        DeadlineGatekeeper.maybeShowCriticalPanel(this);

        db = PlayerDatabase.getInstance(this);
        db.incrementAppOpenForToday(getToday());

        cb1 = findViewById(R.id.cb_mission_1);
        cb2 = findViewById(R.id.cb_mission_2);
        cb3 = findViewById(R.id.cb_mission_3);
        cb4 = findViewById(R.id.cb_mission_4);
        cb5 = findViewById(R.id.cb_mission_5);
        cb6 = findViewById(R.id.cb_mission_6);
        cb7 = findViewById(R.id.cb_mission_7);
        cb8 = findViewById(R.id.cb_mission_8);

        tvStepsMini  = findViewById(R.id.tvStepsMini);
        pbStepsMini  = findViewById(R.id.pbStepsMini);
        btnOpenSteps = findViewById(R.id.btnOpenSteps);
        btnMissionDone = findViewById(R.id.btnMissionDone);
        TextView tvMissionsTitle = findViewById(R.id.tvMissionsTitle);
        View btnGoToProgress = findViewById(R.id.btnGoToProgress);
        View btnBackMissions = findViewById(R.id.btnBackMissions);
        View debugPanel = findViewById(R.id.debugPanel);
        Button btnDebugSimulateCritical = findViewById(R.id.btnDebugSimulateCritical);
        Button btnDebugSimulateMidnight = findViewById(R.id.btnDebugSimulateMidnight);

        // Modo desarrollo: permitir marcar la mision de pasos manualmente.
        cb5.setClickable(true);
        cb5.setFocusable(true);

        setupDebugMidnightTrigger(tvMissionsTitle);
        setupDebugTools(debugPanel, btnDebugSimulateCritical, btnDebugSimulateMidnight);
        loadMissionStates();
        updateMissionDoneButtonState();

        btnMissionDone.setOnClickListener(v -> {
            if (!db.areAllMissionsCompleted()) return;
            Intent intent = new Intent(MissionsActivity.this, MissionCompleteVideoActivity.class);
            intent.putExtra(MissionCompleteVideoActivity.EXTRA_VIDEO_RES_NAME, DEFAULT_MISSION_COMPLETE_VIDEO);
            intent.putExtra(MissionCompleteVideoActivity.EXTRA_APPLY_LEVEL_UP, true);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        });

        cb1.setOnCheckedChangeListener((b, checked) -> {
            onMissionCheckboxChanged(1, checked);
        });

        cb2.setOnCheckedChangeListener((b, checked) -> {
            onMissionCheckboxChanged(2, checked);
        });

        cb3.setOnCheckedChangeListener((b, checked) -> {
            onMissionCheckboxChanged(3, checked);
        });

        cb4.setOnCheckedChangeListener((b, checked) -> {
            onMissionCheckboxChanged(4, checked);
        });

        cb5.setOnCheckedChangeListener((b, checked) -> {
            onMissionCheckboxChanged(5, checked);
        });

        cb6.setOnCheckedChangeListener((b, checked) -> {
            onMissionCheckboxChanged(6, checked);
        });

        cb7.setOnCheckedChangeListener((b, checked) -> {
            onMissionCheckboxChanged(7, checked);
        });

        cb8.setOnCheckedChangeListener((b, checked) -> {
            onMissionCheckboxChanged(8, checked);
        });

        btnOpenSteps.setOnClickListener(v -> {
            startActivity(new Intent(MissionsActivity.this, StepCounterActivity.class));
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        });

        if (btnGoToProgress != null) {
            btnGoToProgress.setOnClickListener(v -> {
                startActivity(new Intent(MissionsActivity.this, ProgressActivity.class));
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            });
        }

        if (btnBackMissions != null) {
            btnBackMissions.setOnClickListener(v -> {
                finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        DeadlineGatekeeper.closeMissedDeadlineIfNeeded(this);
        showingAchievementFrame = false;
        loadMissionStates();
        updateMissionDoneButtonState();
    }

    private void loadMissionStates() {
        isLoadingMissionStates = true;
        isDayLocked = isTodayMissionsLocked();

        cb1.setChecked(db.getMissionState(1));
        cb2.setChecked(db.getMissionState(2));
        cb3.setChecked(db.getMissionState(3));
        cb4.setChecked(db.getMissionState(4));
        cb5.setChecked(db.getMissionState(5));
        cb6.setChecked(db.getMissionState(6));
        cb7.setChecked(db.getMissionState(7));
        cb8.setChecked(db.getMissionState(8));

        long stepsToday = db.getStepsToday();
        // Convertir pasos a km (aproximadamente 1000 pasos = 1 km)
        long kmToday = stepsToday / 1000;
        int progress = (int) Math.min(kmToday * 100L / RUN_GOAL_KM, 100);

        tvStepsMini.setText(kmToday + " / " + RUN_GOAL_KM + " km");
        pbStepsMini.setProgress(progress);

        boolean mission5Done = db.getMissionState(5);
        if (mission5Done) {
            tvStepsMini.setTextColor(0xFF00FF88);
        } else {
            tvStepsMini.setTextColor(0xFF00D4FF);
        }
        setMissionInputsEnabled(!isDayLocked);
         isLoadingMissionStates = false;
    }

    private void onMissionCheckboxChanged(int missionId, boolean checked) {
        if (isLoadingMissionStates || isDayLocked) return;

        String today = getToday();
        db.saveMissionForToday(missionId, checked, today);

        if (checked) {
            ArrayList<String> unlockedNow = db.evaluateAndUnlockAchievements(today, db.getCurrentLevel(), db.getStepsToday());
            if (!unlockedNow.isEmpty() && !showingAchievementFrame) {
                showingAchievementFrame = true;
                Intent intent = new Intent(this, AchievementUnlockedActivity.class);
                intent.putStringArrayListExtra(AchievementUnlockedActivity.EXTRA_ACHIEVEMENT_NAMES, unlockedNow);
                intent.putExtra(AchievementUnlockedActivity.EXTRA_START_INDEX, 0);
                intent.putExtra(AchievementUnlockedActivity.EXTRA_GO_TO_MAIN, false);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        }

        updateMissionDoneButtonState();
    }

    private void updateMissionDoneButtonState() {
        if (isDayLocked) {
            btnMissionDone.setText(R.string.missions_done_locked_label);
            btnMissionDone.setEnabled(false);
            btnMissionDone.setAlpha(0.5f);
            return;
        }

        btnMissionDone.setText(R.string.missions_done_label);
        boolean allDone = db.areAllMissionsCompleted();
        btnMissionDone.setEnabled(allDone);
        btnMissionDone.setAlpha(allDone ? 1f : 0.5f);
    }

    private boolean isTodayMissionsLocked() {
        String today = getToday();
        return "completed".equals(db.getDailyStatus(today)) && !db.isDeadlineReached(today);
    }

    private void setMissionInputsEnabled(boolean enabled) {
        cb1.setEnabled(enabled);
        cb2.setEnabled(enabled);
        cb3.setEnabled(enabled);
        cb4.setEnabled(enabled);
        cb5.setEnabled(enabled);
        cb5.setClickable(enabled);
        cb5.setFocusable(enabled);
        cb6.setEnabled(enabled);
        cb7.setEnabled(enabled);
        cb8.setEnabled(enabled);
        btnOpenSteps.setEnabled(enabled);
        btnOpenSteps.setAlpha(enabled ? 1f : 0.5f);
    }

    private void setupDebugMidnightTrigger(TextView titleView) {
        if (!isDebugBuild() || titleView == null) {
            return;
        }

        titleView.setOnLongClickListener(v -> {
            DailyResetCoordinator.executeMidnightReset(this);
            loadMissionStates();
            updateMissionDoneButtonState();
            Toast.makeText(this, "Debug: reset diario ejecutado", Toast.LENGTH_SHORT).show();
            return true;
        });
    }

    private boolean isDebugBuild() {
        return (getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
    }

    private void setupDebugTools(View debugPanel, Button criticalButton, Button midnightButton) {
        if (debugPanel == null || criticalButton == null || midnightButton == null) {
            return;
        }

        if (!isDebugBuild()) {
            debugPanel.setVisibility(View.GONE);
            return;
        }

        debugPanel.setVisibility(View.VISIBLE);

        criticalButton.setOnClickListener(v -> {
            DeadlineNotificationManager.showCritical(this);
            startActivity(new Intent(this, CriticalDeadlineActivity.class));
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            Toast.makeText(this, "Debug: simulacion 23:55", Toast.LENGTH_SHORT).show();
        });

        midnightButton.setOnClickListener(v -> {
            DailyResetCoordinator.executeMidnightReset(this);
            loadMissionStates();
            updateMissionDoneButtonState();
            Toast.makeText(this, "Debug: simulacion 00:00", Toast.LENGTH_SHORT).show();
        });
    }

    private String getToday() {
        return new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
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
