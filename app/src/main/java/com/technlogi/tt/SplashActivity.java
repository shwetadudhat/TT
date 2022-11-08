package com.technlogi.tt;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ObjectAnimator backgroundColorAnimator = ObjectAnimator.ofArgb(findViewById(R.id.splashScreenConstraintLayout),"backgroundColor",0xFF000000,0xFFFFFFFF);
        backgroundColorAnimator.setDuration(3000);

        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(findViewById(R.id.ApplicationLogo),"alpha",0,1);
        objectAnimator.setDuration(2000);

        ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(findViewById(R.id.ApplicationName),"alpha",0,1);
        objectAnimator1.setDuration(2000);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(objectAnimator).with(objectAnimator1);
        animatorSet.play(objectAnimator1).with(backgroundColorAnimator);
        animatorSet.start();


        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this,ExploreActivity.class));
                finish();
            }
        },3000);

    }
}