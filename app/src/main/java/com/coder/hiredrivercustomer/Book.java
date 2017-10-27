package com.coder.hiredrivercustomer;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Book extends AppCompatActivity {
    private Button MLogout;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatePicker datePicker;
    private Calendar calendar;
    private TextView dateView;
    private int year, month, day;
    private Date date;
    public java.util.Calendar c;
    SimpleDateFormat sdf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        sdf=new SimpleDateFormat("dd/MM/yyyy");
        mAuth = FirebaseAuth.getInstance();


        MLogout = (Button) findViewById(R.id.LogoutButton);

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() == null) {
                    startActivity(new Intent(Book.this, LoginCustomer.class));
                }
            }
        };
        MLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();


            }
        });
        date=new Date();
        SimpleDateFormat sd=new SimpleDateFormat("dd/MM/yyyy");
        dateView=(TextView)findViewById(R.id.fromDate);
        dateView.setText(sd.format(date));
        dateView=(TextView)findViewById(R.id.toDate);
        dateView.setText(new SimpleDateFormat("dd/MM/yyyy").format(new Date(date.getTime()+24*60*60*1000)));

    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }
    public void FindDrivers(View v) {
        //check validations and if everthing right then show list
        EditText place = (EditText) findViewById(R.id.address);
        TextView fdatetv=((TextView) findViewById(R.id.fromDate));
        TextView tdatetv=((TextView) findViewById(R.id.toDate));
        Date fdate =new Date (fdatetv.getText().toString());
        Date tdate =new Date (tdatetv.getText().toString());

        if (place.getText().toString().length()<=0) {
            place.setError("invalid address");
            place.requestFocus();
        }
        else if(tdate.before(fdate)){
            fdatetv.setError("invalid date Entered");
            fdatetv.requestFocus();
        }
        else {
            booking bookingInfo = new booking();
            bookingInfo.place = (place).getText().toString();
            try {
                bookingInfo.fDate =sdf.parse((fdatetv).getText().toString()) ;//new Date((fdatetv).getText().toString());
                bookingInfo.tDate = sdf.parse((tdatetv).getText().toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            startActivity(new Intent(Book.this, driverList.class).putExtra("bookingInfo", bookingInfo));
        }
    }

    public void setDate(View view) {
        dateView=(TextView) view;
        showDialog(999);
    }


    @Override

    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            c= java.util.Calendar.getInstance();
            return new DatePickerDialog(this,
                    myDateListener,c.get(c.YEAR), c.get(c.MONTH),c.get(c.DAY_OF_MONTH));
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    // arg1 = year
                    // arg2 = month
                    // arg3 = day
                    showDate(arg1, arg2+1, arg3);
                }
            };


    private void showDate(int year, int month, int day) {
        dateView.setText(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));
    }

}
