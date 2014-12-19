package com.giftomaticapp.gift_o_matic;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

public class BrowseForumActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_browse_forum);
		
		final ListView lv = (ListView) findViewById(R.id.thread_list);
		String url = "http://api.giftomaticapp.com/thread";
		JsonArrayRequest request = new JsonArrayRequest(url,
				new Response.Listener<JSONArray>() {
					@Override
					public void onResponse(JSONArray arr) {
						try {
							final List<ForumThread> threads = ForumThread.toThreadList(arr);
							ThreadListAdapter adapter = new ThreadListAdapter(BrowseForumActivity.this, threads);
							lv.setAdapter(adapter);
							
							lv.setOnItemClickListener(new OnItemClickListener() {
								@Override
								public void onItemClick(AdapterView<?> parent,
										View v, int position, long id) {
									Intent intent = new Intent(BrowseForumActivity.this, ViewForumActivity.class);
									intent.putExtra("threadId", threads.get(position).id);
									startActivity(intent);
								}
							});
							
						} catch (JSONException ex) {
							ex.printStackTrace();
						}
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError err) {
						Toast.makeText(BrowseForumActivity.this, "Connection error: connected to the internet?", Toast.LENGTH_SHORT).show();
					}
				});
		NetworkSingleton.getInstance(this).addToRequestQueue(request);
	}
	
}
