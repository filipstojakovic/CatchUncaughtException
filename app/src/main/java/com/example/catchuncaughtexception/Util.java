package com.example.catchuncaughtexception;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Util
{

    public static final String DATE_FORMAT = "dd/MM/yyyy HH:mm:ss";

    public static String getCurrentDateAndTime()
    {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT);
        return simpleDateFormat.format(Calendar.getInstance().getTime());
    }
}
