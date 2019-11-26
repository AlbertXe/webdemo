package com.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * @author AlbertXe
 * @date 2019-11-20 9:49
 */
public class DateUtils {
    public static boolean isValid(String date, String datePattern) {
        SimpleDateFormat format = new SimpleDateFormat(datePattern);
        format.setLenient(false);
        try {
            format.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
}
