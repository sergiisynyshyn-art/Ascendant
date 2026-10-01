package com.example.ascendant.nickname;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ascendant.R;
import com.example.ascendant.classselection.ClassSelectionActivity;
import com.example.ascendant.database.PlayerDatabase;

public class NicknameActivity extends AppCompatActivity {

    private TextView titleText;
    private EditText nicknameInput;
    private Button btnConfirm;
    private PlayerDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Fullscreen
        getWindow().getDecorView().setSystemUiVisibility(
                android.view.View.SYSTEM_UI_FLAG_FULLSCREEN |
                        android.view.View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                        android.view.View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        );

        setContentView(R.layout.activity_nickname);

        titleText = findViewById(R.id.titleText);
        nicknameInput = findViewById(R.id.nicknameInput);
        btnConfirm = findViewById(R.id.btnConfirm);

        database = PlayerDatabase.getInstance(this);

        // Animación inicial
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            animateElements();
        }, 300);

        btnConfirm.setOnClickListener(v -> {
            String nickname = nicknameInput.getText().toString().trim();

            if (TextUtils.isEmpty(nickname)) {
                Toast.makeText(this, "Enter a valid nickname", Toast.LENGTH_SHORT).show();
                return;
            }

            if (nickname.length() < 3) {
                Toast.makeText(this, "Nickname must be at least 3 characters", Toast.LENGTH_SHORT).show();
                return;
            }

            // Guardar en SQLite
            database.saveNickname(nickname);

            // Ir a selección de clase
            Intent intent = new Intent(NicknameActivity.this, ClassSelectionActivity.class);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            finish();
        });
    }

    private void animateElements() {
        ObjectAnimator.ofFloat(titleText, "alpha", 0f, 1f).setDuration(1000).start();

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            ObjectAnimator.ofFloat(nicknameInput, "alpha", 0f, 1f).setDuration(800).start();
            ObjectAnimator.ofFloat(btnConfirm, "alpha", 0f, 1f).setDuration(800).start();
        }, 500);
    }
}

