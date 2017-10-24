package com.coder.hiredrivercustomer;

import java.util.Date;

/**
 * Created by spinykiller on 10/18/2017.
 */

public class booking {
    public String bookId,userId,driverId,place;
    public int ratings,cost;
    public Date fDate,tDate;
    public booking(){
        bookId=userId=driverId=place="";
        ratings=cost=0;
        fDate=tDate=new Date();
    }
}
