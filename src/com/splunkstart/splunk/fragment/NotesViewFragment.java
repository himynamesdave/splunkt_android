package com.splunkstart.splunk.fragment;

import java.util.HashMap;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.splunk.mint.Mint;
import com.splunkstart.android.asynchttp.JsonHttpResponseHandler;
import com.splunkstart.android.util.Constants;
import com.splunkstart.android.util.DeviceUtil;
import com.splunkstart.android.util.PreferenceConnector;
import com.splunkstart.android.util.PublicMethods;
import com.splunkstart.splunk.dao.ILeadDataDao;
import com.splunkstart.splunk.dao.impl.LeadDataDao;
import com.splunkstart.splunkt.MainFragmentActivity;
import com.splunkstart.splunkt.R;

public class NotesViewFragment extends Fragment implements OnClickListener {

	private final String tag = "NotesViewFragment";
	private Context context;

	private Button buttonSubmit;

	// private EditText editTextFirstname, editTextSecondname,
	// editTextCompanyemail, editTextJobTitle;

	private EditText editTextNotes;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.notesfragment, container, false);

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

	public void init(View view) {

		buttonSubmit = (Button) view.findViewById(R.id.buttonSubmit);
		buttonSubmit.setOnClickListener(this);

		// editTextFirstname = (EditText) view
		// .findViewById(R.id.editTextFirstname);
		//
		// editTextSecondname = (EditText) view
		// .findViewById(R.id.editTextSecondname);
		//
		// editTextCompanyemail = (EditText) view
		// .findViewById(R.id.editTextCompanyemail);
		// editTextJobTitle = (EditText)
		// view.findViewById(R.id.editTextJobTitle);

		editTextNotes = (EditText) view.findViewById(R.id.editTextNotes);

	}

	@Override
	public void onClick(View v) {

		if (buttonSubmit == v) {
			sendEventDataToServer();

		}
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();

	}

	// Send data to server
	private void sendEventDataToServer() {
		try {
			PublicMethods.hideKeyboard(getActivity(), editTextNotes);

			Constants.leadData.setNote(editTextNotes.getText().toString()
					.trim());
			Constants.leadData.setDevice_type(DeviceUtil.getDeviceModelName());
			Constants.leadData.setDevice_id(DeviceUtil
					.getDeviceId(getActivity()));

			final ProgressDialog progressDialog = PublicMethods
					.showWaitDialogue(getActivity());

			ILeadDataDao leadDataDao = new LeadDataDao(getActivity());
			leadDataDao.CreateLeadData(Constants.leadData,
					new JsonHttpResponseHandler() {

						@Override
						public void onSuccess(int statusCode, Header[] headers,
								JSONObject response) {
							// TODO Auto-generated method stub
							super.onSuccess(statusCode, headers, response);
							progressDialog.dismiss();
							MainFragmentActivity
									.openFragment(Constants.SuccessFragment);
						}

						@Override
						public void onSuccess(int statusCode, Header[] headers,
								JSONArray response) {
							// TODO Auto-generated method stub
							super.onSuccess(statusCode, headers, response);
							progressDialog.dismiss();

							MainFragmentActivity
									.openFragment(Constants.SuccessFragment);
						}

						@Override
						public void onSuccess(int statusCode, Header[] headers,
								String responseString) {
							// TODO Auto-generated method stub
							super.onSuccess(statusCode, headers, responseString);
							progressDialog.dismiss();

							MainFragmentActivity
									.openFragment(Constants.SuccessFragment);
						}

						@Override
						public void onFailure(int statusCode, Header[] headers,
								String responseString, Throwable throwable) {
							// TODO Auto-generated method stub
							super.onFailure(statusCode, headers,
									responseString, throwable);
							progressDialog.dismiss();

							HashMap<String, Object> mydata = new HashMap<String, Object>();
							mydata.put("DataSend", "Successfully");
							Mint.transactionStop(tag, mydata);

							MainFragmentActivity
									.openFragment(Constants.SuccessFragment);

						}

					});

		} catch (Exception ex) {
		}

		// String basicUrl =
		// "https://54.76.14.205:8089/services/receivers/simple?source=splunkt_splunk_app&sourcetype=event_lead&index=test_splunkevents";

		//
		//
		//
		// var paramsLeads = NSMutableDictionary()
		//
		// paramsLeads.setValue(Constants.sharedInstance.leadData.company_email
		// , forKey: "company_email")
		// paramsLeads.setValue(Constants.sharedInstance.leadData.first_name ,
		// forKey: "first_name")
		// paramsLeads.setValue(Constants.sharedInstance.leadData.second_name ,
		// forKey: "second_name")
		// paramsLeads.setValue(Constants.sharedInstance.leadData.job_title ,
		// forKey: "job_title")
		//
		// // dictionaryWithObjectsAndKeys
		//
		//
		// var paramsQR = NSMutableDictionary()
		//
		// // for(var i = 0)
		// if(Constants.sharedInstance.leadData.qr_data != nil &&
		// Constants.sharedInstance.leadData.qr_data?.length > 0)
		// {
		// var fullNameArr =
		// Constants.sharedInstance.leadData.qr_data!.componentsSeparatedByString(",")
		// var i = 1
		// for qrData in fullNameArr{
		//
		// paramsQR.setValue(qrData, forKey: "field\(i.toString())")
		//
		// i++
		// }
		// }
		//
		//
		//
		// var params = NSMutableDictionary()
		//
		// params.setValue(Constants.sharedInstance.leadData.event_ID , forKey:
		// "event_id")
		// params.setValue( timeStamp.toString(), forKey: "device_timestamp")
		// params.setValue(Constants.sharedInstance.leadData.staff_email ,
		// forKey: "staff_email")
		//
		// params.setValue(paramsQR , forKey: "qr_data")
		// params.setValue(paramsLeads , forKey: "lead_details")
		//
		//
		// params.setValue(Constants.sharedInstance.leadData.shirt_slogan ,
		// forKey: "shirt_slogan")
		// params.setValue(Constants.sharedInstance.leadData.shirt_size ,
		// forKey: "shirt_size")
		//
		// //Constants.sharedInstance.leadData.use_case
		// params.setValue( Constants.sharedInstance.leadData.use_case, forKey:
		// "use_case")
		// //Constants.sharedInstance.leadData.compe66on
		// params.setValue( Constants.sharedInstance.leadData.compe66on, forKey:
		// "competition")
		// params.setValue(Constants.sharedInstance.leadData.note , forKey:
		// "notes")
		//
		// // println("use \(Constants.sharedInstance.leadData.use_case!)")
		// // println("compe66on
		// \(Constants.sharedInstance.leadData.compe66on!)")
		//
		// client.responseSerializer = responseSerializer
		// // client.responseSerializer.acceptableContentTypes = NSSet(objects:
		// "application/json")
		// client.responseSerializer.acceptableContentTypes = NSSet(object:
		// "text/xml") as Set<NSObject>
		//
		//
		//
		//
		// // var params1 = NSDictionary(objectsAndKeys:
		// Constants.sharedInstance.leadData.event_ID! , "event_id",
		// timeStamp.toString(), "device_timestamp",
		// Constants.sharedInstance.leadData.staff_email! , "staff_email",
		// paramsQR , "qr_data", paramsLeads , "lead_details",
		// Constants.sharedInstance.leadData.shirt_slogan! , "shirt_slogan",
		// Constants.sharedInstance.leadData.shirt_size! , "shirt_size",
		// Constants.sharedInstance.leadData.use_case!, "use_case",
		// Constants.sharedInstance.leadData.compe66on!, "competition",
		// Constants.sharedInstance.leadData.note! , "notes")
		//
		//
		// client.POST(basicUrl, parameters: params, success: { (operation :
		// AFHTTPRequestOperation!, response : AnyObject!) -> Void in
		//
		// println("Response : \(response.description)")
		//
		// //Fair
		// self.showSuccessViewController()
		//
		//
		// } , failure: { (operation : AFHTTPRequestOperation!, error :
		// NSError!) -> Void in
		//
		// println("Error \(error.description) \(operation.responseObject) ")
		//
		// // onCompletion(false, nil, nil, nil)
		// })
		//

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
