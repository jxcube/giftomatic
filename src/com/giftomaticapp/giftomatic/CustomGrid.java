package com.giftomaticapp.giftomatic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomGrid extends BaseAdapter {
	private Context mContext;
	private final String[] images;
	private final int[] imageId; 

	public CustomGrid(Context c,String[] images, int[] imageId) {
		mContext = c;
		this.imageId = imageId;
		this.images = images;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return images.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return images[position];
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return imageId[position];
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View grid;
		LayoutInflater inflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		if (convertView == null) {  

			grid = new View(mContext);
			grid = inflater.inflate(R.layout.grid_single, null);
			TextView textView = (TextView) grid.findViewById(R.id.grid_text);
			ImageView imageView = (ImageView)grid.findViewById(R.id.grid_image);
			textView.setText(images[position]);
			imageView.setImageResource(imageId[position]);
		} else {
			grid = (View) convertView;
		}

		return grid;
	}
}
