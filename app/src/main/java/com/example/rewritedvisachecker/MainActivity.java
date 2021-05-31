package com.example.rewritedvisachecker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
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

    ArrayList<DataModel> dataModels;
    RecyclerAdapter adapter;
    UserHelper user;
    String uniqueId;
    FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
    DatabaseReference reference = rootNode.getReference("users");

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
        //swipe item left for delete
        ItemTouchHelper.SimpleCallback itemTouchHelper = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                reference.child(String.valueOf(uniqueId + " - " + user.appNum)).removeValue();
                dataModels.remove(viewHolder.getAdapterPosition());
                adapter.notifyDataSetChanged();
            }
        };

        //show MainActivity with list of visa applications
        uniqueId = App.preferenceManager.getIdU();
        user = new UserHelper();
        recyclerView = findViewById(R.id.recycleview);
        dataModels = new ArrayList<>();
        adapter = new RecyclerAdapter(dataModels, getApplicationContext());
        new ItemTouchHelper(itemTouchHelper).attachToRecyclerView(recyclerView);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //show visa applications with uniqueID related to device they are created on
        reference.orderByChild("uniqueID").equalTo(uniqueId).addValueEventListener(new ValueEventListener() {
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