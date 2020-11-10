package com.example.fuelisticv2client.fuelisticv2client.LoginSignUp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.example.fuelisticv2client.R;

public class AppStartupScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN );
        setContentView(R.layout.activity_app_startup_screen);
    }

    public void callSignUpScreenfromStart(View view) {
        Intent intent = new Intent(AppStartupScreen.this, SignUp.class);
        startActivity(intent);
    }

    public void callLoginScreenfromStart(View view) {
        Intent intent = new Intent(AppStartupScreen.this, Login.class);
        startActivity(intent);
    }


//    public void callLoginScreen(View view){
//        Intent intent = new Intent(AppStartupScreen.this, Login.class);
//        startActivity(intent);
//    }
//
//    public void callSignUpScreen(View view){
//        Intent intent = new Intent(AppStartupScreen.this, SignUp.class);
//        startActivity(intent);
//    }


}