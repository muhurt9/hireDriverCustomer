package com.coder.hiredrivercustomer;

import java.io.Serializable;

/**
 * Created by spinykiller on 10/18/2017.
 */

public class user implements Serializable {
    public String name;
    public String email;
    public String gender;
    public Long contact;
 transient public String userId;
    public int age;
    public user(){
        name=gender=userId=email="";
        age=0;
        contact=new Long(0);
    }

    public Long getContact() {
        return contact;
    }

    public int getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }

    public String getUserId() {
        return userId;
    }

    public String getGender() {
        return gender;
    }
    public String getName() {
        return name;
    }



}
