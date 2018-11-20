package application.android.com.expencestracker.Model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * This class contains several methods which are used to verify whether the
 * input data is in correct format or return data in certain format.
 *
 */

public class DateUtil {

    /**
     * Verify the certain format of the String. If the format is "yyyy/MM/dd",
     * return an object of Date by parsing the String, throw an parseException and
     * print an exception messege otherwise.
     *
     * @param dateStr The string to be verified
     * @return an object of Date
     */
    public static Date createDate(String dateStr) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        formatter.setLenient(false);
        try {
            // try to parse the String, if it is in correct format, return an object of Date
            // corresponding to the string
            Date date = formatter.parse(dateStr);
            return date;
        } catch (ParseException e) {
            // if the string is not in correct format, print an error messege.
            System.out.println("please Enter the correct format of date");
            return null;
        }
    }


    /**
     * Convert an object of Date to a string with format of "yyyy/MM/dd".
     *
     * @param date an object of Date to be transformed
     * @return a string with format of "yyyy/MM/dd"
     */
    public static String dateToString(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        dateFormat.setLenient(false);
        // to convert Date to String, use format method of SimpleDateFormat class.
        String strDate = dateFormat.format(date);
        return strDate;
    }


}

