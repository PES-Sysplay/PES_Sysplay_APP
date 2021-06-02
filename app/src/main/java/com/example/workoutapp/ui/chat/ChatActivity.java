package com.example.workoutapp.ui.chat;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.workoutapp.Activitat;
import com.example.workoutapp.Chat;
import com.example.workoutapp.Message;
import com.example.workoutapp.Organizer;
import com.example.workoutapp.R;
import com.example.workoutapp.Review;
import com.example.workoutapp.UserActivityController;
import com.example.workoutapp.UserSingleton;
import com.example.workoutapp.ui.home.ActivityListAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ChatActivity extends AppCompatActivity {

    EditText userInput;
    RecyclerView recyclerView;
    List<Message> MessageList;
    MessageAdapter messageAdapter;
    int activity_id;
    Activitat act;
    Chat chat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        getWindow().setBackgroundDrawableResource(R.drawable.ivinini8);

        //cuando se abre/cierra el teclado, scrollea la vista para que se vean los mensajes
        setKeyboardListener(visible -> {
            if (MessageList.size()!=0) {
                recyclerView.smoothScrollToPosition(messageAdapter.getItemCount() - 1);
            }
        });

        Context ctx = this;

        activity_id = getIntent().getIntExtra("Id recycler",0);
        //act = ActivityListAdapter.getInstance(this, new ArrayList<>()).copyInfo().get(pos);
        act = get_activity();

        setTitle(Objects.requireNonNull(act).getOrganizerName() + " - " + act.getName());

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
            public void onResponse(String message) {}

            @Override
            public void onResponseFavorites(ArrayList<Activitat> ret) {}

            @Override
            public void onResponseFav() {}

            @Override
            public void onResponseJoinedActivities(ArrayList<Activitat> ret) {}

            @Override
            public void onResponseReviewList(ArrayList<Review> ret) {}

            @Override
            public void onResponseOrganizationList(ArrayList<Organizer> ret) {}

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
                            chat = ret.get(0);
                            for (int i = 0; i<chat.getMessageList().size(); i++) {
                                MessageList.add(chat.getMessageList().get(i));
                                updateMessages();
                            }
                            if(!isVisible()) {
                                recyclerView.smoothScrollToPosition(messageAdapter.getItemCount()-1);
                            }
                        }

                        @Override
                        public void onResponseReportReview() {

                        }
                    });
                }

            }

            @Override
            public void onResponseReportReview() {

            }
        });



        userInput.setOnEditorActionListener((v, actionId, event) -> {
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
                        if(!isVisible()) {
                            recyclerView.smoothScrollToPosition(messageAdapter.getItemCount()-1);
                        }
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

                    }

                    @Override
                    public void onResponseReportReview() {

                    }
                });
            }
            return true;
        });
    }

    private Activitat get_activity() {
        List<Activitat> al = ActivityListAdapter.getInstance(this, new ArrayList<>()).copyInfo();
        for (int i=0; i<al.size(); i++) {
            if (al.get(i).getId()==activity_id) return al.get(i);
        }
        return null;
    }

    private void updateMessages() {
        messageAdapter.notifyDataSetChanged();
    }

    public boolean isVisible(){
        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        assert linearLayoutManager != null;
        int lastVisibleItemPos = linearLayoutManager.findLastCompletelyVisibleItemPosition();
        int itemCount = Objects.requireNonNull(recyclerView.getAdapter()).getItemCount();
        return (lastVisibleItemPos>=itemCount);
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

    private int chatExists(ArrayList<Chat> chatList) {
        for (int i=0; i<chatList.size(); i++) {
            if (chatList.get(i).getActivity_id() == act.getId()) return i;
        }
        return -1;
    }

    public interface OnKeyboardVisibilityListener {


        void onVisibilityChanged(boolean visible);
    }

    public final void setKeyboardListener(final OnKeyboardVisibilityListener listener) {
        final View activityRootView = ((ViewGroup) findViewById(android.R.id.content)).getChildAt(0);

        activityRootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            private boolean wasOpened;

            private final Rect r = new Rect();

            @Override
            public void onGlobalLayout() {
                // Convert the dp to pixels.
                int defaultKeyboardDP = 100;
                int estimatedKeyboardDP = defaultKeyboardDP + 48;
                int estimatedKeyboardHeight = (int) TypedValue
                        .applyDimension(TypedValue.COMPLEX_UNIT_DIP, estimatedKeyboardDP, activityRootView.getResources().getDisplayMetrics());

                // Conclude whether the keyboard is shown or not.
                activityRootView.getWindowVisibleDisplayFrame(r);
                int heightDiff = activityRootView.getRootView().getHeight() - (r.bottom - r.top);
                boolean isShown = heightDiff >= estimatedKeyboardHeight;

                if (isShown == wasOpened) {
                    return;
                }

                wasOpened = isShown;
                listener.onVisibilityChanged(isShown);
            }
        });
    }
}