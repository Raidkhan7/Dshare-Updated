package com.example.dshare;


import static com.example.dshare.LocationPicker.supportMapFragment;
import static com.example.dshare.Selecttime.Time;
import static com.example.dshare.Selectdate.Date;


import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import static com.example.dshare.LocationPicker.WhereTo;
import static com.example.dshare.LocationPicker_2.From;
import static com.example.dshare.Vehcile_details.V1;
import static com.example.dshare.Vehcile_details.V2;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Schedulerides extends AppCompatActivity  implements View.OnClickListener {

    TextView Schedule;
    Button SD;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedulerides);
        Schedule = findViewById(R.id.SR);
        Schedule.setText(WhereTo + " to " + From);

        SD = findViewById(R.id.SD);
        SD.setOnClickListener(this);




    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.SD:
                Intent intent = new Intent(Schedulerides.this,Ride_details.class);
                intent.putExtra("key",0);
                startActivity(intent);
                break;
        }
    }
}