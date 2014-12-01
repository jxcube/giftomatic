package com.giftomaticapp.giftomatic;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

public class CreateForum extends Activity {

	@InjectView(R.id.contentform) EditText contentform;
	@InjectView(R.id.titleform) EditText titleform;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forum_creating);
		ButterKnife.inject(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.forum_creating, menu);
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
	
	@OnClick(R.id.Submit)
	public void submitThread (){
		//get username
		SharedPreferences sp = this.getSharedPreferences("com.giftomaticapp.giftomatic.LOGIN_DATA", Context.MODE_PRIVATE);
		String username = sp.getString("username", "");
		//get title
		String titleThread = titleform.getText().toString();
		if (titleThread == null || titleThread.length() == 0) {
			Toast.makeText(this, "You must provide the title of the thread", Toast.LENGTH_SHORT).show();
			return;
		}
		//get thread content
		String content = contentform.getText().toString();
		if (content == null || content.length() == 0) {
			Toast.makeText(this, "You must provide the content of the thread", Toast.LENGTH_SHORT).show();
			return;
		}
		//post to the server
		String url = "http://api.giftomaticapp.com/thread";
		JSONObject data = new JSONObject();
		try {
			data.put("username", username);
			data.put("title", titleThread);
			data.put("content", content);
		} catch (JSONException e) {
		}
		JsonObjectRequest request = new JsonObjectRequest(Method.POST, url, data,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject res) {
						try {
							String message = res.getString("message");
							if (message.equals("error")) {
								Toast.makeText(CreateForum.this, res.getString("detail"), Toast.LENGTH_SHORT).show();
								return;
							} else {
								Toast.makeText(CreateForum.this, "You have successfully created a new thread!", Toast.LENGTH_SHORT).show();
								finish();
							}
						} catch (JSONException e) {}
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError err) {
						
					}
				});
		NetworkSingleton.getInstance(this).addToRequestQueue(request);
	}

}
