package com.coder.hiredrivercustomer;

import android.app.Application;

import com.firebase.client.Firebase;

/**
 * Created by Karan on 11-10-2017.
 */

public class hireDriverCustomer extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
    }
}
