package com.example.dshare;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dshare.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CustomCap;
import com.google.android.gms.maps.model.Dash;
import com.google.android.gms.maps.model.Dot;
import com.google.android.gms.maps.model.Gap;
import com.google.android.gms.maps.model.JointType;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PatternItem;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.RoundCap;
import java.util.Arrays;
import java.util.List;
import static com.example.dshare.LocationPicker.latLng1;
import static com.example.dshare.LocationPicker_2.latLng;
import static com.example.dshare.LocationPicker_2.PinLatlng;
import static com.example.dshare.LocationPicker_2.Check;



/**
 * An activity that displays a Google map with polylines to represent paths or routes,
 * and polygons to represent areas.
 */
public class Ride_details extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnPolylineClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Retrieve the content view that renders the map.
        setContentView(R.layout.activity_ride_details);

        // Get the SupportMapFragment and request notification when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map when it's available.
     * The API invokes this callback when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera.
     * In this tutorial, we add polylines and polygons to represent routes and areas on the map.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {

        if(Check==0){

            // Add polylines to the map.
            // Polylines are useful to show a route or some other connection between points.
            Polyline polyline1 = googleMap.addPolyline(new PolylineOptions()
                    .clickable(true)
                    .add(latLng,latLng1));
            // Store a data object with the polyline, used here to indicate an arbitrary type.
            polyline1.setTag("A");
            // Style the polyline.
            stylePolyline(polyline1);



            // Position the map's camera near Alice Springs in the center of Australia,
            // and set the zoom factor so most of Australia shows on the screen.

            googleMap.addMarker(new MarkerOptions().position(latLng));
            googleMap.addMarker(new MarkerOptions().position(latLng1));
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(latLng, 15);
            googleMap.animateCamera(update);

            // Set listeners for click events.
            googleMap.setOnPolylineClickListener(this);

        }
        else{
            // Add polylines to the map.
            // Polylines are useful to show a route or some other connection between points.
            Polyline polyline1 = googleMap.addPolyline(new PolylineOptions()
                    .clickable(true)
                    .add(PinLatlng,latLng1));
            // Store a data object with the polyline, used here to indicate an arbitrary type.
            polyline1.setTag("A");
            // Style the polyline.
            stylePolyline(polyline1);



            // Position the map's camera near Alice Springs in the center of Australia,
            // and set the zoom factor so most of Australia shows on the screen.

            googleMap.addMarker(new MarkerOptions().position(PinLatlng));
            googleMap.addMarker(new MarkerOptions().position(latLng1));
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(PinLatlng));
            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(PinLatlng, 15);
            googleMap.animateCamera(update);

            // Set listeners for click events.
            googleMap.setOnPolylineClickListener(this);

        }



    }

    private static final int COLOR_BLACK_ARGB = 0xff000000;
    private static final int POLYLINE_STROKE_WIDTH_PX = 12;


    private void stylePolyline(Polyline polyline) {
        String type = "";
        // Get the data object stored with the polyline.
        if (polyline.getTag() != null) {
            type = polyline.getTag().toString();
        }

        switch (type) {
            // If no type is given, allow the API to use the default.

            case "B":
                // Use a round cap at the start of the line.
                polyline.setStartCap(new RoundCap());
                break;
        }

        polyline.setEndCap(new RoundCap());
        polyline.setWidth(POLYLINE_STROKE_WIDTH_PX);
        polyline.setColor(COLOR_BLACK_ARGB);
        polyline.setJointType(JointType.ROUND);
    }

    private static final int PATTERN_GAP_LENGTH_PX = 20;
    private static final PatternItem DOT = new Dot();
    private static final PatternItem GAP = new Gap(PATTERN_GAP_LENGTH_PX);

    // Create a stroke pattern of a gap followed by a dot.
    private static final List<PatternItem> PATTERN_POLYLINE_DOTTED = Arrays.asList(GAP, DOT);


    @Override
    public void onPolylineClick(Polyline polyline) {
        // Flip from solid stroke to dotted stroke pattern.
        if ((polyline.getPattern() == null) || (!polyline.getPattern().contains(DOT))) {
            polyline.setPattern(PATTERN_POLYLINE_DOTTED);
        } else {
            // The default pattern is a solid stroke.
            polyline.setPattern(null);
        }

        Toast.makeText(this, "Route type " + polyline.getTag().toString(),
                Toast.LENGTH_SHORT).show();
    }




}
