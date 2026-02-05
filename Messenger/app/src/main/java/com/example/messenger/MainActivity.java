package com.example.messenger;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText editText = findViewById(R.id.editTextMessage);
        Button buttonSend = findViewById(R.id.buttonSend);

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message =  editText.getText().toString();
                launchNextScreen(message);
            }
        });
    }

    private void launchNextScreen(String message) {
        Intent intent = new Intent(MainActivity.this, ChatActivity.class);
        intent.putExtra("message", message);
        startActivity(intent);
    }
}