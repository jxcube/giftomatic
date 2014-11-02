package com.giftomaticapp.giftomatic;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

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

		// request items
		getItemData();
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
	
	public void requestImages(List<Item> items) {
		// TODO
		CustomGrid adapter = new CustomGrid(this, items);
		gridView = (GridView) findViewById(R.id.grid);
		gridView.setAdapter(adapter);
	}

	public void getItemData() {
		String url = "http://api.giftomaticapp.com/item";
		JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
			@Override
			public void onResponse(JSONArray response) {
				try {
					List<Item> items = new ArrayList<Item>();
					for (int i = 0; i < response.length(); i++) {
						JSONObject item = response.getJSONObject(i);
						items.add(new Item(item));						
					}
					requestImages(items);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError err) {

			}
		});
		NetworkSingleton.getInstance(this).addToRequestQueue(request);
	}
}
