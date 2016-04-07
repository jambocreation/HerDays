package com.ovulationcalendar.herdays.calendar;

/**
 * Created by mleano on 3/11/2016.
 */
public class EventItem {

    String date;
    String eventType;

    public EventItem(String date, String eventType) {
        this.date = date;
        this.eventType = eventType;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }
}
