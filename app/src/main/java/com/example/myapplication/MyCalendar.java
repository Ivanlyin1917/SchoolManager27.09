package com.example.myapplication;

import java.util.Calendar;
import java.util.Date;

public class MyCalendar{
  private Calendar myCalendar = Calendar.getInstance();
  private int currentWeek = myCalendar.get(Calendar.WEEK_OF_YEAR);
  private int currentDay = myCalendar.get(Calendar.DAY_OF_WEEK);
  private int currentPosition = currentDay+7;
  private Date currentDate = myCalendar.getTime();

    public MyCalendar() {
    }

    public int getCurrentDay() {
        return currentDay;
    }

    public int getCurrentPosition(){
        return currentPosition;
    }

    public Date getCurrentDate() {
        return currentDate;
    }

    public Date getDate (int position){
        int delta = position-currentPosition;
        myCalendar.add(Calendar.DATE,delta);
        Date newDate = myCalendar.getTime();
        return newDate;
    }

}
