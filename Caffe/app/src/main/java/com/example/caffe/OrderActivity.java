package com.example.caffe;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class OrderActivity extends AppCompatActivity {

    private TextView welcomingText;
    private TextView complementsText;
    private RadioGroup radioGroup;
    private RadioButton tea;
    private RadioButton coffee;
    private CheckBox sugarChB;
    private CheckBox milkChB;
    private CheckBox lemonChB;
    private Spinner spinner;
    private Button makeOrderBt;

    private String greeting;
    private String name;
    private static final String EXTRA_NAME = "name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        name = getIntent().getStringExtra(EXTRA_NAME);
        greeting = getString(R.string.greeting,name);
        init();


        welcomingText.setText(greeting);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(@NonNull RadioGroup group, int checkedId) {
                if (checkedId == tea.getId()){
                    teaChoose();
                }else {
                    coffeeChoose();
                }
            }
        });

        makeOrderBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeOrder();
            }
        });

    }

    public static Intent newIntent(Context context,String name){
        Intent intent = new Intent(context,OrderActivity.class);
        intent.putExtra(EXTRA_NAME,name);
        return intent;
    }

    private void teaChoose(){
        complementsText.setText(getString(R.string.complements_text,"Tea"));
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(OrderActivity.this,R.array.tea_types, android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);
        lemonChB.setVisibility(View.VISIBLE);
    }

    private void coffeeChoose(){
        complementsText.setText(getString(R.string.complements_text,"Coffee"));
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(OrderActivity.this,R.array.coffee_types, android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);
        lemonChB.setVisibility(View.INVISIBLE);
    }

    private void makeOrder(){
        StringBuilder complements = new StringBuilder();
        String drink;
        final StringBuilder drinkType = new StringBuilder();

        if (sugarChB.isChecked()){
            complements.append("Sugar ");
        }

        if (milkChB.isChecked()){
            complements.append("Milk ");
        }

        if (lemonChB.isChecked() && radioGroup.getCheckedRadioButtonId() != coffee.getId()){
            complements.append("Lemon");
        }

        if (radioGroup.getCheckedRadioButtonId() == tea.getId()){
            drink = "Tea";
        }else {
            drink = "Coffee";
        }

        drinkType.append(spinner.getSelectedItem().toString());



        Intent intent = new Intent(this, OrderDetailActivity.class);
        intent.putExtra("name", name);
        intent.putExtra("complements",complements.toString());
        intent.putExtra("drink",drink);
        intent.putExtra("drinkType",drinkType.toString());
        startActivity(intent);
    }

    private void init() {
        welcomingText = findViewById(R.id.welcomeClientTV);
        complementsText = findViewById(R.id.complementsTv);
        radioGroup = findViewById(R.id.radioGroup);
        tea = findViewById(R.id.teaRB);
        coffee = findViewById(R.id.coffeeRB);
        sugarChB = findViewById(R.id.sugarChB);
        milkChB = findViewById(R.id.milkChB);
        lemonChB = findViewById(R.id.lemonChB);
        spinner = findViewById(R.id.teaSpinner);
        makeOrderBt = findViewById(R.id.makeOrderBt);

        complementsText.setText(getString(R.string.complements_text,"Tea"));
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.tea_types, android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);

    }
}