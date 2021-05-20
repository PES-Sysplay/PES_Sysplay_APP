package com.example.workoutapp.ui.chat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import com.example.workoutapp.Message;
import com.example.workoutapp.MessageAdapter;
import com.example.workoutapp.R;
import com.example.workoutapp.UserSingleton;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    EditText userInput;
    RecyclerView recyclerView;
    List<Message> MessageList;
    MessageAdapter messageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        userInput = findViewById(R.id.userInput);
        recyclerView = findViewById(R.id.conversation);
        MessageList = new ArrayList<>();
        messageAdapter = new MessageAdapter(MessageList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(messageAdapter);
        userInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND){
                    Message message = new Message(userInput.getText().toString(), UserSingleton.getInstance().getId());
                    MessageList.add(message);
                    Message message2 = new Message(userInput.getText().toString(), "");
                    MessageList.add(message2);
                    messageAdapter.notifyDataSetChanged();
                    userInput.clearComposingText();
                    userInput.getText().clear();
                    if(!isVisible()) {
                        recyclerView.smoothScrollToPosition(messageAdapter.getItemCount()-1);
                    }
                }
                return true;
            }
        });
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
}