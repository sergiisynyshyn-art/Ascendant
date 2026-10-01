package com.example.ascendant.achievements;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ascendant.R;
import com.example.ascendant.database.PlayerDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AchievementsActivity extends AppCompatActivity {

    private LinearLayout achievementsContainer;
    private ScrollView achievementsScroll;
    private Button btnPrevPage;
    private Button btnNextPage;
    private Button btnScrollUp;
    private Button btnScrollDown;
    private TextView tvPageIndicator;
    private PlayerDatabase db;
    private final List<PlayerDatabase.AchievementItem> allAchievements = new ArrayList<>();
    private int currentPage = 0;
    private static final int PAGE_SIZE = 8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        applyImmersiveFullscreen();
        setContentView(R.layout.activity_achievements);

        db = PlayerDatabase.getInstance(this);
        achievementsContainer = findViewById(R.id.achievementsContainer);
        achievementsScroll = findViewById(R.id.achievementsScroll);
        btnPrevPage = findViewById(R.id.btnPrevPage);
        btnNextPage = findViewById(R.id.btnNextPage);
        btnScrollUp = findViewById(R.id.btnScrollUp);
        btnScrollDown = findViewById(R.id.btnScrollDown);
        tvPageIndicator = findViewById(R.id.tvPageIndicator);

        btnPrevPage.setOnClickListener(v -> changePage(-1));
        btnNextPage.setOnClickListener(v -> changePage(1));
        btnScrollUp.setOnClickListener(v -> smoothScrollByPage(-1));
        btnScrollDown.setOnClickListener(v -> smoothScrollByPage(1));

        Button backButton = findViewById(R.id.btnBackAchievements);
        backButton.setOnClickListener(v -> finish());

        renderAchievements();
    }

    @Override
    protected void onResume() {
        super.onResume();
        renderAchievements();
    }

    private void renderAchievements() {
        achievementsContainer.removeAllViews();

        db.evaluateAndUnlockAchievements(getToday(), db.getCurrentLevel(), db.getStepsToday());
        allAchievements.clear();
        allAchievements.addAll(db.getAllAchievements());

        int totalPages = Math.max(1, (int) Math.ceil(allAchievements.size() / (double) PAGE_SIZE));
        if (currentPage >= totalPages) {
            currentPage = totalPages - 1;
        }

        int start = currentPage * PAGE_SIZE;
        int end = Math.min(start + PAGE_SIZE, allAchievements.size());

        for (int i = start; i < end; i++) {
            PlayerDatabase.AchievementItem item = allAchievements.get(i);
            View row = getLayoutInflater().inflate(R.layout.item_achievement, achievementsContainer, false);

            TextView name = row.findViewById(R.id.tvAchievementName);
            TextView description = row.findViewById(R.id.tvAchievementDescription);
            TextView status = row.findViewById(R.id.tvAchievementStatus);

            name.setText(item.name);
            description.setText(item.description);

            if (item.isUnlocked) {
                row.setBackgroundResource(R.drawable.step_panel_background);
                name.setTextColor(0xFF00D4FF);
                name.setShadowLayer(16f, 0f, 0f, 0xFF00D4FF);
                description.setTextColor(0xFFC9EEFF);
                status.setText("DESBLOQUEADO" + (item.unlockDate != null ? " - " + item.unlockDate : ""));
                status.setTextColor(0xFF00FF88);
            } else {
                row.setBackgroundResource(R.drawable.card_background);
                name.setShadowLayer(0f, 0f, 0f, 0x00000000);
                name.setTextColor(0xFF9AA7B5);
                description.setTextColor(0xFF6F7D8E);
                status.setText(item.progress > 0 ? "BLOQUEADO - progreso: " + item.progress : "BLOQUEADO");
                status.setTextColor(0xFF7C8794);
            }

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            params.bottomMargin = dp(10);
            row.setLayoutParams(params);
            achievementsContainer.addView(row);
        }

        tvPageIndicator.setText((currentPage + 1) + " / " + totalPages);
        btnPrevPage.setEnabled(currentPage > 0);
        btnPrevPage.setAlpha(currentPage > 0 ? 1f : 0.5f);
        btnNextPage.setEnabled(currentPage < totalPages - 1);
        btnNextPage.setAlpha(currentPage < totalPages - 1 ? 1f : 0.5f);

        achievementsScroll.post(() -> achievementsScroll.fullScroll(View.FOCUS_UP));
    }

    private void changePage(int delta) {
        int totalPages = Math.max(1, (int) Math.ceil(allAchievements.size() / (double) PAGE_SIZE));
        int target = currentPage + delta;
        if (target < 0 || target >= totalPages) return;
        currentPage = target;

        achievementsContainer.animate().alpha(0f).setDuration(100).withEndAction(() -> {
            renderAchievements();
            achievementsContainer.setAlpha(0f);
            achievementsContainer.animate().alpha(1f).setDuration(130).start();
        }).start();
    }

    private void smoothScrollByPage(int direction) {
        int viewport = achievementsScroll.getHeight();
        int delta = Math.max(viewport - dp(40), dp(120)) * direction;
        achievementsScroll.smoothScrollBy(0, delta);
    }

    private String getToday() {
        return new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
    }

    private int dp(int value) {
        return Math.round(getResources().getDisplayMetrics().density * value);
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
