package com.example.workoutapp.ui.chat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannedString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.workoutapp.Activitat;
import com.example.workoutapp.Chat;
import com.example.workoutapp.Message;
import com.example.workoutapp.MessageAdapter;
import com.example.workoutapp.R;
import com.example.workoutapp.UserActivityController;
import com.example.workoutapp.UserSingleton;
import com.example.workoutapp.ui.home.ActivityListAdapter;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    EditText userInput;
    RecyclerView recyclerView;
    List<Message> MessageList;
    MessageAdapter messageAdapter;
    int pos;
    Activitat act;
    Chat chat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Context ctx = this;

        pos = getIntent().getIntExtra("Position recycler",0);
        act = ActivityListAdapter.getInstance(this, new ArrayList<>()).copyInfo().get(pos);

        setTitle(act.getOrganizerName());

        userInput = findViewById(R.id.userInput);
        recyclerView = findViewById(R.id.conversation);
        MessageList = new ArrayList<>();
        messageAdapter = new MessageAdapter(MessageList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(messageAdapter);

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
            public void onResponseChat(ArrayList<Chat> ret) {
                int chat_pos = chatExists(ret);
                if (chat_pos!=-1) {
                    chat = ret.get(chat_pos);
                    uac.getChatMessages(chat.getId(), new UserActivityController.VolleyResponseListener() {
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
                        public void onResponseChat(ArrayList<Chat> ret) {
                            chat = ret.get(0);
                            for (int i = 0; i<chat.getMessageList().size(); i++) {
                                MessageList.add(chat.getMessageList().get(i));
                                updateMessages();
                            }
                        }
                    });
                }

            }
        });



        userInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND){
                    String text = userInput.getText().toString();
                    Message mess = new Message(text, UserSingleton.getInstance().getUsername(), System.currentTimeMillis()/1000);
                    uac.sendMessage(act.getId(), text, new UserActivityController.VolleyResponseListener() {
                        @Override
                        public void onError(String message) {
                            Toast.makeText(v.getContext(), message, Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onResponse(String message) {
                            MessageList.add(mess);
                            updateMessages();
                            userInput.clearComposingText();
                            userInput.getText().clear();
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
                        public void onResponseChat(ArrayList<Chat> ret) {

                        }
                    });
                }
                return true;
            }
        });
    }

    private void updateMessages() {
        messageAdapter.notifyDataSetChanged();

        if(!isVisible()) {
            recyclerView.smoothScrollToPosition(messageAdapter.getItemCount()-1);
        }
    }

    public boolean isVisible(){
        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        int lastVisibleItemPos = linearLayoutManager.findLastCompletelyVisibleItemPosition();
        int itemCount = recyclerView.getAdapter().getItemCount();
        return (lastVisibleItemPos>=itemCount);
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

    private int chatExists(ArrayList<Chat> chatList) {
        for (int i=0; i<chatList.size(); i++) {
            if (chatList.get(i).getActivity_id() == act.getId()) return i;
        }
        return -1;
    }
}