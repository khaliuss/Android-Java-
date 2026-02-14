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

import com.example.messengerfirebase.R;
import com.example.messengerfirebase.view_models.UsersViewModel;
import com.google.firebase.auth.FirebaseUser;

public class UsersActivity extends AppCompatActivity {

    private static final String USER_EXTRA = "user";
    private static final String TAG = "ChatActivity";

    private UsersViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        viewModel = new ViewModelProvider(this).get(UsersViewModel.class);

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

    public static Intent newIntent(Context context, FirebaseUser firebaseUser) {
        Intent intent = new Intent(context, UsersActivity.class);
        intent.putExtra(USER_EXTRA,firebaseUser);
        return intent;
    }
}