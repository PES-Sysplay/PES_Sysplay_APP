package com.example.workoutapp.ui.organization;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.workoutapp.Activitat;
import com.example.workoutapp.R;
import com.example.workoutapp.Review;
import com.example.workoutapp.UserActivityController;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class OrganizationActivity extends AppCompatActivity {

    TextView orgName, description;
    ImageView orgImage;
    RatingBar rating;
    OrganizationAdapter adapter;
    List<Review> reviews;
    String organization = "PES";
    RecyclerView reviewList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organization);
        invalidateOptionsMenu();

        adapter = OrganizationAdapter.getInstance(this, new ArrayList<>());

        orgName = findViewById(R.id.org_name);
        description = findViewById(R.id.org_descr);
        orgImage = findViewById(R.id.incono_org);
        rating = findViewById(R.id.org_ratingBar);
        reviewList = findViewById(R.id.org_recyclerView);

        reviewList.setLayoutManager(new LinearLayoutManager(this));
        reviewList.setAdapter(adapter);

        setOrganization();
        setReviews();
    }

    private void setOrganization() {
        //llamada a la api para coger toda la info de la organizacion
        //mientras tanto va hardcoded

        orgName.setText(organization);
        description.setText("Y era un domingo a la tarde, fui a los coches de choque\nPIRIBIRIPIRIBIRIPIRIBIRIPIRIBIRIPIRIBIRIPIRIBIRIPIRIBIRIPIRIBIRI");
        rating.setRating(4.5f);
        //Picasso.get().load(imageURI).into(orgImage);
        Picasso.get().load("https://pbs.twimg.com/profile_images/1142612147945582593/RHeNlcg5_400x400.jpg").into(orgImage);

    }

    private void setReviews() {
        UserActivityController controller = new UserActivityController(this);
        controller.getReviews(organization, new UserActivityController.VolleyResponseListener() {
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
               //adapter.setReviews(reviews);

                Review aux = new Review(4.5f, "tonto quien lo lea", null, null);
                List<Review> asdasda = new ArrayList<Review>();
                for(int i = 0; i < 10; ++i) asdasda.add(aux);
                adapter.setReviews(asdasda);
            }
        });
    }
}
