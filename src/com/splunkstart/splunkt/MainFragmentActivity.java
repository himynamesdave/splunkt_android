package com.splunkstart.splunkt;

import java.util.ArrayList;
import com.splunk.mint.Mint;
import com.splunkstart.android.util.Constants;
import com.splunkstart.splunk.fragment.BarCodeScannerFragment;
import com.splunkstart.splunk.fragment.CompetitionFragment;
import com.splunkstart.splunk.fragment.LeadCaptureMethodFragment;
import com.splunkstart.splunk.fragment.LoginFragment;
import com.splunkstart.splunk.fragment.ManualLeadEntryFragment;
import com.splunkstart.splunk.fragment.NotesViewFragment;
import com.splunkstart.splunk.fragment.ShirtSizeFragment;
import com.splunkstart.splunk.fragment.ShirtSloganFragment;
import com.splunkstart.splunk.fragment.SuccessFragment;
import com.splunkstart.splunk.fragment.UseCaseFragment;
import com.splunkstart.splunkt.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

public class MainFragmentActivity extends FragmentActivity implements
		OnClickListener {

	// public MainFragmentActivity() {
	// super(R.string.app_name);
	//
	// }

	private final String tag = "MainFragmentActivity";

	public static FragmentManager fm;
	public static FrameLayout frame;
	public static Fragment previousFragment;
	// public static Button home, myJournery, mySwag, myFriends, settings,

	// myMessages;
	public static ArrayList<Fragment> fragmentsOnStack = new ArrayList<Fragment>();

	public static Activity currActivity;

	public static int fragment = 0;

	// private static LinearLayout linearLayoutBack;
	// private LinearLayout linearLayoutHome;
	// private LinearLayout linearLayoutAbout;
	// private LinearLayout linearLayoutLocation;
	// /
	private LinearLayout linearLayoutVermisst;
	private ImageView imageViewAddPet;
	// /
	public static LinearLayout linearLayoutMeineApp;

	public static PopupWindow pw;

	// public static ChatFragment chatFragment;
	// public static PrivateChatFragment privateChatFragment;
	//
	// private LinearLayout containerBody;
	// Notfication
	private FrameLayout frameLayoutCount_notification;
	private TextView textViewCount_notification;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

		setContentView(R.layout.activity_main_fragment);

		fragment = Constants.LoginFragment;

		fm = getSupportFragmentManager();

		// init();
		init();

		MainFragmentActivity.openFragmentbackStack(fragment);

		// /Splunk mint
		Mint.initAndStartSession(MainFragmentActivity.this, "374e8b84");

	}

	private void init() {

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		// hidePopupWindow();

		if (fragmentsOnStack.size() > 0) {

			for (int i = 0; i < fragmentsOnStack.size(); i++) {
				destroyFragment(fragmentsOnStack.get(i));
			}

			fragmentsOnStack = new ArrayList<Fragment>();
			// fragmentsOnStack.remove(fragmentsOnStack.get(i));
		}

		fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

	}

	//

	private void UpdateTextAndImage(int type) {

	}

	private void initializeViews() {
		frame = (FrameLayout) findViewById(R.id.fragment_content);

	}

	//
	public static void openFragment(int fragment) {

		switch (fragment) {

		// /
		// case Constants.CategoryListFragment:
		// openFragment(new CategoryListFragment());
		// break;
		// // /
		// case Constants.MatchListFragment:
		// openFragment(new MatchListFragment());
		//
		// break;
		//
		// // /
		// case Constants.TournamentListFragment:
		// openFragment(new TournamentListFragment());
		// break;
		//
		// case Constants.MatchScoreFragment:
		// openFragment(new MatchScoreFragment());
		// break;
		//
		// case Constants.PlayerRoleEnrollmentFragment:
		// openFragment(new PlayerRoleEnrollmentFragment());
		// break;

		//

		case Constants.LoginFragment:
			openFragment(new LoginFragment());
			break;

		case Constants.CompetitionFragment:
			openFragment(new CompetitionFragment());
			break;

		case Constants.LeadCaptureMethodFragment:
			openFragment(new LeadCaptureMethodFragment());
			break;

		case Constants.ManualLeadEntryFragment:
			openFragment(new ManualLeadEntryFragment());
			break;

		case Constants.NotesViewFragment:
			openFragment(new NotesViewFragment());
			break;

		case Constants.ShirtSizeFragment:
			openFragment(new ShirtSizeFragment());
			break;

		case Constants.ShirtSloganFragment:
			openFragment(new ShirtSloganFragment());
			break;

		case Constants.SuccessFragment:
			openFragment(new SuccessFragment());
			break;

		case Constants.UseCaseFragment:
			openFragment(new UseCaseFragment());
			break;

		case Constants.BarCodeScannerFragment:
			openFragment(new BarCodeScannerFragment());
			break;

		default:
			break;
		}
	}

	public static void openFragmentbackStack(int fragment) {

		switch (fragment) {

		// /

		// case Constants.CategoryListFragment:
		// openFragmentbackStack(new CategoryListFragment());
		// break;
		// // /
		// case Constants.MatchListFragment:
		// openFragmentbackStack(new MatchListFragment());
		//
		// break;
		//
		// // /
		// case Constants.TournamentListFragment:
		// openFragmentbackStack(new TournamentListFragment());
		// break;
		//
		// case Constants.MatchScoreFragment:
		// openFragmentbackStack(new MatchScoreFragment());
		// break;
		//
		// case Constants.PlayerRoleEnrollmentFragment:
		// openFragmentbackStack(new PlayerRoleEnrollmentFragment());
		// break;

		//
		case Constants.LoginFragment:
			openFragment(new LoginFragment());
			break;

		case Constants.CompetitionFragment:
			openFragmentbackStack(new CompetitionFragment());
			break;

		case Constants.LeadCaptureMethodFragment:
			openFragmentbackStack(new LeadCaptureMethodFragment());
			break;

		case Constants.ManualLeadEntryFragment:
			openFragmentbackStack(new ManualLeadEntryFragment());
			break;

		case Constants.NotesViewFragment:
			openFragmentbackStack(new NotesViewFragment());
			break;

		case Constants.ShirtSizeFragment:
			openFragmentbackStack(new ShirtSizeFragment());
			break;

		case Constants.ShirtSloganFragment:
			openFragmentbackStack(new ShirtSloganFragment());
			break;

		case Constants.SuccessFragment:
			openFragmentbackStack(new SuccessFragment());
			break;

		case Constants.UseCaseFragment:
			openFragmentbackStack(new UseCaseFragment());
			break;

		case Constants.BarCodeScannerFragment:
			openFragmentbackStack(new BarCodeScannerFragment());
			break;

		default:
			break;
		}
	}

	private static void openFragment(Fragment frag) {
		// frame.removeAllViews();
		if (previousFragment != null) {
			destroyFragment(previousFragment);

		}

		if (fragmentsOnStack.size() > 0) {
			for (int i = 0; i < fragmentsOnStack.size(); i++) {
				destroyFragment(fragmentsOnStack.get(i));
			}

			fragmentsOnStack = new ArrayList<Fragment>();
		}

		FragmentTransaction ft = fm.beginTransaction();
		// /ft.setCustomAnimations(R.anim.slide_in_right,
		// R.anim.slide_out_left);
		// ft.addToBackStack(null);
		ft.add(R.id.fragment_content, frag);
		ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);

		ft.commit();
		previousFragment = frag;
	}

	private static void openFragmentbackStack(Fragment frag) {
		// frame.removeAllViews();

		FragmentTransaction ft = fm.beginTransaction();

		if (fragmentsOnStack.size() > 1) {
			ft.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
		}
		ft.addToBackStack(null);
		ft.add(R.id.fragment_content, frag);
		ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);

		ft.commit();
		fragmentsOnStack.add(frag);
	}

	public static void destroyFragment(Fragment frag) {
		// frame.removeAllViews();

		FragmentTransaction ft = fm.beginTransaction();
		ft.remove(frag);
		ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);

		ft.commit();
	}

	public static void onBackButtonPressed() {

		try {
			// InputMethodManager imm = (InputMethodManager)
			// getSystemService(Context.INPUT_METHOD_SERVICE);
			//
			// if (imm.isActive()) {
			// imm.hideSoftInputFromWindow(
			// linearLayoutVermisst.getWindowToken(), 0);
			// // return;
			// }
		} catch (Exception ex) {
		}

		if (fragmentsOnStack != null && fragmentsOnStack.size() > 1) {
			int index = fragmentsOnStack.size() - 1;
			destroyFragment(fragmentsOnStack.get(index));
			fragmentsOnStack.remove(index);

		}
	}

	@Override
	public void onBackPressed() {

		if (fragmentsOnStack.size() > 1) {
			int index = fragmentsOnStack.size() - 1;
			destroyFragment(fragmentsOnStack.get(index));
			fragmentsOnStack.remove(index);

		} else {
			// Exit();

			try {
				fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
			} catch (Exception ex) {
			}

			// hidePopupWindow();
			MainFragmentActivity.this.finish();
		}
	}

	private void Exit() {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

		alertDialogBuilder
				.setMessage("Are you sure you want to exit?")
				.setCancelable(false)
				.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {

								try {
									fm.popBackStack(
											null,
											FragmentManager.POP_BACK_STACK_INCLUSIVE);
								} catch (Exception ex) {
								}

								MainFragmentActivity.this.finish();
							}
						})
				.setNegativeButton("No", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				});

		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();

	}

	// /
	// Resume the session
	public void onResume() {
		super.onResume();
		Mint.startSession(MainFragmentActivity.this);
	}

	// Stop the session
	public void onStop() {
		super.onStop();
		Mint.closeSession(MainFragmentActivity.this);
	}

}
