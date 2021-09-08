package thang.com.wref.MainScreen.Weather;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class TimeCaculater {
    private static final int MINS_PER_DAY = 60 * 24;
    private static final long MS_PER_DAY = 1000 * 60 * MINS_PER_DAY;

    private static final int SEC = 1000;
    private static final int MIN = SEC * 60;
    private static final int HOUR = MIN * 60;
    private static final int DAY = HOUR * 24;
    private static final long WEEK = DAY * 7;
    private static final long YEAR = WEEK * 52;

    private static final long[] buckets = { YEAR, WEEK, DAY, HOUR, MIN, SEC };
    private static final String[] bucketNames = { "year", "week", "day",
            "hour", "minute", "second" };

    private static GregorianCalendar statFmtCal = new GregorianCalendar();

    private static final String ts24Pat = "H:mm:ss yy-MM-dd";


    // convert milliseconds into the day of the week string
    public static String dayStringFormat(long msecs) {
        GregorianCalendar cal = new GregorianCalendar();

        cal.setTime(new Date(msecs*1000));

        int dow = cal.get(Calendar.DAY_OF_WEEK);

        switch (dow) {
            case Calendar.MONDAY:
                return "Thứ 2";
            case Calendar.TUESDAY:
                return "Thứ 3";
            case Calendar.WEDNESDAY:
                return "Thứ 4";
            case Calendar.THURSDAY:
                return "Thứ 5";
            case Calendar.FRIDAY:
                return "Thứ 6";
            case Calendar.SATURDAY:
                return "Thứ 7";
            case Calendar.SUNDAY:
                return "CN";
        }

        return "Unknown";
    }
    public String day(long msecs){
        return new SimpleDateFormat("dd/MM/yyyy").format(msecs);
    }
}
