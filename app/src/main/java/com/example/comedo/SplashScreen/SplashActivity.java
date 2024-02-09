package com.example.comedo.SplashScreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.example.comedo.OnBoardingScreen.OnBoardingActivity;
import com.example.comedo.R;
import com.example.comedo.SignIn.View.SignInActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(() -> {
            boolean onboardingCompleted = PreferenceManager.isOnboardingCompleted(this);
            Intent intent;
            if (onboardingCompleted) {
                intent = new Intent(SplashActivity.this, SignInActivity.class);
            } else {
                intent = new Intent(SplashActivity.this, OnBoardingActivity.class);
            }
            startActivity(intent);
            finish();
        }, 3000);
    }
}