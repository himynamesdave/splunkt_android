package com.splunkstart.splunk.fragment;

import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.splunk.mint.Mint;
import com.splunkstart.android.util.Constants;
import com.splunkstart.android.util.PreferenceConnector;
import com.splunkstart.splunkt.MainFragmentActivity;
import com.splunkstart.splunkt.R;

public class BarCodeScannerFragment extends Fragment implements OnClickListener {

	private final String tag = "BarCodeScannerFragment";

	private Context context;

	private TextView textViewScanView;
	private String firstName = "";
	private String secondName = "";
	private String companyEmail = "";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.barcodescannerfragment,
				container, false);

		// initNavigationBar(view);

		initNavigationBar(view);
		init(view);

		// FillAdapter();

		return view;
	}

	/**
	 * Init Navigation Bar
	 * 
	 * @param view
	 */

	public TextView textViewTitle, textViewLeft, textViewRight, textViewRight1;
	private ImageView imageViewLeft, imageViewRight, imageViewTitle,
			imageViewRight1;

	private LinearLayout linearLayoutRight1, linearLayoutLeft;

	// private Images images;

	private void initNavigationBar(View view) {

		imageViewLeft = (ImageView) view.findViewById(R.id.imageViewLeft);
		imageViewLeft.setImageResource(R.drawable.back);
		imageViewLeft.setVisibility(View.VISIBLE);

		textViewLeft = (TextView) view.findViewById(R.id.textViewLeft);
		textViewLeft.setVisibility(View.VISIBLE);

		//
		imageViewRight = (ImageView) view.findViewById(R.id.imageViewRight);
		imageViewRight.setVisibility(View.VISIBLE);
		imageViewRight.setImageResource(R.drawable.logout);

		imageViewTitle = (ImageView) view.findViewById(R.id.imageViewTitle);
		imageViewTitle.setVisibility(View.GONE);

		imageViewTitle = (ImageView) view.findViewById(R.id.imageViewTitle);
		imageViewTitle.setVisibility(View.GONE);

		textViewTitle = (TextView) view.findViewById(R.id.textViewTitle);
		textViewTitle.setVisibility(View.VISIBLE);
		textViewTitle.setText(PreferenceConnector.readString(getActivity(),
				PreferenceConnector.addEventName, ""));

		//
		// imageViewLeft.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		// onLeftBarItemButonClick();
		// }
		// });

		imageViewRight.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				onRightBarItemButonClick();
			}
		});

		textViewRight1 = (TextView) view.findViewById(R.id.textViewRight1);
		textViewRight1.setVisibility(View.VISIBLE);

		String email = PreferenceConnector.readString(getActivity(),
				PreferenceConnector.staff_email, "");
		textViewRight1.setText(email.subSequence(0,
				email.indexOf("@splunk.com")));

		// /
		linearLayoutRight1 = (LinearLayout) view
				.findViewById(R.id.linearLayoutRight1);
		linearLayoutRight1.setVisibility(View.VISIBLE);

		linearLayoutLeft = (LinearLayout) view
				.findViewById(R.id.linearLayoutLeft);
		linearLayoutLeft.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onLeftBarItemButonClick();
			}
		});

	}

	public void onLeftBarItemButonClick() {

		MainFragmentActivity.onBackButtonPressed();
	}

	public void onRightBarItemButonClick() {

		MainFragmentActivity.openFragment(Constants.LoginFragment);
	}

	public void init(View view) {

		textViewScanView = (TextView) view.findViewById(R.id.textViewScanView);

		textViewScanView.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {

		if (textViewScanView == v) {

			onClickScan();
		}
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();

	}

	private void onClickScan() {
		Intent intent = new Intent("com.google.zxing.client.android.SCAN");
		intent.putExtra("com.google.zxing.client.android.SCAN.SCAN_MODE",
				"QR_CODE_MODE");
		startActivityForResult(intent, 0);
	}

	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		if (requestCode == 0) {
			if (resultCode == getActivity().RESULT_OK) {
				String result = intent.getStringExtra("SCAN_RESULT");
				String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
				Log.i("xZing", "contents: " + result + " format: " + format); // Handle
																				// successful
																				// scan

				try {

					Constants.leadData.setFirst_name("");
					Constants.leadData.setSecond_name("");
					Constants.leadData.setCompany_email("");
					Constants.leadData.setJob_title("");

					Constants.leadData.setQr_data(result);

					if (checkStr1_spec(result)) {
					} else if (checkStr1_5comment(result)) {

					} else if (checkStr1_space(result)) {

					} else if (checkStr1_nospace(result)) {

					} else if (checkVCard(result)) {

					}

//					Constants.leadData.setFirst_name(firstName);
//					Constants.leadData.setSecond_name(secondName);
//					Constants.leadData.setCompany_email(companyEmail);
//					Constants.leadData.setJob_title("");

				} catch (Exception ex) {
				}

				//MainFragmentActivity
				//		.openFragmentbackStack(Constants.ShirtSloganFragment);
				
				if (PreferenceConnector.readBoolean(getActivity(),
						PreferenceConnector.showShirt, false)) {
					MainFragmentActivity
							.openFragmentbackStack(Constants.ShirtSloganFragment);

				} else {

					MainFragmentActivity
							.openFragmentbackStack(Constants.UseCaseFragment);
				}

			} else if (resultCode == getActivity().RESULT_CANCELED) { // Handle
																		// cancel
				Log.i("xZing", "Cancelled");
			}
		}
	}

	private Boolean checkStr1_spec(String fullStr1) {

		try {
			String fullStr = fullStr1;

			firstName = "";
			secondName = "";
			companyEmail = "";

			int index = 0;
			try {
				index = fullStr.indexOf("firstname=");
			} catch (Exception ex) {
			}

			if (index == -1) {
				return false;
			}

			fullStr = fullStr.substring(index + 10);
			index = 0;
			try {
				index = fullStr.indexOf(",");
			} catch (Exception ex) {
			}

			if (index == -1) {
				return false;
			}

			firstName = fullStr.substring(0, index);

			fullStr = fullStr.substring(index + 1);

			try {
				index = fullStr.indexOf("secondname=");
			} catch (Exception ex) {
			}

			if (index == -1) {
				return false;
			}

			fullStr = fullStr.substring(index + 11);
			index = 0;
			try {
				index = fullStr.indexOf(",");
			} catch (Exception ex) {
			}

			if (index == -1) {
				return false;
			}
			secondName = fullStr.substring(0, index);

			fullStr = fullStr.substring(index + 1);

			try {
				index = fullStr.indexOf("email=");
			} catch (Exception ex) {
			}

			if (index == -1) {
				return false;
			}

			companyEmail = fullStr.substring(index + 6);

		} catch (Exception ex) {
			return false;
		}

		return true;
	}

	private Boolean checkStr1_5comment(String fullStr1) {

		try {
			String fullStr = fullStr1;

			firstName = "";
			secondName = "";
			companyEmail = "";

			int index = 0;
			try {
				index = fullStr.indexOf(", ");
			} catch (Exception ex) {
			}

			if (index == -1) {
				return false;
			}

			firstName = fullStr.substring(0, index);

			fullStr = fullStr.substring(index + 2);

			index = 0;
			try {
				index = fullStr.indexOf(", ");
			} catch (Exception ex) {
			}

			if (index == -1) {
				return false;
			}

			secondName = fullStr.substring(0, index);

			fullStr = fullStr.substring(index + 2);

			index = 0;
			try {
				index = fullStr.indexOf(", ");
			} catch (Exception ex) {
			}

			if (index == -1) {
				return false;
			}

			companyEmail = fullStr.substring(0, index);

		} catch (Exception ex) {
			return false;
		}
		return true;
	}

	private Boolean checkStr1_space(String fullStr1) {
		try {

			String fullStr = fullStr1;

			firstName = "";
			secondName = "";
			companyEmail = "";

			int index = 0;
			try {
				index = fullStr.indexOf(", ");
			} catch (Exception ex) {
			}

			if (index == -1) {
				return false;
			}

			firstName = fullStr.substring(0, index);

			fullStr = fullStr.substring(index + 2);

			index = 0;
			try {
				index = fullStr.indexOf(", ");
			} catch (Exception ex) {
			}

			if (index == -1) {
				return false;
			}

			secondName = fullStr.substring(0, index);

			companyEmail = fullStr.substring(index + 2);

		} catch (Exception ex) {
			return false;
		}

		return true;
	}

	private Boolean checkStr1_nospace(String fullStr1) {

		try {

			String fullStr = fullStr1;

			firstName = "";
			secondName = "";
			companyEmail = "";

			int index = 0;
			try {
				index = fullStr.indexOf(",");
			} catch (Exception ex) {
			}

			if (index == -1) {
				return false;
			}

			firstName = fullStr.substring(0, index);

			fullStr = fullStr.substring(index + 2);

			index = 0;
			try {
				index = fullStr.indexOf(",");
			} catch (Exception ex) {
			}

			if (index == -1) {
				return false;
			}

			secondName = fullStr.substring(0, index);

			companyEmail = fullStr.substring(index + 2);

		} catch (Exception ex) {
			return false;
		}

		return true;
	}

	// ORG:Beckesch\nTITLE:Dipl.-Ing.\nEMAIL:dirk@beckesch.com\nTEL;
	// Optional("BEGIN:VCARD\nVERSION:3.0\nN:Beckesch;Dirk\nORG:Beckesch\nTITLE:Dipl.-Ing.\nEMAIL:dirk@beckesch.com\nTEL;WORK:030/46607333\nEND:VCARD")

	private Boolean checkVCard(String fullStr1) {

		try {
			String fullStr = fullStr1;

			firstName = "";
			secondName = "";
			companyEmail = "";

			int index = 0;
			try {
				index = fullStr.indexOf("TITLE:");
			} catch (Exception ex) {
			}

			if (index == -1) {
				return false;
			}

			fullStr = fullStr.substring(index + 6);
			index = 0;
			try {
				index = fullStr.indexOf("\n");
			} catch (Exception ex) {
			}

			if (index == -1) {
				return false;
			}

			firstName = fullStr.substring(0, index);

			fullStr = fullStr.substring(index + 1);

			//
			// secondName =""
			//
			index = 0;
			try {
				index = fullStr.indexOf("EMAIL:");
			} catch (Exception ex) {
			}

			if (index == -1) {
				return false;
			}

			fullStr = fullStr.substring(index + 6);
			index = 0;
			try {
				index = fullStr.indexOf("\n");
			} catch (Exception ex) {
			}

			if (index == -1) {
				return false;
			}

			companyEmail = fullStr.substring(0, index);

		} catch (Exception ex) {
			return false;
		}

		return true;
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		HashMap<String, Object> mydata = new HashMap<String, Object>();
		mydata.put("startView", "onResume()");
		Mint.transactionStop(tag, mydata);
	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		HashMap<String, Object> mydata = new HashMap<String, Object>();
		mydata.put("startView", "onStop()");
		Mint.transactionStop(tag, mydata);
	}
}
