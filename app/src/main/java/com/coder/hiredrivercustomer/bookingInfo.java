package com.coder.hiredrivercustomer;

import android.content.DialogInterface;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class bookingInfo extends AppCompatActivity {
    booking b;
    driver d;
    TextView place,from,to,driver,contact,rating,cost;
    DatabaseReference df;
    Button cancle,finish;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        b= (booking) (getIntent().getSerializableExtra("bookingInfo"));
        place=(TextView)findViewById(R.id.place);
        cancle=(Button)findViewById(R.id.button3);
        finish=(Button)findViewById(R.id.button4);
        from=(TextView)findViewById(R.id.from);
        to=(TextView)findViewById(R.id.to);
        contact=(TextView)findViewById(R.id.contact);
        rating=(TextView)findViewById(R.id.rating);
        cost=(TextView)findViewById(R.id.fare);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_info);
        place.setText(b.place);
        from.setText(new SimpleDateFormat("dd-MM-yyyy").format(b.fDate));
        to.setText(new SimpleDateFormat("dd-MM-yyyy").format(b.fDate));
        cost.setText(b.cost+"Rs");
        rating.setText(b.ratings+"");
        df= FirebaseDatabase.getInstance().getReference().child("driver");
        df.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                d=dataSnapshot.child(b.driverId).getValue(driver.class);
                driver.setText(d.name);
               // contact.setText(d.contact);
                if(d.booked){
                    cancle.setEnabled(true);
                    finish.setEnabled(true);
                }
                else{
                    cancle.setEnabled(false);
                    finish.setEnabled(false);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public void  finish(View v)
    {
        df.child(d.id).child("booked").setValue(false);
        new AlertDialog.Builder(bookingInfo.this)
                .setTitle("Confirmation")
                .setMessage("Confirm Finish?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        //make entry in database and show booking details
                        df.child(d.id).child("booked").setValue(false);
                        startActivity(new Intent(bookingInfo.this,Book.class));
                    }})
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }}).show();
                        /*
                        Intent i=new Intent(driverList.this,bookconfirm.class);
                        i.putExtra("user",u);
                        startActivity(i);*/

}



    public void cancle(View v)
    {
        df.child(d.id).child("booked").setValue(false);
        df=FirebaseDatabase.getInstance().getReference().child("booking");
        df.child(b.bookId).removeValue();
        Toast.makeText(getApplicationContext(),"Booking cancled",Toast.LENGTH_SHORT).show();
        startActivity(new Intent(bookingInfo.this,Book.class));
    }
}
