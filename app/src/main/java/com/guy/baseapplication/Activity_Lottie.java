package com.guy.baseapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.airbnb.lottie.Lottie;
import com.airbnb.lottie.LottieAnimationView;


// download icons from: https://lottiefiles.com/
// AriBnb Documentation: http://airbnb.io/lottie/#/android
// Gradle: implementation 'com.airbnb.android:lottie:3.7.0'

public class Activity_Lottie extends AppCompatActivity {

    private LottieAnimationView lottie_SPC_brahma;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lottie);

        lottie_SPC_brahma = findViewById(R.id.lottie_SPC_brahma);
        lottie_SPC_brahma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lottie_SPC_brahma.isAnimating()) {
                    lottie_SPC_brahma.pauseAnimation();
                } else {
                    lottie_SPC_brahma.resumeAnimation();
                }
            }
        });
    }
}