package com.coder.hiredrivercustomer;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by spinykiller on 10/18/2017.
 */

public class booking implements Serializable{
    public String bookId,userId,driverId,place;
    public double ratings,cost;
    public Date fDate,tDate;
    public booking(){
        bookId=userId=driverId=place="";
        ratings=cost=0;
        fDate=tDate=new Date();
    }
}
