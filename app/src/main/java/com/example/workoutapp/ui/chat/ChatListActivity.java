package com.example.workoutapp.ui.chat;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.workoutapp.Activitat;
import com.example.workoutapp.Chat;
import com.example.workoutapp.Organizer;
import com.example.workoutapp.R;
import com.example.workoutapp.Review;
import com.example.workoutapp.UserActivityController;
import com.example.workoutapp.ui.home.ActivityListAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ChatListActivity extends AppCompatActivity {

    private List<Chat> chatList = new ArrayList<>();
    private RecyclerView recyclerView;
    private TextView noChat_tv;
    Context ctx = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);

        noChat_tv = findViewById(R.id.nochat_vt);
        noChat_tv.setVisibility(View.INVISIBLE);

        recyclerView = findViewById(R.id.recyclerchatlist);
        recyclerView.setLayoutManager(new LinearLayoutManager(ctx));

        getChatList();
    }

    private void getChatList() {
        UserActivityController uac = new UserActivityController(this);

        uac.getChats(new UserActivityController.VolleyResponseListener() {
            @Override
            public void onError(String message) {
                Toast.makeText(ctx, message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String message) {

            }

            @Override
            public void onResponseFavorites(ArrayList<Activitat> ret) {

            }

            @Override
            public void onResponseFav() {

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
            public void onResponseChat(ArrayList<Chat> ret) {
                chatList = getCurrentChats(ret);
                if (ret.size()==0) noChat_tv.setVisibility(View.VISIBLE);
                else {
                    sortChatList();
                    recyclerView.setAdapter(new ChatListAdapter(chatList, ctx));
                }
            }

            @Override
            public void onResponseReportReview() {

            }
        });
    }

    private void sortChatList() {
        chatList.sort(Comparator.comparing(Chat -> Chat.getLast_message().getDate_timestamp()));
        Collections.reverse(chatList);
    }

    private ArrayList<Chat> getCurrentChats(ArrayList<Chat> clist) {
        List<Activitat> al = ActivityListAdapter.getInstance(ctx, new ArrayList<>()).copyInfo();
        ArrayList<Chat> ret = new ArrayList<>();
        for (int i=0; i<clist.size(); i++) {
            boolean found = false;
            for (int j=0; j<al.size() && !found; j++) {
                if (clist.get(i).getActivity_id() == al.get(j).getId()) {
                    found = true;
                    ret.add(clist.get(i));
                }
            }
        }
        return ret;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}