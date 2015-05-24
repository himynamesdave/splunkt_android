package com.splunkstart.android.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import android.util.Log;

public class DateUtil {
	// returns date in format yyyy-mm-dd
	public static String toISODate(Date date) {
		if (date == null)
			return "null";
		return String.valueOf(date.getYear() + 1900)
				+ "-"
				+ StringUtil.getPaddedString(
						String.valueOf(date.getMonth() + 1), 2, "0")
				+ "-"
				+ StringUtil.getPaddedString(String.valueOf(date.getDate()), 2,
						"0");
	}

	// returns date in format mm-dd-yyyy
	public static String toUSDate(Date date) {
		if (date == null)
			return "null";
		return StringUtil.getPaddedString(String.valueOf(date.getMonth() + 1),
				2, "0")
				+ "-"
				+ StringUtil.getPaddedString(String.valueOf(date.getDate()), 2,
						"0") + "-" + String.valueOf(date.getYear() + 1900);
	}

	public static void clearHourMinutesSeconds(Date date) {
		if (date == null)
			return;
		date.setHours(0);
		date.setMinutes(0);
		date.setSeconds(0);
	}

	public static List<String> getDays() {
		List<String> days = new ArrayList<String>();
		for (int i = 1; i < 32; i++) {
			String month = StringUtil
					.getPaddedString(String.valueOf(i), 2, "0");
			days.add(month);
		}
		return days;
	}

	// returns 2008-02-19-12:14:45-187 (yyyy-mm-dd-hh:mi:ss-milisecond) where hh
	// is hour in 24
	public static String getNow() {
		Calendar calendar = Calendar.getInstance();
		String year = String.valueOf(calendar.get(Calendar.YEAR));
		String month = StringUtil.getPaddedString(
				String.valueOf(calendar.get(Calendar.MONTH) + 1), 2, "0");
		String day = StringUtil.getPaddedString(
				String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)), 2, "0");
		String hour = StringUtil.getPaddedString(
				String.valueOf(calendar.get(Calendar.HOUR_OF_DAY)), 2, "0");
		String minute = StringUtil.getPaddedString(
				String.valueOf(calendar.get(Calendar.MINUTE)), 2, "0");
		String second = StringUtil.getPaddedString(
				String.valueOf(calendar.get(Calendar.SECOND)), 2, "0");
		String milisecond = String.valueOf(calendar.get(Calendar.MILLISECOND));
		String now = year + "-" + month + "-" + day + "-" + hour + ":" + minute
				+ ":" + second + "-" + milisecond;
		return now;
	}

	public static String getTime() {
		Calendar calendar = Calendar.getInstance();
		String year = String.valueOf(calendar.get(Calendar.YEAR));
		String month = StringUtil.getPaddedString(
				String.valueOf(calendar.get(Calendar.MONTH) + 1), 2, "0");
		String day = StringUtil.getPaddedString(
				String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)), 2, "0");
		String hour = StringUtil.getPaddedString(
				String.valueOf(calendar.get(Calendar.HOUR_OF_DAY)), 2, "0");
		String minute = StringUtil.getPaddedString(
				String.valueOf(calendar.get(Calendar.MINUTE)), 2, "0");
		String second = StringUtil.getPaddedString(
				String.valueOf(calendar.get(Calendar.SECOND)), 2, "0");
		String milisecond = String.valueOf(calendar.get(Calendar.MILLISECOND));
		String now = year + "-" + month + "-" + day + "-" + hour + ":" + minute
				+ ":" + second;
		return now;
	}

	public static String getToday() {
		Calendar calendar = Calendar.getInstance();
		String year = String.valueOf(calendar.get(Calendar.YEAR));
		String month = StringUtil.getPaddedString(
				String.valueOf(calendar.get(Calendar.MONTH) + 1), 2, "0");
		String day = StringUtil.getPaddedString(
				String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)), 2, "0");
		String today = year + "-" + month + "-" + day;
		return today;
	}

	public static String getTime(Timestamp timestamp, boolean showTime) {
		String year = String.valueOf(timestamp.getYear() + 1900);
		String month = StringUtil.getPaddedString(
				String.valueOf(timestamp.getMonth() + 1), 2, "0");
		String day = StringUtil.getPaddedString(
				String.valueOf(timestamp.getDate()), 2, "0");
		String now = year + "-" + month + "-" + day;
		if (showTime) {
			String hour = StringUtil.getPaddedString(
					String.valueOf(timestamp.getHours()), 2, "0");
			String minute = StringUtil.getPaddedString(
					String.valueOf(timestamp.getMinutes()), 2, "0");
			String second = StringUtil.getPaddedString(
					String.valueOf(timestamp.getSeconds()), 2, "0");
			now += " " + hour + ":" + minute + ":" + second;
		}
		return now;
	}

	public static Calendar getdDefaultCalenderByHourAndMinute(int hour,
			int minute) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(1971, 1, 16, hour, minute);

		return calendar;
	}

	public static String GetFormatedDateTimeString(Date formatedDate) {

		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");

		String formatedDateString = sdf.format(formatedDate);
		return formatedDateString;

	}

	public static Date GetDateTimeFromDateString(String formatedDate) {
		Date dateObj = null;
		try {
			// java.text.SimpleDateFormat sdf =
			// new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			SimpleDateFormat curFormater = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			dateObj = curFormater.parse(formatedDate);

			// String formatedDateString = sdf.format(dateObj);
			// return formatedDateString;

			// return dateObj;
		} catch (Exception ex) {

		}

		return dateObj;
	}

	public static String GetDateTimeFromDateString(Calendar formatedDate) {
		String dateString = "";

		try {
			Date date = formatedDate.getTime();
			SimpleDateFormat curFormater = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			dateString = curFormater.format(date);

			dateString = date.getYear() + "-" + date.getMonth() + "-"
					+ date.getDate() + " " + date.getHours() + ":00:00";

		} catch (Exception ex) {

		}

		return dateString;
	}

	// String date1 = "31/12/2011";
	// SimpleDateFormat form = new SimpleDateFormat("dd/MM/yyyy");
	// java.util.Date d1 = null;
	// Calendar tdy1;
	//
	// try {
	// d1 = form.parse(date1);

	public static String GetFormatedDateString(Date formatedDate) {

		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
				"yyyy-MM-dd");

		String formatedDateString = sdf.format(formatedDate);
		return formatedDateString;

	}

	public Date GetFormatedTime(int hour, int minute) {
		Date date = new Date(1971, 1, 1, hour, minute);

		return date;
	}

	public Date GetDefaultDate() {
		Date date = new Date(1971, 1, 1);
		return date;
	}

	public static String GetFormatedtDateWithDateYearMonthTime(Date date) {
		String dateString = "";
		try {
			SimpleDateFormat ft = new SimpleDateFormat(
					"E yyyy.MM.dd 'at' hh:mm:ss a ");

			dateString = ft.format(date);
		} catch (Exception ex) {
			Log.w("Exception", ex.getMessage());
		}

		return dateString;

	}

	public static String GetFormatedTimeString(Date formatedDate) {

		try {
			java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
					"hh:mm a");

			String formatedDateString = sdf.format(formatedDate);
			return formatedDateString;
		} catch (Exception ex) {
		}

		return "";

	}

	public static Date GetDateFromDateString(String formatedDate) {
		Date dateObj = null;
		try {
			// java.text.SimpleDateFormat sdf =
			// new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			SimpleDateFormat curFormater = new SimpleDateFormat("yyyy-MM-dd");
			dateObj = curFormater.parse(formatedDate);

			// String formatedDateString = sdf.format(dateObj);
			// return formatedDateString;

			// return dateObj;
		} catch (Exception ex) {
			Log.w("Exception", ex.getMessage());
		}

		return dateObj;
	}

	public static Date getDatebyAddingTime(long seconds) {
		Date date = new Date();

		// date.setTime(date.getTime())

		// long time = date.getTime();
		// time = time + seconds;
		// int m = date.getMonth() ;
		//
		// if (date.getMonth() == 2) {
		// date.setMonth(11);
		// date.setYear(date.getYear() - 1);
		// } else if (date.getMonth() <= 1) {
		// date.setMonth(10);
		// date.setYear(date.getYear() - 1);
		// } else {
		// date.setMonth(date.getMonth() - 2);
		// }

		date.setYear(date.getYear() - 1);

		return date;
	}

	public static String GetCustomDateTimeFromDateString(Date formatedDate) {
		String dateString = "";

		try {
			// Date date = formatedDate.getTime();
			SimpleDateFormat curFormater = new SimpleDateFormat(
					"yyyy-MMM-dd HH:mm:ss");
			dateString = curFormater.format(formatedDate);

			// dateString = date.getYear() + "-" + date.getMonth() + "-"
			// + date.getDate() + " " + date.getHours() + ":00:00";

		} catch (Exception ex) {

		}

		return dateString;
	}

	public static String GetDateTimeStringFromDateTime(Date formatedDate) {
		String dateString = "";

		try {
			// Date date = formatedDate.getTime();
			SimpleDateFormat curFormater = new SimpleDateFormat(
					"dd.MM.yy - HH:mm");
			dateString = curFormater.format(formatedDate);

			// dateString = date.getYear() + "-" + date.getMonth() + "-"
			// + date.getDate() + " " + date.getHours() + ":00:00";

		} catch (Exception ex) {

		}

		return dateString;
	}

	public static String GetDateStringFromDateTime(Date formatedDate) {
		String dateString = "";

		try {
			// Date date = formatedDate.getTime();
			SimpleDateFormat curFormater = new SimpleDateFormat("dd.MM.yyyy");
			dateString = curFormater.format(formatedDate);

			// dateString = date.getYear() + "-" + date.getMonth() + "-"
			// + date.getDate() + " " + date.getHours() + ":00:00";

		} catch (Exception ex) {

		}

		return dateString;
	}

	public static String GetChatFormatedTimeString(Date formatedDate) {

		try {
			java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
					" hh:mm");

			String formatedDateString = sdf.format(formatedDate);
			return formatedDateString;
		} catch (Exception ex) {
		}

		return "";

	}

	public static Date addMinute(Date date, int i) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MINUTE, i);
		return cal.getTime();
	}

	public static Date addDay(Date date, int i) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_YEAR, i);
		return cal.getTime();
	}

	public static Date addMonth(Date date, int i) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, i);
		return cal.getTime();
	}

	public static Date addYear(Date date, int i) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.YEAR, i);
		return cal.getTime();
	}

	public static String GetFormatedDateAndMonthFromDate(Date formatedDate) {
		String dateString = "";

		try {
			// Date date = formatedDate.getTime();
			SimpleDateFormat curFormater = new SimpleDateFormat("dd MMM");
			dateString = curFormater.format(formatedDate);

			// dateString = date.getYear() + "-" + date.getMonth() + "-"
			// + date.getDate() + " " + date.getHours() + ":00:00";

		} catch (Exception ex) {

		}

		return dateString;
	}
}
