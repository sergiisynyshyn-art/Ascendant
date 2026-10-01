package com.example.ascendant.classselection;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ascendant.R;
import com.example.ascendant.database.PlayerDatabase;
import com.example.ascendant.finalmessage.FinalMessageActivity;

public class ClassSelectionActivity extends AppCompatActivity {

    private TextView titleText;
    private LinearLayout warriorCard, assassinCard, mageCard, tankCard, supportCard;
    private Button btnConfirm;
    private PlayerDatabase database;
    private String selectedClass = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Fullscreen
        getWindow().getDecorView().setSystemUiVisibility(
                android.view.View.SYSTEM_UI_FLAG_FULLSCREEN |
                        android.view.View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                        android.view.View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        );

        setContentView(R.layout.activity_class_selection);

        titleText = findViewById(R.id.titleText);
        warriorCard = findViewById(R.id.warriorCard);
        assassinCard = findViewById(R.id.assassinCard);
        mageCard = findViewById(R.id.mageCard);
        tankCard = findViewById(R.id.tankCard);
        supportCard = findViewById(R.id.supportCard);
        btnConfirm = findViewById(R.id.btnConfirm);

        database = PlayerDatabase.getInstance(this);

        // Animación inicial
        new Handler(Looper.getMainLooper()).postDelayed(this::animateElements, 300);

        // Listeners para selección
        warriorCard.setOnClickListener(v -> selectClass("Warrior", warriorCard));
        assassinCard.setOnClickListener(v -> selectClass("Assassin", assassinCard));
        mageCard.setOnClickListener(v -> selectClass("Mage", mageCard));
        tankCard.setOnClickListener(v -> selectClass("Tank", tankCard));
        supportCard.setOnClickListener(v -> selectClass("Support", supportCard));

        btnConfirm.setOnClickListener(v -> {
            if (selectedClass == null) {
                return;
            }

            // Guardar en SQLite
            database.saveClass(selectedClass);

            // Ir a mensaje final
            Intent intent = new Intent(ClassSelectionActivity.this, FinalMessageActivity.class);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            finish();
        });
    }

    private void selectClass(String className, LinearLayout card) {
        selectedClass = className;

        // Quitar highlight de todas
        warriorCard.setBackgroundResource(R.drawable.card_background);
        assassinCard.setBackgroundResource(R.drawable.card_background);
        mageCard.setBackgroundResource(R.drawable.card_background);
        tankCard.setBackgroundResource(R.drawable.card_background);
        supportCard.setBackgroundResource(R.drawable.card_background);

        // Highlight a la seleccionada
        card.setBackgroundResource(R.drawable.card_background_selected);

        // Mostrar botón confirmar
        btnConfirm.setVisibility(View.VISIBLE);
        ObjectAnimator.ofFloat(btnConfirm, "alpha", 0f, 1f).setDuration(500).start();
    }

    private void animateElements() {
        ObjectAnimator.ofFloat(titleText, "alpha", 0f, 1f).setDuration(1000).start();

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            ObjectAnimator.ofFloat(warriorCard, "alpha", 0f, 1f).setDuration(600).start();
        }, 300);

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            ObjectAnimator.ofFloat(assassinCard, "alpha", 0f, 1f).setDuration(600).start();
        }, 450);

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            ObjectAnimator.ofFloat(mageCard, "alpha", 0f, 1f).setDuration(600).start();
        }, 600);

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            ObjectAnimator.ofFloat(tankCard, "alpha", 0f, 1f).setDuration(600).start();
        }, 750);

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            ObjectAnimator.ofFloat(supportCard, "alpha", 0f, 1f).setDuration(600).start();
        }, 900);
    }
}

