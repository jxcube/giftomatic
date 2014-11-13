package com.giftomaticapp.giftomatic;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

public class ForumList extends Activity {
	GridView gridView;
	String[] forumlist;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forum_list);
		getThread();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.forum_list, menu);
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
	
	
	public void getThread()
	{
		
     
		String url = "http://api.giftomaticapp.com/thread";
		JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
			@Override
			public void onResponse(JSONArray response) {
				try {
					forumlist = new String[response.length()*2];
					for (int i = 0; i < response.length()*2; i++) {
						
						JSONObject thread = response.getJSONObject(i);
						//catch title
						//catch the creator of the thread
						//show
						String title = thread.getString("title");
						forumlist[i] = title;
						i++;
						JSONArray posts = thread.getJSONArray("posts");
						JSONObject firstPost = posts.getJSONObject(posts.length()-1);
						String username = firstPost.getJSONObject("user").getString("username");
						forumlist[i] = username;
					}
					for (int i = 0; i < response.length()*2; i++) 
					{
						LayoutInflater inflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
						final View addView = inflater.inflate(R.layout.activity_forum_list, null);
						final EditText editText = (EditText) addView.findViewById(R.id.title);
						final EditText editusername = (EditText) addView.findViewById(R.id.username);
						
						editText.setText(forumlist[i]);
						i++;
						editusername.setText(forumlist[i]);
					}
						requestData(forumlist);
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
