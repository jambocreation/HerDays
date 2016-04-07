package com.ovulationcalendar.herdays.data;

/**
 * Created by mleano on 3/15/2016.
 */
public class NotesItem {
    String date;
    String title;
    String message;

    public NotesItem(String date, String title, String message) {
        this.date = date;
        this.title = title;
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
