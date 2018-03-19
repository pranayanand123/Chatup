package com.example.pranayanand.chatup;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import gr.net.maroulis.library.EasySplashScreen;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EasySplashScreen config = new EasySplashScreen(SplashScreen.this)
                .withFullScreen()
                .withTargetActivity(Login.class)
                .withSplashTimeOut(5000)
                .withBackgroundResource(R.color.teal)
                .withFooterText("Copyright 2017")
                .withBeforeLogoText("CHATUP")
                .withLogo(R.drawable.ic_launcher);


        //change text color
        config.getFooterTextView().setTextColor(Color.WHITE);
        config.getBeforeLogoTextView().setTextSize(30);

        //finally create the view
        View easySplashScreenView = config.create();
        setContentView(easySplashScreenView);
    }
}
