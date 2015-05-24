package com.splunkstart.splunk.dao.impl;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.auth.BasicScheme;
import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.support.v4.app.FragmentActivity;

import com.splunkstart.android.asynchttp.JsonHttpResponseHandler;
import com.splunkstart.android.asynchttp.RequestParams;
import com.splunkstart.android.http.ConnectionManager;
import com.splunkstart.android.http.RequestMethod;
import com.splunkstart.android.util.Constants;
import com.splunkstart.splunk.dao.ILeadDataDao;
import com.splunkstart.splunk.model.LeadData;
import com.splunkstart.splunk.restclient.LeadDataRestClient;

public class LeadDataDao implements ILeadDataDao {

	public Context context;

	public LeadDataDao(Context context) {
		// TODO Auto-generated constructor stub

		this.context = context;
	}

	@Override
	public String getEventDataByEventID(String eventId) throws Exception {
		// TODO Auto-generated method stub

		String response = "";
		try {

			// /https://54.76.14.205:8089/servicesNS/nobody/splunkt_splunk_app/storage/collections/data/eventcollection?query=%7B%22addEventId%22:%20%221%22%7D

			String subUrl = "{\"addEventId\":\"" + eventId + "\"}";

			String eventUrl = URLEncoder.encode(subUrl);

			eventUrl = "https://INDEXER:8089/servicesNS/nobody/splunkt_splunk_app/storage/collections/data/eventcollection?query="
					+ eventUrl;

			// subUrl = LeadDataRestClient.BASE_URL + subUrl;
			ConnectionManager connectionManager = new ConnectionManager(
					eventUrl);
			//
			// connectionManager.AddHeader("", value)

			//
			// HttpGet request = new HttpGet(...);
			// request.setHeader("Authorization",
			// "Basic "+Base64.encodeBytes("login:password".getBytes()));
			// LeadDataRestClient.get(context, eventUrl, new RequestParams(),
			// responseHandler);
			// LeadDataRestClient.get(eventUrl, responseHandler);
			response = connectionManager.sendRequest(RequestMethod.GET);

		} catch (Exception ex) {

			throw new Exception(ex.getMessage());
		}

		return response;
	}

	@Override
	public void getEventDataByEventID(String eventId,
			JsonHttpResponseHandler responseHandler) throws Exception {
		// TODO Auto-generated method stub
		try {

			// /https://54.76.14.205:8089/servicesNS/nobody/splunkt_splunk_app/storage/collections/data/eventcollection?query=%7B%22addEventId%22:%20%221%22%7D

			String subUrl = "{\"addEventId\":\"" + eventId + "\"}";

			String eventUrl = URLEncoder.encode(subUrl);

			eventUrl = "eventcollection?query=" + eventUrl;

			// subUrl = LeadDataRestClient.BASE_URL + subUrl;
			// ConnectionManager connectionManager = new
			// ConnectionManager(subUrl);
			//
			// connectionManager.AddHeader("", value)

			//
			// HttpGet request = new HttpGet(...);
			// request.setHeader("Authorization",
			// "Basic "+Base64.encodeBytes("login:password".getBytes()));
			LeadDataRestClient.get(eventUrl, responseHandler);
			// LeadDataRestClient.get(eventUrl, responseHandler);

			// public LeadDataRestClient(String USERNAME, String PASSWORD)
			// {
			// UsernamePasswordCredentials credentials = new
			// UsernamePasswordCredentials(
			// Constants.USERNAME, Constants.PASSWORD);
			//
			// Header header = BasicScheme.authenticate(credentials, "UTF-8",
			// false);
			//
			// Header[] headers = { header };
			//
			// RequestParams params = new RequestParams();

		} catch (Exception ex) {

			throw new Exception(ex.getMessage());
		}
	}

	@Override
	public void getAllCompetition(JsonHttpResponseHandler responseHandler)
			throws Exception {
		// TODO Auto-generated method stub
		try {
			String subUrl = "competitioncollection";
			LeadDataRestClient.get(subUrl, responseHandler);

		} catch (Exception ex) {

			throw new Exception(ex.getMessage());
		}
	}

	@Override
	public void getAllShirtSlogan(JsonHttpResponseHandler responseHandler)
			throws Exception {
		// TODO Auto-generated method stub
		try {
			String subUrl = "shirtslogancollection";
			LeadDataRestClient.get(subUrl, responseHandler);

		} catch (Exception ex) {

			throw new Exception(ex.getMessage());
		}
	}

	@Override
	public void getAllShirtSize(JsonHttpResponseHandler responseHandler)
			throws Exception {
		// TODO Auto-generated method stub
		try {
			String subUrl = "shirtsizecollection";
			LeadDataRestClient.get(subUrl, responseHandler);

		} catch (Exception ex) {

			throw new Exception(ex.getMessage());
		}
	}

	@Override
	public void getAllUseCase(JsonHttpResponseHandler responseHandler)
			throws Exception {
		// TODO Auto-generated method stub
		try {
			String subUrl = "usecasecollection";
			LeadDataRestClient.get(subUrl, responseHandler);

		} catch (Exception ex) {

			throw new Exception(ex.getMessage());
		}
	}

	@Override
	public ArrayList<String> getCategoryFronJSONArray(JSONArray json)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void CreateLeadData(LeadData leadData,
			JsonHttpResponseHandler responseHandler) throws Exception {
		// TODO Auto-generated method stub
		try {

			String url = "https://INDEXER:8089/services/receivers/simple?source=splunkt_splunk_app&sourcetype=event_lead&index=splunkevents";

			JSONObject paramsLeads = new JSONObject();
			JSONObject paramsQR = new JSONObject();

			paramsLeads.put("company_email",
					Constants.leadData.getCompany_email());
			paramsLeads.put("first_name", Constants.leadData.getFirst_name());
			paramsLeads.put("second_name", Constants.leadData.getSecond_name());
			paramsLeads.put("job_title", Constants.leadData.getJob_title());

			if (Constants.leadData.getQr_data() != null
					&& Constants.leadData.getQr_data().length() > 0) {
				if (Constants.leadData.getQr_data().contains("BEGIN:VCARD")) {
					String[] fullNameArr = Constants.leadData.getQr_data()
							.split("\n");

					for (String fullStr : fullNameArr) {

						String value = "";
						String key = "";

						int index = fullStr.indexOf(":");
						if (index > 0) {
							key = fullStr.substring(0, index);
							value = fullStr.substring(index + 1,
									fullStr.length());
						}

						paramsQR.put(key, value);

					}

				} else {
					String[] fullNameArr = Constants.leadData.getQr_data()
							.split(",");
					int i = 0;
					for (String fullStr : fullNameArr) {

						paramsQR.put("field" + i, fullStr);
						i++;
					}
				}
			}

			JSONObject params = new JSONObject();

			Date date = new Date();

			params.put("event_id", Constants.leadData.getEvent_ID());
			params.put("device_timestamp", date.getTime());
			params.put("staff_email", Constants.leadData.getStaff_email());
			params.put("qr_data", paramsQR);
			params.put("lead_details", paramsLeads);

			params.put("shirt_slogan",
					Constants.leadData.getShirt_slogan());
			params.put("shirt_size", Constants.leadData.getShirt_size());

			params.put("use_case", Constants.leadData.getUse_case());
			params.put("competition", Constants.leadData.getCompe66on());
			params.put("notes", Constants.leadData.getNote());
			params.put("shirt_size", Constants.leadData.getShirt_size());
			
			//

			params.put("device_type", Constants.leadData.getDevice_type());
			params.put("device_id", Constants.leadData.getDevice_id());
			
			///

			HttpEntity httpEntity = null;
			try {
				httpEntity = new ByteArrayEntity(params.toString()
						.getBytes("UTF8"));

			} catch (Exception e) {
				// TODO: handle exception
			}

			LeadDataRestClient.post(context, url, httpEntity,
					paramsLeads.toString(), responseHandler);

		} catch (Exception ex) {

			throw new Exception(ex.getMessage());
		}
	}
}
