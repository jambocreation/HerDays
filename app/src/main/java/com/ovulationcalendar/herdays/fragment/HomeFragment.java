package com.ovulationcalendar.herdays.fragment;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.ovulationcalendar.herdays.R;
import com.ovulationcalendar.herdays.activity.NotesDiaryActivity;
import com.ovulationcalendar.herdays.data.EZSharedPreferences;
import com.ovulationcalendar.herdays.notification.NotificationReciever;
import com.ovulationcalendar.herdays.utils.HerDaysUtils;
import com.ovulationcalendar.herdays.utils.LogUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private Context spCtx;
    private final String TAG = HomeFragment.class.getSimpleName();

    private TextView tvDaysUntilNextPeriod, tvNextPeriodDate;
    private TextView tvDaysBeforeOvulation, tvOvulationDate;
    private TextView tvSafeDaysRange, tvSafeDaysRange2;

    private Button btnPregCalc;
    private Button btnDueDateCalc;
    private Button btnNotesDiary;

    public HomeFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        createWidgets(v);
        createListeners();
        initData();

        showTip();

        return v;
    }


    private void createWidgets(View v) {
        tvDaysUntilNextPeriod = (TextView) v.findViewById(R.id.tvDaysUntilNextPeriod);
        tvNextPeriodDate = (TextView) v.findViewById(R.id.tvNextPeriodDate);

        tvDaysBeforeOvulation = (TextView) v.findViewById(R.id.tvDaysBeforeOvulation);
        tvOvulationDate = (TextView) v.findViewById(R.id.tvOvulationDate);

        tvSafeDaysRange = (TextView) v.findViewById(R.id.tvSafeRange);
        tvSafeDaysRange2 = (TextView) v.findViewById(R.id.tvSafeRange2);


        btnPregCalc = (Button) v.findViewById(R.id.btnPregCalc);
//        btnPregCalc.setVisibility(View.GONE);


        btnDueDateCalc = (Button) v.findViewById(R.id.btnDueDateCalc);
        btnNotesDiary = (Button) v.findViewById(R.id.btnNotesDiary);
    }

    private void createListeners() {
        btnPregCalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(getActivity(), PregnancyCalculator.class));
                onPregClick();
            }
        });

        btnDueDateCalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(getActivity(), DueDateActivity.class));
                lmpClickListener();
            }
        });

        btnNotesDiary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), NotesDiaryActivity.class));
            }
        });
    }


    private void initData() {
        spCtx = getActivity().getApplicationContext();

        String lastPeriod = EZSharedPreferences.getLastMonth(spCtx);
        int cycleLength = Integer.valueOf(EZSharedPreferences.getCycle(spCtx));

        LogUtils.d(TAG, "Last Period: " + lastPeriod);

        computeNextPeriod(lastPeriod, cycleLength);
    }

    private void computeNextPeriod(String lastPeriod, int cycle) {

        Calendar calNextPeriod = stringToCalendar(lastPeriod);
        calNextPeriod.add(Calendar.DAY_OF_MONTH, cycle);

        println(" 1 Next Period: " + calNextPeriod.getTime());

//        Calendar x = calNextPeriod;
        setAlarm(lastPeriod, cycle);


        Calendar calCurrentDate = Calendar.getInstance();
        calCurrentDate.set(Calendar.HOUR_OF_DAY, 0);
        calCurrentDate.set(Calendar.MINUTE, 0);
        calCurrentDate.set(Calendar.SECOND, 0);
        calCurrentDate.set(Calendar.MILLISECOND, 0);

        long lNextPeriod = calNextPeriod.getTimeInMillis();
        long lCurrentDate = calCurrentDate.getTimeInMillis();

        long diff = lNextPeriod - lCurrentDate;
        int days = differenceInDays(diff);

        println("Days difference: " + days);

        if (days < cycle && days >= 0) {
            // TODO : CHANGE 4 to constant variable;
//            if (days >= cycle - 4) {
//                int mensDay = cycle - days;
//                println("Day: " + mensDay);
//            }
            tvDaysUntilNextPeriod.setText(days + " days until next period");
            tvNextPeriodDate.setText(calendarFormat(calNextPeriod));

            computeOvulationPeriod(lastPeriod, cycle, lCurrentDate);
        }
//        else {
//            computeNextPeriod(calendarToString(calNextPeriod), cycle);
//        }
    }

    private void computeOvulationPeriod(String lastPeriod, int cycle, long lCurrentDate) {

        String safePeriod1Start, safePeriod1End;
        String safePeriod2Start, safePeriod2End;

        safePeriod1Start = lastPeriod;
        Calendar calendar = stringToCalendar(lastPeriod);

        int start = cycle - 18;
        int range = 8;

        calendar.add(Calendar.DAY_OF_MONTH, start - 1);
        safePeriod1End = calendarFormat(calendar);

        tvSafeDaysRange.setText(safePeriod1Start + " to " + safePeriod1End);

        calendar.add(Calendar.DAY_OF_MONTH, 1);
        String ovulationStart = calendarFormat(calendar);
        long lOvulationDate = calendar.getTimeInMillis();
        long diff = lOvulationDate - lCurrentDate;
        int days = differenceInDays(diff);

        calendar.add(Calendar.DAY_OF_MONTH, range);
        String ovulationEnd = calendarFormat(calendar);

        calendar.add(Calendar.DAY_OF_MONTH, 1);
        safePeriod2Start = calendarFormat(calendar);

        calendar = stringToCalendar(lastPeriod);
        calendar.add(Calendar.DAY_OF_MONTH, cycle - 1);
        safePeriod2End = calendarFormat(calendar);

        tvSafeDaysRange2.setText(safePeriod2Start + " to " + safePeriod2End);

        tvDaysBeforeOvulation.setText(days + " days before ovulation");
        tvOvulationDate.setText(ovulationStart + " to " + ovulationEnd);
        println("next ovulation: " + days);
        if (days <= 0 && days >= -8) {
            tvDaysBeforeOvulation.setText("You are Fertile today");
        } else if (days < -8) {
            tvDaysBeforeOvulation.setText((days + cycle + range) + " days before next ovulation");
        }

    }

    public Calendar stringToCalendar(String date) {
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
        String result = "";

        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
        result = sdf.format(calendar.getTime());
        println("result: " + result);
        return result;
    }

    public String calendarFormat(Calendar calendar) {
        String format;

        SimpleDateFormat sdf = new SimpleDateFormat("EEEE, MMM dd, yyyy");
        format = sdf.format(calendar.getTime());
        println("Next period: " + format);
        return format;
    }

    private void onPregClick() {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        final int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

//        Calendar minDate = Calendar.getInstance();
//        minDate.add(Calendar.DAY_OF_YEAR, -28);

        DatePickerDialog dpd = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                Date date = new Date(year - HerDaysUtils.YEAR_DEFAULT, month, day);
                SimpleDateFormat format = new SimpleDateFormat("EEEE, MMMM d, yyyy", Locale.getDefault());
                String formattedDate = format.format(date);
                computePregTest(formattedDate);
            }
        }, year, month, day);

//        dpd.getDatePicker().setMinDate(minDate.getTime().getTime());
        dpd.setTitle("First day of your last month period");
        dpd.show();


    }

    private void computePregTest(String formattedDate) {

        int cycle = Integer.valueOf(EZSharedPreferences.getCycle(spCtx));
        SimpleDateFormat format = new SimpleDateFormat("EEEE, MMMM d, yyyy", Locale.getDefault());
        Calendar calendar = Calendar.getInstance();

        try {
            calendar.setTime(format.parse(formattedDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        calendar.add(Calendar.DAY_OF_MONTH, cycle - 11);

        Calendar calCurrentDate = Calendar.getInstance();
        calCurrentDate.set(Calendar.HOUR_OF_DAY, 0);
        calCurrentDate.set(Calendar.MINUTE, 0);
        calCurrentDate.set(Calendar.SECOND, 0);
        calCurrentDate.set(Calendar.MILLISECOND, 0);

        long lNextPeriod = calendar.getTimeInMillis();
        long lCurrentDate = calCurrentDate.getTimeInMillis();

        long diff = lNextPeriod - lCurrentDate;
        int days = differenceInDays(diff);

        AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
        if (days < 1) {
            builder.setMessage("You can now use pregnancy test kit!");
        } else {
            builder.setMessage("You can use pregnancy test kit after " + days + " days!");
        }
        builder.setNegativeButton("Okay", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();


    }


    private void lmpClickListener() {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        final int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

//        Calendar minDate = Calendar.getInstance();
//        minDate.add(Calendar.DAY_OF_YEAR, -28);

        DatePickerDialog dpd = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                Date date = new Date(year - HerDaysUtils.YEAR_DEFAULT, month, day);
                SimpleDateFormat format = new SimpleDateFormat("EEEE, MMMM d, yyyy", Locale.getDefault());
                String formattedDate = format.format(date);
                computeDueDate(formattedDate);
            }
        }, year, month, day);

//        dpd.getDatePicker().setMinDate(minDate.getTime().getTime());
        dpd.setTitle("Your last month period");
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
//        tvDueDate.setText(dueDate);
        showDueDate(dueDate);
    }

    public void showDueDate(String date) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Your expected due date is:\n" + date);
        builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();

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

    public void println(String message) {
        LogUtils.d(TAG, message);
    }

    private void setAlarm(String lastPeriod, int cycle) {
        Calendar cal = stringToCalendar(lastPeriod);
        cal.add(Calendar.DAY_OF_MONTH, cycle - 1);
        cal.set(Calendar.HOUR_OF_DAY, 4);
        cal.set(Calendar.MINUTE, 0);

        SimpleDateFormat sdf = new SimpleDateFormat("EEEE, MMMM d, yyyy", Locale.getDefault());
        String x = sdf.format(cal.getTime());
        Log.d(TAG, "Time: " + x);

        Intent intent = new Intent(getActivity(), NotificationReciever.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                getActivity().getBaseContext(), 1, intent, 0);

        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),
                pendingIntent);
    }

    private void showTip() {

        boolean isShown = EZSharedPreferences.isTipShown(getActivity());
//        EZSharedPreferences.setTipShown(getActivity(), false);
        Log.d(TAG, "" + isShown);
        if (!isShown) {
            EZSharedPreferences.setTipShown(getActivity(), true);
            Random rand = new Random();
            int i = rand.nextInt(16);

            String tips = tips(i);

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Tips of the Day!");
            builder.setMessage(tips);
            builder.setNegativeButton("Okay", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    EZSharedPreferences.setTipShown(getActivity(), false);
                    dialog.dismiss();

                }
            });
            builder.setCancelable(false).show();
        }
    }

    private String tips(int index) {
        String TIPS[] = {
                "it\\’s important to get enough sleep. While you’re asleep you produce an important hormone called leptin, which regulates your cycle and promotes fertility",
                "Acupuncture is one of the most popular alternative therapies for helping boost fertility.",
                "Yoga also fights fatigue and reduces stress and anxiety – removing these potential barriers to fertility.",
                "if you reduce your salt intake a few days before your periods it will help your kidneys flush out excess water.",
                "Avoid tight clothes when you have menstruation, especially at the waist. They only hurt the stomach and further compressing it causes discomfort.",
                "Eat a lot of red meat because it has the effect of increase body heat and therefore stimulating menstruation.",
                "Stress has the effect of causing hormonal imbalance and therefore delayed and irregular cycles.",
                "Some people do find that they get more spots around the time their period is due, but everyone is more likely to get pimples during the teenage years because skin gets oilier.",
                "If you\\'ve skipped a period, try to relax. Restoring your life to emotional and physical balance can help.",
                "You may see a clear or whitish fluid in your underwear. This is vaginal discharge and is a sign that your period is on its way.",
                "Poor nutrition seems to physically change the proteins in the brain so they can no longer send the proper signals for normal ovulation.",
                "As the fertile time varies, you can't accurately pinpoint the best time, so just have sex three times a week throughout the month.",
                "Regular, moderate exercise helps maintain a healthy body mass index (BMI) or weight, which in turn supports hormonal balance and healthy insulin levels both important for fertility and ovulation.",
                "In order to have the best chance of getting pregnant, it is very important that a woman knows when she is ovulating.",
                "Skip tobacco and recreational drugs. These can cause poor sperm function.",
                "Get enough of certain key nutrients – like zinc, folic acid, calcium, and vitamins C and D – that help create strong and plentiful sperm."};

        return TIPS[index];
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
