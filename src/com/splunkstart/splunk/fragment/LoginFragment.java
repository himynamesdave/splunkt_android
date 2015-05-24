package com.splunkstart.splunk.fragment;

import java.util.HashMap;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.splunk.mint.Mint;
import com.splunkstart.android.asynchttp.JsonHttpResponseHandler;
import com.splunkstart.android.util.Constants;
import com.splunkstart.android.util.PreferenceConnector;
import com.splunkstart.android.util.PublicMethods;
import com.splunkstart.splunk.dao.ILeadDataDao;
import com.splunkstart.splunk.dao.impl.LeadDataDao;
import com.splunkstart.splunk.model.LeadData;
import com.splunkstart.splunkt.MainFragmentActivity;
import com.splunkstart.splunkt.R;

public class LoginFragment extends Fragment implements OnClickListener {

	private final String tag = "LoginFragment";

	private Context context;

	private Button buttonEnter;

	private EditText editTextSplunkEmail, editTextEventID;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.loginfragment, container, false);

		// initNavigationBar(view);

		// initNavigationBar(view);
		init(view);

		// FillAdapter();

		return view;
	}

	// /**
	// * Init Navigation Bar
	// *
	// * @param view
	// */
	//
	// public TextView textViewTitle, textViewLeft, textViewRight;
	// private ImageView imageViewLeft, imageViewRight, imageViewTitle,
	// imageViewRight1;
	//
	// private LinearLayout linearLayoutRight1;
	//
	// // private Images images;
	//
	// private void initNavigationBar(View view) {
	//
	// imageViewLeft = (ImageView) view.findViewById(R.id.imageViewLeft);
	// // imageViewLeft1 = (ImageView) view.findViewById(R.id.imageViewLeft1);
	// // imageViewRight1 = (ImageView)
	// // view.findViewById(R.id.imageViewRight1);
	// imageViewRight = (ImageView) view.findViewById(R.id.imageViewRight);
	//
	// imageViewTitle = (ImageView) view.findViewById(R.id.imageViewTitle);
	//
	// // titleSeparatorRight = (ImageView) view
	// // .findViewById(R.id.titleSeparatorRight);
	// textViewLeft = (TextView) view.findViewById(R.id.textViewLeft);
	//
	// textViewLeft.setVisibility(View.GONE);
	// // textViewLeft.setText("Chat");
	// // titleSeparatorRight.setVisibility(View.VISIBLE);
	//
	// imageViewLeft.setImageResource(R.drawable.back_fit);
	// imageViewLeft.setVisibility(View.GONE);
	// // imageViewLeft1.setVisibility(View.INVISIBLE);
	// // imageViewRight1.setVisibility(View.INVISIBLE);
	// imageViewRight.setVisibility(View.GONE);
	// imageViewRight.setImageResource(R.drawable.add_icon);
	//
	// imageViewTitle.setVisibility(View.VISIBLE);
	//
	// // imageViewRight.setImageResource(R.drawable.ic_launchermageViewRight);
	//
	// textViewTitle = (TextView) view.findViewById(R.id.textViewTitle);
	// textViewTitle.setVisibility(View.VISIBLE);
	// textViewTitle.setText("Groups");
	//
	// imageViewLeft.setOnClickListener(new OnClickListener() {
	//
	// @Override
	// public void onClick(View v) {
	// // TODO Auto-generated method stub
	// onLeftBarItemButonClick();
	// }
	// });
	//
	// imageViewRight.setOnClickListener(new OnClickListener() {
	//
	// @Override
	// public void onClick(View v) {
	// onRightBarItemButonClick();
	// }
	// });
	//
	// textViewLeft = (TextView) view.findViewById(R.id.textViewLeft);
	// textViewLeft.setVisibility(View.GONE);
	//
	// textViewRight = (TextView) view.findViewById(R.id.textViewRight);
	// textViewRight.setVisibility(View.GONE);
	//
	// // /
	// linearLayoutRight1 = (LinearLayout) view
	// .findViewById(R.id.linearLayoutRight1);
	// linearLayoutRight1.setVisibility(View.GONE);
	//
	// // /
	// // imageViewRight1 = (ImageView)
	// // view.findViewById(R.id.imageViewRight1);
	// // // imageViewRight1.setOnClickListener(this);
	// // imageViewRight1.setVisibility(View.VISIBLE);
	// //
	// // imageViewRight1.setImageResource(R.drawable.member_icon);
	// //
	// // // /
	// // imageViewRight1.setOnClickListener(new OnClickListener() {
	// //
	// // @Override
	// // public void onClick(View v) {
	// // // TODO Auto-generated method stub
	// // showRquestedMemberList();
	// // }
	// // });
	//
	// }
	//
	// public void onLeftBarItemButonClick() {
	//
	// }
	//
	// public void onRightBarItemButonClick() {
	//
	// }

	public void init(View view) {

		buttonEnter = (Button) view.findViewById(R.id.buttonEnter);
		buttonEnter.setOnClickListener(this);

		editTextSplunkEmail = (EditText) view
				.findViewById(R.id.editTextSplunkEmail);

		editTextEventID = (EditText) view.findViewById(R.id.editTextEventID);


	}

	@Override
	public void onClick(View v) {

		if (buttonEnter == v) {

			getEventDataByEventID();
		}
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();

	}

	private Boolean isCheckForm() {
		if (editTextSplunkEmail.getText().toString().trim().equals("")
				|| !PublicMethods.isEmailValid(editTextSplunkEmail.getText()
						.toString())) {

			PublicMethods.showAlertDialog(getActivity(),
					"Please enter your Splunk email address");

			editTextSplunkEmail.setBackgroundColor(Color.RED);

			return false;
		} else {

			editTextSplunkEmail.setBackgroundColor(Color.WHITE);
		}

		if (!editTextSplunkEmail.getText().toString().trim()
				.contains("@splunk.com")) {

			PublicMethods.showAlertDialog(getActivity(),
					"Please enter your Splunk email address");

			editTextSplunkEmail.setBackgroundColor(Color.RED);
			return false;
		} else {

			editTextSplunkEmail.setBackgroundColor(Color.WHITE);
		}

		if (editTextEventID.getText().toString().trim().equals("")) {
			// PublicMethods.ShowToastMessage(getActivity(),
			// "Please enter an event ID");

			PublicMethods.showAlertDialog(getActivity(),
					"Please enter an event ID");

			editTextEventID.setBackgroundColor(Color.RED);
			return false;
		} else {

			editTextEventID.setBackgroundColor(Color.WHITE);
		}

		return true;
	}

	private void getEventDataByEventID() {

		PublicMethods.hideKeyboard(getActivity(), editTextEventID);

		// /new UpdateDriverCurrentLocationAndAddressAsync().execute();

		if (!PublicMethods.isConnected(getActivity()))
			return;

		if (!isCheckForm()) {
			return;
		}

		try {
			String eventId = editTextEventID.getText().toString().trim();

			final ProgressDialog progressDialog = PublicMethods
					.showWaitDialogue(getActivity());

			ILeadDataDao leadDataDao = new LeadDataDao(getActivity());
			leadDataDao.getEventDataByEventID(eventId,
					new JsonHttpResponseHandler() {

						@Override
						public void onSuccess(int statusCode, Header[] headers,
								JSONObject response) {
							// TODO Auto-generated method stub
							super.onSuccess(statusCode, headers, response);

							try {
								progressDialog.dismiss();

								if (response != null && response.length() > 0) {
									String addEventId = response
											.getString("addEventId");
									String addEventName = response
											.getString("addEventName");

									PreferenceConnector.writeString(
											getActivity(),
											PreferenceConnector.addEventId,
											addEventId);
									PreferenceConnector.writeString(
											getActivity(),
											PreferenceConnector.addEventName,
											addEventName);

									Constants.leadData = new LeadData();

									Constants.leadData.setEvent_ID(addEventId);
									Constants.leadData
											.setStaff_email(editTextSplunkEmail
													.getText().toString());

									PreferenceConnector.writeString(
											getActivity(),
											PreferenceConnector.addEventId,
											addEventId);
									PreferenceConnector.writeString(
											getActivity(),
											PreferenceConnector.staff_email,
											editTextSplunkEmail.getText()
													.toString());

									//

									MainFragmentActivity
											.openFragmentbackStack(Constants.LeadCaptureMethodFragment);
								} else {

									PublicMethods
											.showAlertDialog(getActivity(),
													"The event ID entered does not exist");

									HashMap<String, Object> mydata = new HashMap<String, Object>();
									mydata.put("loginbutton",
											"loginUnSuccessfully");
									Mint.transactionStop(tag, mydata);
								}

							} catch (Exception ex) {
							}
						}

						@Override
						public void onSuccess(int statusCode, Header[] headers,
								JSONArray response) {
							// TODO Auto-generated method stub
							super.onSuccess(statusCode, headers, response);
							try {

								try {
									progressDialog.dismiss();

									if (response != null
											&& response.length() > 0) {

										JSONObject jsonObject = response
												.getJSONObject(0);

										String addEventId = jsonObject
												.getString("addEventId");
										String addEventName = jsonObject
												.getString("addEventName");

										Boolean showShirt = jsonObject
												.getBoolean("showShirt");

										PreferenceConnector.writeString(
												getActivity(),
												PreferenceConnector.addEventId,
												addEventId);
										PreferenceConnector
												.writeString(
														getActivity(),
														PreferenceConnector.addEventName,
														addEventName);

										Constants.leadData = new LeadData();

										Constants.leadData
												.setEvent_ID(addEventId);
										Constants.leadData
												.setStaff_email(editTextSplunkEmail
														.getText().toString());

										PreferenceConnector.writeString(
												getActivity(),
												PreferenceConnector.addEventId,
												addEventId);

										PreferenceConnector
												.writeString(
														getActivity(),
														PreferenceConnector.staff_email,
														editTextSplunkEmail
																.getText()
																.toString());

										//
										PreferenceConnector.writeBoolean(
												getActivity(),
												PreferenceConnector.showShirt,
												showShirt);

										//

										HashMap<String, Object> mydata = new HashMap<String, Object>();
										mydata.put("loginbutton",
												"loginSuccessfully");
										Mint.transactionStop(tag, mydata);

										MainFragmentActivity
												.openFragmentbackStack(Constants.LeadCaptureMethodFragment);
									} else {

										PublicMethods
												.showAlertDialog(getActivity(),
														"The event ID entered does not exist");

										HashMap<String, Object> mydata = new HashMap<String, Object>();
										mydata.put("loginbutton",
												"loginUnSuccessfully");
										Mint.transactionStop(tag, mydata);
									}

								} catch (Exception ex) {
								}
							} catch (Exception ex) {
							}
						}

						@Override
						public void onSuccess(int statusCode, Header[] headers,
								String responseString) {
							// TODO Auto-generated method stub
							super.onSuccess(statusCode, headers, responseString);

							progressDialog.dismiss();

							// PublicMethods.ShowToastMessage(getActivity(),
							// "The event ID entered does not exist");

							PublicMethods.showAlertDialog(getActivity(),
									"The event ID entered does not exist");

							HashMap<String, Object> mydata = new HashMap<String, Object>();
							mydata.put("loginbutton", "loginUnSuccessfully");
							Mint.transactionStop(tag, mydata);
						}
					});

		} catch (Exception ex) {
		}
	}

	//
	// func getEventDataByEventID(){
	//
	// var eventId = eventIDTextField.text as String!
	//
	// // eventcollection, usecasecollection, competitioncollection,
	// shirtslogancollection, shirtsizecollection
	// var subUrl = "{\"addEventId\": \"\(eventId)\"}"
	//
	// subUrl = subUrl.URLEncodedString()!
	// ///
	// var basicUrl =
	// "https://54.76.14.205:8089/servicesNS/nobody/splunkt_splunk_app/storage/collections/data/eventcollection?query=\(subUrl)"
	//
	// println("basicUrl : \(basicUrl)")
	//
	// var policy : AFSecurityPolicy = AFSecurityPolicy();
	// policy.allowInvalidCertificates = true;
	//
	// var client = AFHTTPRequestOperationManager()
	// client.operationQueue = NSOperationQueue.mainQueue()
	// client.securityPolicy = policy
	//
	//
	//
	// var requestSerializer : AFJSONRequestSerializer =
	// AFJSONRequestSerializer()
	// var responseSerializer : AFJSONResponseSerializer =
	// AFJSONResponseSerializer()
	//
	//
	//
	// client.requestSerializer = requestSerializer
	//
	// client.requestSerializer.setAuthorizationHeaderFieldWithUsername(Constants.sharedInstance.SPLUNK_USER,
	// password: Constants.sharedInstance.SPLUNK_PASS)
	// client.securityPolicy.allowInvalidCertificates = true
	//
	// var params = NSMutableDictionary()
	//
	//
	// // params .setValue("EventID1", forKey: "addEventId")
	//
	//
	// client.responseSerializer = responseSerializer
	// client.responseSerializer.acceptableContentTypes = NSSet(objects:
	// "application/json") as Set<NSObject>
	//
	//
	// ///addEventId
	//
	// // EventID1
	//
	// // addEventEndYear = 2015;
	// // addEventId = EventID1;
	//
	// // ?query=
	//
	// hud.show(true)
	//
	// client.GET(basicUrl, parameters: params, success: { (operation :
	// AFHTTPRequestOperation!, response : AnyObject!) -> Void in
	//
	//
	// self.hud.hide(true)
	//
	// println("Response : \(response.description)")
	//
	//
	//
	// let jsonDict = response as! NSArray
	//
	//
	// if(jsonDict.count > 0)
	// {
	//
	// for var i=0 ;i < jsonDict.count; i++ {
	//
	//
	// let row = jsonDict.objectAtIndex(i) as! NSDictionary
	//
	// println("row : \(row)")
	//
	// var addEventId = row.valueForKey("addEventId") as! String!
	//
	// var addEventName = row.valueForKey("addEventName")as! String!
	//
	// var defaults = NSUserDefaults.standardUserDefaults()
	//
	// Constants.sharedInstance.leadData.staff_email = self.emailTextField.text
	// as String!
	// Constants.sharedInstance.leadData.event_ID = addEventId
	//
	// defaults.setValue(addEventName, forKey: "addEventName")
	// defaults.setValue(Constants.sharedInstance.leadData.staff_email , forKey:
	// "staff_email")
	// defaults.setValue(Constants.sharedInstance.leadData.event_ID , forKey:
	// "event_ID")
	//
	// // Constants.sharedInstance.eventIDList.append(addEventId)
	// self.showLeadCaptureMethodViewController()
	// }
	//
	// }else
	// {
	// PublicMethod.showAlertDialog("EventId incorrect!", Message:
	// "Please enter correct EventID")
	// }
	// } , failure: { (operation : AFHTTPRequestOperation!, error : NSError!) ->
	// Void in
	//
	// println("Error \(error.description) \(operation.responseObject) ")
	//
	// self.hud.hide(true)
	// // onCompletion(false, nil, nil, nil)
	// })
	//
	//
	// }
	//

	// getEventDataByEventID

	public class UpdateDriverCurrentLocationAndAddressAsync extends
			AsyncTask<String, Integer, String> {
		private ProgressDialog progressDialog;

		@Override
		protected void onPreExecute() {

			super.onPreExecute();
			// progressDialog = ProgressDialog.show(getActivity(),
			// getString(R.string.app_name), "Please Wait");
		}

		@Override
		protected String doInBackground(String... params) {
			String result = null;

			try {

				String eventId = editTextEventID.getText().toString().trim();

				ILeadDataDao leadDataDao = new LeadDataDao(getActivity());
				result = leadDataDao.getEventDataByEventID(eventId);

			} catch (Exception ex) {
			}

			return result;

		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);

		}
	}

	// ///
	// public String POST() {
	// InputStream inputStream = null;
	// String result = "";
	// try {
	//
	// // 1. create HttpClient
	// HttpClient httpclient = new DefaultHttpClient();
	//
	// // 2. make POST request to the given URL
	// // String url =
	// // "https://arwerks.azure-mobile.net/tables/User_account/";
	// String url =
	// "https://arps-ops-stg2.azurewebsites.net/api/security/authorizeuser";
	//
	// HttpPost httpPost = new HttpPost(url);
	//
	// String json = "";
	//
	// // json.put("tag", "UserAuthenticateByEmailAndPassword");
	// //
	// // // private String lastName;
	// // json.put("authtoken", Constants.authToken);
	// // json.put("username", email);
	// // json.put("password", password);
	// // // private String firstName;
	// // json.put("permissionname", Constants.permissionName);
	//
	// // 3. build jsonObject
	// JSONObject jsonObject = new JSONObject();
	// // jsonObject.accumulate("name", person.getName());
	// // jsonObject.accumulate("country", person.getCountry());
	// // jsonObject.accumulate("twitter", person.getTwitter());
	//
	// // jsonObject.accumulate("authtoken", Constants.authToken);
	// // private String l_name;
	// jsonObject.accumulate("username", editTextUserName.getText()
	// .toString().trim());
	// // private String email_address;
	// jsonObject.accumulate("password", editTextPassword.getText()
	// .toString().trim());
	// // private String gender;
	// jsonObject.accumulate("permissionname", Constants.permissionName);
	//
	// // 4. convert JSONObject to JSON to String
	// json = jsonObject.toString();
	//
	// // ** Alternative way to convert Person object to JSON string usin
	// // Jackson Lib
	// // ObjectMapper mapper = new ObjectMapper();
	// // json = mapper.writeValueAsString(person);
	//
	// // 5. set json to StringEntity
	// StringEntity se = new StringEntity(json);
	//
	// // 6. set httpPost Entity
	// httpPost.setEntity(se);
	//
	// // 7. Set some headers to inform server about the type of the
	//
	// String authorizationString =
	// "Bearer B0F986A7-4FA1-4EE8-AB6B-F1767DB3DE93";
	//
	// // content
	// httpPost.setHeader("Accept", "application/json");
	// httpPost.setHeader("Content-type", "application/json");
	// httpPost.setHeader("Authorization", authorizationString);
	//
	// // 8. Execute POST request to the given URL
	// HttpResponse httpResponse = httpclient.execute(httpPost);
	//
	// // 9. receive response as inputStream
	// inputStream = httpResponse.getEntity().getContent();
	//
	// StatusLine status = httpResponse.getStatusLine();
	//
	// if (status.getStatusCode() >= 300) {
	// throw new HttpResponseException(status.getStatusCode(),
	// status.getReasonPhrase());
	// } else if (status.getStatusCode() == 200) {
	//
	// PreferenceConnector.writeString(this,
	// PreferenceConnector.EMAIL, editTextUserName.getText()
	// .toString().trim());
	//
	// return "200";
	// } else
	// return null;
	//
	// } catch (Exception e) {
	// Log.d("InputStream", e.getLocalizedMessage());
	// }
	//
	// // 11. return result
	// return result;
	// }

//	private void getData() {
//		DefaultHttpClient dhttpclient = new DefaultHttpClient();
//
//		String username = "abc";
//		String password = "def";
//		String host = "abc.example.com";
//		String uri = "http://abc.example.com/protected";
//
//		try {
//			dhttpclient.getCredentialsProvider().setCredentials(
//					new AuthScope(host, AuthScope.ANY_PORT),
//					new UsernamePasswordCredentials(Constants.USERNAME,
//							Constants.PASSWORD));
//			HttpGet dhttpget = new HttpGet(uri);
//
//			System.out
//					.println("executing request " + dhttpget.getRequestLine());
//			HttpResponse dresponse = dhttpclient.execute(dhttpget);
//
//			System.out.println(dresponse.getStatusLine());
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			dhttpclient.getConnectionManager().shutdown();
//		}
//
//	}

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
