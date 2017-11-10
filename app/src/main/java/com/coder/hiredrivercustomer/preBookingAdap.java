package com.coder.hiredrivercustomer;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by spinykiller on 10/24/2017.
 */

public class preBookingAdap extends ArrayAdapter {
    driver d=new driver();
   // private static final String LOG_TAG = AndroidFlavorAdapter.class.getSimpleName();
    public preBookingAdap(Activity context, ArrayList<booking> userlist) {
        super(context, 0, userlist);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.prev_book_template, parent, false);
        }
        final booking currentUser = (booking) getItem(position);
        TextView nameTextView = (TextView) listItemView.findViewById(R.id.bookingIdValue);
        TextView numberTextView = (TextView) listItemView.findViewById(R.id.driverName);
        FirebaseDatabase.getInstance().getReference().child("driver").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                d=dataSnapshot.child(currentUser.driverId).getValue(driver.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        nameTextView.setText(currentUser.bookId);

        numberTextView.setText(d.name);
        //TextView contact = (TextView) listItemView.findViewById(R.id.contact);
    //    contact.setText(d.contact);
        RatingBar r=(RatingBar)listItemView.findViewById(R.id.ratingBar);
       //change rating for/ user
        r.setRating(new Float(3.5));
        return listItemView;
    }

}
