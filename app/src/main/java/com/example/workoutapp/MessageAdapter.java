package com.example.workoutapp;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.CustomViewHolder> {

    List<Message> Messages;
    Context context;
    class CustomViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        public CustomViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textMessage);
        }
    }

    public MessageAdapter(List<Message> responseMessages, Context context) {
        this.Messages = responseMessages;
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        if(Messages.get(position).getUserId().equals(UserSingleton.getInstance().getId())) {
            return R.layout.me_bubble;
        }
        return R.layout.org_bubble;
    }

    @Override
    public int getItemCount() {
        return Messages.size();
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CustomViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(viewType, parent, false));
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        String text = Messages.get(position).getText();
        Spannable orgText = new SpannableString("Organizador\n");

        orgText.setSpan(new ForegroundColorSpan(Color.RED), 0, orgText.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        orgText.setSpan(new AbsoluteSizeSpan(12, true), 0, orgText.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        if (Messages.get(position).getUserId().equals("")) {
            holder.textView.setText(orgText);
        }
        holder.textView.append(text);
    }
}
