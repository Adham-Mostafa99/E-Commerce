package com.modern_tec.ecommerce.core.date;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateInfo {

    private final Calendar calendar;
    private String time;
    private String date;

    public DateInfo() {
        calendar = Calendar.getInstance();
    }

    public String getTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss a");
        return simpleDateFormat.format(calendar.getTime());
    }

    public String getDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM dd,yyy");
        return simpleDateFormat.format(calendar.getTime());
    }
}
