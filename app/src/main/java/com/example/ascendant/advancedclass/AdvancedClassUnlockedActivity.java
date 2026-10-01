package com.example.ascendant.advancedclass;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ascendant.MainActivity;
import com.example.ascendant.R;
import com.example.ascendant.achievements.AchievementUnlockedActivity;

import java.util.ArrayList;

public class AdvancedClassUnlockedActivity extends AppCompatActivity {

    public static final String EXTRA_CLASS_NAME = "extra_class_name";
    public static final String EXTRA_ACHIEVEMENT_NAMES = "extra_achievement_names";
    public static final String EXTRA_GO_TO_MAIN = "extra_go_to_main";

    private TextView tvTitle;
    private TextView tvClassName;
    private Button btnEnterSystem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        applyImmersiveFullscreen();
        setContentView(R.layout.activity_advanced_class_unlocked);

        tvTitle = findViewById(R.id.tvAdvancedClassTitle);
        tvClassName = findViewById(R.id.tvAdvancedClassName);
        btnEnterSystem = findViewById(R.id.btnEnterAdvancedSystem);

        String className = getIntent().getStringExtra(EXTRA_CLASS_NAME);
        if (className == null || className.trim().isEmpty()) {
            className = "Shadow Monarch";
        }

        ArrayList<String> achievementNames = getIntent().getStringArrayListExtra(EXTRA_ACHIEVEMENT_NAMES);
        boolean goToMain = getIntent().getBooleanExtra(EXTRA_GO_TO_MAIN, true);

        tvTitle.setText("Has desbloqueado tu Clase Avanzada.");
        tvClassName.setText("Clase adquirida: " + className);
        animateFrame();

        btnEnterSystem.setOnClickListener(v -> {
            if (achievementNames != null && !achievementNames.isEmpty()) {
                Intent achievementIntent = new Intent(this, AchievementUnlockedActivity.class);
                achievementIntent.putStringArrayListExtra(AchievementUnlockedActivity.EXTRA_ACHIEVEMENT_NAMES, achievementNames);
                achievementIntent.putExtra(AchievementUnlockedActivity.EXTRA_START_INDEX, 0);
                achievementIntent.putExtra(AchievementUnlockedActivity.EXTRA_GO_TO_MAIN, goToMain);
                startActivity(achievementIntent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
                return;
            }

            if (goToMain) {
                Intent mainIntent = new Intent(this, MainActivity.class);
                mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(mainIntent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
            finish();
        });
    }

    private void animateFrame() {
        ObjectAnimator titleFade = ObjectAnimator.ofFloat(tvTitle, "alpha", 0f, 1f);
        titleFade.setDuration(700);
        titleFade.start();

        ObjectAnimator classFade = ObjectAnimator.ofFloat(tvClassName, "alpha", 0f, 1f);
        classFade.setDuration(900);
        classFade.start();

        ObjectAnimator buttonFade = ObjectAnimator.ofFloat(btnEnterSystem, "alpha", 0f, 1f);
        buttonFade.setDuration(600);
        buttonFade.setStartDelay(260);
        buttonFade.start();
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

