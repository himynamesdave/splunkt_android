package com.splunkstart.base.fragment;

import com.splunkstart.splunkt.R;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class BaseListFragment extends Fragment implements OnClickListener {

	public TextView textViewHeader;
	public ListView listView;
	public Button buttonSubmit;

	public Boolean isLoading = false;

	//
	// public LinearLayout linearLayoutStatus;
	// public ImageView imageViewStatus;
	// public TextView textViewStatus;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.baselistfragment, container,
				false);

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
	public ImageView imageViewLeft, imageViewRight, imageViewTitle,
			imageViewRight1;

	public LinearLayout linearLayoutRight1, linearLayoutLeft;

	// private Images images;

	public void initNavigationBar(View view) {

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

	// private void showRquestedMemberList() {
	//
	// MainFragmentActivity
	// .openFragmentbackStack(Constants.OrganizationMemberListFragment);
	// }

	public void init(View view) {
		listView = (ListView) view.findViewById(R.id.listView);
		textViewHeader = (TextView) view.findViewById(R.id.textViewHeader);

		// /

		buttonSubmit = (Button) view.findViewById(R.id.buttonSubmit);
		buttonSubmit.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {

		if (v == buttonSubmit) {
			onClickSubmitButton();
		}
	}

	public void onClickSubmitButton() {

	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();

	}

	public void refreshListView() {
		// GetAllGroups(limit, skip);
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);

		initListViewAdapter();

		FillAdapter();

		getAllData();
	}

	public void initListViewAdapter() {

	}

	public void FillAdapter() {

	}

	public void getAllData() {

	}

}
