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

public class FormSuggest extends Activity {
	@InjectView(R.id.contentform) EditText contentform;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_form_suggest);
		ButterKnife.inject(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.form_suggest, menu);
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
	public void submitSuggestion (){
		//get username
		SharedPreferences sp = this.getSharedPreferences("com.giftomaticapp.giftomatic.LOGIN_DATA", Context.MODE_PRIVATE);
		String username = sp.getString("username", "");
		//get thread ID
		
		//get thread content
		String content = contentform.getText().toString();
		//post to the server
		String url = "http://api.giftomaticapp.com/thread";
		JSONObject data = new JSONObject();
		try {
			data.put("username", username);
			data.put("content", content);
		} catch (JSONException e) {
		}
		JsonObjectRequest request = new JsonObjectRequest(Method.POST, url, data,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject res) {
						try {
							String message = res.getString("message");
							if (message.equals("user not found")) {
								Toast.makeText(FormSuggest.this, message, Toast.LENGTH_SHORT).show();
								return;
							} else {
								Toast.makeText(FormSuggest.this, "You have successfully posted your suggestion", Toast.LENGTH_SHORT).show();
								
								// Go to another activity, what?
								startActivity(new Intent(FormSuggest.this, ForumList.class));
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
		finish();
	}
		
	}

