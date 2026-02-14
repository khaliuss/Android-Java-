package com.example.messengerfirebase.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.messengerfirebase.R;
import com.example.messengerfirebase.view_models.RegisterViewModel;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {


    private RegisterViewModel viewModel;
    private EditText emailEdTx;
    private EditText passwordEdTx;
    private EditText nameEdTx;
    private EditText lastNameEdTx;
    private EditText ageEdTx;
    private Button registerBt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        viewModel = new ViewModelProvider(this).get(RegisterViewModel.class);

        onSetClickListeners();

        onObservers();

    }


    private void onObservers(){

        viewModel.getIsSignedUp().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                Intent intent = UsersActivity.newIntent(RegisterActivity.this,firebaseUser);
                startActivity(intent);
            }
        });

        viewModel.getError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String errorString) {
                Toast.makeText(RegisterActivity.this,errorString,Toast.LENGTH_LONG).show();
            }
        });
    }

    private String getTrimmedText(EditText editText){
        return  editText.getText().toString().trim();
    }

    private void onSetClickListeners(){

        registerBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = getTrimmedText(emailEdTx);
                String password = getTrimmedText(passwordEdTx);
                String name = getTrimmedText(nameEdTx);
                String lastName = getTrimmedText(lastNameEdTx);
                int age = Integer.parseInt(getTrimmedText(ageEdTx));

                if (!email.isBlank() && !password.isBlank()){
                    viewModel.signUp(email,password,name,lastName,age);
                }
            }
        });

    }

    private void initView() {

        emailEdTx = findViewById(R.id.emailRegEdTx);
        passwordEdTx = findViewById(R.id.passwordRegEdTx);
        nameEdTx = findViewById(R.id.nameRegEdTx);
        lastNameEdTx = findViewById(R.id.lastNameRegEdTx);
        ageEdTx = findViewById(R.id.ageRegEdTx);
        registerBt = findViewById(R.id.signUpBt);

    }


    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context,RegisterActivity.class);
        return intent;
    }
}