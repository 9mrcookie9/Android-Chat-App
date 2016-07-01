package com.officialcookiegames.androidjsontests;

/**
 * Created by Kacper on 2016-06-29.
 */


/**
* Adapter = representation one cell json data from server
* */
public class Adapter {
    public String id,name,message,date;

    public Adapter(String Name,String Id,String Date,String Message){
        this.name = Name;
        this.id = Id;
        this.date = Date;
        this.message = Message;
    }
}
