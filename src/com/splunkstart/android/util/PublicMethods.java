package com.splunkstart.android.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONException;
import org.json.JSONObject;

import com.splunkstart.splunkt.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.Bitmap.CompressFormat;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.provider.MediaStore;
import android.speech.RecognizerIntent;
import android.support.v4.app.Fragment;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.webkit.URLUtil;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class PublicMethods {

	private final String tag = "PublicMethods";

	private static String[] newMenu = { "Capture Image from Camera",
			"Select From Gallery" };
	private static AlertDialog alert;
	private static String imageFilePath;

	public static boolean isConnected(Context mActivity) {

		// ConnectivityManager cm = (ConnectivityManager) mActivity
		// .getSystemService(Context.CONNECTIVITY_SERVICE);
		// NetworkInfo netInfo = cm.getActiveNetworkInfo();
		// if (netInfo != null && netInfo.isConnectedOrConnecting()) {
		// return true;
		// }
		// // showAlertDialog(mActivity,
		// // mActivity.getString(R.string.msg_error_connection));
		// return false;

		try {

			ConnectivityManager cm = (ConnectivityManager) mActivity
					.getSystemService(Context.CONNECTIVITY_SERVICE);

			NetworkInfo wifiNetwork = cm
					.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
			if (wifiNetwork != null && wifiNetwork.isConnected()) {
				return true;
			}

			NetworkInfo mobileNetwork = cm
					.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
			if (mobileNetwork != null && mobileNetwork.isConnected()) {
				return true;
			}

			NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
			if (activeNetwork != null && activeNetwork.isConnected()) {
				return true;
			}

			showAlertDialog(mActivity,
					mActivity.getString(R.string.msg_error_connection));

		} catch (Exception ex) {
			Log.e("PublicMethods", ex.getMessage());
		}

		return false;

	}

	private static boolean isConnected1(Context context) {

		ConnectivityManager conMan = ((ConnectivityManager) context
				.getSystemService(context.CONNECTIVITY_SERVICE));
		boolean isWifiEnabled = conMan.getNetworkInfo(
				ConnectivityManager.TYPE_WIFI).isAvailable();
		boolean is3GEnabled = !(conMan.getNetworkInfo(
				ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.DISCONNECTED && conMan
				.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getReason()
				.equals("dataDisabled"));

		if (isWifiEnabled)
			return isWifiEnabled;
		else
			return is3GEnabled;

	}

	// public static boolean hasConnection() {
	// ConnectivityManager cm = (ConnectivityManager) MbridgeApp.getContext()
	// .getSystemService(Context.CONNECTIVITY_SERVICE);
	//
	// NetworkInfo wifiNetwork = cm
	// .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
	// if (wifiNetwork != null && wifiNetwork.isConnected()) {
	// return true;
	// }
	//
	// NetworkInfo mobileNetwork = cm
	// .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
	// if (mobileNetwork != null && mobileNetwork.isConnected()) {
	// return true;
	// }
	//
	// NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
	// if (activeNetwork != null && activeNetwork.isConnected()) {
	// return true;
	// }
	//
	// return false;
	// }

	public static void playVideo(Context activity, String url) {
		// TODO Auto-generated method stub
		activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
	}

	public static String getCurrentDate() {
		Calendar c = Calendar.getInstance();

		return String.valueOf(c.get(Calendar.YEAR) + "-"
				+ c.get(Calendar.MONTH) + "-" + c.get(Calendar.DAY_OF_MONTH));
	}

	public static void overrideFonts(final Context context, final View v) {
		Typeface font = Typeface.createFromAsset(context.getAssets(),
				"CustomFontName.ttf");
		try {
			if (v instanceof ViewGroup) {
				ViewGroup vg = (ViewGroup) v;
				for (int i = 0; i < vg.getChildCount(); i++) {
					View child = vg.getChildAt(i);
					overrideFonts(context, child);
				}
			} else if (v instanceof TextView) {
				((TextView) v).setTypeface(font);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static ProgressDialog showWaitDialogue(Context context) {
		ProgressDialog waitDialog = new ProgressDialog(context);
		waitDialog.setMessage("Please wait..");
		waitDialog.setMessage(context.getString(R.string.app_name));
		waitDialog.setIndeterminate(true);
		waitDialog.show();
		waitDialog.setCanceledOnTouchOutside(false);
		return waitDialog;

	}

	// public static void selectActionToTakePicture(final Activity activity) {
	//
	// AlertDialog.Builder builder = new AlertDialog.Builder(activity);
	// builder.setTitle("Take Photo").setItems(newMenu,
	// new DialogInterface.OnClickListener() {
	//
	// public void onClick(DialogInterface dialog, int which) {
	// if (which == 0) {
	// createFolderIfnotExist(activity);
	// capturePhoto(activity, Constants.CAPTURE_IMAGE,
	// getCreatedFileName(activity));
	// alert.dismiss();
	// }
	// if (which == 1) {
	// createFolderIfnotExist(activity);
	// PublicMethods.pickPhotoFromGallery(activity,
	// Constants.PICK_IMAGE);
	// alert.dismiss();
	//
	// }
	//
	// }
	// });
	// alert = builder.create();
	// alert.show();
	//
	// }

	// public static void selectActionToTakePicture(final Fragment frag) {
	//
	// AlertDialog.Builder builder = new AlertDialog.Builder(
	// frag.getActivity());
	// builder.setTitle("Take Photo").setItems(newMenu,
	// new DialogInterface.OnClickListener() {
	//
	// public void onClick(DialogInterface dialog, int which) {
	// if (which == 0) {
	// createFolderIfnotExist(frag.getActivity());
	// capturePhoto(frag, Constants.CAPTURE_IMAGE,
	// getCreatedFileName(frag));
	// alert.dismiss();
	// }
	// if (which == 1) {
	// createFolderIfnotExist(frag.getActivity());
	// PublicMethods.pickPhotoFromGallery(frag,
	// Constants.PICK_IMAGE);
	// alert.dismiss();
	//
	// }
	//
	// }
	// });
	// alert = builder.create();
	// alert.show();
	//
	// }

	public static String getCreatedFileName(Fragment frag) {
		imageFilePath = Environment.getExternalStorageDirectory()
				.getAbsolutePath();
		imageFilePath += "/" + frag.getString(R.string.app_name) + "/"
				+ frag.getString(R.string.app_name) + "-"
				+ PublicMethods.getCurrentTime() + ".jpg";

		return imageFilePath;
	}

	public static String getCreatedFileName(Activity activity) {
		imageFilePath = Environment.getExternalStorageDirectory()
				.getAbsolutePath();
		imageFilePath += "/" + activity.getString(R.string.app_name) + "/"
				+ activity.getString(R.string.app_name) + "-"
				+ PublicMethods.getCurrentTime() + ".jpg";

		return imageFilePath;
	}

	public static String getImageFilePath() {

		return imageFilePath;
	}

	public static File convertBitmapIntoFile(Bitmap bitmap, String filename)
			throws IOException {
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, 40, bytes);

		// you can create a new file name "test.jpg" in sdcard folder.
		File f = new File(filename);
		f.createNewFile();
		// write the bytes in file
		FileOutputStream fo = new FileOutputStream(f);
		fo.write(bytes.toByteArray());

		// remember close de FileOutput
		fo.close();
		return f;

	}

	public static int getScreenWidth(Activity activity) {
		DisplayMetrics displaymetrics = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay()
				.getMetrics(displaymetrics);

		return displaymetrics.widthPixels;
	}

	public static int getScreenHeight(Activity activity) {
		DisplayMetrics displaymetrics = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay()
				.getMetrics(displaymetrics);

		return displaymetrics.heightPixels;
	}

	public static String getAddress(Activity activity, String lat, String lng) {
		Geocoder geocoder = new Geocoder(activity, Locale.getDefault());
		String add = "";
		try {
			List<Address> addresses = geocoder.getFromLocation(
					Double.valueOf(lat), Double.valueOf(lng), 1);
			Address obj = addresses.get(0);
			add = obj.getAddressLine(0);

			add = add + " " + obj.getCountryName();
			add = add + " " + obj.getCountryCode();
			add = add + " " + obj.getAdminArea();
			add = add + " " + obj.getPostalCode();
			add = add + " " + obj.getSubAdminArea();
			add = add + " " + obj.getLocality();
			add = add + " " + obj.getSubThoroughfare();

			Log.v("IGA", "Address" + add);
			add = add.replaceAll("null", "");

		} catch (IOException e) {
			e.printStackTrace();
			Toast.makeText(activity, e.getMessage(), Toast.LENGTH_SHORT).show();
		}
		return add;
	}

	public static String getAddress(Activity activity, double lat, double lng) {
		Geocoder geocoder = new Geocoder(activity, Locale.getDefault());
		String add = "";
		try {
			List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
			Address obj = addresses.get(0);
			add = obj.getAddressLine(0);
			add = add + " " + obj.getCountryName();
			add = add + " " + obj.getCountryCode();
			add = add + " " + obj.getAdminArea();
			add = add + " " + obj.getPostalCode();
			add = add + " " + obj.getSubAdminArea();
			add = add + " " + obj.getLocality();
			add = add + " " + obj.getSubThoroughfare();

			Log.v("IGA", "Address" + add);
			add = add.replaceAll("null", "");
			// Toast.makeText(this, "Address=>" + add,
			// Toast.LENGTH_SHORT).show();

			// TennisAppActivity.showDialog(add);
		} catch (IOException e) {
			e.printStackTrace();
			Toast.makeText(activity, e.getMessage(), Toast.LENGTH_SHORT).show();
		}
		return add;
	}

	public static void capturePhoto(Fragment activity, int call, String fileName) {

		Intent intentPicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

		intentPicture.putExtra(android.provider.MediaStore.EXTRA_OUTPUT,
				Uri.fromFile(new File(fileName)));
		activity.startActivityForResult(intentPicture, call);
	}

	public static void capturePhoto(Activity activity, int call, String fileName) {

		Intent intentPicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		// intentPicture.putExtra(android.provider.MediaStore.EXTRA_OUTPUT,
		// Uri.fromFile(new File(fileName)));
		intentPicture.putExtra(android.provider.MediaStore.EXTRA_OUTPUT,
				Uri.fromFile(new File(fileName)));
		activity.startActivityForResult(intentPicture, call);
	}

	public static void captureVideo(Fragment activity, int call, String fileName) {

		Intent intentVideo = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
		intentVideo.putExtra(android.provider.MediaStore.EXTRA_OUTPUT,
				Uri.fromFile(new File(fileName)));

		activity.startActivityForResult(intentVideo, call);
	}

	public static void createFolderIfnotExist(Activity activity) {
		File direct = new File(Environment.getExternalStorageDirectory() + "/"
				+ activity.getString(R.string.app_name));

		if (!direct.exists()) {
			if (direct.mkdir()) {
				System.out.println("Directory is Created.");
			}

		}

	}

	public static void hideKeyboard(Activity activity, EditText editText) {
		InputMethodManager imm = (InputMethodManager) activity
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);

	}

	public static String getFormatedTime(Calendar calendar) {
		String format = calendar.get(Calendar.AM_PM) == 0 ? "AM" : "PM";
		String minutes = String.valueOf(calendar.get(Calendar.MINUTE));
		if (calendar.get(Calendar.MINUTE) < 10) {
			minutes = "0" + minutes;
		}
		String time = null;
		String hour = String.valueOf(calendar.get(Calendar.HOUR));
		if (calendar.get(Calendar.HOUR) == 0) {
			hour = "12";
		}
		time = hour + ":" + minutes + " " + format;

		return time;
	}

	public static void openLinkView(Activity activity, String url) {
		if (PublicMethods.isConnected(activity)) {
			activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri
					.parse(url)));
		}

	}

	public static String getFormatedDate(Calendar calendar) {
		// calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
		// calendar.get(Calendar.DAY_OF_MONTH),hourOfDay,minute
		String date;
		// if (mDay < 10) {
		// day = "0" + mDay ;
		// } else {
		// day = "" + mDay;
		// }
		// mMonth=mMonth+1;
		// if (mMonth < 10) {
		// month = "0" + mMonth + "/";
		// } else {
		// month = "" + mMonth + "/";
		// }
		// mMonth=mMonth-1;

		// date = day+month+mYear;
		date = calendar.get(Calendar.YEAR) + "/" + calendar.get(Calendar.MONTH)
				+ "/" + calendar.get(Calendar.DAY_OF_MONTH);
		return date;
	}

	public static boolean compareTimes(Calendar shouldBeGreater,
			Calendar shouldBeLess) {

		if (shouldBeGreater.get(Calendar.YEAR) >= shouldBeLess
				.get(Calendar.YEAR)) {

			if (shouldBeGreater.get(Calendar.YEAR) > shouldBeLess
					.get(Calendar.YEAR)) {
				return true;
			}
			if (shouldBeGreater.get(Calendar.MONTH) >= shouldBeLess
					.get(Calendar.MONTH)) {
				if (shouldBeGreater.get(Calendar.MONTH) > shouldBeLess
						.get(Calendar.MONTH)) {
					return true;
				}
				if (shouldBeGreater.get(Calendar.DAY_OF_MONTH) >= shouldBeLess
						.get(Calendar.DAY_OF_MONTH)) {
					if (shouldBeGreater.get(Calendar.DAY_OF_MONTH) > shouldBeLess
							.get(Calendar.DAY_OF_MONTH)) {
						return true;
					}
					if (shouldBeGreater.get(Calendar.HOUR_OF_DAY) >= shouldBeLess
							.get(Calendar.HOUR_OF_DAY)) {
						if (shouldBeGreater.get(Calendar.HOUR_OF_DAY) > shouldBeLess
								.get(Calendar.HOUR_OF_DAY)) {
							return true;
						}
						if (shouldBeGreater.get(Calendar.MINUTE) >= shouldBeLess
								.get(Calendar.MINUTE)) {
							if (shouldBeGreater.get(Calendar.MINUTE) > shouldBeLess
									.get(Calendar.MINUTE)) {
								return true;
							} else {
								return false;
							}

						} else {
							return false;
						}
					} else {
						return false;
					}

				} else {
					return false;
				}
			} else {
				return false;
			}

		}

		return false;
	}

	public static void pickPhotoFromGallery(Fragment activity, int call) {
		Intent intent = new Intent(Intent.ACTION_PICK);
		intent.setType("image/*");
		intent.setAction(Intent.ACTION_GET_CONTENT);
		activity.startActivityForResult(intent, call);

	}

	public static void pickPhotoFromGallery(Activity activity, int call) {
		Intent intent = new Intent(Intent.ACTION_PICK);
		intent.setType("image/*");
		intent.setAction(Intent.ACTION_GET_CONTENT);
		activity.startActivityForResult(intent, call);

	}

	public static void pickVideoFromGallery(Fragment activity, int call) {
		Intent intent = new Intent(Intent.ACTION_PICK);
		intent.setType("video/*");
		intent.setAction(Intent.ACTION_GET_CONTENT);
		activity.startActivityForResult(intent, call);

	}

	public static void chooseImage(Fragment activity, int call) {
		Intent intent = new Intent();
		intent.setType("image/*");
		intent.setAction(Intent.ACTION_GET_CONTENT);
		activity.startActivityForResult(
				Intent.createChooser(intent, "Select Picture"), call);

	}

	public String getRealPathFromURI(Activity activity, Uri contentUri) {
		String[] proj = { MediaStore.Images.Media.DATA };
		Cursor cursor = activity.managedQuery(contentUri, proj, null, null,
				null);
		int column_index = cursor
				.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		cursor.moveToFirst();
		return cursor.getString(column_index);
	}

	public static void chooseVideo(Fragment activity, int call) {
		Intent intent = new Intent();
		intent.setType("video/*");
		intent.setAction(Intent.ACTION_GET_CONTENT);
		activity.startActivityForResult(
				Intent.createChooser(intent, "Select Video"), call);

	}

	public static String getCurrentTime() {
		Calendar c = Calendar.getInstance();

		return String.valueOf(c.get(Calendar.YEAR) + "-"
				+ c.get(Calendar.MONTH) + "-" + c.get(Calendar.DAY_OF_MONTH)
				+ "-" + c.get(Calendar.HOUR_OF_DAY) + "-"
				+ c.get(Calendar.MINUTE) + "-" + c.get(Calendar.SECOND));
	}

	public static boolean isEmailValid(String email) {
		boolean isValid = false;

		String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
		CharSequence inputStr = email;

		Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(inputStr);
		if (matcher.matches()) {
			isValid = true;
			return isValid;
		}

		return isValid;
	}

	public static boolean isUserNameInValid(String name) {
		Pattern p = Pattern.compile("[^a-zA-Z0-9 ]", Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(name);
		boolean b = m.find();

		return b;
	}

	public static String getString(JSONObject object, String tag) {
		String value = null;
		try {
			value = object.getString(tag);
		} catch (JSONException e) {
		}
		return value;
	}

	public static int getIntVal(String str) throws JSONException {
		int value = -1;
		value = Integer.parseInt(str);
		return value;
	}

	public static Boolean getValidUrl(String url) {

		Boolean isResult = false;

		try {
			isResult = URLUtil.isValidUrl(url);
		} catch (Exception ex) {
			isResult = false;
		}

		return isResult;
	}

	public static void showAlertDialog(Context context, final String msg) {

		// final Dialog dialog = new Dialog(context);
		// dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		// dialog.setContentView(R.layout.alert_dialog);
		//
		// // set the custom dialog components - text, image and button
		// TextView text = (TextView) dialog.findViewById(R.id.tv_msg);
		// text.setText(msg);
		// // text.setTypeface(Constants.typeface);
		// Button dialogButton = (Button) dialog.findViewById(R.id.btn_ok);
		// // if button is clicked, close the custom dialog
		// dialogButton.setOnClickListener(new View.OnClickListener() {
		// @Override
		// public void onClick(View v) {
		// dialog.dismiss();
		// }
		// });
		//
		// dialog.show();

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				context);

		alertDialogBuilder.setMessage(msg).setCancelable(false)
				.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {

					}
				});
		// .setNegativeButton("No", new DialogInterface.OnClickListener() {
		// public void onClick(DialogInterface dialog, int id) {
		// dialog.cancel();
		// }
		// }
		// );

		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();

	}

	public static void showAlertDialog(Context context, final String title,
			final String msg) {

		// final Dialog dialog = new Dialog(context);
		// dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		// dialog.setContentView(R.layout.alert_dialog);
		//
		// // set the custom dialog components - text, image and button
		// TextView text = (TextView) dialog.findViewById(R.id.tv_msg);
		// text.setText(msg);
		// // text.setTypeface(Constants.typeface);
		// Button dialogButton = (Button) dialog.findViewById(R.id.btn_ok);
		// // if button is clicked, close the custom dialog
		// dialogButton.setOnClickListener(new View.OnClickListener() {
		// @Override
		// public void onClick(View v) {
		// dialog.dismiss();
		// }
		// });
		//
		// dialog.show();

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				context);

		alertDialogBuilder.setTitle(title).setMessage(msg).setCancelable(false)
				.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {

					}
				});
		// .setNegativeButton("No", new DialogInterface.OnClickListener() {
		// public void onClick(DialogInterface dialog, int id) {
		// dialog.cancel();
		// }
		// }
		// );

		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();

	}

	public boolean isVoiceRecognitionAvailable(Activity activity) {
		// Check if voice recognition is present
		boolean results = true;
		PackageManager pm = activity.getPackageManager();
		List<ResolveInfo> activities = pm.queryIntentActivities(new Intent(
				RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
		if (activities.size() == 0) {
			Toast.makeText(activity, "Voice recognizer not present",
					Toast.LENGTH_SHORT).show();
			return results;
		}
		return results;
	}

	// Date Formatedt method
	public static String GetFormatedtDateWithDateYearMonthTime(String dateString) {
		Date date;
		try {
			SimpleDateFormat curFormater = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			date = curFormater.parse(dateString);

			SimpleDateFormat ft = new SimpleDateFormat("MMM, dd yyyy");
			dateString = ft.format(date);

		} catch (Exception ex) {
			Log.w("Exception", ex.getMessage());
		}

		return dateString;

	}

	public static String GetFormatedDateString(String formatedDate) {

		Date date;
		String formatedDateString = "";
		try {
			SimpleDateFormat curFormater = new SimpleDateFormat(
					"dd-MM-yyyy HH:mm:ss");
			date = curFormater.parse(formatedDate);

			java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
					"dd-MM-yyyy");

			formatedDateString = sdf.format(formatedDate);
			return formatedDateString;

		} catch (Exception ex) {
			Log.w("Exception", ex.getMessage());
		}

		return formatedDateString;

	}

	public static void SeeSoftKeyBoard(Context context, Boolean hasFocus,
			EditText editText) {
		if (hasFocus) {
			// open keyboard
			((InputMethodManager) context
					.getSystemService(Context.INPUT_METHOD_SERVICE))
					.showSoftInput(editText, InputMethodManager.SHOW_FORCED);

		} else { // close keyboard
			((InputMethodManager) context
					.getSystemService(Context.INPUT_METHOD_SERVICE))
					.hideSoftInputFromWindow(editText.getWindowToken(), 0);
		}
	}

	public static void HideSoftKeyBoard(Context context, Boolean hasFocus,
			EditText editText) {
		// if (hasFocus) {
		// // open keyboard
		// ((InputMethodManager) context
		// .getSystemService(Context.INPUT_METHOD_SERVICE))
		// .showSoftInput(editText, InputMethodManager.SHOW_FORCED);
		//
		// } else { // close keyboard
		((InputMethodManager) context
				.getSystemService(Context.INPUT_METHOD_SERVICE))
				.hideSoftInputFromWindow(editText.getWindowToken(), 0);
		// }
	}

	public static void ShowToastMessage(Context context, String message) {
		Toast.makeText(context, message, Toast.LENGTH_LONG).show();
	}

	// public static void FillMasjidAdapter(Context context, Spinner
	// spinner_name) {
	//
	// ArrayList<Masjids> masjids = new ArrayList<Masjids>();
	// Masjids masjid = new Masjids();
	//
	// masjid.setName("Select Masjid");
	// masjids.add(masjid);
	//
	// int position = 0;
	// int count = 1;
	//
	// for (Masjids masjids2 : Constants.masjidsList) {
	//
	// if (PreferenceConnector.readLong(context,
	// PreferenceConnector.SELECTED_MASJID_ID, 0) == masjids2
	// .getId())
	// position = count;
	//
	// masjids.add(masjids2);
	// count++;
	// }
	//
	// MasjidZoneSpinnerCustomAdapter2 adapter = new
	// MasjidZoneSpinnerCustomAdapter2(
	// context, R.layout.spinner_item, masjids);
	//
	// spinner_name.setAdapter(adapter);
	//
	// spinner_name.setSelection(position);
	//
	// }
	// public static Boolean checkHolidayFromHolidayServer(Context context) {
	//
	// String holidateDate = PreferenceConnector.readString(context,
	// PreferenceConnector.CHECKED_HOLDAY_DATE, "");
	//
	// if (!holidateDate.toString().equals(
	// DateUtil.GetFormatedDateString(new Date()))) {
	//
	// try {
	// IHolidayDao holidayDao = new HolidayDao();
	// Boolean holiday = holidayDao
	// .CheckHolidayByTodayDate(new Date());
	//
	// PreferenceConnector.writeString(context,
	// PreferenceConnector.CHECKED_HOLDAY_DATE,
	// DateUtil.GetFormatedDateString(new Date()));
	//
	// PreferenceConnector.writeBoolean(context,
	// PreferenceConnector.CHECKED_HOLDAY, holiday);
	// } catch (Exception ex) {
	// }
	//
	// }
	//
	// return PreferenceConnector.readBoolean(context,
	// PreferenceConnector.CHECKED_HOLDAY, false);
	//
	// }

	//
	// -(IBAction)openFb:(id)sender
	// {
	// [[UIApplication sharedApplication] openURL:[NSURL
	// URLWithString:@"https://www.facebook.com/pages/Studvesna-SCO/1465893070297616"]];
	// }
	//
	// -(IBAction)openTw:(id)sender
	// {
	// [[UIApplication sharedApplication] openURL:[NSURL
	// URLWithString:@"https://twitter.com/studvesna_sco"]];
	// }
	//
	// -(IBAction)openVk:(id)sender
	// {
	// [[UIApplication sharedApplication] openURL:[NSURL
	// URLWithString:@"http://vk.com/russiastudvesna"]];
	// }
	//
	// -(IBAction)openIn:(id)sender
	// {
	// [[UIApplication sharedApplication] openURL:[NSURL
	// URLWithString:@"http://instagram.com/studvesna_rsm#"]];
	// }

	public static void openFaceBook(Context context) {
		try {

			String url = "https://www.facebook.com/pages/Studvesna-SCO/1465893070297616";// textViewWebSite.getText().toString();//
			// "http://www.hotmail.com";
			Uri uri = Uri.parse(url);
			Intent i = new Intent(Intent.ACTION_VIEW, uri);
			context.startActivity(i);

		} catch (Exception ex) {

			// Log.e(tag, ex.getMessage());
		}
	}

	public static void openTwitter(Context context) {
		try {

			String url = "https://twitter.com/studvesna_sco";// textViewWebSite.getText().toString();//
			// "http://www.hotmail.com";
			Uri uri = Uri.parse(url);
			Intent i = new Intent(Intent.ACTION_VIEW, uri);
			context.startActivity(i);

		} catch (Exception ex) {

			// Log.e(tag, ex.getMessage());
		}
	}

	public static void openVK(Context context) {
		try {

			String url = "http://vk.com/russiastudvesna";// textViewWebSite.getText().toString();//
			// "http://www.hotmail.com";
			Uri uri = Uri.parse(url);
			Intent i = new Intent(Intent.ACTION_VIEW, uri);
			context.startActivity(i);

		} catch (Exception ex) {

			// Log.e(tag, ex.getMessage());
		}
	}

	public static void openIn(Context context) {
		try {

			String url = "http://instagram.com/studvesna_rsm#";// textViewWebSite.getText().toString();//
			// "http://www.hotmail.com";
			Uri uri = Uri.parse(url);
			Intent i = new Intent(Intent.ACTION_VIEW, uri);
			context.startActivity(i);

		} catch (Exception ex) {

			// Log.e(tag, ex.getMessage());
		}
	}

	// this function will make your image to file
	public static File convertBitmap_to_File(Bitmap bitmap) {

		File casted_image = null;

		try {
			File rootSdDirectory = Environment.getExternalStorageDirectory();

			casted_image = new File(rootSdDirectory, "attachment.jpg");
			if (casted_image.exists()) {
				casted_image.delete();
			}
			casted_image.createNewFile();

			FileOutputStream stream = new FileOutputStream(casted_image);
			bitmap.compress(CompressFormat.JPEG, 100, stream);
			stream.flush();
			stream.close();

			// FileOutputStream fos = new FileOutputStream(casted_image);
			//
			// URL url = new URL(img_url);
			// HttpURLConnection connection = (HttpURLConnection) url
			// .openConnection();
			// connection.setRequestMethod("GET");
			// connection.setDoOutput(true);
			// connection.connect();
			// InputStream in = connection.getInputStream();
			//
			// byte[] buffer = new byte[1024];
			// int size = 0;
			// while ((size = in.read(buffer)) > 0) {
			// fos.write(buffer, 0, size);
			// }
			// fos.close();
			return casted_image;

		} catch (Exception e) {

			System.out.print(e);
			// e.printStackTrace();

		}
		return casted_image;
	}

	public static boolean isValidURL(CharSequence input) {
		if (TextUtils.isEmpty(input)) {
			return false;
		}
		Pattern URL_PATTERN = Patterns.WEB_URL;
		boolean isURL = URL_PATTERN.matcher(input).matches();
		if (!isURL) {
			String urlString = input + "";
			if (URLUtil.isNetworkUrl(urlString)) {
				try {
					new URL(urlString);
					isURL = true;
				} catch (Exception e) {
				}
			}
		}
		return isURL;
	}

	public static String GetRandomUniqueID() {
		UUID uniqueKey = UUID.randomUUID();
		// System.out.println(uniqueKey);
		return uniqueKey.toString();
	}

	// Getting ip address
	public static String GetIpAddress(Context context) {
		WifiManager wm = (WifiManager) context
				.getSystemService(Context.WIFI_SERVICE);
		String ip = Formatter.formatIpAddress(wm.getConnectionInfo()
				.getIpAddress());
		return ip;
	}

	// Getting Device id

	public static String getDeviceID(Context context) {
		TelephonyManager tManager = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		String uid = tManager.getDeviceId();
		return uid;
	}

}
