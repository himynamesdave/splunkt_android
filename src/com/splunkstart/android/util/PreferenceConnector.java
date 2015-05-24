package com.splunkstart.android.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class PreferenceConnector {
	public static final String PREF_NAME = "CHAT_PREF";
	public static final int MODE = Context.MODE_PRIVATE;

	public static final String LOGIN_NAME = "LOGIN_NAME";
	public static final String FIRSTNAME = "FIRSTNAME";
	public static final String LASTNAME = "LASTNAME";
	public static final String EMAIL = "EMAIL";
	public static final String PASSWORD = "PASSWORD";
	public static final String ISLOGIN = "ISLOGIN";
	public static final String WELCOME_SCREEN = "WELCOME_SCREEN";
	public static final String PROFILE_ID = "PROFILE_ID";
	public static final String NAME = "NAME";
	public static final String PROFILE_PIC = "PROFILE_PIC";
	public static final String TOKEN = "TOKEN";
	public static final String USER_ID = "USER_ID";
	public static final String REFERRAL = "REFERRAL";
	public static final String ISCHECKED = "ISCHECKED";
	public static final String COUNTRY = "COUNTRY";
	public static final String LEVEL = "LEVEL";
	public static final String STATUS = "STATUS";

	// public static final String GENDER = "GENDER";
	public static final String LINK = "LINK";

	// public static final String ISALREADYSETMASJID = "ISALREADYSETMASJID";

	public static final String ISFIRSTRUN = "ISFIRSTRUN";

	public static final String CHECKED_HOLDAY_DATE = "HOLDAY_DATE";
	public static final String CHECKED_HOLDAY = "HOLDAY";

	public static final String VK_ACCOUNT_TOKEN = "VK_ACCOUNT_TOKEN";
	public static final String VK_ACCOUNT_USER_ID = "VK_ACCOUNT_USER_ID";

	public static final String FACEBOOK_TOKEN = "FACEBOOK_TOKEN";
	public static final String FACEBOOK_ID = "FACEBOOK_ID";
	public static final String PROFILE_IMAGE_URL = "PROFILE_IMAGE_URL";
	// public static final String ISOVERLAY_VISIABLE1 = "ISOVERLAY_VISIABLE1";
	// public static final String ISOVERLAY_VISIABLE2 = "ISOVERLAY_VISIABLE2";
	// public static final String ISOVERLAY_VISIABLE3 = "ISOVERLAY_VISIABLE3";

	public static final String ISOVERLAY_VISIABLE = "ISOVERLAY_VISIABLE";
	public static final String marginTop = "marginTop";

	public static final String GROUPID = "GROUPID";
	public static final String ORGANIZATIONID = "ORGANIZATIONID";

	public static final String addEventId = "addEventId";
	public static final String addEventName = "addEventName";
	
	public static final String staff_email = "staff_email";
	public static final String showShirt = "showShirt";

	
	
	//

	public static final String USERTYPE = "USERTYPE";

	// /

	public static void writeBoolean(Context context, String key, boolean value) {
		getEditor(context).putBoolean(key, value).commit();
	}

	public static boolean readBoolean(Context context, String key,
			boolean defValue) {
		return getPreferences(context).getBoolean(key, defValue);
	}

	public static void writeInteger(Context context, String key, int value) {
		getEditor(context).putInt(key, value).commit();

	}

	public static int readInteger(Context context, String key, int defValue) {
		return getPreferences(context).getInt(key, defValue);
	}

	public static void writeString(Context context, String key, String value) {
		getEditor(context).putString(key, value).commit();

	}

	public static String readString(Context context, String key, String defValue) {
		return getPreferences(context).getString(key, defValue);
	}

	public static void writeFloat(Context context, String key, float value) {
		getEditor(context).putFloat(key, value).commit();
	}

	public static float readFloat(Context context, String key, float defValue) {
		return getPreferences(context).getFloat(key, defValue);
	}

	public static void writeLong(Context context, String key, long value) {
		getEditor(context).putLong(key, value).commit();
	}

	public static long readLong(Context context, String key, long defValue) {
		return getPreferences(context).getLong(key, defValue);
	}

	public static SharedPreferences getPreferences(Context context) {
		return context.getSharedPreferences(PREF_NAME, MODE);
	}

	public static Editor getEditor(Context context) {
		return getPreferences(context).edit();
	}
}
