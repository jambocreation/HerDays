package com.ovulationcalendar.herdays.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import com.ovulationcalendar.herdays.R;

public class PregnancyCalculator extends AppCompatActivity {

    private int yesId[] = {
            R.id.rbYes1, R.id.rbYes2, R.id.rbYes3, R.id.rbYes4, R.id.rbYes5, R.id.rbYes6, R.id.rbYes7, R.id.rbYes8, R.id.rbYes9};
    private RadioButton rbYes[];
    private Button btnSubmit;


    int yes, ctr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pregnancy_calculator);

        createWidgets();
        createListener();

    }

    private void createWidgets() {
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        rbYes = new RadioButton[9];

        for (ctr = 0; ctr < 9; ctr++) {
            rbYes[ctr] = (RadioButton) findViewById(yesId[ctr]);
        }
    }

    private void createListener() {
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitClickListener();
            }
        });
    }

    private void submitClickListener() {

        for (ctr = 0; ctr < 9; ctr++) {
            if (rbYes[ctr].isChecked()) {
                yes++;
            }
        }

        computePregnancy(yes);
    }

    private void computePregnancy(int yes) {

        double percentage = (double) yes / 9;
        if (percentage > 0.8) {
            showResult("Congratulations You are " + (Math.round((percentage * 100)) + "% pregnant"));
        } else {
            showResult("You are not pregnant.");
        }
    }

    private void showResult(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(PregnancyCalculator.this);
        builder.setMessage(message);
        builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                PregnancyCalculator.this.finish();
            }
        });
        builder.show();
    }


}
