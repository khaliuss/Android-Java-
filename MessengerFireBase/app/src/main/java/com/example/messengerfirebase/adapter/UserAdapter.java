package com.example.messengerfirebase.adapter;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.messengerfirebase.R;
import com.example.messengerfirebase.data.User;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserHolder> {


    private List<User> users = new ArrayList<>();
    private OnUserClickListener onUserClickListener;

    @NonNull
    @Override
    public UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.user_items,parent,
                false
        );
        return new UserHolder(view);
    }

    public void setUsers(List<User> users) {
        this.users = users;
        notifyDataSetChanged();
    }

    public void setOnUserClickListener(OnUserClickListener onUserClickListener) {
        this.onUserClickListener = onUserClickListener;
    }

    @Override
    public void onBindViewHolder(@NonNull UserHolder holder, int position) {
        User user = users.get(position);
        String info = String.format("%s, %s, %s",user.getName(),user.getLastName(),user.getAge());
        holder.nameTv.setText(info);

        int resId;

        if (user.isOnline()){
            resId = R.drawable.online_circle;
        }else {
            resId = R.drawable.offline_circle;
        }
        Drawable background = ContextCompat.getDrawable(holder.itemView.getContext(),resId);
        holder.statusView.setBackground(background);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onUserClickListener.onClick(user);
            }
        });

    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public static class UserHolder extends RecyclerView.ViewHolder {
        TextView nameTv;
        View statusView;
        public UserHolder(@NonNull View itemView) {
            super(itemView);
            nameTv = itemView.findViewById(R.id.userName);
            statusView = itemView.findViewById(R.id.statusView);

        }
    }

    public interface OnUserClickListener{
        void onClick(User user);
    }
}
