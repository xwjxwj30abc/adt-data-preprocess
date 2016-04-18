package zx.soft.adt.utils;

import java.util.Calendar;
import java.util.Date;

public class CalendarUtil {

	public static long belongToOneHour(long start, long end) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date(start * 1000));
		int i = cal.get(Calendar.HOUR_OF_DAY);
		cal.setTime(new Date(end * 1000));
		int j = cal.get(Calendar.HOUR_OF_DAY);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		if (i == j) {
			return 0;
		} else {
			return cal.getTimeInMillis() / 1000;
		}

	}
}
