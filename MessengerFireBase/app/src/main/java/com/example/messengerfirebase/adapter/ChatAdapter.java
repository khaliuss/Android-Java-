package com.example.messengerfirebase.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.messengerfirebase.R;
import com.example.messengerfirebase.data.Message;

import java.util.ArrayList;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatHolder> {

    private static final int MY_MESSAGES = 100;
    private static final int OTHER_MESSAGES = 101;
    private List<Message> chatList = new ArrayList<>();
    private final String currentUserId;

    public void setChatList(List<Message> chatList) {
        this.chatList = chatList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ChatHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int  layoutResId;
        if (viewType == MY_MESSAGES){
            layoutResId = R.layout.my_message_layout;
        }else {
            layoutResId = R.layout.other_message_layout;
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(layoutResId, parent, false);
        return new ChatHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatHolder holder, int position) {
        Message message = chatList.get(position);
        holder.textView.setText(message.getMessage());
    }

    public ChatAdapter(String currentUserId) {
        this.currentUserId = currentUserId;
    }

    @Override
    public int getItemViewType(int position) {
        Message message = chatList.get(position);
        if (message.getSenderId().equals(currentUserId)){
            return MY_MESSAGES;
        }else {
            return OTHER_MESSAGES;
        }
    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    static class ChatHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public ChatHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.messageText);
        }
    }
}
