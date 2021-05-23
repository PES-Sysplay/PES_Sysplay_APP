package com.example.workoutapp.ui.home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.workoutapp.Activitat;
import com.example.workoutapp.ActivityController;
import com.example.workoutapp.Chat;
import com.example.workoutapp.R;
import com.example.workoutapp.UserActivityController;
import com.example.workoutapp.ui.chat.ChatActivity;
import com.example.workoutapp.ui.userfeedback.ReportActivity;
import com.example.workoutapp.ui.userfeedback.ReviewActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ActivityDetail extends AppCompatActivity implements OnMapReadyCallback, PopupMenu.OnMenuItemClickListener {

    int pos, clientsJoin;
    ImageView photo, people_photo,superhost;
    TextView activity,organization, time, place,price, member_price,description,people_activity;
    Boolean favorite, is_old, checked_in, joined,host;
    MenuItem favBtn, unfavBtn, moreBtn, qrBtn;
    ExtendedFloatingActionButton buttonJoin;
    ExtendedFloatingActionButton buttonLeave;
    List<Activitat> activity_list = new ArrayList<>();
    private GoogleMap mMap;
    Uri uri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        pos = getIntent().getIntExtra("Position recycler", 0);
        invalidateOptionsMenu();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);

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
        // ATTENTION: This was auto-generated to handle app links.

    }

    @Override
    public boolean onCreateOptionsMenu(@NotNull Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_details_menu, menu);
        favBtn = menu.findItem(R.id.action_fav);
        unfavBtn = menu.findItem(R.id.action_unfav);
        moreBtn = menu.findItem(R.id.action_more);
        qrBtn = menu.findItem(R.id.action_qr);
        favorite =  activity_list.get(pos).isFavorite();
        joined = activity_list.get(pos).isJoined();
        is_old = activity_list.get(pos).isOld();
        checked_in = activity_list.get(pos).isChecked_in();

        if(!favorite){
            favBtn.setVisible(true);
            unfavBtn.setVisible(false);
        }
        else{
            favBtn.setVisible(false);
            unfavBtn.setVisible(true);
        }

        qrBtn.setVisible(false); //de momento, hasta solucionar bug de detail de muchos botones

        /*if (!joined) {
            qrBtn.setVisible(false);
        }

        else {
            qrBtn.setVisible(true);
        }*/

        if (is_old && !checked_in) {
            moreBtn.setVisible(false);
        }
        else {
            moreBtn.setVisible(true);
        }
        return true;
    }




    public boolean onOptionsItemSelected(MenuItem item){
        UserActivityController uaController = new UserActivityController(this);
        Integer activityID = activity_list.get(pos).getId();

        switch (item.getItemId()){
            case R.id.action_fav:
                uaController.favorite(activityID, new UserActivityController.VolleyResponseListener() {
                    @Override
                    public void onError(String message) {
                        Toast.makeText(getBaseContext(), message, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String message) {}

                    @Override
                    public void onResponseFavorites(ArrayList<Activitat> ret) {}

                    @Override
                    public void onResponseFav() {
                        item.setVisible(false);
                        unfavBtn.setVisible(true);
                    }

                    @Override
                    public void onResponseJoinedActivites(ArrayList<Activitat> ret) {}

                    @Override
                    public void onResponseChat(ArrayList<Chat> ret) {}

                });

                return true;

            case R.id.action_unfav:
                uaController.unfavorite(activityID, new UserActivityController.VolleyResponseListener() {
                    @Override
                    public void onError(String message) {
                        Toast.makeText(getBaseContext(), message, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String message) {}

                    @Override
                    public void onResponseFavorites(ArrayList<Activitat> ret) {}

                    @Override
                    public void onResponseFav() {
                        item.setVisible(false);
                        favBtn.setVisible(true);
                    }

                    @Override
                    public void onResponseJoinedActivites(ArrayList<Activitat> ret) { }

                    @Override
                    public void onResponseChat(ArrayList<Chat> ret) {}

                });

                return true;

            case R.id.action_more:
                showPopup(findViewById(R.id.action_more));
                return true;

            case R.id.action_qr:
                Intent intent = new Intent(this, QRActivity.class);
                intent.putExtra("Position recycler", pos);
                intent.putExtra("Updated Token", activity_list.get(pos).getToken());
                this.startActivity(intent);
                return true;

            case R.id.sharewith:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.setType("text/plain");
                sendIntent.putExtra(Intent.EXTRA_TEXT, "Qué te parece este plan? '" + activity_list.get(pos).getName() + "' \n https://www.workout.com/activity/" + pos);
                Intent shareIntent = Intent.createChooser(sendIntent, "Share Activity");
                startActivity(shareIntent);
                return true;

            case android.R.id.home:
                onBackPressed();
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onBackPressed() {
        super.onBackPressed();
    }

    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.scrolling_options_menu);

        MenuItem reportBt = popup.getMenu().findItem(R.id.reportBt);
        MenuItem reviewBt = popup.getMenu().findItem(R.id.reviewBt);
        MenuItem questionBt = popup.getMenu().findItem(R.id.questionBt);
        is_old = activity_list.get(pos).isOld();
        checked_in = activity_list.get(pos).isChecked_in();

        //ini a false
        reportBt.setVisible(false);
        reviewBt.setVisible(false);
        questionBt.setVisible(false);

        if (is_old && checked_in) {

            if (!activity_list.get(pos).isReported()) {
                reportBt.setVisible(true);
            }

            if (!activity_list.get(pos).isReviewed()) {
                reviewBt.setVisible(true);
            }
        }

        else if (!is_old) { //not old
            questionBt.setVisible(true);
        }

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
                Intent review = new Intent(this, ReviewActivity.class);
                review.putExtra("Position recycler", pos);
                this.startActivity(review);
                break;
            case R.id.questionBt:
                Intent question = new Intent(this, ChatActivity.class);
                question.putExtra("Position recycler", pos);
                this.startActivity(question);

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
        people_activity = findViewById(R.id.people);
        photo = findViewById(R.id.imageView);
        people_photo = findViewById(R.id.people_drawable);
        buttonJoin = findViewById(R.id.meapunto);
        superhost = findViewById(R.id.suphost_detail);

    }
    void updatePeople(int join){
        clientsJoin += join;

        String people = (clientsJoin + " / " +activity_list.get(pos).getNumberParticipants());
        people_activity.setText(people);
        if(activity_list.get(pos).getNumberParticipants() - clientsJoin <= 2) {
            people_activity.setTextColor(Color.parseColor("#A41E01"));
            if(activity_list.get(pos).getNumberParticipants() - clientsJoin == 0)
                people_photo.setColorFilter(Color.parseColor("#A41E01"));
            else{
                people_photo.setColorFilter(Color.BLACK);
            }
        }
        else{
            people_activity.setTextColor(Color.BLACK);
            people_photo.setColorFilter(Color.BLACK);
        }
    }
    @SuppressLint("SetTextI18n")
    void set_values() throws IOException {
        activity.setText(activity_list.get(pos).getName());

        clientsJoin = activity_list.get(pos).getClientJoined() ;
        updatePeople(0);


        organization.setText((activity_list.get(pos).getOrganizerName()));

        String[] date_time_beg = activity_list.get(pos).getDate_time().split(", ");
        String[] date_time_end = activity_list.get(pos).getDateTimeFinish().split(", ");
        String Date;
        if((date_time_beg[0].split(" "))[1].equals((date_time_end[0].split(" "))[1])){
           Date = activity_list.get(pos).getDate_time() +" - "+date_time_end[2];
        }
        else{
            Date = activity_list.get(pos).getDate_time() +" - "+activity_list.get(pos).getDateTimeFinish();
        }

        host = activity_list.get(pos).isSuperHost();
        if(host){
            superhost.setVisibility(View.VISIBLE);
        }
        else{
            superhost.setVisibility(View.GONE);
        }
        time.setText(Date);
        time.setText(Date);
        Integer activity_ID = activity_list.get(pos).getId();
        boolean joined = activity_list.get(pos).isJoined();
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


        if(!activity_list.get(pos).isJoined()) {
            buttonJoin.setText("¡ME APUNTO!");
        }
        else{
            buttonJoin.setText("ME DESAPUNTO");
        }

        buttonJoin.setOnClickListener(v -> {
            if(buttonJoin.getText().equals("¡ME APUNTO!")){
                activityController.joinActivity(activity_ID, new ActivityController.VolleyResponseListener(){
                    @Override
                    public void onError(String message) {
                        Toast.makeText(getBaseContext(), message, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponseActivity(ArrayList<Activitat> ret) {

                    }

                    @Override
                    public void onResponseType(ArrayList<String> ret) {
                    }

                    @Override
                    public void onResponseJoinActivity() {
                        buttonJoin.setText("ME DESAPUNTO");
                        updatePeople(1);
                        activityController.getActivitats(new ActivityController.VolleyResponseListener() {
                            @Override
                            public void onError(String message) {
                                Toast.makeText(getBaseContext(), message, Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onResponseActivity(ArrayList<Activitat> ret) {
                                activity_list = ret;
                            }

                            @Override
                            public void onResponseType(ArrayList<String> ret) {

                            }

                            @Override
                            public void onResponseJoinActivity() {

                            }
                        });
                        qrBtn.setVisible(true);
                    }

                });



            }
            else {
                activityController.leftActivity(activity_ID, new ActivityController.VolleyResponseListener() {
                    @Override
                    public void onError(String message) {
                        Toast.makeText(getBaseContext(), message, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponseActivity(ArrayList<Activitat> ret) {

                    }

                    @Override
                    public void onResponseType(ArrayList<String> ret) {
                    }

                    @Override
                    public void onResponseJoinActivity() {
                        buttonJoin.setText("¡ME APUNTO!");
                        updatePeople(-1);
                        qrBtn.setVisible(false);
                    }
                });

            }

        });

    }
    //Log.d("ABNS BEE  m   E", String.valueOf(activity_list.get(0)));

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setScrollGesturesEnabled(false);
        mMap.getUiSettings().setScrollGesturesEnabledDuringRotateOrZoom(false);
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
