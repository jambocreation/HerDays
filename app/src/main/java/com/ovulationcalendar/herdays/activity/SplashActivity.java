package com.ovulationcalendar.herdays.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;

import com.ovulationcalendar.herdays.R;
import com.ovulationcalendar.herdays.data.EZSharedPreferences;

import java.util.Random;

public class SplashActivity extends AppCompatActivity {

    RelativeLayout bg;
    int background[] = {R.drawable.rsz_background1, R.drawable.rsz_background2, R.drawable.rsz_background3, R.drawable.background_sub};

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Random rand = new Random();

        int x = rand.nextInt(background.length);

        bg = (RelativeLayout) findViewById(R.id.bg);
        bg.setBackground(getResources().getDrawable(background[x]));
        nextActivity();
    }

    private void nextActivity() {

        final int DELAY = 2000;
        final int setup = EZSharedPreferences.getSetup(SplashActivity.this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (setup == 0)
                    startActivity(new Intent(SplashActivity.this, Information.class));
                else
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));

                finish();
            }
        }, DELAY);


    }


}
