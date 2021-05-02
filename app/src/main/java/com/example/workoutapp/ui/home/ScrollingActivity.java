package com.example.workoutapp.ui.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import com.example.workoutapp.Activitat;
import com.example.workoutapp.ui.profile.ChangePasswordActivity;
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
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.workoutapp.R;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class ScrollingActivity extends AppCompatActivity implements OnMapReadyCallback, PopupMenu.OnMenuItemClickListener {

    int pos;
    ImageView photo;
    TextView activity,organization, time, place,price, member_price,description;
    ExtendedFloatingActionButton button;
    FloatingActionButton optionsBt;
    List<Activitat> activity_list = new ArrayList<>();
    private GoogleMap mMap;
    //public static final String API_KEY = "AIzaSyDvpqaDWNAMYWb6ePt-PFrLkl1F5MKorS0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        pos = getIntent().getIntExtra("Position recycler",0);


        optionsBt = findViewById(R.id.optionsBt);

        optionsBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopup(v);
            }
        });


        Toolbar toolbar =(Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int to = getIntent().getExtras().getInt("From");
                Log.d("IEEPAAA", "okey");

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

        /*reportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Intent intent = new Intent(context, ReportActivity.class);
                context.startActivity(intent);
            }
        });*/


    }

    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.scrolling_options_menu);
        popup.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.reportBt:
                Intent intent = new Intent(this, ReportActivity.class);
                intent.putExtra("Position recycler", pos);
                this.startActivity(intent);
                break;
            case R.id.reviewBt:
                break;
            default:
                return super.onContextItemSelected(item);

        }
        return true;
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

        //reportButton = findViewById(R.id.reportButton);

        button = findViewById(R.id.meapunto);

    }
    @SuppressLint("SetTextI18n")
    void set_values() throws IOException {
        activity.setText(activity_list.get(pos).getName());
        organization.setText((activity_list.get(pos).getOrganizerName()));
        time.setText(activity_list.get(pos).getDate_time());


        String[] locations = activity_list.get(pos).getLocation().split(", ");
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List <Address> addresses = geocoder.getFromLocation(Double.parseDouble(locations[0]),Double.parseDouble(locations[1]), 1);

        place.setText(String.valueOf(addresses.get(0).getAddressLine(0)));

        if(activity_list.get(pos).getPreu().equals("0.0")) {
            price.setText("GRATIS");
        }
        else price.setText(activity_list.get(pos).getPreu() + " €");


        if(activity_list.get(pos).getPreuSoci().equals("0.0")){
            member_price.setText("GRATIS");
        }
        else member_price.setText(activity_list.get(pos).getPreuSoci()+ " €");
        description.setText(activity_list.get(pos).getDescription());
        Picasso.get().load(activity_list.get(pos).getPhoto_url()).into(photo);
        //Log.d("ABNS BEE  m   E", String.valueOf(activity_list.get(0)));

        button.setOnClickListener((View v) -> {

            View parentLayout = findViewById(R.id.activity_detail);
            Snackbar snackbar = Snackbar.make(v, "HOLA", Snackbar.LENGTH_INDEFINITE).setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE);
            snackbar.setAction("IR A LA ACTIVIDAD", new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
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
