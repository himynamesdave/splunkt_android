package com.splunkstart.android.util;

import java.util.ArrayList;

import com.splunkstart.splunk.model.LeadData;

public class Constants {

	public static final String lang_en = "en";
	public static final String lang2_ru = "ru";
	public static final String lang2_ch = "ch";

	public static final int CategoryListFragment = 5001;
	public static final int MatchListFragment = 5002;
	public static final int TournamentListFragment = 5003;
	public static final int MatchScoreFragment = 5004;
	public static final int PlayerRoleEnrollmentFragment = 5005;

	// /

	public static final int LoginFragment = 101;
	public static final int CompetitionFragment = 102;
	public static final int LeadCaptureMethodFragment = 103;
	public static final int ManualLeadEntryFragment = 104;
	public static final int NotesViewFragment = 105;
	public static final int ShirtSizeFragment = 106;
	public static final int ShirtSloganFragment = 107;
	public static final int SuccessFragment = 108;
	public static final int UseCaseFragment = 109;
	public static final int BarCodeScannerFragment = 110;

	//

	public static class Config {
		public static final boolean DEVELOPER_MODE = false;
	}

	// /Tally

	// //

	public static ArrayList<String> shirtSloganList;
	public static ArrayList<String> shirtSizeList;
	public static ArrayList<String> useCaseList;
	public static ArrayList<String> competitionList;

	public static LeadData leadData;

	//
	public static String USERNAME = "USER";
	public static String PASSWORD = "PASSWORD";

}
