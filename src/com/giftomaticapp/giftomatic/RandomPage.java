package com.giftomaticapp.giftomatic;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;

public class RandomPage extends Activity {
	
	@InjectView(R.id.randomImage) ImageView image;
	@InjectView(R.id.item_name) TextView itemName;
	@InjectView(R.id.item_description) TextView itemDesc;
	@InjectView(R.id.item_price_range) TextView itemPriceRange;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_random_page);
		ButterKnife.inject(this);
		getRandom();
	}
	
	@OnClick(R.id.get_random_btn)
	public void getRandom() {
		String url = "http://api.giftomaticapp.com/item/random";
		JsonObjectRequest request = new JsonObjectRequest(Method.GET, url, null, new Response.Listener<JSONObject>() {
			public void onResponse(JSONObject res) {
				try {
					String imgUrl = "http://api.giftomaticapp.com/img/" + res.getString("imgUrl");
					Glide.with(RandomPage.this).load(imgUrl).into(image);
					itemName.setText(res.getString("name"));
					itemDesc.setText(res.getString("description"));
					itemPriceRange.setText("Start from Rp. " + res.getString("minPrice"));
				} catch (JSONException e) {
					Toast.makeText(RandomPage.this, "Failed to parse json", Toast.LENGTH_SHORT).show();
				}
			}
		}, new Response.ErrorListener() {
			public void onErrorResponse(VolleyError err) {
				Toast.makeText(RandomPage.this, "Not connected to the internet", Toast.LENGTH_SHORT).show();
			}
		});
		NetworkSingleton.getInstance(this).addToRequestQueue(request);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.random_page, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
//		int id = item.getItemId();
//		if (id == R.id.action_settings) {
//			return true;
//		}
		return super.onOptionsItemSelected(item);
	}
}
