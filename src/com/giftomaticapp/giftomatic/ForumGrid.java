package com.giftomaticapp.giftomatic;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ForumGrid extends BaseAdapter{
	private Context mContext;
	private final String[] threads;

	public ForumGrid(Context c, String[] threads) {
		mContext = c;
		this.threads = threads;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return threads.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		LayoutInflater inflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View gridView;
		if (convertView == null) {  
			gridView = new View(mContext);
			gridView = inflater.inflate(R.layout.activity_forum_list, parent, false);
		} else {
			gridView = convertView;
		}
////		TextView textView = (TextView) gridView
////				.findViewById(R.);
//		textView.setText(threads[position]);
		return gridView;
	}
	
	
}
