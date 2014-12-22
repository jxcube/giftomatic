package com.giftomaticapp.giftomatic;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

public class GiftGallery extends Activity {

	GridView gridView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gift_gallery);

		// request items
		Intent intent = getIntent();

		if (intent.hasExtra("tags")) {
			String fromIntent = intent.getStringExtra("tags");
			getItemData(fromIntent);
		}else{
		getItemData();}
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
		// int id = item.getItemId();
		// if (id == R.id.action_settings) {
		// return true;
		// }
		return super.onOptionsItemSelected(item);
	}

	public void requestItems(final List<Item> items) {
		// TODO
		CustomGrid adapter = new CustomGrid(this, items);
		gridView = (GridView) findViewById(R.id.grid);
		gridView.setAdapter(adapter);
		gridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				Intent intent = new Intent(GiftGallery.this, ItemDetail.class);
				intent.putExtra("itemId", items.get(position).id);
				startActivity(intent);
			}
		});
	}

	public void getItemData(String specified) {
		String url;
		if (!specified.contains("\\d")) {
			url = "http://api.giftomaticapp.com/item?tag=";
		} else {
			url = "http://api.giftomaticapp.com/item?budget=";
		}
		url += specified;
		JsonArrayRequest request = new JsonArrayRequest(url,
				new Response.Listener<JSONArray>() {
					@Override
					public void onResponse(JSONArray response) {
						try {
							List<Item> items = new ArrayList<Item>();
							for (int i = 0; i < response.length(); i++) {
								JSONObject item = response.getJSONObject(i);
								items.add(new Item(item));
							}
							requestItems(items);
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

	public void getItemData() {
		String url = "http://api.giftomaticapp.com/item";
		JsonArrayRequest request = new JsonArrayRequest(url,
				new Response.Listener<JSONArray>() {
					@Override
					public void onResponse(JSONArray response) {
						try {
							List<Item> items = new ArrayList<Item>();
							for (int i = 0; i < response.length(); i++) {
								JSONObject item = response.getJSONObject(i);
								items.add(new Item(item));
							}
							requestItems(items);
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
