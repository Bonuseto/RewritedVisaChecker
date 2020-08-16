package com.example.visacheck;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class YearActivity extends AppCompatActivity {

    String applicationNumber;
    String applicationNumberFake;
    String type;
    String year;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        this.getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_year);

        Intent intent = getIntent();
        applicationNumber =  intent.getStringExtra("applicationNumber");
        applicationNumberFake =  intent.getStringExtra("applicationNumberFake");
        type =  intent.getStringExtra("type");

        ///start spinner

        final Spinner spinner = (Spinner) findViewById(R.id.spinner);
        // Initializing a String Array
        String[] plants = new String[]{
                "YYYY",
                "2020",
                "2019",
                "2018"
        };
        final List<String> plantsList = new ArrayList<>(Arrays.asList(plants));
        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this,android.R.layout.simple_spinner_item,plantsList){
            @Override
            public boolean isEnabled(int position){
                if(position == 0)
                {
                    return false;
                }
                else
                {
                    return true;
                }
            }
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0){
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };

        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner.setAdapter(spinnerArrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);

                if(position > 0){
                    // Notify the selected item text
                    Toast.makeText
                            (getApplicationContext(), "Selected : " + selectedItemText, Toast.LENGTH_SHORT)
                            .show();
                }
                year = selectedItemText;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        ///end spinner

        configureAplNumButton();



    }

    private void configureAplNumButton() {
        ImageButton nextActivity = findViewById(R.id.imageButton);
        nextActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(YearActivity.this, FinalActivity.class);
                i.putExtra("applicationNumber", applicationNumber);
                i.putExtra("applicationNumberFake", applicationNumberFake);
                i.putExtra("type", type);
                i.putExtra("year", year);
                startActivity(i);
            }
        });
    }

}


