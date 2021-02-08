package com.example.rewritedvisachecker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    ListView listView;
    DatabaseReference ref;
    ArrayList<String> list;
    ArrayAdapter<String> adapter;
    UserHelper user;
    Intent intent;
    String uniqueId;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        intent = getIntent();
        uniqueId = intent.getStringExtra("uniqueId");

        user = new UserHelper();
        listView = (ListView) findViewById(R.id.listView);

        ref = FirebaseDatabase.getInstance().getReference();
        list = new ArrayList<>();
        adapter = new ArrayAdapter<String>(this, R.layout.user_info, R.id.userInfo, list);
        ref.child("users").orderByChild("uniqueID").equalTo(uniqueId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {

                    user = ds.getValue(UserHelper.class);
                    list.add(user.getUniqueID());
                    list.add(user.getAppNum());
                }
                listView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void sendMessage(View view) {
        Intent intent = new Intent(ListActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
