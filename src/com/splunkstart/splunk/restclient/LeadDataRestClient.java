package com.splunkstart.splunk.restclient;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.auth.BasicScheme;

import android.content.Context;

import com.splunkstart.android.asynchttp.AsyncHttpClient;
import com.splunkstart.android.asynchttp.AsyncHttpResponseHandler;
import com.splunkstart.android.asynchttp.RequestParams;
import com.splunkstart.android.util.Constants;

public class LeadDataRestClient {
	
	// http://54.76.14.205:8000/
	
//	public static final String BASE_URL = "https://54.76.14.205:8000/servicesNS/nobody/splunkt_splunk_app/storage/collections/data/";

	
	public static final String BASE_URL = "https://INDEXER:8089/servicesNS/nobody/splunkt_splunk_app/storage/collections/data/";

	private static AsyncHttpClient client = new AsyncHttpClient(true, 80, 443);

	// public LeadDataRestClient(String USERNAME, String PASSWORD)
	// {
	// UsernamePasswordCredentials credentials =
	// new UsernamePasswordCredentials(USERNAME,
	// PASSWORD);
	//
	// Header header = BasicScheme.authenticate(credentials, "UTF-8", false);
	//
	// Header[] headers = {header};
	//
	// RequestParams params = new RequestParams();
	//
	// client.get(context, url, headers, params, new AsyncHttpResponseHandler(){
	// }

	// UsernamePasswordCredentials credentials =
	// new UsernamePasswordCredentials(ApplicationConstants.userName,
	// ApplicationConstants.password);
	//
	// Header header = BasicScheme.authenticate(credentials, "UTF-8", false);
	//
	// Header[] headers = {header};
	//
	// RequestParams params = new RequestParams();
	//
	// httpClient.get(context, url, headers, params, new
	// AsyncHttpResponseHandler(){

	public static void get(String url, AsyncHttpResponseHandler responseHandler) {

		client.setBasicAuth(Constants.USERNAME, Constants.PASSWORD, true);
		client.get(getAbsoluteUrl(url), responseHandler);
	}

	public static void get(String url, RequestParams params,
			AsyncHttpResponseHandler responseHandler) {
		client.get(getAbsoluteUrl(url), params, responseHandler);
	}

	public static void get(Context context, String url, RequestParams params,
			AsyncHttpResponseHandler responseHandler) {
		client.get(context, getAbsoluteUrl(url), params, responseHandler);
	}

	public static void get(Context context, String url, Header[] headers,
			RequestParams params, AsyncHttpResponseHandler responseHandler) {
		client.get(context, getAbsoluteUrl(url), headers, params,
				responseHandler);
	}

	public static void post(Context context, String url, HttpEntity entity,
			String contentType, AsyncHttpResponseHandler responseHandler) {

		client.setBasicAuth(Constants.USERNAME, Constants.PASSWORD, true);
		client.post(context, url, entity, contentType, responseHandler);

	}

	private static String getAbsoluteUrl(String relativeUrl) {
		return BASE_URL + relativeUrl;
	}

}
