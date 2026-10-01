package com.example.ascendant.confirmation;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ascendant.R;
import com.example.ascendant.nickname.NicknameActivity;

public class ConfirmationActivity extends AppCompatActivity {

    private TextView questionText;
    private Button btnYes, btnNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Fullscreen
        getWindow().getDecorView().setSystemUiVisibility(
                android.view.View.SYSTEM_UI_FLAG_FULLSCREEN |
                        android.view.View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                        android.view.View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        );

        setContentView(R.layout.activity_confirmation);

        questionText = findViewById(R.id.questionText);
        btnYes = findViewById(R.id.btnYes);
        btnNo = findViewById(R.id.btnNo);

        // Animación inicial
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            animateElements();
        }, 300);

        btnYes.setOnClickListener(v -> {
            Intent intent = new Intent(ConfirmationActivity.this, NicknameActivity.class);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            finish();
        });

        btnNo.setOnClickListener(v -> {
            finishAffinity(); // Cierra la app completamente
        });
    }

    private void animateElements() {
        ObjectAnimator.ofFloat(questionText, "alpha", 0f, 1f).setDuration(1000).start();

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            ObjectAnimator.ofFloat(btnYes, "alpha", 0f, 1f).setDuration(800).start();
            ObjectAnimator.ofFloat(btnNo, "alpha", 0f, 1f).setDuration(800).start();
        }, 500);
    }
}

