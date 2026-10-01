package com.example.ascendant.missionscomplete;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ascendant.R;

public class MissionCompleteVideoActivity extends AppCompatActivity {

    public static final String EXTRA_VIDEO_RES_NAME = "extra_video_res_name";
    public static final String EXTRA_APPLY_LEVEL_UP = "extra_apply_level_up";
    private static final String FALLBACK_VIDEO_NAME = "lvlup3";

    private VideoView videoView;
    private View fadeOverlay;
    private boolean hasNavigated;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        applyImmersiveFullscreen();
        setContentView(R.layout.activity_mission_complete_video);

        videoView = findViewById(R.id.missionCompleteVideo);
        fadeOverlay = findViewById(R.id.fadeOverlay);

        playMissionVideo();
    }

    private void playMissionVideo() {
        String requestedVideo = getIntent().getStringExtra(EXTRA_VIDEO_RES_NAME);
        if (requestedVideo == null || requestedVideo.trim().isEmpty()) {
            requestedVideo = FALLBACK_VIDEO_NAME;
        }

        int videoResId = getResources().getIdentifier(requestedVideo, "raw", getPackageName());
        if (videoResId == 0) {
            videoResId = getResources().getIdentifier(FALLBACK_VIDEO_NAME, "raw", getPackageName());
        }

        if (videoResId == 0) {
            goToMissionCompleteFrame();
            return;
        }

        Uri videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + videoResId);
        videoView.setVideoURI(videoUri);

        videoView.setOnPreparedListener(mp -> {
            adjustVideoBounds(mp);
            startFadeIn();
            videoView.start();
        });

        videoView.setOnCompletionListener(mp -> goToMissionCompleteFrame());
        videoView.setOnErrorListener((mp, what, extra) -> {
            goToMissionCompleteFrame();
            return true;
        });
    }

    private void startFadeIn() {
        ObjectAnimator fadeIn = ObjectAnimator.ofFloat(fadeOverlay, "alpha", 1f, 0f);
        fadeIn.setDuration(700);
        fadeIn.start();
    }

    private void adjustVideoBounds(MediaPlayer mediaPlayer) {
        int videoWidth = mediaPlayer.getVideoWidth();
        int videoHeight = mediaPlayer.getVideoHeight();
        if (videoWidth <= 0 || videoHeight <= 0) {
            return;
        }

        int screenWidth = getResources().getDisplayMetrics().widthPixels;
        int screenHeight = getResources().getDisplayMetrics().heightPixels;

        float videoRatio = (float) videoWidth / (float) videoHeight;
        float screenRatio = (float) screenWidth / (float) screenHeight;

        int targetWidth;
        int targetHeight;
        if (videoRatio > screenRatio) {
            targetWidth = screenWidth;
            targetHeight = (int) (screenWidth / videoRatio);
        } else {
            targetHeight = screenHeight;
            targetWidth = (int) (screenHeight * videoRatio);
        }

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(targetWidth, targetHeight);
        params.gravity = Gravity.CENTER;
        videoView.setLayoutParams(params);
    }

    private void goToMissionCompleteFrame() {
        if (hasNavigated) {
            return;
        }
        hasNavigated = true;

        boolean shouldApplyLevelUp = getIntent().getBooleanExtra(EXTRA_APPLY_LEVEL_UP, false);

        Intent intent = new Intent(this, MissionCompleteFrameActivity.class);
        intent.putExtra(EXTRA_APPLY_LEVEL_UP, shouldApplyLevelUp);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();
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

