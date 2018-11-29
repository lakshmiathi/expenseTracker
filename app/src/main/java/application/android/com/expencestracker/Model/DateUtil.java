package application.android.com.expencestracker.Model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


/**
 * This class contains several methods which are used to verify whether the
 * input data is in correct format or return data in certain format.
 *
 */

public class DateUtil {
   // static SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
   static SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy", Locale.US);

    /**
     * Verify the certain format of the String. If the format is "MM/dd/YYYY",
     * return an object of Date by parsing the String, throw an parseException and
     * print an exception messege otherwise.
     *
     * @param dateStr The string to be verified
     * @return an object of Date
     * @throws IllegalArgumentException if the input string isn't in a correct format.
     */
    public static Date createDate(String dateStr) {
        formatter.setLenient(false);
        try {
            // try to parse the String, if it is in correct format, return an object of Date
            // corresponding to the string
            Date date = formatter.parse(dateStr);
            return date;
        } catch (ParseException e) {
            // if the string is not in correct format, throw an exception.
            throw new IllegalArgumentException("Date format is incorrect!");
        }
    }


    /**
     * Convert an object of Date to a string with format of "MM/dd/yyyy".
     *
     * @param date an object of Date to be transformed
     * @return a string with format of "MM/dd/yyyy"
     */
    public static String dateToString(Date date) {
        formatter.setLenient(false);
        // to convert Date to String, use format method of SimpleDateFormat class.
        String strDate = formatter.format(date);
        return strDate;
    }

}

