package com.example.visacheck;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class ApplicationNumberFakeActivity extends AppCompatActivity {

    String applicationNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.getSupportActionBar().hide();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aplication_number_fake);

        Intent intent = getIntent();
        applicationNumber =  intent.getStringExtra("applicationNumber");

        final EditText text = findViewById(R.id.applicationNumberFake_editText);

        text.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= (text.getRight() - text.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        String applicationNumberFake= text.getText().toString();
                        Intent i = new Intent(ApplicationNumberFakeActivity.this, TypeActivity.class);
                        i.putExtra("applicationNumber",applicationNumber);
                        i.putExtra("applicationNumberFake",applicationNumberFake);
                        startActivity(i); // your action here

                        return true;
                    }
                }
                return false;
            }
        });

    }
}
