package com.example.ascendant.intro;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.TextView;
import android.widget.VideoView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.ascendant.R;
import com.example.ascendant.MainActivity;
import com.example.ascendant.confirmation.ConfirmationActivity;
import com.example.ascendant.database.PlayerDatabase;
public class IntroActivity extends AppCompatActivity {
    private static final String TAG = "IntroActivity";
    private VideoView introVideo;
    private TextView debugText;
    private MediaPlayer mediaPlayer;
    private Handler transitionHandler = new Handler(Looper.getMainLooper());
    private PlayerDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate - Iniciando IntroActivity");
        // Fullscreen inmersivo
        getWindow().getDecorView().setSystemUiVisibility(
                android.view.View.SYSTEM_UI_FLAG_FULLSCREEN |
                        android.view.View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                        android.view.View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        );
        setContentView(R.layout.activity_intro);
        database = PlayerDatabase.getInstance(this);
        introVideo = findViewById(R.id.introVideo);
        debugText = findViewById(R.id.debugText);
        Log.d(TAG, "Views inicializadas");
        // Ruta del video en /res/raw/
        Uri videoPath = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.intro2);
        Log.d(TAG, "Video path: " + videoPath.toString());
        introVideo.setVideoURI(videoPath);
        // Handler de error
        introVideo.setOnErrorListener((mp, what, extra) -> {
            Log.e(TAG, "Error en video: what=" + what + " extra=" + extra);
            debugText.setText("ERROR LOADING VIDEO\nTAP TO CONTINUE");
            Toast.makeText(this, "Video error, tap to continue", Toast.LENGTH_SHORT).show();
            return true;
        });
        // FULLSCREEN REAL
        introVideo.setOnPreparedListener(mp -> {
            Log.d(TAG, "Video preparado, iniciando reproducción");
            mediaPlayer = mp;
            mp.setVideoScalingMode(MediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING);
            // Ocultar texto de debug cuando el video esté listo
            debugText.setVisibility(android.view.View.GONE);
            introVideo.start();
        });
        // Cuando el video termina -> transicion inmediata
        introVideo.setOnCompletionListener(mp -> {
            Log.d(TAG, "Video completado");
            goToNextStage();
        });
        // Click para saltar
        introVideo.setOnClickListener(v -> {
            Log.d(TAG, "Click en video, saltando");
            goToNextStage();
        });
        debugText.setOnClickListener(v -> {
            Log.d(TAG, "Click en debug text, saltando");
            goToNextStage();
        });
    }
    private void goToNextStage() {
        transitionHandler.removeCallbacksAndMessages(null);
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
        Intent intent;
        if (isOnboardingComplete()) {
            Log.d(TAG, "Onboarding completado, transicion a MainActivity directamente");
            intent = new Intent(IntroActivity.this, MainActivity.class);
        } else {
            Log.d(TAG, "Onboarding no completado, transicion a ConfirmationActivity");
            intent = new Intent(IntroActivity.this, ConfirmationActivity.class);
        }
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();
    }
    private boolean isOnboardingComplete() {
        String nickname = database.getNickname();
        String playerClass = database.getPlayerClass();
        return nickname != null && !nickname.trim().isEmpty()
                && playerClass != null && !playerClass.trim().isEmpty();
    }
    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
        if (introVideo != null && introVideo.isPlaying()) {
            introVideo.pause();
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
        transitionHandler.removeCallbacksAndMessages(null);
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}