package com.coder.hiredrivercustomer;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;

public class previousBooking extends AppCompatActivity {

    preBookingAdap dadap;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previous_booking);
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() == null) {
                    startActivity(new Intent(previousBooking.this, Book.class));
                }

            }
        };


    }
    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);

        final ListView listView = (ListView) findViewById(R.id.prevBookList);

        final ArrayList<booking> Users=new ArrayList<booking>();
        DatabaseReference df= FirebaseDatabase.getInstance().getReference().child("booking");
        df.addValueEventListener(new ValueEventListener() {
            booking b;
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot booking:dataSnapshot.getChildren()){

                    b=new booking();
                    b.userId=booking.child("userId").getValue(String.class);
                    if(mAuth.getUid().equals(b.userId)){
                        b.bookId=booking.getKey();
                        b.driverId=booking.child("driverId").getValue(String.class);
                        b.fDate=booking.child("fDate").getValue(Date.class);
                        b.cost=booking.child("cost").getValue(double.class);
                        b.place=booking.child("place").getValue(String.class);
                        Users.add(b);
                    }
                }
                dadap=new preBookingAdap(previousBooking.this,Users);
                listView.setAdapter(dadap);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        final booking u=Users.get(i);
                        startActivity(new Intent(previousBooking.this, bookingInfo.class).putExtra("bookingInfo", u));
                    }
                }
        );

    }

}
