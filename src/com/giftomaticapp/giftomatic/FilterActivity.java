package com.giftomaticapp.giftomatic;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.InjectView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

//just for testing to show first item in technolgy not yet injected in the main page as the button itself is not yet fixed and the api is not yet fixed
public class FilterActivity extends Activity {
	@InjectView(R.id.randomImage)
	ImageView image;
	@InjectView(R.id.item_name)
	TextView itemName;
	@InjectView(R.id.item_description)
	TextView itemDesc;
	@InjectView(R.id.item_price_range)
	TextView itemPriceRange;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_filter);
		ButterKnife.inject(this);
		getRandom();
	}

	public void getRandom() {
		String url = "http://api.giftomaticapp.com/item/tag?technology";
		JsonObjectRequest request = new JsonObjectRequest(Method.GET, url,
				null, new Response.Listener<JSONObject>() {
					public void onResponse(JSONObject res) {
						try {
							String imgUrl = "http://api.giftomaticapp.com/img/"
									+ res.getString("imgUrl");
							Glide.with(FilterActivity.this).load(imgUrl)
									.into(image);
							itemName.setText(res.getString("name"));
							itemDesc.setText(res.getString("description"));
							itemPriceRange.setText("Start from Rp. "
									+ res.getString("minPrice"));
						} catch (JSONException e) {
							Toast.makeText(FilterActivity.this,
									"Failed to parse json", Toast.LENGTH_SHORT)
									.show();
						}
					}
				}, new Response.ErrorListener() {
					public void onErrorResponse(VolleyError err) {
						Toast.makeText(FilterActivity.this,
								"Not connected to the internet",
								Toast.LENGTH_SHORT).show();
					}
				});
		NetworkSingleton.getInstance(this).addToRequestQueue(request);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.filter, menu);
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

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_filter,
					container, false);
			return rootView;
		}
	}
}
