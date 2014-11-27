package com.giftomaticapp.giftomatic;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class CustomTags extends BaseAdapter {
	private Context mContext;
	private final List<String> tags;

	public CustomTags(Context c, List<String> tags) {
		mContext = c;
		this.tags = tags;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return tags.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return tags.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return getItem(position).hashCode();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View grid;
		LayoutInflater inflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		String tag = (String) getItem(position);
		if (convertView == null) {  
			grid = new View(mContext);
			grid = inflater.inflate(R.layout.grid_single, parent, false);
		} else {
			grid = convertView;
		}
		final TextView teView = (TextView) grid.findViewById(R.id.textView);
		return grid;
	}
}
