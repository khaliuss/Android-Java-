package com.example.messengerfirebase.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.messengerfirebase.R;
import com.example.messengerfirebase.view_models.LoginViewModel;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel mainViewModel;
    private EditText emailEditText;
    private EditText passwordEditText;
    private Button login;
    private TextView forgotPassword;
    private TextView register;

    private static final String TAG = "MainActivity";

    public static Intent newIntent(Context context) {
        return new Intent(context,LoginActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();

        mainViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        onViewModelObserve();

        onClickListeners();



    }

    private void onClickListeners(){

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString().trim();
                Intent intent = ResetPasswordActivity.newIntent(LoginActivity.this,email);
                startActivity(intent);
            }
        });


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = RegisterActivity.newIntent(LoginActivity.this);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                if (!email.isBlank() && !password.isBlank()){
                    mainViewModel.signInUser(email,password);
                }else {
                    Toast.makeText(LoginActivity.this,"Please put valid information",Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    private void onViewModelObserve(){

        mainViewModel.getIsSignedIn().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if (firebaseUser != null){
                    Intent intent = UsersActivity.newIntent(LoginActivity.this,firebaseUser.getUid());
                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(LoginActivity.this,"Authentication failed.",Toast.LENGTH_LONG).show();
                }
            }
        });


        mainViewModel.getError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String errorString) {
                if (errorString != null){
                    Toast.makeText(LoginActivity.this,errorString,Toast.LENGTH_LONG).show();
                }
            }
        });

    }
    private void init(){
        emailEditText = findViewById(R.id.emailEdTx);
        passwordEditText = findViewById(R.id.passwordEdTX);
        login = findViewById(R.id.loginButton);
        forgotPassword = findViewById(R.id.forgotPasswordTv);
        register = findViewById(R.id.registerTv);
    }






    /*private void signInUser(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                        } else {

                        }
                        return false;
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }


    private void onResetPasswordEmail(String email) {
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){

                        }
                        return false;
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }

    private void signOutUser() {
        mAuth.signOut();
    }*/


}