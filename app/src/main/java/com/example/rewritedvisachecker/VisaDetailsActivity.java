package com.example.rewritedvisachecker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class VisaDetailsActivity extends AppCompatActivity {

    TextInputLayout et_appNum, et_appNumFak;
    AutoCompleteTextView et_type;
    AutoCompleteTextView et_year;
    Button resultBtn;

    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visa_details);

        et_appNum = findViewById(R.id.applicationNumber);
        et_appNumFak = findViewById(R.id.applicationNumberFake);
        et_type = findViewById(R.id.type);
        et_year = findViewById(R.id.year);

        resultBtn = findViewById(R.id.resultButton);

        String[] arrayType = getResources().getStringArray(R.array.application_type);

        ArrayAdapter<String> adapterType = new ArrayAdapter<>(this, R.layout.list_item, arrayType);
        et_type.setAdapter(adapterType);

        String[] arrayYear = getResources().getStringArray(R.array.application_year);

        ArrayAdapter<String> adapterYear = new ArrayAdapter<>(this, R.layout.list_item, arrayYear);
        et_year.setAdapter(adapterYear);


        resultBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference("users");

                String uniqueId = App.preferenceManager.getIdU();

                String appNum = getText(et_appNum);
                String appNumFak = getText(et_appNumFak);
                String type = et_type.getText().toString();
                String year = et_year.getText().toString();
                String status = "NotSet";
                String firstTimeAdded = "true";
                String finalStatus = "false";

                appNum = appNum.replaceFirst("^0+(?!$)", "");

                UserHelper user = new UserHelper(appNum, appNumFak, type, year, status, uniqueId, firstTimeAdded, finalStatus);

                reference.child(String.valueOf(uniqueId + " - " + appNum)).setValue(user);
                Intent myIntent = new Intent(VisaDetailsActivity.this, MainActivity.class);
                myIntent.putExtra("uniqueId", uniqueId);
                VisaDetailsActivity.this.startActivity(myIntent);
            }
        });
    }

    private String getText(TextInputLayout layout) {
        return layout.getEditText().getText().toString();
    }


}