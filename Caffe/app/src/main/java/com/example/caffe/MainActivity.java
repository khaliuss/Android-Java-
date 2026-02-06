package com.example.caffe;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText nameEd;
    EditText passwordEd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameEd = findViewById(R.id.nameEditText);
        passwordEd = findViewById(R.id.passwordEditText);
        Button singIn = findViewById(R.id.singInButton);

        singIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                singIn();
            }
        });

    }

    private void singIn() {
            String name = nameEd.getText().toString();
            String password = passwordEd.getText().toString();

            if (!name.isBlank() && !password.isBlank()){
                Intent intent = new Intent(this,OrderActivity.class);
                intent.putExtra("name",nameEd.getText().toString());
                intent.putExtra("password",passwordEd.getText().toString());
                startActivity(intent);
            }else {
                Toast.makeText(this,"Your name or password is incorrect",Toast.LENGTH_SHORT).show();
            }

    }
}