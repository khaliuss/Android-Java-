package com.example.messengerfirebase.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.messengerfirebase.R;
import com.example.messengerfirebase.adapter.UserAdapter;
import com.example.messengerfirebase.data.User;
import com.example.messengerfirebase.view_models.UsersViewModel;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class UsersActivity extends AppCompatActivity {

    private static final String TAG = "ChatActivity";

    private UsersViewModel viewModel;
    private RecyclerView usersRecycler;
    private UserAdapter userAdapter;

    private String currentUserId;
    private static final String CURRENT_USER_ID = "current_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        intiView();

        currentUserId = getIntent().getStringExtra(CURRENT_USER_ID);


        viewModel = new ViewModelProvider(this).get(UsersViewModel.class);
        viewModelObservers();
        userAdapter.setOnUserClickListener(new UserAdapter.OnUserClickListener() {
            @Override
            public void onClick(User user) {
               Intent intent =  ChatActivity.newIntent(UsersActivity.this,currentUserId,user.getId());
               startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.setOnline(true);
    }

    @Override
    protected void onPause() {
        super.onPause();
        viewModel.setOnline(false);
    }

    private void viewModelObservers(){

        viewModel.getUser().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if (firebaseUser == null){
                    Intent  intent = LoginActivity.newIntent(UsersActivity.this);
                    startActivity(intent);
                    finish();
                }
            }
        });

        viewModel.getUsers().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                userAdapter.setUsers(users);
            }
        });

    }

    private void intiView() {
        usersRecycler = findViewById(R.id.user_recycler);
        userAdapter = new UserAdapter();
        usersRecycler.setAdapter(userAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.chat_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.logout){
            viewModel.signOut();
        }
        return super.onOptionsItemSelected(item);
    }

    public static Intent newIntent(Context context, String currentUserId) {
        Intent intent = new Intent(context, UsersActivity.class);
        intent.putExtra(CURRENT_USER_ID,currentUserId);
        return intent;
    }
}