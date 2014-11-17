package com.giftomaticapp.giftomatic;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class CustomGrid extends BaseAdapter {
	private Context mContext;
	private final List<Item> items;

	public CustomGrid(Context c, List<Item> items) {
		mContext = c;
		this.items = items;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return items.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return items.get(position);
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
		Item item = (Item) getItem(position);
		String url = "http://api.giftomaticapp.com/img/" + item.imageUrl;

		if (convertView == null) {  
			grid = new View(mContext);
			grid = inflater.inflate(R.layout.grid_single, parent, false);
		} else {
			grid = convertView;
		}
		final ImageView imageView = (ImageView) grid.findViewById(R.id.item_image);
		Glide.with(mContext).load(url).centerCrop().placeholder(R.drawable.logo).into(imageView);

		return grid;
	}
}
