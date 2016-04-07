package com.ovulationcalendar.herdays.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.ovulationcalendar.herdays.R;
import com.ovulationcalendar.herdays.data.EZSharedPreferences;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
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
