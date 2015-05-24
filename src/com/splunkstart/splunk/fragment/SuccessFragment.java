package com.splunkstart.splunk.fragment;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;

import com.splunk.mint.Mint;
import com.splunkstart.android.asynchttp.JsonHttpResponseHandler;
import com.splunkstart.android.util.Constants;
import com.splunkstart.android.util.PreferenceConnector;
import com.splunkstart.base.fragment.BaseListFragment;
import com.splunkstart.splunk.model.LeadData;
import com.splunkstart.splunkt.MainFragmentActivity;
import com.splunkstart.splunkt.R;

public class SuccessFragment extends Fragment implements OnClickListener {

	private final String tag = "SuccessFragment";

	private Context context;

	// private Button buttonSubmit;

	private EditText editTextFirstname, editTextSecondname,
			editTextCompanyemail, editTextJobTitle;

	private LinearLayout LinearLayoutMain;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater
				.inflate(R.layout.successfragment, container, false);

		// initNavigationBar(view);

		// initNavigationBar(view);
		init(view);

		// FillAdapter();

		return view;
	}

	/**
	 * Init Navigation Bar
	 * 
	 * @param view
	 */

	public TextView textViewTitle, textViewLeft, textViewRight;
	private ImageView imageViewLeft, imageViewRight, imageViewTitle,
			imageViewRight1;

	private LinearLayout linearLayoutRight1;

	// private Images images;

	private void initNavigationBar(View view) {

		imageViewLeft = (ImageView) view.findViewById(R.id.imageViewLeft);
		// imageViewLeft1 = (ImageView) view.findViewById(R.id.imageViewLeft1);
		// imageViewRight1 = (ImageView)
		// view.findViewById(R.id.imageViewRight1);
		imageViewRight = (ImageView) view.findViewById(R.id.imageViewRight);

		imageViewTitle = (ImageView) view.findViewById(R.id.imageViewTitle);

		// titleSeparatorRight = (ImageView) view
		// .findViewById(R.id.titleSeparatorRight);
		textViewLeft = (TextView) view.findViewById(R.id.textViewLeft);

		textViewLeft.setVisibility(View.GONE);
		// textViewLeft.setText("Chat");
		// titleSeparatorRight.setVisibility(View.VISIBLE);

		imageViewLeft.setImageResource(R.drawable.back);
		imageViewLeft.setVisibility(View.GONE);
		// imageViewLeft1.setVisibility(View.INVISIBLE);
		// imageViewRight1.setVisibility(View.INVISIBLE);
		imageViewRight.setVisibility(View.GONE);
		imageViewRight.setImageResource(R.drawable.add_icon);

		imageViewTitle.setVisibility(View.VISIBLE);

		// imageViewRight.setImageResource(R.drawable.ic_launchermageViewRight);

		textViewTitle = (TextView) view.findViewById(R.id.textViewTitle);
		textViewTitle.setVisibility(View.VISIBLE);
		textViewTitle.setText("Groups");

		imageViewLeft.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onLeftBarItemButonClick();
			}
		});

		imageViewRight.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				onRightBarItemButonClick();
			}
		});

		textViewLeft = (TextView) view.findViewById(R.id.textViewLeft);
		textViewLeft.setVisibility(View.GONE);

		textViewRight = (TextView) view.findViewById(R.id.textViewRight);
		textViewRight.setVisibility(View.GONE);

		// /
		linearLayoutRight1 = (LinearLayout) view
				.findViewById(R.id.linearLayoutRight1);
		linearLayoutRight1.setVisibility(View.GONE);

		// /
		// imageViewRight1 = (ImageView)
		// view.findViewById(R.id.imageViewRight1);
		// // imageViewRight1.setOnClickListener(this);
		// imageViewRight1.setVisibility(View.VISIBLE);
		//
		// imageViewRight1.setImageResource(R.drawable.member_icon);
		//
		// // /
		// imageViewRight1.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		// showRquestedMemberList();
		// }
		// });

	}

	public void onLeftBarItemButonClick() {

	}

	public void onRightBarItemButonClick() {

	}

	public void init(View view) {

		// buttonSubmit = (Button) view.findViewById(R.id.buttonSubmit);
		// buttonSubmit.setOnClickListener(this);

		LinearLayoutMain = (LinearLayout) view
				.findViewById(R.id.LinearLayoutMain);

		LinearLayoutMain.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {

		if (LinearLayoutMain == v) {

			Constants.leadData = new LeadData();
			Constants.leadData.setEvent_ID(PreferenceConnector.readString(
					getActivity(), PreferenceConnector.addEventId, ""));
			Constants.leadData.setStaff_email(PreferenceConnector.readString(
					getActivity(), PreferenceConnector.staff_email, ""));

			MainFragmentActivity
					.openFragmentbackStack(Constants.LeadCaptureMethodFragment);

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