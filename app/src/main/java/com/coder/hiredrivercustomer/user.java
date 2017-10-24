package com.coder.hiredrivercustomer;

import java.io.Serializable;

/**
 * Created by spinykiller on 10/18/2017.
 */

public class user implements Serializable {
    public String name;
    public String email;
    public String gender,contact;
 transient public String userId;
    public int age;
    public user(){
        name=contact=gender=userId=email="";
        age=0;
    }
}
