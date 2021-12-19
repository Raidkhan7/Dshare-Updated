package com.example.dshare;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class LocationPicker extends FragmentActivity implements OnMapReadyCallback, View.OnClickListener {

    private GoogleMap googleMap;
    static SupportMapFragment supportMapFragment;
    TextView whereto;
    Button NexttbtnLP;
    private List<Place.Field> field;
    final int place_picker_req_code = 100;
    String name , Address ;
    static LatLng latLng1;
    Intent intent;
    Button NextbLP;
    ImageView backLP1;
    static String WhereTo;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_picker);

        supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        supportMapFragment.getMapAsync(this);



        whereto = findViewById(R.id.editTextWhereto);
        Places.initialize(getApplicationContext(),"AIzaSyBJ_srZ7U5BwYCShVMUhVsTq3r10R6QIpg");
        whereto.setOnClickListener(this);



        NextbLP = findViewById(R.id.NexttbtnLP);
        NextbLP.setOnClickListener(this);

        backLP1 = findViewById(R.id.backLP1);
        backLP1.setOnClickListener(this);




    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case place_picker_req_code:
                Place place = Autocomplete.getPlaceFromIntent(data);
                name = place.getName();
                latLng1 = place.getLatLng();
                Address = place.getAddress();

                googleMap.addMarker(new MarkerOptions().position(latLng1).title(name));
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng1));
                CameraUpdate update = CameraUpdateFactory.newLatLngZoom(latLng1,15);
                googleMap.animateCamera(update);
                whereto.setText(name);



                break;

        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap Map) {
        googleMap = Map;
        LatLng sydney = new LatLng(34,151);
        googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));




    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.backLP1:
                startActivity(new Intent(LocationPicker.this,Userscreen1.class));
                break;

            case R.id.NexttbtnLP:

                WhereTo = whereto.getText().toString().trim();

                if(WhereTo.isEmpty()){
                    whereto.setError("End Point is required");
                    whereto.requestFocus();
                    return;
                }
                intent = new Intent(LocationPicker.this,LocationPicker_2.class);
                intent.putExtra("key",0);
                startActivity(intent);
                break;

            case R.id.editTextWhereto:

                field = Arrays.asList(Place.Field.ADDRESS,Place.Field.NAME,Place.Field.LAT_LNG);
                intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY,field).build(LocationPicker.this);
                startActivityForResult(intent,place_picker_req_code);


        }


    }
}