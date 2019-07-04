package com.wave;

import java.util.Calendar;
import java.util.Date;

public class DisplayUsageDataUnit {
    int index;
    String stringTimePoint;
    Calendar calendar ;
    long timePoint;
    int voltage;

    DisplayUsageDataUnit()
    {
        index = 0;
        stringTimePoint = "";
        calendar = Calendar.getInstance();
        timePoint = 0;
        voltage = 0;
    }

    public void timePointToCalendar() {
        Date date = new Date();
        date.setTime(timePoint * 1000);
        calendar.setTime(date);
    }
}
