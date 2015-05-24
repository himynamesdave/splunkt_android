package com.splunkstart.splunk.dao;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.splunkstart.android.asynchttp.JsonHttpResponseHandler;
import com.splunkstart.splunk.model.LeadData;

public interface ILeadDataDao {

	// public void getAllCategoryByTournamentID(long tournamentID,
	// JsonHttpResponseHandler responseHandler) throws Exception;

	public ArrayList<String> getCategoryFronJSONArray(JSONArray json)
			throws Exception;

	public void getEventDataByEventID(String eventId,
			JsonHttpResponseHandler responseHandler) throws Exception;

	public String getEventDataByEventID(String eventId) throws Exception;

	//
	public void getAllCompetition(JsonHttpResponseHandler responseHandler)
			throws Exception;

	public void getAllShirtSlogan(JsonHttpResponseHandler responseHandler)
			throws Exception;

	public void getAllShirtSize(JsonHttpResponseHandler responseHandler)
			throws Exception;

	public void getAllUseCase(JsonHttpResponseHandler responseHandler)
			throws Exception;

	public void CreateLeadData(LeadData leadData,
			JsonHttpResponseHandler responseHandler) throws Exception;

}
