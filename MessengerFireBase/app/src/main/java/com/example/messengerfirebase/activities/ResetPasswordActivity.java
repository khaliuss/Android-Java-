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
import com.example.messengerfirebase.view_models.ResetPasswordViewModel;

public class ResetPasswordActivity extends AppCompatActivity {

    private static final String EMAIL_EXTRA = "email";
    private EditText emailEdTx;
    private Button resetPassBt;

    private ResetPasswordViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);
        initView();

        viewModel = new ViewModelProvider(this).get(ResetPasswordViewModel.class);

        observeViewModel();


        String email = getIntent().getStringExtra(EMAIL_EXTRA);

        if (!email.isBlank()) {
            emailEdTx.setText(email);
        }

        resetPassBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEdTx.getText().toString().trim();
                if (!email.isBlank()) {
                    viewModel.resetPassword(email);
                }
            }
        });



    }

    private void observeViewModel(){
        viewModel.getIsSuccess().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean success) {
                if (success){
                    Toast.makeText(ResetPasswordActivity.this, "We sent you link", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });

        viewModel.getError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String errorString) {
                if (errorString != null){
                    Toast.makeText(ResetPasswordActivity.this, errorString, Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    private void initView() {
        emailEdTx = findViewById(R.id.forgotEmailEdTx);
        resetPassBt = findViewById(R.id.resetPasswordBt);
    }

    public static Intent newIntent(Context context, String email) {
        Intent intent = new Intent(context, ResetPasswordActivity.class);
        intent.putExtra(EMAIL_EXTRA, email);
        return intent;
    }
}