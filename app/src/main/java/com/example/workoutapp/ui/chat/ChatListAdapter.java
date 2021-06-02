package com.example.workoutapp.ui.chat;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.workoutapp.Chat;
import com.example.workoutapp.R;
import com.example.workoutapp.UserSingleton;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.Holder> {
    private final List<Chat> chatList;
    private final Context context;

    public ChatListAdapter(List<Chat> chatList, Context ctx) {
        this.chatList = chatList;
        this.context = ctx;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_chat_list, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatListAdapter.Holder holder, int position) {

        Chat chat = chatList.get(position);

        holder.name_tv.setText(chat.getOrganization());
        holder.name_tv.append(" - ");
        holder.name_tv.append(chat.getActivity_name());

        String last_message;
        if (chat.getLast_message().getUsername().equals(UserSingleton.getInstance().getUsername())) {
            last_message = ("Tú: " + chat.getLast_message().getText());
        }

        else {
            last_message = (chat.getLast_message().getUsername() + ": " + chat.getLast_message().getText());
        }

        holder.desc_tv.setText(last_message);

        float x = chat.getLast_message().getDate_timestamp();
        long lx = (long) x;
        Timestamp stamp = new Timestamp(lx*1000);
        Date date = new Date(stamp.getTime());
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yy");
        String strTime = dateFormat.format(date);
        holder.date_tv.setText(strTime);

        Glide.with(context).load(chat.getOrganization_photo()).into(holder.profileImg);

        holder.itemView.setOnClickListener((View v) -> {
            Context context = v.getContext();
            Intent intent = new Intent(context, ChatActivity.class);
            intent.putExtra("Id recycler", chat.getActivity_id());
            context.startActivity(intent);

        });
    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    public static class Holder extends RecyclerView.ViewHolder {
        private final TextView name_tv;
        private final TextView desc_tv;
        private final TextView date_tv;
        private final CircularImageView profileImg;

        public Holder(@NonNull View itemView) {
            super(itemView);

            date_tv = itemView.findViewById(R.id.tv_date);
            desc_tv = itemView.findViewById(R.id.tv_desc);
            name_tv = itemView.findViewById(R.id.tv_name);
            profileImg = itemView.findViewById(R.id.image_profile);
        }
    }
}
