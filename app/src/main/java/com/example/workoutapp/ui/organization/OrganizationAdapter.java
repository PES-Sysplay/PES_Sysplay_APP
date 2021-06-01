package com.example.workoutapp.ui.organization;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.workoutapp.Activitat;
import com.example.workoutapp.Chat;
import com.example.workoutapp.Organizer;
import com.example.workoutapp.R;
import com.example.workoutapp.Review;
import com.example.workoutapp.UserActivityController;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class OrganizationAdapter extends RecyclerView.Adapter<OrganizationAdapter.ViewHolder> {

    private static OrganizationAdapter INSTANCE;
    LayoutInflater inflater;
    List<Review> reviews;

    public OrganizationAdapter(Context ctx, List<Review> reviews) {
        this.inflater = LayoutInflater.from(ctx);
        this.reviews = reviews;
    }

    public static OrganizationAdapter getInstance(Context ctx, List<Review> reviews) {
        if (INSTANCE == null) INSTANCE = new OrganizationAdapter(ctx, reviews);
        return INSTANCE;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.review_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        holder.username.setText(reviews.get(position).getUsername());
        holder.description.setText(reviews.get(position).getComment());
        holder.rating.setRating(reviews.get(position).getRating());

        holder.rating.setIsIndicator(true);

        holder.pop_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(inflater.getContext(), holder.pop_up);
                popup.inflate(R.menu.organization_pop_up);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.reportReview:

                                UserActivityController uc = new UserActivityController(inflater.getContext());

                                uc.reportReview(reviews.get(position).getId(), new UserActivityController.VolleyResponseListener() {
                                    @Override
                                    public void onError(String message) {
                                        Toast.makeText(inflater.getContext(), message, Toast.LENGTH_SHORT).show();
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

                                    }

                                    @Override
                                    public void onResponseChat(ArrayList<Chat> ret) {

                                    }

                                    @Override
                                    public void onResponseReportReview() {
                                        Toast.makeText(inflater.getContext(), "Reseña reportada con exito", Toast.LENGTH_SHORT).show();
                                    }
                                });

                                break;
                        }
                        return false;
                    }
                });
                //displaying the popup
                popup.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    void setReviews(List<Review> reviews) {
        this.reviews = reviews;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView username, description, pop_up;
        RatingBar rating;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            description = itemView.findViewById(R.id.review_description);
            username = itemView.findViewById(R.id.review_user);
            rating = itemView.findViewById(R.id.ratingBar_review);
            pop_up = itemView.findViewById(R.id.optionsmenu_orgcard);

        }
    }
}
