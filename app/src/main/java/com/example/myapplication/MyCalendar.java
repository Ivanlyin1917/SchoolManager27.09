package com.example.myapplication;

import java.util.Calendar;
import java.util.Date;

public class MyCalendar{
  private Calendar myCalendar;
 // private int currentWeek = myCalendar.get(Calendar.WEEK_OF_YEAR);
  private int currentDay;
  private int currentPosition;
  private Date currentDate;

    public MyCalendar() {
        myCalendar = Calendar.getInstance();
        //myCalendar.set(Calendar.DAY_OF_WEEK,Calendar.SUNDAY);

    }

    public int getCurrentDay() {
        currentDay = myCalendar.get(Calendar.DAY_OF_WEEK);
        return currentDay;
    }

    private int getCurrentPosition(){
        currentPosition  = currentDay+6;
        return currentPosition;
    }

    public Date getCurrentDate() {
        currentDate = myCalendar.getTime();
        return currentDate;
    }

    public Date getDate (int position){
        int delta = position-getCurrentPosition();
        myCalendar.add(Calendar.DATE,delta);
        Date newDate = myCalendar.getTime();
        return newDate;
    }


}
