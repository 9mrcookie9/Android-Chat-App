package com.officialcookiegames.androidjsontests;

/**
 * Created by Kacper on 2016-06-29.
 */
public class SortAdapter {
    public static void Descending( Adapter[] Adapter) {
        int j;
        boolean flag = true;   // set flag to true to begin first pass
        Adapter temp;   //holding variable

        while (flag){
            flag= false;    //set flag to false awaiting a possible swap
            for( j=0;  j < Adapter.length -1;  j++ ){
                if ( Integer.parseInt(Adapter[j].id) > Integer.parseInt(Adapter[j+1].id)){  // change to > for ascending sort
                    temp = Adapter[ j ];                //swap elements
                    Adapter[ j ] = Adapter[ j+1 ];
                    Adapter[ j+1 ] = temp;
                    flag = true;              //shows a swap occurred
                }
            }
        }
    }
    public static void Ascending( Adapter[] Adapter) {
        int j;
        boolean flag = true;   // set flag to true to begin first pass
        Adapter temp;   //holding variable

        while (flag){
            flag= false;    //set flag to false awaiting a possible swap
            for( j=0;  j < Adapter.length -1;  j++ ){
                if ( Integer.parseInt(Adapter[j].id) > Integer.parseInt(Adapter[j+1].id)){  // change to > for ascending sort
                    temp = Adapter[ j ];                //swap elements
                    Adapter[ j ] = Adapter[ j+1 ];
                    Adapter[ j+1 ] = temp;
                    flag = true;              //shows a swap occurred
                }
            }
        }
    }
}
