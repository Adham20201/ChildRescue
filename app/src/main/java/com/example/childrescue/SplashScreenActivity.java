package com.example.childrescue;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

@SuppressLint("CustomSplashScreen")
public class SplashScreenActivity extends AppCompatActivity {

    private static int SPLASH_SCREEN = 4000;

    Animation topAnim, bottomAnim;
    ImageView logoImage;
    TextView logoText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        topAnim = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        logoImage = findViewById(R.id.logoImage);
        logoText = findViewById(R.id.logoText);

        logoImage.setAnimation(topAnim);
        logoText.setAnimation(bottomAnim);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreenActivity.this, LoginActivity.class);

                Pair[] pairs = new Pair[2];
                pairs[0] = new Pair<View,String>(logoImage, "logo_image");
                pairs[1] = new Pair<View,String>(logoText, "logo_text");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SplashScreenActivity.this,pairs);
                startActivity(intent,options.toBundle());
            }
        },SPLASH_SCREEN);
    }
}