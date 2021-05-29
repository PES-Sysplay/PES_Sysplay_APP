package com.example.workoutapp.ui.chat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.workoutapp.Activitat;
import com.example.workoutapp.Chat;
import com.example.workoutapp.Organizer;
import com.example.workoutapp.R;
import com.example.workoutapp.Review;
import com.example.workoutapp.UserActivityController;

import java.util.ArrayList;
import java.util.List;

public class ChatListActivity extends AppCompatActivity {

    private List<Chat> chatList = new ArrayList<>();
    private RecyclerView recyclerView;
    Context ctx = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);

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
            public void onResponseJoinedActivites(ArrayList<Activitat> ret) {

            }

            @Override
            public void onResponseReviewList(ArrayList<Review> ret) {

            }

            @Override
            public void onResponseOrganizationList(ArrayList<Organizer> ret) {

            }

            @Override
            public void onResponseChat(ArrayList<Chat> ret) {
                chatList = ret;
                recyclerView.setAdapter(new ChatListAdapter(chatList, ctx));
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id) {
            case android.R.id.home:
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