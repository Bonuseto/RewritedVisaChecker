package com.example.rewritedvisachecker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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

import model.CustomAdapter;
import model.DataModel;
import model.RecyclerAdapter;

public class MainActivity extends AppCompatActivity {


    //ListView listView;
   RecyclerView recyclerView;
    DatabaseReference ref;
    ArrayList<DataModel> dataModels;
    //CustomAdapter adapter;
    RecyclerAdapter adapter;
    UserHelper user;
    String uniqueId;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        boolean idHasBeenGenerated = App.preferenceManager.getIdGenerator();

        if (!idHasBeenGenerated) {
            //ran only on first launch
            startActivity(new Intent(MainActivity.this, VisaDetailsActivity.class));
            App.preferenceManager.setIdU();
        }

        uniqueId = App.preferenceManager.getIdU();

        user = new UserHelper();
        //listView = findViewById(R.id.robot);
        recyclerView = findViewById(R.id.robot);

        ref = FirebaseDatabase.getInstance().getReference();
        dataModels = new ArrayList<>();
        //adapter = new CustomAdapter(dataModels, getApplicationContext());
        adapter = new RecyclerAdapter(dataModels, getApplicationContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ref.child("users").orderByChild("uniqueID").equalTo(uniqueId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dataModels.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {


                    user = ds.getValue(UserHelper.class);
                    // list.add(user.getAppNum());
                    dataModels.add(new DataModel(user.getAppNum(), user.getStatus()));
                    // list.add(user.getStatus());
                }
                //listView.setAdapter(adapter);
               // recyclerView.setAdapter(adapter);

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