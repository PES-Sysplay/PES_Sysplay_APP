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
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.example.workoutapp.Activitat;
import com.example.workoutapp.Chat;
import com.example.workoutapp.Organizer;
import com.example.workoutapp.R;
import com.example.workoutapp.Review;
import com.example.workoutapp.UserActivityController;
import com.squareup.picasso.Picasso;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class ActivityListAdapter extends RecyclerView.Adapter<ActivityListAdapter.ViewHolder> {
    private static ActivityListAdapter INSTANCE;
    LayoutInflater inflater;
    List<Activitat> activitats;
    List<Activitat> activitatsFull;
    int link = -33;
    boolean secure = true;

    private final Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Activitat> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0 || constraint.equals("StartTitleEndTitleStartOrganizationEndOrganizationStartSportEndSport")) {
                filteredList.addAll(activitatsFull);
            } else {
                List<String> list = getQueryParameters(constraint.toString());
                String name = list.get(0).toLowerCase().trim();
                String org = list.get(1).toLowerCase().trim();
                String sport = list.get(2).trim();

                //String filterPattern = constraint.toString().toLowerCase().trim();
                for (Activitat item : activitatsFull) {
                    if (item.getName().toLowerCase().contains(name) &&
                            (org.equals("") || (item.getOrganizerName() != null && item.getOrganizerName().toLowerCase().contains(org))) &&
                            (sport.equals("") || (item.getActivity_type_id() != null && item.getActivity_type_id().equals(sport)))) {

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
            activitats = new ArrayList<>((ArrayList<Activitat>) results.values); //esto pinta que causa errores
            notifyDataSetChanged();
        }
    };
    ArrayList<String> activity_id_list;

    public static ActivityListAdapter getInstance(Context ctx, List<Activitat> activitats){
        if (INSTANCE == null) INSTANCE = new ActivityListAdapter(ctx,activitats);
        return INSTANCE;
    }
  
    public ActivityListAdapter(Context ctx, List<Activitat> activitats){
        this.inflater = LayoutInflater.from(ctx);
        this.activitats = activitats;
        activitatsFull = new ArrayList<>(activitats);
    }

    //for testing purposes
    public ActivityListAdapter(List<Activitat> activitats) {
        this.activitats = activitats;
        activitatsFull = new ArrayList<>(activitats);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.activity_element, parent, false);
        return new ViewHolder(view);
    }

    public int getActivityIndex(String title){
        for (int i = 0; i < activitatsFull.size(); ++i){
            if(activitatsFull.get(i).getName().equals(title)) return i;
        }
        return -1;
    }

    public void setLink(int param){
        link = param;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // bind the data
        holder.organization.setText(activitats.get(position).getOrganizerName());
        holder.activityTitle.setText(activitats.get(position).getName());
        holder.dateTime.setText(activitats.get(position).getDateTimeString());
        Picasso.get().load(activitats.get(position).getPhoto_url()).into(holder.image);

        boolean shost = activitats.get(position).isSuperHost();
        if(shost){
            holder.superhost.setVisibility(View.VISIBLE);
        }
        else{
            holder.superhost.setVisibility(View.GONE);

        }
        if(link != -33){
            Context context = holder.getContext();
            Intent intent = new Intent(context, ActivityDetail.class);
            int pos = getActivity(link);
            intent.putExtra("Position recycler", pos);
            intent.putExtra("adapter",1);
            context.startActivity(intent);
            link = -33;
            //secure = false;
        }
        holder.itemView.setOnClickListener((View v) -> {
            Context context = v.getContext();
            Intent intent = new Intent(context, ActivityDetail.class);
            intent.putExtra("Position recycler", position);
            intent.putExtra("From", "home");
            intent.putExtra("adapter",1);
            context.startActivity(intent);

        });
        Integer activityID = activitats.get(position).getId();
        boolean favorite = activitats.get(position).isFavorite();
        if (!favorite) {
            holder.favBtn.setVisibility(View.VISIBLE);
            holder.unfavBtn.setVisibility(View.GONE);
        } else {
            holder.unfavBtn.setVisibility(View.VISIBLE);
            holder.favBtn.setVisibility(View.GONE);
        }

        holder.favBtn.setOnClickListener(v -> {
            UserActivityController uaController = new UserActivityController(v.getContext());
            uaController.favorite(activityID, new UserActivityController.VolleyResponseListener() {
                @Override
                public void onError(String message) {
                    Toast.makeText(v.getContext(), message, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onResponse(String message) {
                    Toast.makeText(v.getContext(), message, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onResponseFavorites(ArrayList<Activitat> ret) {
                }

                @Override
                public void onResponseJoinedActivities(ArrayList<Activitat> ret) {
                }

                @Override
                public void onResponseReviewList(ArrayList<Review> ret) {

                }

                @Override
                public void onResponseOrganizationList(ArrayList<Organizer> ret) {

                }

                @Override
                public void onResponseChat(ArrayList<Chat> ret) {}

                @Override
                public void onResponseReportReview() {

                }

                @Override
                public void onResponseFav() {}

            });
            activitats.get(position).toggleFavorite();
            holder.favBtn.setVisibility(View.GONE);
            holder.unfavBtn.setVisibility(View.VISIBLE);
        });


        holder.unfavBtn.setOnClickListener(v -> {
            UserActivityController uaController = new UserActivityController(v.getContext());
            uaController.unfavorite(activityID, new UserActivityController.VolleyResponseListener() {
                @Override
                public void onError(String message) {
                    Toast.makeText(v.getContext(), message, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onResponse(String message) {
                    Toast.makeText(v.getContext(), message, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onResponseFavorites(ArrayList<Activitat> ret) {}

                @Override
                public void onResponseJoinedActivities(ArrayList<Activitat> ret) {}

                @Override
                public void onResponseChat(ArrayList<Chat> ret) {}

                @Override
                public void onResponseReportReview() {

                }

                @Override
                public void onResponseReviewList(ArrayList<Review> ret) {

                }

                @Override
                public void onResponseOrganizationList(ArrayList<Organizer> ret) {

                }

                @Override
                public void onResponseFav() {}

            });
            activitats.get(position).toggleFavorite();
            holder.favBtn.setVisibility(View.VISIBLE);
            holder.unfavBtn.setVisibility(View.GONE);
        });

    }

    public int getActivity(int id){
        int pos = -33,i = 0;
        for(Activitat act: activitats){
            if(act.getId().equals(id)){
                pos = i;
            }
            else {
                ++i;
            }
        }
        return pos;
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
        activitatsFull = new ArrayList<>(aux);

        notifyDataSetChanged();
    }

    //Traduce la query del filtro y devuelve los parametros nombre, organizacion y nombre de deporte
    //POST: Lista con elem0: nombre, elem1: organizacion, elem2: nombre del deporte
    private List<String> getQueryParameters(String query) {
        List<String> out = new ArrayList<>();
        out.add(StringUtils.substringBetween(query, "StartTitle", "EndTitle"));
        out.add(StringUtils.substringBetween(query, "StartOrganization", "EndOrganization"));
        out.add(StringUtils.substringBetween(query, "StartSport", "EndSport"));

        return out;
    }

    public ArrayList<String> getActivity_id_list() {
        return activity_id_list;
    }

    public void setActivity_id_list(ArrayList<String> activity_id_list) {
        this.activity_id_list = activity_id_list;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView organization, activityTitle, dateTime;
        AppCompatButton favBtn, unfavBtn;
        ImageView image, superhost;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            organization = itemView.findViewById(R.id.organization);
            activityTitle = itemView.findViewById(R.id.activityTitle);
            dateTime = itemView.findViewById(R.id.dateTime);
            image = itemView.findViewById(R.id.coverImage);
            favBtn = itemView.findViewById(R.id.favButton);
            unfavBtn = itemView.findViewById(R.id.unfavButton);
            superhost = itemView.findViewById(R.id.suphost);

            // handle onClick

            itemView.setOnClickListener(v ->
                    Toast.makeText(v.getContext(), "Do Something With this Click", Toast.LENGTH_SHORT).show()
            );
        }

        public Context getContext() {
            return itemView.getContext();
        }
    }
}