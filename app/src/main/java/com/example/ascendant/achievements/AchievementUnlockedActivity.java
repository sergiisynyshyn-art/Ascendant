package com.example.ascendant.achievements;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ascendant.MainActivity;
import com.example.ascendant.R;

import java.util.ArrayList;

public class AchievementUnlockedActivity extends AppCompatActivity {

    public static final String EXTRA_ACHIEVEMENT_NAMES = "extra_achievement_names";
    public static final String EXTRA_START_INDEX = "extra_start_index";
    public static final String EXTRA_GO_TO_MAIN = "extra_go_to_main";

    private TextView tvUnlockedTitle;
    private TextView tvUnlockedName;
    private Button btnContinue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        applyImmersiveFullscreen();
        setContentView(R.layout.activity_achievement_unlocked);

        tvUnlockedTitle = findViewById(R.id.tvUnlockedTitle);
        tvUnlockedName = findViewById(R.id.tvUnlockedName);
        btnContinue = findViewById(R.id.btnContinueSystem);

        ArrayList<String> achievementNames = getIntent().getStringArrayListExtra(EXTRA_ACHIEVEMENT_NAMES);
        if (achievementNames == null || achievementNames.isEmpty()) {
            finish();
            return;
        }

        int index = getIntent().getIntExtra(EXTRA_START_INDEX, 0);
        if (index < 0 || index >= achievementNames.size()) {
            index = 0;
        }

        boolean goToMain = getIntent().getBooleanExtra(EXTRA_GO_TO_MAIN, false);
        tvUnlockedTitle.setText("Logro desbloqueado:");
        tvUnlockedName.setText(achievementNames.get(index));

        animateFrame();

        int nextIndex = index + 1;
        btnContinue.setOnClickListener(v -> {
            if (nextIndex < achievementNames.size()) {
                Intent nextIntent = new Intent(this, AchievementUnlockedActivity.class);
                nextIntent.putStringArrayListExtra(EXTRA_ACHIEVEMENT_NAMES, achievementNames);
                nextIntent.putExtra(EXTRA_START_INDEX, nextIndex);
                nextIntent.putExtra(EXTRA_GO_TO_MAIN, goToMain);
                startActivity(nextIntent);
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
        ObjectAnimator titleFade = ObjectAnimator.ofFloat(tvUnlockedTitle, "alpha", 0f, 1f);
        titleFade.setDuration(700);
        titleFade.start();

        ObjectAnimator nameFade = ObjectAnimator.ofFloat(tvUnlockedName, "alpha", 0f, 1f);
        nameFade.setDuration(900);
        nameFade.start();

        ObjectAnimator buttonFade = ObjectAnimator.ofFloat(btnContinue, "alpha", 0f, 1f);
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

