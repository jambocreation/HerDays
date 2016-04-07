package com.ovulationcalendar.herdays.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.ovulationcalendar.herdays.R;
import com.ovulationcalendar.herdays.adapter.MainPagerAdapter;
import com.ovulationcalendar.herdays.sliding.SlidingTabLayout;

public class MainActivity extends AppCompatActivity {

    SlidingTabLayout tabs;
    MainPagerAdapter adapter;
    ViewPager pager;
    CharSequence titles[] = {"Home", "Calendar", "Information"};

    TextView tvTitle;

    int tabColor[] = {R.color.periodColor, R.color.fertileColor, R.color.ovulationColor};

    private static MainActivity inst;

    public static MainActivity instance() {
        return inst;
    }

    @Override
    protected void onStart() {
        super.onStart();
        inst = this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupActivity();
        setupToolbar();
        setupPager();
        setupAlarm();
    }


    private void setupActivity() {

    }

    private void setupToolbar() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

//        mToolbar.setTitle(title);
//        mToolbar.setPadding(0, AppUtils.getStatusBarHeight(getApplicationContext()), 0, 0);
        tvTitle = (TextView) mToolbar.findViewById(R.id.title);

    }

    private void setupPager() {
        adapter = new MainPagerAdapter(getSupportFragmentManager(), titles);
        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(adapter);
//        pager.setPageTransformer(false, new DepthPageTransformer());
        tabs = (SlidingTabLayout) findViewById(R.id.tabs);
        tabs.setDistributeEvenly(true);
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.textColorPrimary);
            }
        });

        tabs.setViewPager(pager);

        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {

                tvTitle.setText(titles[position]);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            Window window = getWindow();
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(Color.BLUE);
//        }
    }

    private void setupAlarm() {
//        Calendar c = Calendar.getInstance();
//        c.set(Calendar.HOUR_OF_DAY, 5);
//        c.set(Calendar.MINUTE, 11);
//
//        Intent intent = new Intent(MainActivity.this, AlarmReceiver.class);
//        PendingIntent pIntent = PendingIntent.getBroadcast(MainActivity.this, 0, intent, 0);
//        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
//        alarmManager.set(AlarmManager.RTC, c.getTimeInMillis(), pIntent);
//        Calendar c = Calendar.getInstance();
//
//        Date
    }


}
