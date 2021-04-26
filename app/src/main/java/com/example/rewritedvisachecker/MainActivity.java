package com.example.rewritedvisachecker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import model.DataModel;
import model.RecyclerAdapter;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference ref;
    ArrayList<DataModel> dataModels;
    RecyclerAdapter adapter;
    UserHelper user;
    String uniqueId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        boolean idGenerated = App.preferenceManager.getIdGenerator();

        //if application opened for a first time open VisaDetailsActivity
        if (!idGenerated) {
            //ran only on first launch
            startActivity(new Intent(MainActivity.this, VisaDetailsActivity.class));
            App.preferenceManager.setIdU();
        }

        //show MainActivity with list of visa applications
        uniqueId = App.preferenceManager.getIdU();
        user = new UserHelper();
        recyclerView = findViewById(R.id.recycleview);
        ref = FirebaseDatabase.getInstance().getReference();
        dataModels = new ArrayList<>();
        adapter = new RecyclerAdapter(dataModels, getApplicationContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //show visa applications with uniqueID related to device they are created on
        ref.child("users").orderByChild("uniqueID").equalTo(uniqueId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dataModels.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    user = ds.getValue(UserHelper.class);
                    dataModels.add(new DataModel(user.getAppNum(), user.getStatus()));
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void sendMessage(View view) {
        Intent intent = new Intent(MainActivity.this, VisaDetailsActivity.class);
        startActivity(intent);
    }
}