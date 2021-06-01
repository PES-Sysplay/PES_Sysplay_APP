package com.example.workoutapp.ui.profile;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.workoutapp.Activitat;
import com.example.workoutapp.R;
import com.example.workoutapp.ui.calendar.CalendarAdapter;
import com.example.workoutapp.ui.home.ActivityDetail;
import com.example.workoutapp.ui.home.ActivityListAdapter;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {

    private static FavoriteAdapter INSTANCE;
    LayoutInflater inflater;
    List<Activitat> activitats;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.activity_element, parent, false);
        return new FavoriteAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.organization.setText(activitats.get(position).getOrganizerName());
        holder.activityTitle.setText(activitats.get(position).getName());
        holder.dateTime.setText(activitats.get(position).getDateTimeString());
        Picasso.get().load(activitats.get(position).getPhoto_url()).into(holder.image);


        holder.itemView.setOnClickListener((View v) -> {
            Context context = v.getContext();
            Intent intent = new Intent(context, ActivityDetail.class);
            intent.putExtra("Position recycler", position);
            intent.putExtra("adapter",5);
            context.startActivity(intent);
        });
    }

    public List<Activitat> copyInfo(){
        return activitats;
    }

    @Override
    public int getItemCount() {
        return activitats.size();
    }

    public static FavoriteAdapter getInstance(Context ctx, List<Activitat> activitats){
        if (INSTANCE == null) INSTANCE = new FavoriteAdapter(ctx, activitats);
        return INSTANCE;
    }

    public FavoriteAdapter(Context ctx, List<Activitat> activitats){
        this.inflater = LayoutInflater.from(ctx);
        this.activitats = activitats;
    }

    public void setInflater(LayoutInflater inflater) {
        this.inflater = inflater;
    }

    public void setActivitatsUsuari(List<Activitat> activitats) {
        this.activitats = activitats;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView organization, activityTitle, dateTime;
        ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            organization = itemView.findViewById(R.id.organization);
            activityTitle = itemView.findViewById(R.id.activityTitle);
            dateTime = itemView.findViewById(R.id.dateTime);
            image = itemView.findViewById(R.id.coverImage);

            // handle onClick

            itemView.setOnClickListener(v ->
                    Toast.makeText(v.getContext(), "Do Something With this Click", Toast.LENGTH_SHORT).show()
            );
        }
    }
}
