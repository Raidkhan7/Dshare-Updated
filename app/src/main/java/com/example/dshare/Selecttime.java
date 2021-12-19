package com.example.dshare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.type.Date;

import java.util.HashMap;
import static com.example.dshare.Selectdate.Date;
import static com.example.dshare.LocationPicker.WhereTo;
import static com.example.dshare.LocationPicker_2.From;
import static com.example.dshare.Vehcile_details.V1;
import static com.example.dshare.Vehcile_details.V2;



public class Selecttime extends AppCompatActivity implements View.OnClickListener{

    TextView time;
    TimePicker simpleTimePicker;
    Button NextbST;
    ImageView backbT;
    ProgressBar progressBar;
    static String Time;
    private Dialog dialog;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Schedulerequest");


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selecttime);

        //  initiate the view's
        time = (TextView) findViewById(R.id.stime);
        simpleTimePicker = (TimePicker) findViewById(R.id.simpleTimePicker);
        simpleTimePicker.setIs24HourView(false); // used to display AM/PM mode
        // perform set on time changed listener event
        simpleTimePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                // display a toast with changed values of time picker
                Toast.makeText(getApplicationContext(), hourOfDay + "  " + minute, Toast.LENGTH_SHORT).show();
                time.setText( hourOfDay + " : " + minute); // set the current time in text view
            }
        });
        NextbST = findViewById(R.id.NexttbtnSt);
        NextbST.setOnClickListener(this);

        backbT = findViewById(R.id.STbackbtn);
        backbT.setOnClickListener(this);

        dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialogbox);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.background_dialog));
        }
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false); //Optional
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation; //Setting the animations to dialog

        Button Okay = dialog.findViewById(R.id.btn_okay);
        Okay.setOnClickListener(this);





    }
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.NexttbtnSt:
                Validations();
                break;

            case R.id.STbackbtn:
                startActivity(new Intent(Selecttime.this,Selectdate.class));
                break;

            case R.id.btn_okay:
                Toast.makeText(Selecttime.this, "Okay", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                startActivity(new Intent(Selecttime.this,Schedulerides.class));
                break;


        }


    }
    private void Validations(){

        Time = time.getText().toString().trim();

        if(Time.isEmpty()){
            time.setError("Time is required");
            time.requestFocus();
            return;
        }

        HashMap<String , String > request = new HashMap<>();

        request.put("Where to",WhereTo);
        request.put("From",From);
        request.put("Vehcile Type",V1);
        request.put("Available seats",V2);
        request.put("Time",Time);
        request.put("Date",Date);


        myRef.setValue(request);
        dialog.show(); // Showing the dialog here







    }

}