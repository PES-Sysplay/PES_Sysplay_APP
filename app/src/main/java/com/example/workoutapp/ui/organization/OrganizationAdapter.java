package com.example.workoutapp.ui.organization;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.workoutapp.R;
import com.example.workoutapp.Review;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class OrganizationAdapter extends RecyclerView.Adapter<OrganizationAdapter.ViewHolder>{

    private static OrganizationAdapter INSTANCE;
    LayoutInflater inflater;
    List<Review> reviews;

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.review_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        //holder.username.setText(reviews.get(position).getUsername());
        holder.description.setText(reviews.get(position).getComment());
        //holder.date.setText(reviews.get(position).getDate());
        holder.rating.setRating(reviews.get(position).getRating());


        holder.username.setText("Benito");
        holder.date.setText("05/04/20");
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    public static OrganizationAdapter getInstance(Context ctx, List<Review> reviews){
        if (INSTANCE == null) INSTANCE = new OrganizationAdapter(ctx, reviews);
        return INSTANCE;
    }

    public OrganizationAdapter(Context ctx, List<Review> reviews){
        this.inflater = LayoutInflater.from(ctx);
        this.reviews = reviews;
    }

    void setReviews(List<Review> reviews){
        this.reviews = reviews;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView username, date, description;
        RatingBar rating;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            description = itemView.findViewById(R.id.review_description);
            date = itemView.findViewById(R.id.rating_date);
            username = itemView.findViewById(R.id.review_user);
            rating = itemView.findViewById(R.id.ratingBar_review);

        }
    }
}
