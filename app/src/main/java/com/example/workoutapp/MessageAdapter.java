package com.example.workoutapp;

import android.content.Context;
import android.graphics.Color;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.AlignmentSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.CustomViewHolder> {

    List<Message> Messages;
    Context context;
    static class CustomViewHolder extends RecyclerView.ViewHolder{
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
        if(Messages.get(position).getUsername().equals(UserSingleton.getInstance().getUsername())) {
            return R.layout.me_bubble;
        }
        return R.layout.org_bubble;
    }

    @Override
    public int getItemCount() {
        return Messages.size();
    }

    @NotNull
    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CustomViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(viewType, parent, false));
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        String text = Messages.get(position).getText();

        Spannable orgText = new SpannableString("Organizador\n\n");
        orgText.setSpan(new ForegroundColorSpan(Color.RED), 0, orgText.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        orgText.setSpan(new AbsoluteSizeSpan(12, true), 0, orgText.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        Spannable orgName = new SpannableString(Messages.get(position).getUsername() + " - ");
        orgName.setSpan(new ForegroundColorSpan(Color.GRAY), 0, orgName.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        orgName.setSpan(new AbsoluteSizeSpan(12, true), 0, orgName.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        float x = Messages.get(position).getDate_timestamp();
        long lx = (long) x;
        Timestamp stamp = new Timestamp(lx*1000);
        Date date = new Date(stamp.getTime());
        DateFormat dateFormat = new SimpleDateFormat("d MMM, HH:mm");
        String strTime = dateFormat.format(date);
        Spannable time = new SpannableString(strTime);
        time.setSpan(new ForegroundColorSpan(Color.GRAY), 0, time.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        time.setSpan(new AbsoluteSizeSpan(11, true), 0, time.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        time.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_OPPOSITE), 0, time.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        holder.textView.setText(""); //necesario para hacer el append

        if (!Messages.get(position).getUsername().equals(UserSingleton.getInstance().getUsername())) {
            holder.textView.setText(orgName);
            holder.textView.append(orgText);
        }
        holder.textView.append(text);
        holder.textView.append("\n");
        holder.textView.append(time);
    }
}
