package com.example.workoutapp.ui.home;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.workoutapp.Activitat;
import com.example.workoutapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ActivityListAdapter extends RecyclerView.Adapter<ActivityListAdapter.ViewHolder> {
    private static ActivityListAdapter INSTANCE;
    LayoutInflater inflater;
    List<Activitat> activitats;
    List<Activitat> activitatsFull;


    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView organization,activityTitle, dateTime;
        ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            organization = itemView.findViewById(R.id.organization);
            activityTitle = itemView.findViewById(R.id.activityTitle);
            dateTime = itemView.findViewById(R.id.dateTime);
            image = itemView.findViewById(R.id.coverImage);

            // handle onClick


        }
    }

    public static ActivityListAdapter getInstance(Context ctx, List<Activitat> activitats){
        if (INSTANCE == null) INSTANCE = new ActivityListAdapter(ctx,activitats);
        return INSTANCE;
    }
    public ActivityListAdapter(Context ctx, List<Activitat> activitats){
        this.inflater = LayoutInflater.from(ctx);
        this.activitats = activitats;
        activitatsFull = new ArrayList<>(activitats);
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
        Picasso.get().load(activitats.get(position).getPhoto_url()).into(holder.image);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Context context = v.getContext();
                                            Intent intent = new Intent(context, ScrollingActivity.class);
                                            intent.putExtra("Position recycler", position);
                                            context.startActivity(intent);


                                        }
                                    }
        );
    }

    @Override
    public int getItemCount() {
        return activitats.size();
    }


    public Filter getFilter() {
        return exampleFilter;
    }


    public List<Activitat> copyInfo(){
        return activitats;
    }
    public void setList(List<Activitat> aux){
        activitats = new ArrayList<>(aux);
        activitatsFull  = new ArrayList<>(aux);
        notifyDataSetChanged();
    }

    private final Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Activitat> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(activitatsFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Activitat item : activitatsFull) {
                    if (item.getName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            activitats = new ArrayList<>((ArrayList<Activitat>) results.values);
            notifyDataSetChanged();
        }
    };

}