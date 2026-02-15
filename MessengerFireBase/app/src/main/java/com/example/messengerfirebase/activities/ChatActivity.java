package com.example.messengerfirebase.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.messengerfirebase.R;
import com.example.messengerfirebase.adapter.ChatAdapter;
import com.example.messengerfirebase.data.Message;
import com.example.messengerfirebase.data.User;
import com.example.messengerfirebase.view_models.ChatViewModel;
import com.example.messengerfirebase.view_models.ChatViewModelFactory;

import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private static final String CURRENT_USER_ID = "current_id";
    private static final String OTHER_USER_ID = "other_id";

    private TextView nameTextView;
    private View netStatus;
    private EditText messageEdTx;
    private ImageView imgSendButton;
    private RecyclerView messageRecycler;
    private ChatAdapter chatAdapter;

    private ChatViewModel chatViewModel;
    private ChatViewModelFactory chatViewModelFactory;

    private String currentUserId;
    private String otherUserId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        init();

        viewModelObservers();

        messageRecycler.setAdapter(chatAdapter);

        imgSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = messageEdTx.getText().toString().trim();
                if (!text.isBlank()) {
                    Message message = new Message(
                            currentUserId,
                            otherUserId,
                            text
                    );
                    chatViewModel.sendMessage(message);
                }

            }
        });

    }

    private void viewModelObservers() {

        chatViewModel.getMessages().observe(this, new Observer<List<Message>>() {
            @Override
            public void onChanged(List<Message> messages) {
                chatAdapter.setChatList(messages);
            }
        });

        chatViewModel.getError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String errorMessage) {
                Toast.makeText(ChatActivity.this, errorMessage, Toast.LENGTH_LONG).show();
            }
        });

        chatViewModel.getMessageSent().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isSent) {
                if (isSent){
                    messageEdTx.setText("");
                }
            }
        });

        chatViewModel.getOtherUser().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                String info = String.format("%s %s",user.getName(),user.getLastName() );
                nameTextView.setText(info);
                int resId;

                if (user.isOnline()){
                    resId = R.drawable.online_circle;
                }else {
                    resId = R.drawable.offline_circle;
                }
                Drawable background = ContextCompat.getDrawable(ChatActivity.this,resId);
                netStatus.setBackground(background);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        chatViewModel.setOnline(true);
    }

    @Override
    protected void onPause() {
        super.onPause();
        chatViewModel.setOnline(false);
    }

    private void init() {
        nameTextView = findViewById(R.id.nameTextView);
        netStatus = findViewById(R.id.netStatus);
        messageEdTx = findViewById(R.id.editTextMessage);
        imgSendButton = findViewById(R.id.sendImgButton);
        messageRecycler = findViewById(R.id.messageRecycler);
        currentUserId = getIntent().getStringExtra(CURRENT_USER_ID);
        otherUserId = getIntent().getStringExtra(OTHER_USER_ID);
        chatViewModelFactory = new ChatViewModelFactory(currentUserId, otherUserId);
        chatViewModel = new ViewModelProvider(this, chatViewModelFactory).get(ChatViewModel.class);
        chatAdapter = new ChatAdapter(currentUserId);
    }

    public static Intent newIntent(Context context, String currentUserId, String otherUserId) {
        Intent intent = new Intent(context, ChatActivity.class);
        intent.putExtra(CURRENT_USER_ID, currentUserId);
        intent.putExtra(OTHER_USER_ID, otherUserId);
        return intent;
    }
}