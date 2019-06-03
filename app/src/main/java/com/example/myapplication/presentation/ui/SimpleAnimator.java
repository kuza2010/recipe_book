package com.example.myapplication.presentation.ui;

import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

public class SimpleAnimator {

    public static void setDefaultAnimation(int repeatMode,long delayMs, View view){
        Animation animation = new AlphaAnimation(0.0f, 1.0f);
        animation.setDuration(delayMs);
        animation.setStartOffset(50);
        animation.setRepeatMode(repeatMode);
        animation.setRepeatCount(Animation.INFINITE);
        view.startAnimation(animation);
    }
}
