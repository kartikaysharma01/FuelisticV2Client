package com.example.fuelisticv2client.fuelisticv2client.LoginSignUp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.fuelisticv2client.R;
import com.example.fuelisticv2client.fuelisticv2client.Adapter.SliderAdapter;

public class onboarding1 extends AppCompatActivity {
ViewPager viewPager;
TextView[] dots;
LinearLayout dots_layout;
SliderAdapter SliderAdapter;
Button startbtn ;
Animation banim;
int currentPos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_onboarding1);

        viewPager=findViewById(R.id.slider);
        dots_layout= findViewById(R.id.dots);
        startbtn = findViewById(R.id.get_started_btn);

        //call adapter
        SliderAdapter= new SliderAdapter(this);
        viewPager.setAdapter(SliderAdapter);

        addDots(0);
        viewPager.addOnPageChangeListener(changeListener);
    }
    public void skip(View view){
        startActivity(new Intent(this,  AppStartupScreen.class));
        finish();
    }

    public void next(View view){

        viewPager.setCurrentItem(currentPos+1);

    }
    private void addDots(int position){

        dots=new TextView[3];
        dots_layout.removeAllViews();
        for(int i=0 ; i<dots.length ; i++){

            dots[i]=new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226"));
            dots[i].setTextSize(30);
            dots_layout.addView(dots[i]);
        }
        if(dots.length>0){
            dots[position].setTextColor(getResources().getColor(R.color.colorAccent));
        }
    }
        ViewPager.OnPageChangeListener changeListener=new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            
            @Override
            public void onPageSelected(int position) {
            addDots(position);
            currentPos=position;
            if(position == 0 ){
                startbtn.setVisibility(View.INVISIBLE);
            }
            else if(position == 1) {
                startbtn.setVisibility(View.INVISIBLE);
            }
            else {
                banim= AnimationUtils.loadAnimation(onboarding1.this, R.anim.button_anim);
                startbtn.setAnimation(banim);
                startbtn.setVisibility(View.VISIBLE);
               // startActivity(new Intent(this, Login.class));
            }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        };
    }
