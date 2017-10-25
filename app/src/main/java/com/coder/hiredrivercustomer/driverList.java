package com.coder.hiredrivercustomer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class driverList extends AppCompatActivity {

    driverAdapter dadap;


    private DatabaseReference mDatabase;
// ...
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_list);

        final ListView listView = (ListView) findViewById(R.id.driversList);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        final  ArrayList<user> Users=new ArrayList<user>();
        DatabaseReference df=FirebaseDatabase.getInstance().getReference().child("user");
        df.addValueEventListener(new ValueEventListener() {
            public static final String TAG = "";
            public user usert=new user();
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot user : dataSnapshot.getChildren()){
                    usert=new user();
                   usert.userId= user.getKey();
                    usert.name=user.child("name").getValue(String.class);
                    usert.gender=user.child("gender").getValue(String.class);
                    usert.email=user.child("email").getValue(String.class);
                    usert.contact=(user.child("contact").getValue(Long.class));
                    Users.add(usert);
                }
                dadap=new driverAdapter(driverList.this,Users);
                listView.setAdapter(dadap);
//on selecting driver take the driver object related to the selection and book confirmation
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        user u=Users.get(i);
                        Toast.makeText(driverList.this,u.getName(),Toast.LENGTH_SHORT).show();
                        /*
                        Intent i=new Intent(driverList.this,bookconfirm.class);
                        i.putExtra("user",u);
                        startActivity(i);*/
                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
      //  ArrayAdapter<String> adapater=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,Users);
       // listView.setAdapter(adapater);
    }
}
