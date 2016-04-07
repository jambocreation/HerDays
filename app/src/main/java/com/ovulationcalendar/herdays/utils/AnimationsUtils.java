package com.ovulationcalendar.herdays.utils;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.ovulationcalendar.herdays.R;

/**
 * Created by mleano on 2/10/2016.
 */
public class AnimationsUtils {

    Animation anim;
//    Animation zoomIn, zoomOut, fadeIn, fadeOut, slideIn, slideOut;
//    Animation clockwise, counter, slideUp, slideDown, slideLeft, slideRight;
//    Animation bounce, blink, flip;

    Context ctx;
    View view;

    public AnimationsUtils(Context ctx) {
        this.ctx = ctx;
    }

    public void zoomIn(View view) {
//        zoomIn = AnimationUtils.loadAnimation(ctx, R.anim.zoom_in);
//        view.startAnimation(zoomIn);
        anim = AnimationUtils.loadAnimation(ctx, R.anim.shake);
        view.startAnimation(anim);
    }

    public void zoomOut(View view) {

        anim = AnimationUtils.loadAnimation(ctx, R.anim.zoom_out);
        view.startAnimation(anim);
    }

    public void fadeIn(View view) {

        anim = AnimationUtils.loadAnimation(ctx, R.anim.fade_in);
        view.startAnimation(anim);
    }

    public void fadeOut(View view) {

        anim = AnimationUtils.loadAnimation(ctx, R.anim.fade_out);
        view.startAnimation(anim);
    }

    public void slideUp(View view) {

        anim = AnimationUtils.loadAnimation(ctx, R.anim.slide_up);
        view.startAnimation(anim);
    }

    public void slideDown(View view) {

        anim = AnimationUtils.loadAnimation(ctx, R.anim.slide_down);
        view.startAnimation(anim);
    }

    public void clockwise(View view) {
        anim = AnimationUtils.loadAnimation(ctx, R.anim.clockwise);
        view.startAnimation(anim);
    }

    public void counter(View view) {
        anim = AnimationUtils.loadAnimation(ctx, R.anim.counter_clockwise);
        view.startAnimation(anim);
    }

}
