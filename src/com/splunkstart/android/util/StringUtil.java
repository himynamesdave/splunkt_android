package com.splunkstart.android.util;

public class StringUtil {
	//paddingString should be a single character String
	public static String getPaddedString(String inputString, int desiredLength, String paddingString){
		String paddedString = inputString;
		for (int i=inputString.length(); i<desiredLength; i++)
			paddedString = paddingString + paddedString;
		return paddedString;
	}
	
	//examples of valid times: 01:50, 1:50, 13:50
	public static boolean isValidTime(String timeString){
		String[] hourMinute = null;
		try{
			hourMinute = timeString.split(":");
			if (Integer.parseInt(hourMinute[0]) > 24)
				return false;
			if (Integer.parseInt(hourMinute[1]) > 59)
				return false;
		}catch (Exception ex){
			return false;
		}
		return true;
	}
	
	//example- input: 8:30 output: 08:30
	public static String makePrettyTime(String timeString){
		if (timeString == null)
			return null;
		String[] hourMinute = timeString.split(":");
		if (hourMinute[0].length()<2)
			hourMinute[0] = "0" + hourMinute[0];
		if (hourMinute[1].length()<2)
			hourMinute[1] = "0" + hourMinute[1];
		return hourMinute[0] + ":" + hourMinute[1];
	}
	
	public static boolean isInteger(String integerString){
		try{
			Integer.parseInt(integerString);
		}catch(Exception ex){
			return false; 
		}
		return true;
	}
	
	public static String removeSpecialCharacters(String string){
		if (string == null)
			return null;
		string = string.replace("\n", "").replace("\t", "");
		return string;
	}
	
	public static String removeLeadingTrailingWhiteSpaces(String string){
		if (string == null)
			return null;
		//remove leading whitespaces
		while (string != "" && string.startsWith(" ")){
			string = string.substring(1);
		}
		while (string != "" && string.endsWith(" ")){
			string = string.substring(0, string.length()-1);
		}
		return string;
	}
	//removes \t, \n and white spaces from string
	public static String getSubstanceString(String string){
		if (string == null)
			return null;
		string = removeSpecialCharacters(string);
		string = removeLeadingTrailingWhiteSpaces(string);
		return string;
	}
}
