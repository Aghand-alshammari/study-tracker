package com.taqaddum.app;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {
    private final Handler handler = new Handler(Looper.getMainLooper());
    @Override protected void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.activity_splash);
        handler.postDelayed(() -> { startActivity(new Intent(this, MainActivity.class)); finish(); }, 2000);
    }
    @Override protected void onDestroy() { handler.removeCallbacksAndMessages(null); super.onDestroy(); }
}
