package com.example.workoutapp.ui.organization;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.workoutapp.Activitat;
import com.example.workoutapp.Organizer;
import com.example.workoutapp.R;
import com.example.workoutapp.Review;
import com.example.workoutapp.UserActivityController;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class OrganizationActivity extends AppCompatActivity {

    TextView orgName;
    ImageView orgImage;
    RatingBar rating;
    OrganizationAdapter adapter;
    List<Review> reviews;
    String organizationName;
    Organizer organization;
    RecyclerView reviewList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organization);
        invalidateOptionsMenu();

        adapter = OrganizationAdapter.getInstance(this, new ArrayList<>());

        orgName = findViewById(R.id.org_name);
        orgImage = findViewById(R.id.incono_org);
        rating = findViewById(R.id.org_ratingBar);
        reviewList = findViewById(R.id.org_recyclerView);

        reviewList.setLayoutManager(new LinearLayoutManager(this));
        reviewList.setAdapter(adapter);

        rating.setIsIndicator(true);

        Intent intent = getIntent();
        organizationName = intent.getStringExtra("orgName");

        getOrganizer();
        setRatings();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getOrganizer() {
        UserActivityController controller = new UserActivityController(this);

        controller.getOrganizers(new UserActivityController.VolleyResponseListener() {
            @Override
            public void onError(String message) {

            }

            @Override
            public void onResponse(String message) {

            }

            @Override
            public void onResponseFavorites(ArrayList<Activitat> ret) {

            }

            @Override
            public void onResponseFav() {

            }

            @Override
            public void onResponseJoinedActivites(ArrayList<Activitat> ret) {

            }

            @Override
            public void onResponseReviewList(ArrayList<Review> ret) {
            }

            @Override
            public void onResponseOrganizationList(ArrayList<Organizer> ret) {
                organization = searchOrg(ret);
                if(organization != null) setOrganization();
            }
        });
    }

    private void setRatings() {
        UserActivityController controller = new UserActivityController(this);

        controller.getReviews(organizationName, new UserActivityController.VolleyResponseListener() {
            @Override
            public void onError(String message) {

            }

            @Override
            public void onResponse(String message) {

            }

            @Override
            public void onResponseFavorites(ArrayList<Activitat> ret) {

            }

            @Override
            public void onResponseFav() {

            }

            @Override
            public void onResponseJoinedActivites(ArrayList<Activitat> ret) {

            }

            @Override
            public void onResponseReviewList(ArrayList<Review> ret) {
                reviews = ret;
                adapter.setReviews(ret);
            }

            @Override
            public void onResponseOrganizationList(ArrayList<Organizer> ret) {
            }
        });
    }

    private void setOrganization() {
        orgName.setText(organizationName);
        Picasso.get().load(organization.getPhoto()).into(orgImage);
        //Picasso.get().load("https://r1.ilikewallpaper.net/iphone-8-wallpapers/download/30787/Funny-Movie-Cartoon-Minion-iphone-8-wallpaper-ilikewallpaper_com.jpg").into(orgImage);
        rating.setRating(organization.getRank());
    }

    private Organizer searchOrg(ArrayList<Organizer> ret) {
        for(int i = 0; i < ret.size(); ++i){
            if(ret.get(i).getName().equals(organizationName)) return ret.get(i);
        }
        return null;
    }
}