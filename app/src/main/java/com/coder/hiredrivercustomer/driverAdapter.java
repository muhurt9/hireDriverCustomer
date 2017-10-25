package com.coder.hiredrivercustomer;

import android.widget.ArrayAdapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by spinykiller on 10/24/2017.
 */

public class driverAdapter extends ArrayAdapter {

   // private static final String LOG_TAG = AndroidFlavorAdapter.class.getSimpleName();
    public driverAdapter(Activity context, ArrayList<user> userlist) {
        super(context, 0, userlist);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.drivertemplate, parent, false);
        }
        user currentUser = (user)getItem(position);
        TextView nameTextView = (TextView) listItemView.findViewById(R.id.driverName);
        nameTextView.setText(currentUser.getName());
        TextView numberTextView = (TextView) listItemView.findViewById(R.id.gender);
        numberTextView.setText(currentUser.getGender());
        TextView contact = (TextView) listItemView.findViewById(R.id.contact);
        contact.setText(currentUser.getContact().toString());
        RatingBar r=(RatingBar)listItemView.findViewById(R.id.ratingBar);
       //change rating for user
        r.setRating(new Float(3.5));
        return listItemView;
    }

}
