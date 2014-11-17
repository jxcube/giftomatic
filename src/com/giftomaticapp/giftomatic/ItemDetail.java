package com.giftomaticapp.giftomatic;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;

import butterknife.InjectView;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ItemDetail extends Activity {
	@InjectView(R.id.randomImage) ImageView image;
	@InjectView(R.id.item_name) TextView itemName;
	@InjectView(R.id.item_description) TextView itemDesc;
	@InjectView(R.id.item_price_range) TextView itemPriceRange;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_item_detail);
		getItem();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.item_detail, menu);
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
	public void getItem() {
		
		Intent intent = getIntent();
		int itemId = intent.getIntExtra("itemId", -1);
		String url = "http://api.giftomaticapp.com/item/" + itemId;
		JsonObjectRequest request = new JsonObjectRequest(Method.GET, url, null, new Response.Listener<JSONObject>() {
			public void onResponse(JSONObject res) {
				try {
					Log.d("######", res.toString());
					Item item = Item.fromJson(res);
					show(item);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

		}, new Response.ErrorListener() {
			public void onErrorResponse(VolleyError err) {
				Toast.makeText(ItemDetail.this, "Not connected to the internet", Toast.LENGTH_SHORT).show();
			}
		});
		NetworkSingleton.getInstance(this).addToRequestQueue(request);
	}
	
	private void show(Item item) {
		String url = "http://api.giftomaticapp.com/img/" + item.imageUrl;
		Glide.with(this)
			.load(url)
			.centerCrop()
			.crossFade()
			.placeholder(R.drawable.logo)
			.into(image);
		itemName.setText(item.name);
		itemDesc.setText(item.description);
		itemPriceRange.setText("Start from Rp. " + item.minPrice);
	}
	
}

