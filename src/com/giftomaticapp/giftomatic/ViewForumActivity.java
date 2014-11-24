package com.giftomaticapp.giftomatic;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

public class ViewForumActivity extends Activity {
	int threadId;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_forum);
		
		Intent intent = getIntent();
		threadId = intent.getIntExtra("threadId", -1);
		
		String url = "http://api.giftomaticapp.com/thread/" + threadId;
		final ListView lv = (ListView) findViewById(R.id.post_list);
		JsonObjectRequest request = new JsonObjectRequest(Method.GET, url, null, new Response.Listener<JSONObject>() {
			public void onResponse(JSONObject res) {
				try {
					Log.d("######", res.toString());
					ForumThread thread = ForumThread.fromJson(res);
					ThreadPostAdapter adapter = new ThreadPostAdapter(ViewForumActivity.this, thread.posts);
					lv.setAdapter(adapter);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}, new Response.ErrorListener() {
			public void onErrorResponse(VolleyError err) {
				Toast.makeText(ViewForumActivity.this, "Not connected to the internet", Toast.LENGTH_SHORT).show();
			}
		});
		NetworkSingleton.getInstance(this).addToRequestQueue(request);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.thread_action, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.new_thread) {
			Intent intent = new Intent(ViewForumActivity.this, SuggestForm.class);
			intent.putExtra("threadId", threadId);
			startActivity(intent);
			finish();
		}
		return super.onOptionsItemSelected(item);			
	}
	
	
	
}
