package com.example.rewritedvisachecker;

import android.os.Bundle;
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
    FirebaseDatabase database;
    DatabaseReference ref;
    ArrayList<String> list;
    ArrayAdapter <String> adapter;
    UserHelper user;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        user = new UserHelper();
        listView = (ListView) findViewById(R.id.listView);
        //database = FirebaseDatabase.getInstance();

        ref = FirebaseDatabase.getInstance().getReference();
           //     = database.getReference("users");
        list = new ArrayList<>();
        adapter = new ArrayAdapter<String>(this, R.layout.user_info,R.id.userInfo, list);
        ref.child("users").orderByChild("appNumFak").equalTo("14").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds: snapshot.getChildren())
                {

                    user = ds.getValue(UserHelper.class);
                    list.add(user.getAppNum());
                }
                listView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
