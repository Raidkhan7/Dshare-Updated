package com.example.dshare;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CustomCap;
import com.google.android.gms.maps.model.Dash;
import com.google.android.gms.maps.model.Dot;
import com.google.android.gms.maps.model.Gap;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.JointType;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PatternItem;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.RoundCap;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import static com.example.dshare.LocationPicker.latLng1;
import static java.lang.Math.cos;


public class LocationPicker_2 extends FragmentActivity implements OnMapReadyCallback, View.OnClickListener {

    private GoogleMap googleMap;
    SupportMapFragment supportMapFragment;
    TextView from;
    Button NexttbtnLP2;
    private List<Place.Field> field;
    final int place_picker_req_code = 100;
    String name;
    static LatLng latLng, PinLatlng;
    Button NextbLP2;
    ImageView backLP2, PinLP2;
    FusedLocationProviderClient fusedLocationProviderClient, client;
    static int Check;
    public double startLat, startLang;
    double new_longitude;
    LatLng origin;
    LatLng destination;


    static String From;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_picker2);

        supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        supportMapFragment.getMapAsync(this);

        from = findViewById(R.id.editTextFrom);
        Places.initialize(getApplicationContext(), "AIzaSyBJ_srZ7U5BwYCShVMUhVsTq3r10R6QIpg");
        from.setOnClickListener(this);
        NextbLP2 = findViewById(R.id.NexttbtnLP2);
        NextbLP2.setOnClickListener(this);
        backLP2 = findViewById(R.id.backLP2);
        backLP2.setOnClickListener(this);

        PinLP2 = findViewById(R.id.PinLP2);
        PinLP2.setOnClickListener(this);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case place_picker_req_code:
                Place place = Autocomplete.getPlaceFromIntent(data);
                name = place.getName();
                latLng = place.getLatLng();
                startLang = latLng.longitude;
                startLat = latLng.latitude;
                googleMap.addMarker(new MarkerOptions().position(latLng).title(name));
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                CameraUpdate update = CameraUpdateFactory.newLatLngZoom(latLng, 15);
                googleMap.animateCamera(update);
                from.setText(name);
                break;

        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap Map) {
        googleMap = Map;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backLP2:
                startActivity(new Intent(LocationPicker_2.this, LocationPicker.class));
                break;

            case R.id.NexttbtnLP2:
                From = from.getText().toString().trim();
                if (From.isEmpty()) {
                    from.setError("Pickup is required");
                    from.requestFocus();
                    return;
                }
                startActivity(new Intent(LocationPicker_2.this, Vehcile_details.class));
                break;

            case R.id.editTextFrom:
                Check = 0;
                field = Arrays.asList(Place.Field.ADDRESS, Place.Field.NAME, Place.Field.LAT_LNG);
                intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, field).build(LocationPicker_2.this);
                startActivityForResult(intent, place_picker_req_code);

                break;

            case R.id.PinLP2:
                Check = 1;
                client = LocationServices.getFusedLocationProviderClient(this);
                if (ActivityCompat.checkSelfPermission(LocationPicker_2.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    getLocation();
                } else {
                    ActivityCompat.requestPermissions(LocationPicker_2.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
                }


        }

    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Task<Location> task = client.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                supportMapFragment.getMapAsync(new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(@NonNull GoogleMap googleMap) {


                        PinLatlng = new LatLng(location.getLatitude(), location.getLongitude());
                        startLat = location.getLatitude();
                        startLang = location.getLongitude();
                        googleMap.addMarker(new MarkerOptions().position(PinLatlng));
                        googleMap.moveCamera(CameraUpdateFactory.newLatLng(PinLatlng));
                        CameraUpdate update1 = CameraUpdateFactory.newLatLngZoom(PinLatlng, 15);
                        googleMap.animateCamera(update1);


                    }
                });

            }
        });


        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Location location = task.getResult();

                try {
                    Geocoder geocoder = new Geocoder(LocationPicker_2.this, Locale.getDefault());
                    List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                    from.setText(Html.fromHtml(addresses.get(0).getAddressLine(0)));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                radius();

            }


        });


    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    public void radius() {
        // circle settings
        int radiusM = 500;// your radius in meters
        double latitude = startLat;
        double longitude = startLang;
        LatLng latLng = new LatLng(latitude, longitude);

        // draw circle
        int d = 500; // diameter
        Bitmap bm = Bitmap.createBitmap(d, d, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bm);
        Paint p = new Paint();
        p.setColor(getResources().getColor(R.color.grey));
        c.drawCircle(d / 2, d / 2, d / 2, p);

        // generate BitmapDescriptor from circle Bitmap
        BitmapDescriptor bmD = BitmapDescriptorFactory.fromBitmap(bm);

// mapView is the GoogleMap
        googleMap.addGroundOverlay(new GroundOverlayOptions().
                image(bmD).
                position(latLng, radiusM * 2, radiusM * 2).
                transparency(0.4f));

    }
}

