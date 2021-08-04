package ru.a_party.hw6_notes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MyDateUtil {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy", Locale.getDefault());
    private static final Calendar calendar = Calendar.getInstance();

    public static String dateToString(Date date){
        return dateFormat.format(date);
    }

    public static Date stringToDate(String date) throws ParseException {

        calendar.setTime(dateFormat.parse(date));
        return calendar.getTime();

    }
}
