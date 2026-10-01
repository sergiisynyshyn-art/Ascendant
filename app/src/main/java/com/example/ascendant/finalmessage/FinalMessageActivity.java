package com.example.ascendant.finalmessage;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ascendant.MainActivity;
import com.example.ascendant.R;

public class FinalMessageActivity extends AppCompatActivity {

    private TextView messageText;
    private Button btnEnter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Fullscreen
        getWindow().getDecorView().setSystemUiVisibility(
                android.view.View.SYSTEM_UI_FLAG_FULLSCREEN |
                        android.view.View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                        android.view.View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        );

        setContentView(R.layout.activity_final_message);

        messageText = findViewById(R.id.messageText);
        btnEnter = findViewById(R.id.btnEnter);

        // Animación inicial
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            animateElements();
        }, 300);

        btnEnter.setOnClickListener(v -> {
            Intent intent = new Intent(FinalMessageActivity.this, MainActivity.class);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            finish();
        });
    }

    private void animateElements() {
        ObjectAnimator.ofFloat(messageText, "alpha", 0f, 1f).setDuration(2000).start();

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            ObjectAnimator.ofFloat(btnEnter, "alpha", 0f, 1f).setDuration(1000).start();
        }, 1500);
    }
}

