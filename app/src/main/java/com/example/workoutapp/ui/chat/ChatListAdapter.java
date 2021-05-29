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
import com.example.workoutapp.ui.home.ActivityDetail;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.List;

public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.Holder> {
    private List<Chat> chatList;
    private Context context;

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
        holder.desc_tv.setText("last message");

        /*float x = chat.getLastMessageTimestamp();
        long lx = (long) x;
        Timestamp stamp = new Timestamp(lx*1000);
        Date date = new Date(stamp.getTime());
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yy\nhh:mm");
        String strTime = dateFormat.format(date);
        holder.date_tv.setText(strTime);*/

        holder.date_tv.setText("Today");

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

    public class Holder extends RecyclerView.ViewHolder {
        private TextView name_tv, desc_tv, date_tv;
        private CircularImageView profileImg;

        public Holder(@NonNull View itemView) {
            super(itemView);

            date_tv = itemView.findViewById(R.id.tv_date);
            desc_tv = itemView.findViewById(R.id.tv_desc);
            name_tv = itemView.findViewById(R.id.tv_name);
            profileImg = itemView.findViewById(R.id.image_profile);
        }
    }
}
