package com.example.ascendant.missionscomplete;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ascendant.MainActivity;
import com.example.ascendant.R;
import com.example.ascendant.advancedclass.AdvancedClassUnlockedActivity;
import com.example.ascendant.achievements.AchievementUnlockedActivity;
import com.example.ascendant.database.PlayerDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MissionCompleteFrameActivity extends AppCompatActivity {

    private static final int MAX_LEVEL = 100;

    private TextView completionText;
    private TextView currentLevelText;
    private Button enterSystemButton;
    private ArrayList<String> unlockedAchievements = new ArrayList<>();
    private String unlockedAdvancedClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        applyImmersiveFullscreen();
        setContentView(R.layout.activity_mission_complete_frame);

        completionText = findViewById(R.id.completionText);
        currentLevelText = findViewById(R.id.currentLevelText);
        enterSystemButton = findViewById(R.id.enterSystemButton);

        PlayerDatabase db = PlayerDatabase.getInstance(this);
        boolean shouldApplyLevelUp = getIntent().getBooleanExtra(MissionCompleteVideoActivity.EXTRA_APPLY_LEVEL_UP, false);
        String today = getToday();

        int currentLevel = db.getCurrentLevel();
        if (shouldApplyLevelUp && db.areAllMissionsCompleted() && !db.isDailyLevelApplied(today)) {
            currentLevel = db.incrementLevel(MAX_LEVEL);
            db.setDailyLevelApplied(today, true);
            unlockedAdvancedClass = db.unlockAdvancedClassIfEligible(currentLevel, today);
        }

        int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        long stepsToday = db.getStepsToday();

        if (db.areAllMissionsCompleted()) {
            db.recordDailyMissionsCompletion(today, hour, stepsToday);
        }

        unlockedAchievements = db.evaluateAndUnlockAchievements(today, currentLevel, stepsToday);

        // El reset diario real ocurre a las 00:00; hasta entonces quedan bloqueadas como completadas.
        completionText.setText("Has subido de nivel.");
        currentLevelText.setText("Nivel actual: " + currentLevel + "/" + MAX_LEVEL);

        new Handler(Looper.getMainLooper()).postDelayed(this::animateFrame, 180);

        enterSystemButton.setOnClickListener(v -> {
            if (unlockedAdvancedClass != null && !unlockedAdvancedClass.isEmpty()) {
                Intent advancedIntent = new Intent(MissionCompleteFrameActivity.this, AdvancedClassUnlockedActivity.class);
                advancedIntent.putExtra(AdvancedClassUnlockedActivity.EXTRA_CLASS_NAME, unlockedAdvancedClass);
                advancedIntent.putStringArrayListExtra(AdvancedClassUnlockedActivity.EXTRA_ACHIEVEMENT_NAMES, unlockedAchievements);
                advancedIntent.putExtra(AdvancedClassUnlockedActivity.EXTRA_GO_TO_MAIN, true);
                startActivity(advancedIntent);
            } else if (!unlockedAchievements.isEmpty()) {
                Intent unlockedIntent = new Intent(MissionCompleteFrameActivity.this, AchievementUnlockedActivity.class);
                unlockedIntent.putStringArrayListExtra(AchievementUnlockedActivity.EXTRA_ACHIEVEMENT_NAMES, unlockedAchievements);
                unlockedIntent.putExtra(AchievementUnlockedActivity.EXTRA_START_INDEX, 0);
                unlockedIntent.putExtra(AchievementUnlockedActivity.EXTRA_GO_TO_MAIN, true);
                startActivity(unlockedIntent);
            } else {
                Intent intent = new Intent(MissionCompleteFrameActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            finish();
        });
    }

    private String getToday() {
        return new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
    }

    private void animateFrame() {
        ObjectAnimator textFade = ObjectAnimator.ofFloat(completionText, "alpha", 0f, 1f);
        textFade.setDuration(1000);
        textFade.start();

        ObjectAnimator textScaleX = ObjectAnimator.ofFloat(completionText, "scaleX", 0.96f, 1f);
        ObjectAnimator textScaleY = ObjectAnimator.ofFloat(completionText, "scaleY", 0.96f, 1f);
        textScaleX.setDuration(1000);
        textScaleY.setDuration(1000);
        textScaleX.start();
        textScaleY.start();

        ObjectAnimator levelFade = ObjectAnimator.ofFloat(currentLevelText, "alpha", 0f, 1f);
        levelFade.setDuration(1000);
        levelFade.start();

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            ObjectAnimator buttonFade = ObjectAnimator.ofFloat(enterSystemButton, "alpha", 0f, 1f);
            buttonFade.setDuration(600);
            buttonFade.start();
        }, 520);
    }

    private void applyImmersiveFullscreen() {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        );
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            applyImmersiveFullscreen();
        }
    }
}
