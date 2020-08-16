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

public class TypeActivity extends AppCompatActivity  {

    String applicationNumber;
    String applicationNumberFake;
    String type;
    ArrayAdapter<String> dataAdapter;
    Spinner spinner;





    @Override
    protected void onCreate(Bundle savedInstanceState) {

        this.getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type);

        Intent intent = getIntent();
        applicationNumber =  intent.getStringExtra("applicationNumber");
        applicationNumberFake =  intent.getStringExtra("applicationNumberFake");

        final Spinner spinner = (Spinner) findViewById(R.id.spinner);
        // Initializing a String Array
        String[] plants = new String[]{
                "CC",
                "DP",
                "DV",
                "PP",
                "ZK",
                "ZM",
                "ST",
                "TP",
                "CD",
                "VP",
                "MK"
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
                type = selectedItemText;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        configureAplNumButton();
    }


    private void configureAplNumButton() {
        ImageButton nextActivity = findViewById(R.id.imageButton);
        nextActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TypeActivity.this, YearActivity.class);
                i.putExtra("applicationNumber", applicationNumber);
                i.putExtra("applicationNumberFake", applicationNumberFake);
                i.putExtra("type", type);
                startActivity(i);
            }
        });
    }

}


