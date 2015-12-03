package utilities;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Utilities {
	
	public static String getCurrentDate() {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(calendar.getTime());
	}
	
	public static String getFutureDateInDays(int days) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar calendar = Calendar.getInstance(); // creates calendar
		calendar.setTime(new Date()); // sets calendar time/date
		calendar.add(Calendar.DATE, days); // adds days
	    return format.format(calendar.getTime()); // returns new date object, days in the future
	}
}
