package com.example.findpeken;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity {
    private int loadingtime=2000;

    //Variabel
    ImageView logoImage;
    TextView logoTitle;
    TextView logoTitle2;

    //Animations
    Animation sideAnim, bottomAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        //Hooks
        logoImage = findViewById(R.id.img_logo);
        logoTitle = findViewById(R.id.tv_logo);
        logoTitle2 = findViewById(R.id.tv_logo2);

        //Animations
        sideAnim = AnimationUtils.loadAnimation(this, R.anim.side_anim);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_anim);

        //Set Animation on elements
        logoImage.setAnimation(sideAnim);
        logoTitle.setAnimation(bottomAnim);
        logoTitle2.setAnimation(bottomAnim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent main= new Intent(SplashScreen.this, MainActivity.class);
                startActivity(main);
                finish();
            }
        }, loadingtime);

    }
}