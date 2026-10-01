package com.example.ascendant;

import android.app.ActivityOptions;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import com.example.ascendant.deadlines.DeadlineGatekeeper;
import com.example.ascendant.deadlines.DeadlineScheduler;
import com.example.ascendant.deadlines.NotificationPermissionHelper;
import com.example.ascendant.achievements.AchievementsActivity;
import com.example.ascendant.database.PlayerDatabase;
import com.example.ascendant.missions.MissionsActivity;
import com.example.ascendant.progress.ProgressActivity;

public class MainActivity extends AppCompatActivity {

    private static final int MAX_LEVEL = 100;

    private LinearLayout missionsPanel;
    private LinearLayout achievementsPanel;
    private LinearLayout progressPanel;
    private TextView tvCurrentLevel;
    private ImageView bgImage;
    private View auraOverlay;
    private PlayerDatabase db;
    private ObjectAnimator backgroundMoveX;
    private ObjectAnimator backgroundMoveY;
    private ObjectAnimator backgroundScaleX;
    private ObjectAnimator backgroundScaleY;
    private ObjectAnimator auraPulse;
    private ObjectAnimator auraScaleX;
    private ObjectAnimator auraScaleY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        enableFullscreen();

        setContentView(R.layout.activity_main);

        NotificationPermissionHelper.requestIfNeeded(this);
        DeadlineScheduler.scheduleAll(this);
        DeadlineGatekeeper.closeMissedDeadlineIfNeeded(this);
        DeadlineGatekeeper.maybeShowCriticalPanel(this);

        db = PlayerDatabase.getInstance(this);
        bgImage = findViewById(R.id.bgImage);
        auraOverlay = findViewById(R.id.auraOverlay);
        missionsPanel = findViewById(R.id.missionsPanel);
        achievementsPanel = findViewById(R.id.achievementsPanel);
        progressPanel = findViewById(R.id.progressPanel);
        tvCurrentLevel = findViewById(R.id.tvCurrentLevel);

        startBackgroundAura();
        refreshLevelPanel();

        missionsPanel.setOnClickListener(v -> {
            animatePulse(missionsPanel);
            Intent intent = new Intent(MainActivity.this, MissionsActivity.class);
            ActivityOptions options = ActivityOptions.makeCustomAnimation(this, android.R.anim.fade_in, android.R.anim.fade_out);
            startActivity(intent, options.toBundle());
        });

        achievementsPanel.setOnClickListener(v -> {
            animatePulse(achievementsPanel);
            Intent intent = new Intent(MainActivity.this, AchievementsActivity.class);
            ActivityOptions options = ActivityOptions.makeCustomAnimation(this, android.R.anim.fade_in, android.R.anim.fade_out);
            startActivity(intent, options.toBundle());
        });

        progressPanel.setOnClickListener(v -> {
            animatePulse(progressPanel);
            Intent intent = new Intent(MainActivity.this, ProgressActivity.class);
            ActivityOptions options = ActivityOptions.makeCustomAnimation(this, android.R.anim.fade_in, android.R.anim.fade_out);
            startActivity(intent, options.toBundle());
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshLevelPanel();
    }

    private void refreshLevelPanel() {
        int currentLevel = db.getCurrentLevel();
        tvCurrentLevel.setText(getString(R.string.level_format, currentLevel, MAX_LEVEL));
    }

    private void animatePulse(View view) {
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", 1f, 0.95f, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", 1f, 0.95f, 1f);
        scaleX.setDuration(200);
        scaleY.setDuration(200);
        scaleX.start();
        scaleY.start();
    }

    private void startBackgroundAura() {
        if (bgImage == null || auraOverlay == null) return;

        backgroundMoveX = ObjectAnimator.ofFloat(bgImage, View.TRANSLATION_X, -24f, 24f);
        backgroundMoveY = ObjectAnimator.ofFloat(bgImage, View.TRANSLATION_Y, -16f, 16f);
        backgroundScaleX = ObjectAnimator.ofFloat(bgImage, View.SCALE_X, 1.03f, 1.08f);
        backgroundScaleY = ObjectAnimator.ofFloat(bgImage, View.SCALE_Y, 1.03f, 1.08f);

        configureLoop(backgroundMoveX, 12000);
        configureLoop(backgroundMoveY, 15000);
        configureLoop(backgroundScaleX, 18000);
        configureLoop(backgroundScaleY, 18000);

        auraPulse = ObjectAnimator.ofFloat(auraOverlay, View.ALPHA, 0.10f, 0.22f);
        auraScaleX = ObjectAnimator.ofFloat(auraOverlay, View.SCALE_X, 1f, 1.06f);
        auraScaleY = ObjectAnimator.ofFloat(auraOverlay, View.SCALE_Y, 1f, 1.06f);

        configureLoop(auraPulse, 5000);
        configureLoop(auraScaleX, 5000);
        configureLoop(auraScaleY, 5000);

        backgroundMoveX.start();
        backgroundMoveY.start();
        backgroundScaleX.start();
        backgroundScaleY.start();
        auraPulse.start();
        auraScaleX.start();
        auraScaleY.start();
    }

    private void configureLoop(ObjectAnimator animator, long duration) {
        animator.setDuration(duration);
        animator.setRepeatMode(ObjectAnimator.REVERSE);
        animator.setRepeatCount(ObjectAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (backgroundMoveX != null) backgroundMoveX.cancel();
        if (backgroundMoveY != null) backgroundMoveY.cancel();
        if (backgroundScaleX != null) backgroundScaleX.cancel();
        if (backgroundScaleY != null) backgroundScaleY.cancel();
        if (auraPulse != null) auraPulse.cancel();
        if (auraScaleX != null) auraScaleX.cancel();
        if (auraScaleY != null) auraScaleY.cancel();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            enableFullscreen();
        }
    }

    private void enableFullscreen() {
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        WindowInsetsControllerCompat controller = WindowCompat.getInsetsController(getWindow(), getWindow().getDecorView());
        if (controller != null) {
            controller.hide(WindowInsetsCompat.Type.systemBars());
            controller.setSystemBarsBehavior(WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE);
        }
    }
}
