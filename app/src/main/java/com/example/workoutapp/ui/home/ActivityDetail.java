package com.example.workoutapp.ui.home;

import android.annotation.SuppressLint;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import com.example.workoutapp.Activitat;
import com.example.workoutapp.ActivityController;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.BaseTransientBottomBar;
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
import android.widget.Toast;

import com.example.workoutapp.R;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class ActivityDetail extends AppCompatActivity implements OnMapReadyCallback {

    int pos;
    ImageView photo;
    TextView activity,organization, time, place,price, member_price,description;
    ExtendedFloatingActionButton button;
    List<Activitat> activity_list = new ArrayList<>();
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        pos = getIntent().getIntExtra("Position recycler",0);

        Toolbar toolbar =(Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.submap);
        mapFragment.getMapAsync(this);



        init_values();
        activity_list = ActivityListAdapter.getInstance(this, new ArrayList<>()).copyInfo();
        try {
            set_values();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }



    void init_values(){

        activity = findViewById(R.id.activitat_text);
        organization = findViewById(R.id.organization_text);
        time = findViewById(R.id.timehour_text);
        place = findViewById(R.id.place_text);
        price = findViewById(R.id.price_text);
        member_price = findViewById(R.id.price_text2);
        description = findViewById(R.id.description_text);
        photo = findViewById(R.id.imageView);

        button = findViewById(R.id.meapunto);

        /*if (activity_client.get(pos).getApuntat()) {
            button = findViewById(R.id.meapunto);
        }
        else {
            button = findViewById(R.id.medesapunto);
        }*/
    }
    @SuppressLint("SetTextI18n")
    void set_values() throws IOException {
        activity.setText(activity_list.get(pos).getName());
        organization.setText((activity_list.get(pos).getOrganizerName()));
        time.setText(activity_list.get(pos).getDate_time());
        Integer activity_ID = activity_list.get(pos).getId();
        ActivityController activityController = new ActivityController(this);

        String[] locations = activity_list.get(pos).getLocation().split(", ");
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses = geocoder.getFromLocation(Double.parseDouble(locations[0]), Double.parseDouble(locations[1]), 1);

        place.setText(String.valueOf(addresses.get(0).getAddressLine(0)));

        if (activity_list.get(pos).getPreu().equals("0.0")) {
            price.setText("GRATIS");
        } else price.setText(activity_list.get(pos).getPreu() + " €");


        if (activity_list.get(pos).getPreuSoci().equals("0.0")) {
            member_price.setText("GRATIS");
        } else member_price.setText(activity_list.get(pos).getPreuSoci() + " €");
        description.setText(activity_list.get(pos).getDescription());
        Picasso.get().load(activity_list.get(pos).getPhoto_url()).into(photo);

        //if (activity_client.get(pos).getApuntat()) {
        if (false) {
            //me desapunto button
            button.setText("ME DESAPUNTO");
            button.setOnClickListener(v -> {
                activityController.leftActivity(activity_ID.toString(), new ActivityController.VolleyResponseListener(){
                    @Override
                    public void onError(String message) {
                        Toast.makeText(null, message, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponseJoinedOrLeft(String message) {
                        Toast.makeText(null, message, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponseActivity(ArrayList<Activitat> ret) {
                    }

                    @Override
                    public void onResponseType(ArrayList<String> ret) {
                    }
                });
            });
        }
        else {
            //me apunto button
            button.setOnClickListener(v -> {
                activityController.joinActivity(activity_ID.toString(), new ActivityController.VolleyResponseListener(){
                    @Override
                    public void onError(String message) {
                        Toast.makeText(null, message, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponseJoinedOrLeft(String message) {
                        Toast.makeText(null, message, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponseActivity(ArrayList<Activitat> ret) {
                    }

                    @Override
                    public void onResponseType(ArrayList<String> ret) {
                    }
                });
            });
        }
        //Log.d("ABNS BEE  m   E", String.valueOf(activity_list.get(0)));

        /*if (activity_client.get(pos).getApuntat()) {
            //me desapunto button
            button.setOnClickListener(v -> {
                View parentLayout = findViewById(R.id.activity_detail);
                Snackbar snackbar = Snackbar.make(v, "HOLA", Snackbar.LENGTH_INDEFINITE).setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE);
                snackbar.setAction("IR A LA ACTIVIDAD", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                }
            }
        }
        else {
            //me apunto
            button.setOnClickListener(v -> {
                View parentLayout = findViewById(R.id.activity_detail);
                Snackbar snackbar = Snackbar.make(v, "HOLA", Snackbar.LENGTH_INDEFINITE).setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE);
                snackbar.setAction("IR A LA ACTIVIDAD", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                }
            }
        }*/
        button.setOnClickListener(v -> {

        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        List<Marker> markers = new ArrayList<>();
        String[] locations = activity_list.get(pos).getLocation().split(", ");


        LatLng sydney = new LatLng(Double.parseDouble(locations[0]), Double.parseDouble(locations[1]));


        markers.add(mMap.addMarker(new MarkerOptions().position(sydney).title(activity_list.get(pos).getName())));


        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,15));
        // Zoom in, animating the camera.
        mMap.animateCamera(CameraUpdateFactory.zoomIn());
        // Zoom out to zoom level 10, animating with a duration of 2 seconds.
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);

        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

}
