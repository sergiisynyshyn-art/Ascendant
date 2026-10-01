package com.example.ascendant.preintro;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import com.example.ascendant.intro.IntroActivity;
import com.example.ascendant.welcome.WelcomeBackActivity;
import com.example.ascendant.R;
import com.example.ascendant.database.PlayerDatabase;
public class PreIntroActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        );
        setContentView(R.layout.activity_preintro);
        PlayerDatabase database = PlayerDatabase.getInstance(this);
        boolean isOnboardingComplete = isOnboardingComplete(database);
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            Intent intent;
            if (isOnboardingComplete) {
                intent = new Intent(PreIntroActivity.this, WelcomeBackActivity.class);
            } else {
                intent = new Intent(PreIntroActivity.this, IntroActivity.class);
            }
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            finish();
        }, 2000);
    }
    private boolean isOnboardingComplete(PlayerDatabase database) {
        String nickname = database.getNickname();
        String playerClass = database.getPlayerClass();
        return nickname != null && !nickname.trim().isEmpty()
                && playerClass != null && !playerClass.trim().isEmpty();
    }
}
