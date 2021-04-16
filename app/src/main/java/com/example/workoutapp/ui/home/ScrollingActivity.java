package com.example.workoutapp.ui.home;

import android.annotation.SuppressLint;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import com.example.workoutapp.Activitat;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.workoutapp.R;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class ScrollingActivity extends AppCompatActivity implements OnMapReadyCallback {

    int pos;
    ImageView photo;
    TextView activity,organization, time, place,price, member_price,description;
    List<Activitat> activity_list = new ArrayList<>();
    private GoogleMap mMap;
    //public static final String API_KEY = "AIzaSyDvpqaDWNAMYWb6ePt-PFrLkl1F5MKorS0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        pos = getIntent().getIntExtra("Position recycler",0);
        // = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        Toolbar toolbar =(Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
       // toolbar = getSupportActionBar();
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.submap);
        mapFragment.getMapAsync(this);
        //mMapView = findViewById(R.id.mapView);
        //initGoogleMap(savedInstanceState);

        //FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        init_values();
        activity_list = ActivityListAdapter.getInstance(this, new ArrayList<>()).copyInfo();
        try {
            set_values();
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }

    /*private void initGoogleMap(Bundle savedInstanceState){
        // *** IMPORTANT ***
        // MapView requires that the Bundle you pass contain _ONLY_ MapView SDK
        // objects or sub-Bundles.
        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(API_KEY);
        }

        mMapView.onCreate(mapViewBundle);

        mMapView.getMapAsync(this);
    }*/

    void init_values(){

        activity = findViewById(R.id.activitat_text);
        organization = findViewById(R.id.organization_text);
        time = findViewById(R.id.timehour_text);
        place = findViewById(R.id.place_text);
        price = findViewById(R.id.price_text);
        member_price = findViewById(R.id.price_text2);
        description = findViewById(R.id.description_text);
        photo = findViewById(R.id.imageView);

    }
    void set_values() throws IOException {
        Log.d("STATE", activity_list.get(pos).getName());
        activity.setText(activity_list.get(pos).getName());
        //organization.setText((activity_list.get(pos).getOrganizerName()));
        time.setText(activity_list.get(pos).getDate_time());


        String[] locations = activity_list.get(pos).getLocation().split(", ");
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List <Address> addresses = geocoder.getFromLocation(Double.parseDouble(locations[0]),Double.parseDouble(locations[1]), 1);
        Log.d("0000000000000000000000", String.valueOf(addresses.get(0)));

        Log.d("0000000000000000000000", String.valueOf(addresses.get(0).getAddressLine(0)));
        place.setText(String.valueOf(addresses.get(0).getAddressLine(0)));


        price.setText(activity_list.get(pos).getPreu());
        member_price.setText(activity_list.get(pos).getPreuSoci());
        description.setText(activity_list.get(pos).getDescription());
        Picasso.get().load(activity_list.get(pos).getPhoto_url()).into(photo);


    }


    /*@Override
    public void onMapReady(GoogleMap map) {
        map.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
    }*/
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        String[] locations = activity_list.get(pos).getLocation().split(", ");
        Log.d("STATE", locations[0]);
        Log.d("hola", locations[1]);

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(Double.parseDouble(locations[0]), Double.parseDouble(locations[1]));
        //LatLng sydney = new LatLng(41.391461899999996, 2.1352135);

        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

}