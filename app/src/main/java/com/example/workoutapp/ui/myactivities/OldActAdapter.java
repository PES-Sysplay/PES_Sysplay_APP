package com.example.workoutapp.ui.myactivities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import com.example.workoutapp.Organizer;
import com.example.workoutapp.Chat;
import com.example.workoutapp.R;
import com.example.workoutapp.Review;
import com.example.workoutapp.UserActivityController;
import com.example.workoutapp.ui.home.ActivityDetail;
import com.example.workoutapp.ui.home.ActivityListAdapter;
import com.squareup.picasso.Picasso;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class OldActAdapter extends RecyclerView.Adapter<OldActAdapter.ViewHolder> {
    private static OldActAdapter INSTANCE;
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

    public static OldActAdapter getInstance(Context ctx, List<Activitat> activitats){
        if (INSTANCE == null) INSTANCE = new OldActAdapter(ctx,activitats);
        return INSTANCE;
    }

    public OldActAdapter(Context ctx, List<Activitat> activitats){
        this.inflater = LayoutInflater.from(ctx);
        this.activitats = activitats;
        activitatsFull = new ArrayList<>(activitats);
    }

    //for testing purposes
    public OldActAdapter(List<Activitat> activitats) {
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
        if(link != -33 && secure){
            Context context = holder.getContext();
            Intent intent = new Intent(context, ActivityDetail.class);
            intent.putExtra("Position recycler", link);
            intent.putExtra("adapter",4);
            context.startActivity(intent);
            link = -33;
            secure = false;
        }
        holder.itemView.setOnClickListener((View v) -> {
            Context context = v.getContext();
            Intent intent = new Intent(context, ActivityDetail.class);
            intent.putExtra("Position recycler", position);
            intent.putExtra("adapter",4);
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

    }

    public void updateFavs(@NonNull ViewHolder holder, int position){
        Integer activityID = activitats.get(position).getId();
        boolean favorite =  activitats.get(position).isFavorite();
        if(!favorite) {
            holder.favBtn.setVisibility(View.VISIBLE);
            holder.unfavBtn.setVisibility(View.GONE);
        }
        else{
            holder.unfavBtn.setVisibility(View.VISIBLE);
            holder.favBtn.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return activitats.size();
    }

    public List<Activitat> getActivitats() {
        return activitats;
    }

    public List<Activitat> getActivitatsFull() {
        return activitatsFull;
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

        //TODO que devuelva el codigo del deporte

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
        Context context = itemView.getContext();

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