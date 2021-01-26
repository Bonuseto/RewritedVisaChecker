package com.example.rewritedvisachecker;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    TextInputLayout et_appNum, et_appNumFak, et_type, et_year;
    Button resultBtn;

    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_appNum = findViewById(R.id.applicationNumber);
        et_appNumFak = findViewById(R.id.applicationNumberFake);
        et_type = findViewById(R.id.type);
        et_year = findViewById(R.id.year);

        resultBtn = findViewById(R.id.resultButton);
        resultBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference("users");

                String appNum = et_appNum.getEditText().getText().toString();
                String appNumFak = et_appNumFak.getEditText().getText().toString();
                String type = et_type.getEditText().getText().toString();
                String year = et_year.getEditText().getText().toString();
                String status = "Null";

                UserHelper user = new UserHelper(appNum, appNumFak, type, year, status);

                reference.child(appNum).setValue(user);
                Intent myIntent = new Intent(MainActivity.this, ListActivity.class);
                myIntent.putExtra("appNum", appNum);
                MainActivity.this.startActivity(myIntent);
                
            }
        });
    }
}