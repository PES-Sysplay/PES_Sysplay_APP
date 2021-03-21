package com.example.workoutapp.ui.home;

import android.content.Context;
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

import java.util.List;

public class ActivityListAdapter extends RecyclerView.Adapter<ActivityListAdapter.ViewHolder> {
    LayoutInflater inflater;
    List<Activitat> activitats;

    public ActivityListAdapter(Context ctx, List<Activitat> activitats){
        this.inflater = LayoutInflater.from(ctx);
        this.activitats = activitats;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.activity_element, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // bind the data
        holder.organization.setText(activitats.get(position).getOrganizerName());
        holder.activityTitle.setText(activitats.get(position).getName());
        holder.dateTime.setText(activitats.get(position).getDateTimeString());
        //TODO poner la imagen

    }

    @Override
    public int getItemCount() {
        return activitats.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView organization,activityTitle, dateTime;
        ImageView songCoverImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            organization = itemView.findViewById(R.id.organization);
            activityTitle = itemView.findViewById(R.id.activityTitle);
            dateTime = itemView.findViewById(R.id.dateTime);
            songCoverImage = itemView.findViewById(R.id.coverImage);

            // handle onClick

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), "Do Something With this Click", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}