package com.coder.hiredrivercustomer;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class driverList extends AppCompatActivity {

    driverAdapter dadap;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private DatabaseReference mDatabase;
// ...


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_list);


        mAuth = FirebaseAuth.getInstance();


        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() == null) {
                    startActivity(new Intent(driverList.this, LoginCustomer.class));
                }
            }
        };



        final ListView listView = (ListView) findViewById(R.id.driversList);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        final  ArrayList<user> Users=new ArrayList<user>();
        DatabaseReference df=FirebaseDatabase.getInstance().getReference().child("driver");
        df.addValueEventListener(new ValueEventListener() {
            public static final String TAG = "";
            public user usert=new user();
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot user : dataSnapshot.getChildren()){
                    usert=new user();
                    usert.userId= user.getKey();
                    if(!user.child("booked").getValue(Boolean.class)) {
                        usert.name = user.child("name").getValue(String.class);
                        usert.gender = user.child("gender").getValue(String.class);
                        usert.email = user.child("email").getValue(String.class);
                        usert.contact = (user.child("contact").getValue(Long.class));
                        Users.add(usert);
                    }
                }
                dadap=new driverAdapter(driverList.this,Users);
                listView.setAdapter(dadap);
//on selecting driver take the driver object related to the selection and book confirmation
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                       final user u=Users.get(i);
                        //Toast.makeText(driverList.this,u.getName(),Toast.LENGTH_SHORT).show();
                        new AlertDialog.Builder(driverList.this)
                                .setTitle("Confirmation")
                                .setMessage("Confirm Booking?")
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface dialog, int whichButton) {
                                            //make entry in database and show booking details
                                        booking b=(booking) getIntent().getSerializableExtra("bookingInfo");
                                        b.userId=mAuth.getUid();
                                        b.driverId=u.userId;
                                        mDatabase = FirebaseDatabase.getInstance().getReference();
                                        b.bookId=mDatabase.child("booking").push().getKey();
                                        mDatabase.child("booking").child(b.bookId).setValue(b);
                                        mDatabase.child("driver").child(b.driverId).child("booked").setValue(true);

                                        //Setting dates
                                        b.cost=((b.tDate.getTime()-b.fDate.getTime())/(86400000))*1000+800;

                                        //Intent i=new Intent(driverList.this,confirmedBook.java);
                                        //start booking confirmed activity and show booking details passing booking objecct b
                                        startActivity(new Intent(driverList.this,confirmedBook.class).putExtra("bookingInfo",b));

                                    }})
                                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        startActivity(new Intent(driverList.this,Book.class));
                                    }}).show();
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

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }
}
