package com.giftomaticapp.giftomatic;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.JsonObjectRequest;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class SuggestForm extends Activity {
	private int threadId;
	@InjectView(R.id.titleform) EditText titleform;
	@InjectView(R.id.contentform) EditText contentform;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_suggest_form);
		ButterKnife.inject(this);
		Intent intent = getIntent();
		threadId = intent.getIntExtra("threadId", -1);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.suggest_form, menu);
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
	@OnClick(R.id.Submit)
	public void submitReply()
	{
		SharedPreferences sp = this.getSharedPreferences("com.giftomaticapp.giftomatic.LOGIN_DATA", Context.MODE_PRIVATE);
		String username = sp.getString("username", "");	
		String titleThread = titleform.getText().toString();
		if (titleThread == null || titleThread.length() == 0) {
			Toast.makeText(this, "You must provide the title of your suggestion", Toast.LENGTH_SHORT).show();
			return;
		}
		//get thread content
		String content = contentform.getText().toString();
		if (content == null || content.length() == 0) {
			Toast.makeText(this, "You must provide the content of your suggestion", Toast.LENGTH_SHORT).show();
			return;
		}
		//get threadID
		String url = "http://api.giftomaticapp.com/thread/" + threadId;
		JSONObject data = new JSONObject();
		try {
			data.put("username", username);
			data.put("title", titleThread);
			data.put("content", content);
			JsonObjectRequest request = new JsonObjectRequest(Method.POST, url, data,
					new Response.Listener<JSONObject>() {
				@Override
				public void onResponse(JSONObject res) {
					try {
						String message = res.getString("message");
						if (message.equals("error")) {
							Toast.makeText(SuggestForm.this, res.getString("detail"), Toast.LENGTH_SHORT).show();
							return;
						} else {
							Toast.makeText(SuggestForm.this, "You have successfully replied to this thread!", Toast.LENGTH_SHORT).show();

							// Go to another activity, what?
							Intent intent = new Intent(SuggestForm.this, ViewForumActivity.class);
							intent.putExtra("threadId", threadId);
							startActivity(intent);
							finish();
						}
					} catch (JSONException e) {}
				}
			}, new Response.ErrorListener() {
				@Override
				public void onErrorResponse(VolleyError err) {
					Toast.makeText(SuggestForm.this, "Connection error: are you connected to the internet?", Toast.LENGTH_SHORT).show();
				}
			});
			NetworkSingleton.getInstance(this).addToRequestQueue(request);
		} catch (JSONException e) {
		}
	}
}


