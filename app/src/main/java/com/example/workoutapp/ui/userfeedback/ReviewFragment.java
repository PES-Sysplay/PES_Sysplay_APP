package com.example.workoutapp.ui.userfeedback;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.workoutapp.Activitat;
import com.example.workoutapp.R;
import com.example.workoutapp.UserActivityController;
import com.example.workoutapp.ui.home.ActivityListAdapter;

import java.util.ArrayList;
import java.util.List;


public class ReviewFragment extends Fragment {

    Button reviewBtn;
    TextView commentText;
    RatingBar stars;
    List<Activitat> activity_list = new ArrayList<>();
    int pos;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_review, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        activity_list = ActivityListAdapter.getInstance(view.getContext(), new ArrayList<>()).copyInfo();

        pos = getActivity().getIntent().getIntExtra("Position recycler",0);

        reviewBtn = view.findViewById(R.id.sendReviewBt);
        commentText = view.findViewById(R.id.commentText);
        stars = view.findViewById(R.id.simpleRatingBar);

        reviewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String comment = commentText.getText().toString();
                Float userStars = stars.getRating();
                if(checkCorrect(userStars)) {
                    UserActivityController UAController = new UserActivityController(view.getContext());
                    UAController.sendReview(activity_list.get(pos).getId(), comment, userStars, new UserActivityController.VolleyResponseListener() {

                        @Override
                        public void onError(String message) {
                            Toast.makeText(view.getContext(), message, Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onResponse(String message) {
                            Toast.makeText(view.getContext(), message, Toast.LENGTH_SHORT).show();
                            activity_list.get(pos).toggleReviewed();
                            ((ReviewActivity) getActivity()).goToConfirmedFragment();
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
                    });
                }
            }
        });

    }

    public boolean checkCorrect(Float stars){
        if(stars >= 0 && stars <= 5) return true;
        else return false;
    }
}