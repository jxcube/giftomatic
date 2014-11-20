package com.giftomaticapp.giftomatic;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.InjectView;
import butterknife.OnClick;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.AdapterView.OnItemClickListener;

public class Filter extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_filter);
		// LinkedList<String> Tags = new LinkedList();
		// getTagsData();

	}

	@OnClick(R.id.technology)
	public void getFiltered() {

	}

	// private void getTagsData() {
	// String url = "http://api.giftomaticapp.com/tags";
	// JsonArrayRequest request = new JsonArrayRequest(url, new
	// Response.Listener<JSONArray>() {
	// @Override
	// public void onResponse(JSONArray response) {
	// try {
	// List<String> tags = new ArrayList<String>();
	// for (int i = 0; i < response.length(); i++) {
	// JSONObject item = response.getJSONObject(i);
	// tags.add(item.toString);
	// }
	// requestTags(items);
	// } catch (JSONException e) {
	// e.printStackTrace();
	// }
	// }
	// }, new Response.ErrorListener() {
	// @Override
	// public void onErrorResponse(VolleyError err) {
	//
	// }
	// });
	// NetworkSingleton.getInstance(this).addToRequestQueue(request);
	// }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.filter, menu);
		return true;
	}

}
