package com.ovulationcalendar.herdays.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ovulationcalendar.herdays.R;
import com.ovulationcalendar.herdays.calendar.EventItem;
import com.ovulationcalendar.herdays.calendar.MFCalendarView;
import com.ovulationcalendar.herdays.data.EZSharedPreferences;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class CalendarFragment extends Fragment {

    private MFCalendarView calendarView;

    public CalendarFragment() {
        // Required empty public constructor
    }

    String TAG = "CalendarFragment";

    private final int MENS_LENGTH = 4;
    private final int FERTILE_START_RANGE = 18;
    private final int FERTILE_LENGTH = 8;

    private final String EVENT_MENS = "PERIOD";
    private final String EVENT_FERTILE = "FERTILE";
    private final String EVENT_OVULATION = "OVULATION";

    ArrayList<EventItem> events = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_calendar, container, false);

        createWidgets(v);
        test();
        return v;
    }

    private void createWidgets(View v) {
        calendarView = (MFCalendarView) v.findViewById(R.id.mFCalendarView);
    }

    public void test() {

        String lastPeriod = EZSharedPreferences.getLastMonth(getActivity());
        int cycle = Integer.valueOf(EZSharedPreferences.getCycle(getActivity()));

        displayInCalendar2(lastPeriod, cycle, 12);
    }


    private void displayInCalendar(String lastPeriod, int cycle, int count) {
      /*
                    ArrayList<EventItem> events = new ArrayList<>();
//        events.add(new EventItem(fertile, "FERTILE"));
//        events.add(new EventItem(ovulation, "OVULATION"));
//        events.add(new EventItem(period, "PERIOD"));

        calendarView.setEvent(events);
             */

        Calendar calNextPeriod = stringToCalendar(lastPeriod);

        // FOR MENSTRUAL PERIOD
        for (int ctr = 0; ctr < MENS_LENGTH; ctr++) {
            String strMens = forCalendarEvent(calNextPeriod);
            events.add(new EventItem(strMens, EVENT_MENS));
            calNextPeriod.add(Calendar.DAY_OF_MONTH, 1);
        }

        int fertileStart = cycle - FERTILE_START_RANGE - MENS_LENGTH;
        calNextPeriod.add(Calendar.DAY_OF_MONTH, fertileStart);
//        forCalendarEvent(calNextPeriod);
        for (int ctr = 0; ctr < FERTILE_LENGTH; ctr++) {
            String strFertile = forCalendarEvent(calNextPeriod);
            events.add(new EventItem(strFertile, EVENT_FERTILE));
            calNextPeriod.add(Calendar.DAY_OF_MONTH, 1);
        }

        String strOvulated = forCalendarEvent(calNextPeriod);
        events.add(new EventItem(strOvulated, EVENT_OVULATION));
        //reset Calendar
        calNextPeriod = stringToCalendar(lastPeriod);
        forCalendarEvent(calNextPeriod);

        calNextPeriod.add(Calendar.DAY_OF_MONTH, cycle);

        if (count >= 0) {
            count--;
            log("");
            lastPeriod = calendarToString(calNextPeriod);
            displayInCalendar(lastPeriod, cycle, count);
        }

        calendarView.setEvent(events);
    }

    private void displayInCalendar2(String lastPeriod, int cycle, int count) {
        Calendar calNextPeriod = stringToCalendar(lastPeriod);

        // FOR MENSTRUAL PERIOD
        for (int ctr = 0; ctr < MENS_LENGTH; ctr++) {
            String strMens = forCalendarEvent(calNextPeriod);
            events.add(new EventItem(strMens, EVENT_MENS));
            calNextPeriod.add(Calendar.DAY_OF_MONTH, 1);
        }

        calNextPeriod.add(Calendar.DAY_OF_MONTH, 4);
//        forCalendarEvent(calNextPeriod);
        for (int ctr = 0; ctr < 10; ctr++) {
            String strFertile = forCalendarEvent(calNextPeriod);
            if (ctr == 3) {
                events.add(new EventItem(strFertile, EVENT_OVULATION));
            } else {
                events.add(new EventItem(strFertile, EVENT_FERTILE));
            }
            calNextPeriod.add(Calendar.DAY_OF_MONTH, 1);
        }
        //reset Calendar
        calNextPeriod = stringToCalendar(lastPeriod);
//        forCalendarEvent(calNextPeriod);
        Log.d(TAG, "My current Value: " + lastPeriod);
        calNextPeriod.add(Calendar.DAY_OF_MONTH, cycle);
        String lastP = calendarToString(calNextPeriod);
        Log.d(TAG, "Next Period: " + lastP);

//        if(count > 0){
////            count--;
////            displayInCalendar2();
//        }
        if (count >= 0) {
            count--;
            lastPeriod = calendarToString(calNextPeriod);
            displayInCalendar2(lastPeriod, cycle, count);
        }

        calendarView.setEvent(events);


    }

    private Calendar stringToCalendar(String date) {
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

    private String calendarToString(Calendar calendar) {
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE, MMMM d, yyyy", Locale.getDefault());
        return sdf.format(calendar.getTime());

    }

    private String forCalendarEvent(Calendar calendar) {
        String calEvent;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        calEvent = sdf.format(calendar.getTime());
        return calEvent;
    }

    private void log(String message) {
        Log.d("CalendarFragment", message);
    }


    private void setAlarm() {

    }
}
