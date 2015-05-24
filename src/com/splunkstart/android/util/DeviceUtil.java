package com.splunkstart.android.util;

import android.content.Context;
import android.provider.Settings.Secure;

public class DeviceUtil {

	public static String getDeviceModelName() {
		return android.os.Build.MODEL;
	}

	public static String getDeviceId(Context context) {
		return Secure
				.getString(context.getContentResolver(), Secure.ANDROID_ID);
	}
}
