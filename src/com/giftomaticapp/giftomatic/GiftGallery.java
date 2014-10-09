package com.giftomaticapp.giftomatic;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;

public class GiftGallery extends Activity {

	GridView gridView;
	String[] images = {
		"item1",
		"item2",
		"item3",
		"item4"	
	};
	
	int [] imageId = {
			R.drawable.logo,
			R.drawable.logo,	
			R.drawable.logo,	
			R.drawable.logo,		
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gift_gallery);
		
		CustomGrid adapter = new CustomGrid(GiftGallery.this, images, imageId);
		gridView = (GridView) findViewById(R.id.grid);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.gift_gallery, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
