package com.example.fuelisticv2client.fuelisticv2client.LoginSignUp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.example.fuelisticv2client.R;
import com.example.fuelisticv2client.fuelisticv2client.UI.HomeActivity;

public class LogoSplash extends AppCompatActivity {
    SharedPreferences onBoardingScreen;
    public static int SPLASH_SCREEN= 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN );
        setContentView(R.layout.activity_login_splash);

        new Handler().postDelayed(new Runnable() {
            @Override public void run() {
                onBoardingScreen = getSharedPreferences("onBoardingScreen", MODE_PRIVATE);
                boolean isFirstTime = onBoardingScreen.getBoolean("firstTime", true);
                if (isFirstTime) {
                    SharedPreferences.Editor editor = onBoardingScreen.edit();
                    editor.putBoolean("firstTime", false);
                    editor.commit();

                    Intent i = new Intent(LogoSplash.this, onboarding1.class);
                    startActivity(i);
                    //editor.apply();

                }
                else {
                    Intent i = new Intent(LogoSplash.this,MainActivity.class);
                    startActivity(i);

                }
                finish();

            }
        }, SPLASH_SCREEN);
    }
}