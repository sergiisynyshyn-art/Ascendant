package com.example.ascendant.welcome;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ascendant.MainActivity;
import com.example.ascendant.R;
import com.example.ascendant.database.PlayerDatabase;

public class WelcomeBackActivity extends AppCompatActivity {

    private final Handler handler = new Handler(Looper.getMainLooper());
    private Runnable goMainRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        );

        setContentView(R.layout.activity_welcome_back);

        TextView tvWelcome = findViewById(R.id.tvWelcomeBack);
        PlayerDatabase db = PlayerDatabase.getInstance(this);
        String nickname = db.getNickname();
        if (nickname == null || nickname.trim().isEmpty()) {
            nickname = "Cazador";
        }
        tvWelcome.setText(getString(R.string.welcome_back_format, nickname));

        goMainRunnable = () -> {
            Intent intent = new Intent(WelcomeBackActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            finish();
        };

        handler.postDelayed(goMainRunnable, 1500);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (goMainRunnable != null) {
            handler.removeCallbacks(goMainRunnable);
        }
    }
}

