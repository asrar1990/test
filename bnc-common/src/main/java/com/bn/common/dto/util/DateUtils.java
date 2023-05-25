package com.bn.common.dto.util;

import com.bn.common.validator.Validator;
import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Various date utilities to compare dates, to convert dates from a long to a
 * particular format, to convert a date from a given format to a long
 */

@SuppressWarnings("unchecked")
public class DateUtils {

    private static final long TIME_A_DAY = 86400000L;
    private static String datePattern = "MM/dd/yyyy HH:mm:ss z Z";
    public static final String ISO8601_PATTERN = "yyyy-MM-dd'T'HH:mm:ssZ";
    public static final String ISO8601_PATTERN_TZ_NAME = "yyyy-MM-dd'T'HH:mm:ssz";
	public static final String ISO8601_PATTERN_NOZ = "yyyy-MM-dd'T'HH:mm:ss";
	public static final String ISO8601_PATTERN_MS = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    public static final String ISO8601_PATTERN_Z = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    public static final String MSDN_DATETIME_SHORT_DATE_STRING = "M/d/yyyy";
    public static final String AUDIOBOOK_SUBSCRIPTION_DATE_PATTERN = "yyyy-MM-dd hh:mm:ss";
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    private static ThreadLocal<SimpleDateFormat> dateFormat = new ThreadLocal<SimpleDateFormat>() {
        private SimpleDateFormat sdf = new SimpleDateFormat(datePattern);

        public SimpleDateFormat initialValue() {
            return sdf;
        }
    };

    public static Date midnightOfMorningOfGivenDate(Date givenDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(givenDate);

        GregorianCalendar g1 =
                new GregorianCalendar(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE));
        return g1.getTime();
    }

    public static Date addDays(Date inputDate, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(inputDate);
        cal.add(Calendar.DATE, days);

        return cal.getTime();
    }

    public static Date addDaysToMidnightOfGivenDate(Date inputDate, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(inputDate);
        cal.add(Calendar.DATE, days);

        GregorianCalendar g1 =
                new GregorianCalendar(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE));
        return g1.getTime();
    }

    public static java.sql.Date convertToSqlDate(java.util.Date date) {
        if (date == null) {
            return null;
        }
        return new java.sql.Date(date.getTime());
    }

    public static int convertNumDaysToSeconds(int numDays){
        return numDays*86400;
    }

    //used for bn license signing
    public static long getOneHundredYearExpiration(){
        return  System.currentTimeMillis() +1000*60*60*24*365*100;
    }
    // Is the date larger than some given date

    public static boolean isGreaterThan(long itemDate, long greaterThanValue) {
        boolean isGreaterThan = false;
        long maxTime = new Date().getTime();
        maxTime += greaterThanValue;

        if (itemDate > maxTime) {
            isGreaterThan = true;
        }
        return isGreaterThan;
    }

    public static String getCurrentYear() {
        int year = new GregorianCalendar().get(Calendar.YEAR);
        return Integer.toString(year);	// ironically, the user of this method calls: Integer.parseInt(getCurrentYear) [see AccountAssistant]
    }

    // Is the date less than some given date
    // return (itemDate < NOW - greaterThanValue);  or did you mean: (itemDate < NOW + greaterThanValue); ?
    public static boolean isLessThan(long itemDate, long greaterThanValue) {
        boolean isLessThan = false;
        long maxTime = new Date().getTime();
        maxTime -= greaterThanValue;

        if (itemDate < maxTime) {
            isLessThan = true;
        }

        return isLessThan;
    }

    // Format the date according to some pattern

    public static String formatDate(String aDate, String datePattern) {
        String nDate = aDate;
        Date sqlDate = DateUtils.fromStringToDate(aDate, datePattern);

        SimpleDateFormat formatter = new SimpleDateFormat(datePattern);
        nDate = formatter.format(sqlDate);

        return nDate;
    }

    // Convert a java.util.date to a String with a given format

    public static String fromDateToString(Date aDate, String datePattern) {
        String dateStr = null;
        if(aDate != null) {
            SimpleDateFormat formatter = new SimpleDateFormat(datePattern);
            dateStr = formatter.format(aDate);
        }
        return dateStr;
    }

    public static String fromDateToString(Date aDate) {
        return dateFormat.get().format(aDate);
    }

    public static Date getDateFromString(String dateSting) throws ParseException {
        return simpleDateFormat.parse(dateSting);
    }

    // Convert a java.sql.date to a String with a given format

    public static String fromDateToString(java.sql.Date aDate, String datePattern) {
        if (aDate == null)
            return "0";

        SimpleDateFormat formatter = new SimpleDateFormat(datePattern);
        return formatter.format(aDate);
    }

    // Convert a java.sql.date to a String with a given format

    public static String fromDateToString(java.util.Date aDate, String datePattern, TimeZone timezone) {
        if (aDate == null)
            return "0";

        SimpleDateFormat formatter = new SimpleDateFormat(datePattern);
        formatter.setTimeZone(timezone);
        return formatter.format(aDate);
    }

    // Convert a Date in String to a java.sql.Date

    public static java.sql.Date fromStringToDate(String aDate, String datePattern) {
        if (Validator.isBlank(aDate))
            return null;

        if (is2DigitYear(aDate, datePattern)) {
            // determine if year is 1 digit, if so, change it to two digits
            aDate = convert1DigitYear(aDate, datePattern);
            datePattern = trimYYYY(datePattern);
        }
        SimpleDateFormat formatter = new SimpleDateFormat(datePattern);
        ParsePosition pp = new ParsePosition(0);
        if (aDate == null || aDate.length() == 0)
            return new java.sql.Date(new Date().getTime());

        Date date = formatter.parse(aDate, pp);
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());

        return sqlDate;
    }

    // Trim the 'yyyy' year to 'yy' because it is better at determining
    // the century which we are in. This is to find the correct year
    // when someone enters '05 for the year.

    private static String trimYYYY(String oPattern) {
        String nPattern = oPattern;

        nPattern = oPattern.replaceAll("yyyy", "yy");
        return nPattern;
    }

    // Does the String date contain a 2 digit year

    private static boolean is2DigitYear(String aDate, String dPattern) {
        boolean is2Digits = false;
        int year = 0, delIndex = 0;
        String yearStr = null;

        char dLimiter = getDateSeparator(dPattern);
        int index = dPattern.indexOf("yyyy");
        if (index < 5) {
            // year is in the beginning
            delIndex = aDate.indexOf(dLimiter, index);
            yearStr = aDate.substring(index, delIndex);

        }
        else {
            // year is at the end
            delIndex = aDate.lastIndexOf(dLimiter);
            yearStr = aDate.substring(delIndex + 1);
        }

        if (Validator.isInteger(yearStr)) {
            year = new Integer(yearStr).intValue();
            if (year < 1900)
                is2Digits = true;
        }

        return is2Digits;
    }

    // is the end date after the start date
    public static boolean isStartAndEndDateLogical(java.sql.Date startDate, java.sql.Date endDate) {
        // return !startDate.after(endDate);
        long milliSeconds_startDate = startDate.getTime();
        long milliSeconds_endDate = endDate.getTime();
        if (milliSeconds_startDate <= milliSeconds_endDate)
            return true;

        return false;
    }

    // Is the given date between two dates

    public static boolean betweenDates(java.sql.Date startDate,
                                       java.sql.Date endDate, java.sql.Date dateToCheck) {
    	// return !(dateToCheck.before(startDate) || dateToCheck.after(endDate));
        long milliSeconds_startDate = startDate.getTime();
        long milliSeconds_endDate = endDate.getTime();
        long milliSeconds_dateToCheck = dateToCheck.getTime();

        return ((milliSeconds_startDate <= milliSeconds_dateToCheck) && (milliSeconds_endDate >= milliSeconds_dateToCheck));
    }

    // Is the given date between two dates

    public static boolean betweenDates(long startDate, long endDate, long dateToCheck) {
        return ((startDate <= dateToCheck) && (endDate >= dateToCheck));
    }

    // How many days are between to given dates

    public static int numberOfDaysBetween(Date startDate, Date endDate) {
        return DateUtils.getElapsedDays(startDate, endDate);
    }

    // List all the dates between two given dates

    public static List listOfDates(Date startDate, int numberDays, String dateFormat) {
        List dateList = new ArrayList();
        Date currDate = startDate;
        int i = 0;
        while (i < numberDays) {
            dateList.add(currDate);
            currDate = new Date(currDate.getTime() + TIME_A_DAY);
            i++;
        }

        Collections.sort(dateList);
        Collections.reverse(dateList);
        return dateList;
    }

    /**
     * To convert formatted date string to long value.
     *
     * @param aDate
     * @param datePattern
     * @return
     * @throws ParseException
     */
    public static long convertDateToLong(String aDate, String datePattern) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(datePattern);
        Date date = simpleDateFormat.parse(aDate);
        return date.getTime();
    }

    /**
     * To convert formatted date string to long value.
     *
     * @param aDate
     * @param datePattern
     * @return
     * @throws ParseException
     */
    public static long convertESTDateToLong(String aDate, String datePattern) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(datePattern);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("US/Eastern"));
        Date date = simpleDateFormat.parse(aDate);
        return date.getTime();
    }

    public static Date getGMTTime(Date date) {
        if (date == null)
            return null;
        Calendar cal = GregorianCalendar.getInstance();
        cal.setTimeZone(TimeZone.getTimeZone("GMT"));
        cal.setTime(date);
        return cal.getTime();
    }
    
    public static long getUtcTime() {
    	return getUtcTime(System.currentTimeMillis());
    }

	public static long getUtcTime(long now) {
		return (now - getUtcOffset());
	}
	
	public static int getUtcOffset() {
		TimeZone tz = TimeZone.getDefault();
		Calendar cal = Calendar.getInstance();
		int utcOffset = tz.getOffset(1, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE), cal.get(Calendar.DAY_OF_WEEK), cal.get(Calendar.MILLISECOND));
		return utcOffset;
	}
    /**
     * Elapsed days based on current time
     *
     * @param date Date
     * @return int number of days
     */
    public static int getElapsedDays(Date date) {
        return elapsed(date, Calendar.DATE);
    }

    /**
     * Elapsed days based on two Date objects
     *
     * @param d1 Date
     * @param d2 Date
     * @return int number of days
     */
    public static int getElapsedDays(Date d1, Date d2) {
        return elapsed(d1, d2, Calendar.DATE);
    }

    /**
     * Elapsed months based on current time
     *
     * @param date Date
     * @return int number of months
     */
    public static int getElapsedMonths(Date date) {
        return elapsed(date, Calendar.MONTH);
    }

    /**
     * Elapsed months based on two Date objects
     *
     * @param d1 Date
     * @param d2 Date
     * @return int number of months
     */
    public static int getElapsedMonths(Date d1, Date d2) {
        return elapsed(d1, d2, Calendar.MONTH);
    }

    /**
     * Elapsed years based on current time
     *
     * @param date Date
     * @return int number of years
     */
    public static int getElapsedYears(Date date) {
        return elapsed(date, Calendar.YEAR);
    }

    /**
     * Elapsed years based on two Date objects
     *
     * @param d1 Date
     * @param d2 Date
     * @return int number of years
     */
    public static int getElapsedYears(Date d1, Date d2) {
        return elapsed(d1, d2, Calendar.YEAR);
    }

    /**
     * All elaspsed types
     *
     * @param g1   GregorianCalendar
     * @param g2   GregorianCalendar
     * @param type int (Calendar.FIELD_NAME)
     * @return int number of elapsed "type"
     */
    private static int elapsed(GregorianCalendar g1, GregorianCalendar g2, int type) {
        GregorianCalendar gc1, gc2;
        int elapsed = 0;
        // Create copies since we will be clearing/adding
        if (g2.after(g1)) {
            gc2 = (GregorianCalendar) g2.clone();
            gc1 = (GregorianCalendar) g1.clone();
        }
        else {
            gc2 = (GregorianCalendar) g1.clone();
            gc1 = (GregorianCalendar) g2.clone();
        }
        if (type == Calendar.MONTH || type == Calendar.YEAR) {
            gc1.clear(Calendar.DATE);
            gc2.clear(Calendar.DATE);
        }
        if (type == Calendar.YEAR) {
            gc1.clear(Calendar.MONTH);
            gc2.clear(Calendar.MONTH);
        }
        while (gc1.before(gc2)) {
            gc1.add(type, 1);
            elapsed++;
        }
        return elapsed;
    }

    /**
     * All elaspsed types based on date and current Date
     *
     * @param date Date
     * @param type int (Calendar.FIELD_NAME)
     * @return int number of elapsed "type"
     */
    private static int elapsed(Date date, int type) {
        return elapsed(date, new Date(), type);
    }

    /**
     * All elaspsed types
     *
     * @param d1   Date
     * @param d2   Date
     * @param type int (Calendar.FIELD_NAME)
     * @return int number of elapsed "type"
     */
    private static int elapsed(Date d1, Date d2, int type) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(d1);
        GregorianCalendar g1 = new GregorianCalendar(cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH), cal.get(Calendar.DATE));
        cal.setTime(d2);
        GregorianCalendar g2 = new GregorianCalendar(cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH), cal.get(Calendar.DATE));
        return elapsed(g1, g2, type);
    }

    /**
     * Check to see if date is valid according to the format
     *
     * @param inDate     :
     *                   date entered
     * @param dateFormat :
     *                   date format the date has to follow
     * @return : true or false
     */
    public static boolean isDate(String inDate, String dateFormat) {
        try {
            SimpleDateFormat format = new SimpleDateFormat(dateFormat);
            format.setLenient(false);
            Date sample = format.parse(inDate);
            if (sample == null)
                return false;
            else
                return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    /*
     * this returns an expiration date set to next day at the given hour
     */
    public static Date getNextExpiryAtGivenHr(int hr) {
        Calendar expiryCal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        expiryCal.setTime(new Date(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(24-hr)));
        expiryCal.set(Calendar.HOUR_OF_DAY, hr);
        expiryCal.set(Calendar.MINUTE, 00);
        expiryCal.set(Calendar.SECOND, 00);
        expiryCal.set(Calendar.MILLISECOND, 00);
        return expiryCal.getTime();
    }


    private static char getDateSeparator(String dPattern) {
        char separator = ' ';

        int index = dPattern.indexOf("yyyy");
        if (index > 5)
            // year is at the end, separator is prior to the index
            separator = dPattern.substring(5, 6).charAt(0);
        else
            // year is at the beginning, separator is after
            separator = dPattern.substring(4, 5).charAt(0);

        return separator;
    }
    // This and related methods are highly suboptimal and brittle wrt the date formats.
    private static String convert1DigitYear(String oDate, String dPattern) {
        String nDate = "";
        int delIndex, year;
        String yearStr = null;

        int index = dPattern.indexOf("yyyy");
        char delimiter = getDateSeparator(dPattern);
        if (index > 5) {
            // year is at the end, separator is prior to the index
            delIndex = oDate.lastIndexOf(delimiter);
            yearStr = oDate.substring(delIndex + 1);

        }
        else {
            // year is at the beginning, separator is after
            delIndex = oDate.indexOf(delimiter);
            yearStr = oDate.substring(0, delIndex);
        }

        if (Validator.isInteger(yearStr)) {
            year = Integer.parseInt(yearStr);
            if (year < 10 && yearStr.length() < 2) {	// hmm: if yearStr.length() < 2, then year _must_ be < 10, no?
                yearStr = "0" + year;					// = "0" + yearStr;

                if (index > 5) {
                    // year is at the end, separator is prior to the index
                    String part1 = oDate.substring(0, delIndex + 1);
                    nDate = part1 + yearStr;

                }
                else
                    // year is at the beginning, separator is after [substring replacement might be better than regex compilation]
                    nDate = nDate.replaceFirst(Integer.toString(year), yearStr);
            }
        }

        return nDate;
    }

    public static int getDay(String date, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        int day = 0;
        try {
            Date newD = sdf.parse(date);
            Calendar cal = GregorianCalendar.getInstance();
            cal.setTime(newD);
            day = cal.get(Calendar.DAY_OF_MONTH);

        }
        catch (ParseException e) {
        }

        return day;
    }

    public static int getMonth(String date, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        int month = 0;
        try {
            Date newD = sdf.parse(date);
            Calendar cal = GregorianCalendar.getInstance();
            cal.setTime(newD);
            month = (cal.get(Calendar.MONTH) + 1);

        }
        catch (ParseException e) {
        }

        return month;
    }

    public static int getYear(String date, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        int year = 0;
        try {
            Date newD = sdf.parse(date);
            Calendar cal = GregorianCalendar.getInstance();
            cal.setTime(newD);
            year = cal.get(Calendar.YEAR);

        }
        catch (ParseException e) {
        }

        return year;
    }

    private static TimeZone timeZone = TimeZone.getTimeZone("UTC");

    static {
        dateFormat.get().setTimeZone(timeZone);
    }

    public static final String getDateInUTC(Date date) {
        if (date == null) {
            return null;
        }
        return dateFormat.get().format(date);
    }

    public static Date fromStringToDate(String date) {
        return fromStringToDate(date, datePattern);
    }
    
    public static Date oneDayInTheFuture() {
		Calendar cal = GregorianCalendar.getInstance();
        cal.setTime(new Date(System.currentTimeMillis()));
        cal.add(Calendar.DATE, 1);
        return cal.getTime();
	}

    public static boolean isFutureDate(Date utcDate) {
        if (utcDate == null) {
            return false;
        }

        final Date currentDate = new Date(DateUtils.getUtcTime());
        return currentDate.before(utcDate);
    }

    // Is the date less than or same as some given date
    public static boolean isSameOrBefore(long dateToCheck, long date) {
        boolean isLessThanOrSame = false;
        if (dateToCheck <= date) {
            isLessThanOrSame = true;
        }

        return isLessThanOrSame;
    }

    // Is the date greater than or same as some given date
    public static boolean isSameOrAfter(long dateToCheck, long date) {
        boolean isGreaterThanOrSame = false;
        if (dateToCheck >= date) {
            isGreaterThanOrSame = true;
        }

        return isGreaterThanOrSame;
    }

    // Is the date greater than some given date
    public static boolean isAfter(long dateToCheck, long date) {
        boolean isGreaterThan = false;
        if (dateToCheck > date) {
            isGreaterThan = true;
        }

        return isGreaterThan;
    }

    // Is the date less than some given date
    public static boolean isBefore(long dateToCheck, long date) {
        boolean isLessThan = false;
        if (dateToCheck < date) {
            isLessThan = true;
        }

        return isLessThan;
    }

    // Is the date same as some given date
    public static boolean isSame(Long date1, Long date2) {
        if (date1 == null && date2 == null) {
            return true;
        }
        if (date1 == null || date2 == null) {
            return false;
        }
        return date1.equals(date2);
    }

    public static boolean isSame(Date date1, Date date2) {
        if (date1 == null && date2 == null) {
            return true;
        }
        if (date1 == null || date2 == null) {
            return false;
        }
        return date1.getTime() == date2.getTime();
    }

    public static String getDate(String key, String monthInName) {
        try {
            Calendar cal = Calendar.getInstance();
            int month = cal.get(Calendar.MONTH)+1;
            int year = cal.get(Calendar.YEAR);
            String returnValue = null;

            switch (key) {
                case "CURRENT_MONTH":
                    returnValue = String.valueOf(month);
                    break;
                case "CURRENT_YEAR":
                    returnValue = String.valueOf(year);
                    break;
                case "CURRENT_YEAR_MONTH":
                    returnValue = String.valueOf(year)+ String.valueOf(month);
                    break;
                case "CURRRNT_MONTH_STRING":
                    returnValue = cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
                    break;
                case "MONTH_NUMBER":
                    getMonthInNumber(monthInName);
                    break;
                default:
            }
            return returnValue;
        } catch (Exception e) {

        }
        return null;
    }

    public static String getMonthInNumber(String monthInName){
        try {
            if (StringUtils.isNotBlank(monthInName)) {
                return String.valueOf(Months.valueOf(monthInName.toUpperCase()).getValue());
            }
            return null;
        } catch (IllegalArgumentException e) {
            return null;
            // Invalid monthName.
        } catch (Exception e) {
            return null;
        }
    }

    public enum Months{
        JANUARY,
        FEBRUARY,
        MARCH,
        APRIL,
        MAY,
        JUNE,
        JULY,
        AUGUST,
        SEPTEMBER,
        OCTOBER,
        NOVEMBER,
        DECEMBER;

        public int getValue() {
            return ordinal() + 1;
        }
    }
}
