package com.example.ascendant.progress;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ascendant.R;
import com.example.ascendant.database.PlayerDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ProgressActivity extends AppCompatActivity {

    private PlayerDatabase db;
    private LinearLayout progressContainer;
    private Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_FULLSCREEN |
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        );

        setContentView(R.layout.activity_progress);

        db = PlayerDatabase.getInstance(this);
        progressContainer = findViewById(R.id.progressContainer);
        btnBack = findViewById(R.id.btnBack);

        btnBack.setOnClickListener(v -> {
            finish();
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        });

        loadProgress();
    }

    private void loadProgress() {
        String today = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        List<PlayerDatabase.MissionProgress> progressList = db.getMissionsProgress(this, today);
        
        LayoutInflater inflater = LayoutInflater.from(this);
        progressContainer.removeAllViews();

        for (PlayerDatabase.MissionProgress item : progressList) {
            View itemView = inflater.inflate(R.layout.item_mission_progress, progressContainer, false);
            
            TextView tvName = itemView.findViewById(R.id.tvMissionName);
            TextView tvWeekly = itemView.findViewById(R.id.tvWeeklyProgress);
            TextView tvTotal = itemView.findViewById(R.id.tvTotalProgress);

            tvName.setText(item.name);
            tvWeekly.setText(getString(R.string.progress_value_format, item.weeklyCount, item.unitLabel));
            tvTotal.setText(getString(R.string.progress_value_format, item.totalCount, item.unitLabel));

            progressContainer.addView(itemView);
        }
    }
    
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_FULLSCREEN |
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            );
        }
    }
}
