package com.coder.hiredrivercustomer;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class confirmedBook extends AppCompatActivity {


    booking b;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmed_book);

        mAuth = FirebaseAuth.getInstance();


        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() == null) {
                    startActivity(new Intent(confirmedBook.this, LoginCustomer.class));
                }
            }
        };



        mDatabase = FirebaseDatabase.getInstance().getReference();

      b= (booking) (getIntent().getSerializableExtra("bookingInfo"));
       final TextView name,contact,id,fare;
        name=findViewById(R.id.nameconfirm);
        contact=findViewById(R.id.contactconfirm);
        fare=findViewById(R.id.fare);
        mDatabase.child("driver").child(b.driverId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                name.setText(dataSnapshot.child("name").getValue(String.class));
                contact.setText(dataSnapshot.child("contact").getValue(Long.class).toString());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        fare.setText(b.cost+"");

    }
    public void showPayToDriver(View view)
    {
        AlertDialog alertDialog = new AlertDialog.Builder(confirmedBook.this).create();
        alertDialog.setTitle("Pay To Driver");
        alertDialog.setMessage("Pay the amount to Driver "+b.cost);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        startActivity(new Intent(confirmedBook.this ,Book.class));
                    }
                });
        alertDialog.show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

}
