package com.splunkstart.android.http;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;

import com.splunkstart.android.util.Base64;
import com.splunkstart.android.util.Constants;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ConnectionManager {

	private ArrayList<NameValuePair> params;
	private ArrayList<NameValuePair> headers;
	private String url;
	public static Context context;

	File tempDir;

	public ConnectionManager(String url) {
		this.url = url;
		params = new ArrayList<NameValuePair>();
		headers = new ArrayList<NameValuePair>();
	}

	public static String creatConnection(String url) {
		String xmlResponse = "";
		try {
			URL myurl = new URL(url);
			URLConnection connection = myurl.openConnection();
			// set timeout = 10 seconds
			connection.setConnectTimeout(10000);
			BufferedReader in = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				System.out.println(inputLine);
				xmlResponse = xmlResponse.concat(inputLine);
			}
			in.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return xmlResponse;
	}

	public String sendRequest(RequestMethod method) throws Exception {
		return callServer(method);
	}

	public void AddParam(String name, String value) {
		params.add(new BasicNameValuePair(name, value));
	}

	public void AddHeader(String name, String value) {
		headers.add(new BasicNameValuePair(name, value));
	}

	public String callServer(RequestMethod method) throws Exception {
		String xmlResponse = "";

		switch (method) {
		case GET: {
			// add parameters
			String combinedParams = "";
			if (!params.isEmpty()) {
				combinedParams += "?";
				for (NameValuePair p : params) {
					String paramString = p.getName() + "="
							+ URLEncoder.encode(p.getValue(), "UTF-8");
					if (combinedParams.length() > 1) {
						combinedParams += "&" + paramString;
					} else {
						combinedParams += paramString;
					}
				}
			}

			HttpGet request = new HttpGet(url + combinedParams);

			// add headers
			for (NameValuePair h : headers) {
				request.addHeader(h.getName(), h.getValue());

			}

			String credential = Constants.USERNAME + ":" + Constants.PASSWORD;
			request.setHeader("Authorization",
					"Basic " + Base64.encodeBytes(credential.getBytes()));

			xmlResponse = executeRequest(request, url);
			break;
		}
		case POST1: {

			String combinedParams = "";
			if (!params.isEmpty()) {
				combinedParams += "?";
				for (NameValuePair p : params) {
					String paramString = p.getName() + "="
							+ URLEncoder.encode(p.getValue(), "UTF-8");
					if (combinedParams.length() > 1) {
						combinedParams += "&" + paramString;
					} else {
						combinedParams += paramString;
					}
				}
			}

			// String url1 =
			// "http://chatbotapi.azurewebsites.net/Api/Chat/InitiateChat?name=&email=&ipAddress=192.168.1.232&organizationId=1&deviceId=353926066335026";
			HttpPost request = new HttpPost(url + combinedParams);
			// HttpPost request = new HttpPost(url1);

			// HttpPost request = new HttpPost(url);

			// add headers
			for (NameValuePair h : headers) {
				request.addHeader(h.getName(), h.getValue());

			}

			xmlResponse = executeRequest(request, url);
			break;
		}

		case POST: {
			HttpPost request = new HttpPost(url);

			// add headers
			for (NameValuePair h : headers) {
				StringEntity entity = new StringEntity(h.getValue(), "UTF-8");
				request.setEntity(entity);
				request.addHeader("Accept", "application/xml");
				request.addHeader("Content-Type", "application/xml");

			}

			if (!params.isEmpty()) {
				request.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
			}

			xmlResponse = executeRequest(request, url);
			break;
		}
		case PUT: {
			HttpPut request = new HttpPut(url);

			// add headers
			for (NameValuePair h : headers) {
				StringEntity entity = new StringEntity(h.getValue(), "UTF-8");
				request.setEntity(entity);
				request.addHeader("Accept", "application/xml");
				request.addHeader("Content-Type", "application/xml");

			}

			if (!params.isEmpty()) {
				request.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
			}

			xmlResponse = executeRequest(request, url);
			break;
		}
		default:
			xmlResponse = "No Response Returned";
			break;
		}
		return xmlResponse;
	}

	private String executeRequest(HttpUriRequest request, String url)
			throws Exception {
		// HttpClient client = new DefaultHttpClient();
		DefaultHttpClient client = new DefaultHttpClient();
		HttpParams params = client.getParams();
		// Set Connection TimeOut

		HttpConnectionParams.setConnectionTimeout(params, 6000);

		HttpResponse httpResponse;

		String xmlResponse = "";
		// try {
		httpResponse = client.execute(request);
		HttpEntity entity = httpResponse.getEntity();

		if (entity != null) {

			InputStream instream = entity.getContent();
			xmlResponse = convertStreamToString(instream);

			// Closing the input stream will trigger connection release
			instream.close();
		}

		return xmlResponse;
	}

	private String convertStreamToString(InputStream is) {

		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

	public static boolean haveInternet(Context ctx) {
		NetworkInfo info = (NetworkInfo) ((ConnectivityManager) ctx
				.getSystemService(Context.CONNECTIVITY_SERVICE))
				.getActiveNetworkInfo();
		if (info == null || !info.isConnected()) {
			return false;
		}
		if (info.isRoaming()) {
			return false;
		}

		return true;
	}

}