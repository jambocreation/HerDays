package com.ovulationcalendar.herdays.activity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.RelativeLayout;

import com.ovulationcalendar.herdays.R;
import com.ovulationcalendar.herdays.utils.HerDaysUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class NewCalendarTest extends AppCompatActivity {

    String TAG = NewCalendarTest.class.getSimpleName();
    Context context = NewCalendarTest.this;
    RelativeLayout xc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_calendar_test);

        xc = (RelativeLayout) findViewById(R.id.xc);
        xc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLastPeriodListener(28);
            }
        });
        onLastPeriodListener(28);
//        dotesting();
    }

    private void setSomeDate() {

        Date date = new Date();


        SimpleDateFormat format = new SimpleDateFormat("EEEE, MMMM d, yyyy", Locale.getDefault());
        //  String formattedDate = format.format(date);
    }

    private void onLastPeriodListener(final int cycleLength) {

        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        final int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        Calendar minDate = Calendar.getInstance();
        minDate.add(Calendar.DAY_OF_YEAR, 1 - cycleLength);

        DatePickerDialog dpd = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                Date date = new Date(year - HerDaysUtils.YEAR_DEFAULT, month, day);

                Log.d(TAG, "YEAR: " + year);
                SimpleDateFormat format = new SimpleDateFormat("EEEE, MMMM d, yyyy", Locale.getDefault());
                String formattedDate = format.format(date);

                Log.d(TAG, formattedDate);
                dotesting(formattedDate, cycleLength);


            }
        }, year, month, day);
        dpd.getDatePicker().setMaxDate(new Date().getTime());
//        dpd.getDatePicker().setMinDate(minDate.getTime().getTime());
//        dpd.getDatePicker().setMinDate(minDate.getTime().getTime());
        dpd.show();

    }


    private void dotesting(String lastPeriod, int cycleLength) {


        Log.d(TAG, "testing");
//        String lastPeriod = EZSharedPreferences.getLastMonth(context);
//        int cycleLength = Integer.valueOf(EZSharedPreferences.getCycle(context));

        Calendar nextPeriod = calendarFromSharedPreference(lastPeriod);
        nextPeriod.add(Calendar.DAY_OF_MONTH, cycleLength);

        Log.d(TAG, "next period date:" + nextPeriod.getTime().toString());


        Calendar currentDate = Calendar.getInstance();
        currentDate.set(Calendar.HOUR_OF_DAY, 0);
        currentDate.set(Calendar.MINUTE, 0);
        currentDate.set(Calendar.SECOND, 0);
        currentDate.set(Calendar.MILLISECOND, 0);

        long lNextPeriod = nextPeriod.getTimeInMillis();
        long lCurrentDate = currentDate.getTimeInMillis();

        long diff = lNextPeriod - lCurrentDate;
        int days = differenceInDays(diff);

        Log.d(TAG, "Days to next period: " + days);


        while(days < 0){

            nextPeriod.add(Calendar.DAY_OF_MONTH, cycleLength);
            Log.d(TAG, "new period date:" + nextPeriod.getTime().toString());
            lNextPeriod = nextPeriod.getTimeInMillis();
            diff = lNextPeriod - lCurrentDate;

            days = differenceInDays(diff);

            Log.d(TAG, "new Days to next period: " + days);

            if(days < 4){
                switch(days){
//                    case
                }
            }


        }


    }

    public Calendar calendarFromSharedPreference(String date) {
        Calendar c = Calendar.getInstance();

        SimpleDateFormat sdf = new SimpleDateFormat("EEEE, MMMM d, yyyy", Locale.getDefault());
//        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
        try {
            c.setTime(sdf.parse(date));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return c;
    }

    public String calendarToString(Calendar calendar) {
        //SimpleDateFormat sdf = SimpleDateFormat("EEEE, MMMM d, yyy")
        return null;
    }

    public int differenceInDays(long diff) {
        int HOURS_IN_DAY = 24;
        int MINUTES_IN_HOUR = 60;
        int SECONDS_IN_MINUTE = 60;
        int MS_IN_SECONDS = 1000;
        // println("" + diff);
        return (int) (diff / (HOURS_IN_DAY * MINUTES_IN_HOUR
                * SECONDS_IN_MINUTE * MS_IN_SECONDS));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_calendar_test, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
