package com.ovulationcalendar.herdays.activity;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import com.ovulationcalendar.herdays.R;
import com.ovulationcalendar.herdays.adapter.NotesAdapter;
import com.ovulationcalendar.herdays.data.DatabaseHelper;
import com.ovulationcalendar.herdays.data.NotesItem;
import com.ovulationcalendar.herdays.utils.HerDaysUtils;
import com.ovulationcalendar.herdays.utils.LogUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class NotesDiaryActivity extends AppCompatActivity {

    FloatingActionButton fabNotes;
    EditText etDate, etTitle, etMessage;

    RecyclerView rvNotes;
    NotesAdapter adapter;
    ArrayList<NotesItem> item;

    DatabaseHelper dbHelper;
    SQLiteDatabase db;
    Cursor cursor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_diary);

        createWidgets();
        createListener();
        initData();
    }

    public void createWidgets() {
        fabNotes = (FloatingActionButton) findViewById(R.id.fabNotes);
        rvNotes = (RecyclerView) findViewById(R.id.rvNotes);

    }

    public void createListener() {
        fabNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFabClickListener();
            }
        });

    }

    public void initData() {
        item = new ArrayList<>();
        adapter = new NotesAdapter(item, getApplicationContext());

        rvNotes.setAdapter(adapter);
        rvNotes.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rvNotes.setHasFixedSize(true);
        populateNotes();
    }

    public void populateNotes() {
        dbHelper = DatabaseHelper.getInstance(getApplicationContext());
        db = dbHelper.getReadableDatabase();

        cursor = db.query("tbl_notes", null, null, null, null, null, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                item.add(0, new NotesItem(cursor.getString(1), cursor.getString(2), cursor.getString(3)));
            }
        }

        adapter.notifyDataSetChanged();
    }

    private void onFabClickListener() {
        AlertDialog.Builder builder = new AlertDialog.Builder(NotesDiaryActivity.this);
        builder.setTitle("Add Notes");

        View v = getLayoutInflater().inflate(R.layout.dialog_addnotes, null);

        etDate = (EditText) v.findViewById(R.id.etDate);
        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDateClickListener();
            }
        });
        etTitle = (EditText) v.findViewById(R.id.etTitle);
        etMessage = (EditText) v.findViewById(R.id.etMessage);
        builder.setView(v);

        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String date = etDate.getText().toString();
                String title = etTitle.getText().toString();
                String message = etMessage.getText().toString();
                LogUtils.d("TAG", date + " : " + title + " : " + message);
                addNotes(date, title, message);
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.show();

    }

    private void addNotes(String date, String title, String message) {
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("date", date);
        values.put("title", title);
        values.put("message", message);

        db.insert("tbl_notes", null, values);

        item.add(0, new NotesItem(date, title, message));
        adapter.notifyItemInserted(0);
    }

    private void onDateClickListener() {

        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        final int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        Calendar minDate = Calendar.getInstance();
        minDate.add(Calendar.DAY_OF_YEAR, -28);

        DatePickerDialog dpd = new DatePickerDialog(NotesDiaryActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                Date date = new Date(year - HerDaysUtils.YEAR_DEFAULT, month, day);
                SimpleDateFormat format = new SimpleDateFormat("EEEE, MMMM d, yyyy", Locale.getDefault());
                String formattedDate = format.format(date);

                etDate.setText(formattedDate);
            }
        }, year, month, day);

        dpd.getDatePicker().setMaxDate(new Date().getTime());
        dpd.getDatePicker().setMinDate(minDate.getTime().getTime());
        dpd.show();

    }

}
