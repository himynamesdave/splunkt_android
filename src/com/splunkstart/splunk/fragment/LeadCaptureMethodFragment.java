package com.splunkstart.splunk.fragment;

import java.util.HashMap;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.splunk.mint.Mint;
import com.splunkstart.android.util.Constants;
import com.splunkstart.android.util.PreferenceConnector;
import com.splunkstart.splunkt.MainFragmentActivity;
import com.splunkstart.splunkt.R;

public class LeadCaptureMethodFragment extends Fragment implements
		OnClickListener {
	
	private final String tag = "CompetitionFragment";

	private Context context;

	private Button buttonScanbarode;
	private TextView textViewNobarcode;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.leadcapturemethodfragment,
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
		imageViewLeft.setVisibility(View.INVISIBLE);

		textViewLeft = (TextView) view.findViewById(R.id.textViewLeft);
		textViewLeft.setVisibility(View.INVISIBLE);

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
		linearLayoutLeft = (LinearLayout) view
				.findViewById(R.id.linearLayoutLeft);
		linearLayoutLeft.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onLeftBarItemButonClick();
			}
		});
		
		linearLayoutLeft.setVisibility(View.INVISIBLE);

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

	}

	public void onLeftBarItemButonClick() {

		MainFragmentActivity.onBackButtonPressed();
	}

	public void onRightBarItemButonClick() {

		MainFragmentActivity.openFragment(Constants.LoginFragment);
	}

	// private void showRquestedMemberList() {
	//
	// MainFragmentActivity
	// .openFragmentbackStack(Constants.OrganizationMemberListFragment);
	// }

	public void init(View view) {

		buttonScanbarode = (Button) view.findViewById(R.id.buttonScanbarode);
		buttonScanbarode.setOnClickListener(this);

		textViewNobarcode = (TextView) view
				.findViewById(R.id.textViewNobarcode);
		textViewNobarcode.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {

		if (buttonScanbarode == v) {

			MainFragmentActivity
					.openFragmentbackStack(Constants.BarCodeScannerFragment);

		} else if (v == textViewNobarcode) {

			MainFragmentActivity
					.openFragmentbackStack(Constants.ManualLeadEntryFragment);
		}
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();

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
