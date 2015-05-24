package com.splunkstart.splunk.adapter;

import java.util.ArrayList;

import com.splunkstart.android.util.Constants;
import com.splunkstart.android.util.DateUtil;
import com.splunkstart.splunkt.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ListViewSplunkCollectionCustomizeAdapter extends BaseAdapter {

	public Context context;
	public LayoutInflater inflater;
	private ArrayList<String> dataList;

	private ArrayList<String> selectDataList = new ArrayList<String>();

	public ListViewSplunkCollectionCustomizeAdapter(Context context,
			ArrayList<String> dataList) {
		super();
		this.context = context;
		this.dataList = dataList;
		this.inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	}

	public int getCount() {
		// TODO Auto-generated method stub
		return dataList.size();
	}

	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();

			convertView = inflater.inflate(
					R.layout.listview_splunk_collections, null);

			holder.textViewTitle = (TextView) convertView
					.findViewById(R.id.textViewTitle);
			holder.imageViewCheck = (ImageView) convertView
					.findViewById(R.id.imageViewCheck);

			convertView.setTag(holder);

		} else
			holder = (ViewHolder) convertView.getTag();

		try {

			holder.textViewTitle.setText(dataList.get(position));

			Boolean isFound = false;
			for (String string : selectDataList) {

				if (dataList.get(position).equals(string)) {
					holder.imageViewCheck
							.setImageResource(R.drawable.check);
					holder.imageViewCheck.setVisibility(View.VISIBLE);
					isFound = true;
					break;
				}
			}

			if (!isFound) {
				holder.imageViewCheck.setVisibility(View.GONE);
			}

		} catch (Exception ex) {
		}

		// if (position % 2 == 0) {
		// convertView.setBackgroundColor(context.getResources().getColor(
		// R.color.Khaki1));
		// } else {
		// convertView.setBackgroundColor(context.getResources().getColor(
		// R.color.GreenYellow1));
		//
		// }

		return convertView;
	}

	public static class ViewHolder {

		private TextView textViewTitle;
		private ImageView imageViewCheck;
	}

	public void setData(ArrayList<String> dataList) {
		// TODO Auto-generated method stub

		this.dataList = dataList;
	}

	public void setSelectedData(ArrayList<String> selectDataList) {
		// TODO Auto-generated method stub

		this.selectDataList = selectDataList;
	}

}