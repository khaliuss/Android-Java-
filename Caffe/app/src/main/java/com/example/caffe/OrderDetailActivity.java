package com.example.caffe;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class OrderDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        TextView textView = findViewById(R.id.detailText);

        String name = getIntent().getStringExtra("name");
        String complements = getIntent().getStringExtra("complements");
        String drink = getIntent().getStringExtra("drink");
        String drinkType = getIntent().getStringExtra("drinkType");


        textView.setText("Name: "+name+"\nDrink: "+drink+"\nComplements: "+complements+"\nDrink Type: "+drinkType);


    }
}