package com.example.ascendant.deadlines;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import com.example.ascendant.R;

public class CriticalDeadlineActivity extends AppCompatActivity {

    private LinearLayout dangerPanelContainer;
    private ObjectAnimator pulseAnimator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFullscreen();
        setContentView(R.layout.activity_deadline_critical);

        dangerPanelContainer = findViewById(R.id.dangerPanelContainer);
        TextView title = findViewById(R.id.tvDangerTitle);
        TextView body = findViewById(R.id.tvDangerBody);
        Button continueButton = findViewById(R.id.btnContinue);

        title.setShadowLayer(28f, 0f, 0f, 0xFFFF2A2A);
        body.setShadowLayer(18f, 0f, 0f, 0xFF7A0000);

        startPulse();

        continueButton.setOnClickListener(v -> finish());
    }

    private void startPulse() {
        pulseAnimator = ObjectAnimator.ofFloat(dangerPanelContainer, View.SCALE_X, 1f, 1.02f);
        ObjectAnimator pulseY = ObjectAnimator.ofFloat(dangerPanelContainer, View.SCALE_Y, 1f, 1.02f);
        ObjectAnimator fade = ObjectAnimator.ofFloat(dangerPanelContainer, View.ALPHA, 0.96f, 1f);

        pulseAnimator.setDuration(1200);
        pulseAnimator.setRepeatMode(ObjectAnimator.REVERSE);
        pulseAnimator.setRepeatCount(ObjectAnimator.INFINITE);
        pulseAnimator.start();

        pulseY.setDuration(1200);
        pulseY.setRepeatMode(ObjectAnimator.REVERSE);
        pulseY.setRepeatCount(ObjectAnimator.INFINITE);
        pulseY.start();

        fade.setDuration(1200);
        fade.setRepeatMode(ObjectAnimator.REVERSE);
        fade.setRepeatCount(ObjectAnimator.INFINITE);
        fade.start();
    }

    private void setFullscreen() {
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        WindowInsetsControllerCompat controller = WindowCompat.getInsetsController(getWindow(), getWindow().getDecorView());
        controller.hide(WindowInsetsCompat.Type.systemBars());
        controller.setSystemBarsBehavior(WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        DeadlineGatekeeper.applyImmersiveMode(this);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            DeadlineGatekeeper.applyImmersiveMode(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (pulseAnimator != null) {
            pulseAnimator.cancel();
        }
    }
}


