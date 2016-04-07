package com.ovulationcalendar.herdays.activity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.ovulationcalendar.herdays.R;
import com.ovulationcalendar.herdays.utils.HerDaysUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DueDateActivity extends AppCompatActivity {


    TextView tvLMP;
    TextView tvDueDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_due_date);

        initViews();
    }

    private void initViews() {

        tvLMP = (TextView) findViewById(R.id.tvLMP);
        tvLMP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lmpClickListener();
            }
        });

        tvDueDate = (TextView) findViewById(R.id.tvDueDate);
    }

    private void lmpClickListener() {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        final int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        Calendar minDate = Calendar.getInstance();
        minDate.add(Calendar.DAY_OF_YEAR, -28);

        DatePickerDialog dpd = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                Date date = new Date(year - HerDaysUtils.YEAR_DEFAULT, month, day);
                SimpleDateFormat format = new SimpleDateFormat("EEEE, MMMM d, yyyy", Locale.getDefault());
                String formattedDate = format.format(date);
                tvLMP.setText(formattedDate);
                computeDueDate(formattedDate);
            }
        }, year, month, day);

        dpd.getDatePicker().setMinDate(minDate.getTime().getTime());
        dpd.show();
    }

    private void computeDueDate(String formattedDate) {

        SimpleDateFormat format = new SimpleDateFormat("EEEE, MMMM d, yyyy", Locale.getDefault());
        Calendar calendar = Calendar.getInstance();

        try {
            calendar.setTime(format.parse(formattedDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        calendar.add(Calendar.MONTH, -3);
        calendar.add(Calendar.YEAR, 1);
        calendar.add(Calendar.DAY_OF_MONTH, 7);

        String dueDate = format.format(calendar.getTime());
        tvDueDate.setText(dueDate);
    }
}
