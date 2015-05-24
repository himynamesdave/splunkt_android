package com.splunkstart.splunk.fragment;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import android.R.string;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.splunk.mint.Mint;
import com.splunkstart.android.asynchttp.JsonHttpResponseHandler;
import com.splunkstart.android.util.Constants;
import com.splunkstart.android.util.PreferenceConnector;
import com.splunkstart.android.util.PublicMethods;
import com.splunkstart.base.fragment.BaseListFragment;
import com.splunkstart.splunk.adapter.ListViewSplunkCollectionCustomizeAdapter;
import com.splunkstart.splunk.dao.ILeadDataDao;
import com.splunkstart.splunk.dao.impl.LeadDataDao;
import com.splunkstart.splunkt.MainFragmentActivity;
import com.splunkstart.splunkt.R;

public class ShirtSloganFragment extends BaseListFragment {

	private final String tag = "ShirtSloganFragment";

	private Context context;
	private ListViewSplunkCollectionCustomizeAdapter adapter;

	private ArrayList<String> selectedList = new ArrayList<String>();

	@Override
	public void init(View view) {
		// TODO Auto-generated method stub
		super.init(view);

		context = getActivity();

		this.textViewHeader.setText("Select Shirt Slogan");

	}

	@Override
	public void initNavigationBar(View view) {
		// TODO Auto-generated method stub
		super.initNavigationBar(view);

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
		linearLayoutLeft = (LinearLayout) view
				.findViewById(R.id.linearLayoutLeft);
		linearLayoutLeft.setOnClickListener(new OnClickListener() {

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

	@Override
	public void initListViewAdapter() {
		// TODO Auto-generated method stub
		super.initListViewAdapter();

		if (Constants.shirtSloganList == null)
			Constants.shirtSloganList = new ArrayList<String>();

		adapter = new ListViewSplunkCollectionCustomizeAdapter(getActivity(),
				Constants.shirtSloganList);
		listView.setAdapter(adapter);

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub

				checkCompetition(Constants.shirtSloganList.get(position));
			}
		});
	}

	private void checkCompetition(String competition) {
		selectedList = new ArrayList<String>();
		Boolean isFound = false;
		for (String string : selectedList) {
			if (string.equals(competition)) {
				selectedList.remove(string);
				isFound = true;
				break;
			}
		}

		if (!isFound) {
			selectedList.add(competition);
		}

		adapter.setSelectedData(selectedList);
		adapter.notifyDataSetChanged();
	}

	@Override
	public void FillAdapter() {
		// TODO Auto-generated method stub
		super.FillAdapter();

	}

	@Override
	public void getAllData() {
		// TODO Auto-generated method stub

		if (Constants.shirtSloganList != null
				&& Constants.shirtSloganList.size() > 0) {
			adapter.setData(Constants.shirtSloganList);
			adapter.notifyDataSetChanged();
		} else {
			getAllTournaments();
		}
	}

	private void getAllTournaments() {

		final ILeadDataDao leadDataDao = new LeadDataDao(context);

		try {
			final ProgressDialog progressDialog = PublicMethods
					.showWaitDialogue(getActivity());
			leadDataDao.getAllShirtSlogan(new JsonHttpResponseHandler() {

				@Override
				public void onSuccess(int statusCode, Header[] headers,
						JSONObject response) {
					// TODO Auto-generated method stub
					super.onSuccess(statusCode, headers, response);

				}

				@Override
				public void onSuccess(int statusCode, Header[] headers,
						JSONArray response) {
					// TODO Auto-generated method stub
					super.onSuccess(statusCode, headers, response);

					progressDialog.dismiss();
					try {

						for (int i = 0; i < response.length(); i++) {
							JSONObject jsonObject = response.getJSONObject(i);

							String addShirtSlogan = jsonObject
									.getString("addShirtSlogan");

							Constants.shirtSloganList.add(addShirtSlogan);

						}
						adapter.setData(Constants.shirtSloganList);
						adapter.notifyDataSetChanged();

					} catch (Exception ex) {
					}

				}

				@Override
				public void onFailure(int statusCode, Header[] headers,
						String responseString, Throwable throwable) {
					// TODO Auto-generated method stub
					super.onFailure(statusCode, headers, responseString,
							throwable);
					progressDialog.dismiss();
				}
			});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void onClickSubmitButton() {
		// TODO Auto-generated method stub
		super.onClickSubmitButton();

		if (selectedList.size() == 0) {
			PublicMethods.showAlertDialog(getActivity(),
					"Please select shirt Size");
			return;
		}

		Constants.leadData.setShirt_slogan(selectedList.get(0));

		MainFragmentActivity.openFragmentbackStack(Constants.ShirtSizeFragment);

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
