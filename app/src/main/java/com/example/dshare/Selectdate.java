package com.example.dshare;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;

public class Selectdate extends AppCompatActivity implements View.OnClickListener {

    TextView date;
    DatePickerDialog datePickerDialog;
    Button NextbS ;
    ImageView SDback;
    static String Date;


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectdate);
        // initiate the date picker and a button
        date = (TextView) findViewById(R.id.sdate);
        // perform click event on edit text
        date.setOnClickListener(this);
        NextbS = findViewById(R.id.NexttbtnS);
        NextbS.setOnClickListener(this);

        SDback= findViewById(R.id.SDbackbtn);
        NextbS.setOnClickListener(this);


    }
    @Override
        public void onClick(View view) {
            switch(view.getId()){
                case R.id.NexttbtnS:
                    Validations();
                    break;

                case R.id.SDbackbtn:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            new Home()).commit();
                    break;

                case R.id.sdate:
                    final Calendar c = Calendar.getInstance();
                    int mYear = c.get(Calendar.YEAR); // current year
                    int mMonth = c.get(Calendar.MONTH); // current month
                    int mDay = c.get(Calendar.DAY_OF_MONTH); // current day

                    // date picker dialog
                    datePickerDialog = new DatePickerDialog(Selectdate.this,
                            new DatePickerDialog.OnDateSetListener() {

                                @Override
                                public void onDateSet(DatePicker view, int year,
                                                      int monthOfYear, int dayOfMonth) {
                                    // set day of month , month and year value in the edit text
                                    date.setText(dayOfMonth + "/"
                                            + (monthOfYear + 1) + "/" + year);

                                }
                            }, mYear, mMonth, mDay);
                    datePickerDialog.show();


            }
        }


    private void Validations(){

        Date = date.getText().toString().trim();

        if(Date.isEmpty()){
            date.setError("Date is required");
            date.requestFocus();
            return;
        }

        else{
            startActivity(new Intent(Selectdate.this,Selecttime.class));
        }


    }
}