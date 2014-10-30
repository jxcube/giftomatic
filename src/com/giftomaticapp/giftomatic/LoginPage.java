package com.giftomaticapp.giftomatic;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

public class LoginPage extends Activity {
	
	private static final String TAG = "LoginPage";
	private EditText emailField, passwordField;
	private Button loginBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login_page);
		
		emailField = (EditText) findViewById(R.id.emailField);
		passwordField = (EditText) findViewById(R.id.passwordField);
		loginBtn = (Button) findViewById(R.id.login_btn);
		
		loginBtn.setOnClickListener(new LoginHandler());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login_page, menu);
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
	
	public void submit(final String email, final String password) {
		String url = "http://api.giftomaticapp.com/auth/login";
		JSONObject data = new JSONObject();
		try {
			data.put("email", email);
			data.put("password", password);
		} catch (JSONException e) {
			Log.e(TAG, e.getMessage(), e);
		}
		
		JsonObjectRequest request = new JsonObjectRequest
				(Request.Method.POST, url, data, new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						String message = "";
						try {
							message = response.getString("message");
						} catch (JSONException e) {
							Log.e(TAG, e.getMessage(), e);
						}
						if (message.equals("error")) {
							Alert.alert(LoginPage.this, "Authentication failed", "Invalid username or password");
							return;
						}
						if (message.equals("success")) {
							String username = "";
							try {
								username = response.getString("username");
							} catch (JSONException e) {
								Log.e(TAG, e.getMessage(), e);
							}
							authenticate(email, username);
						}
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError err) {
						Alert.alert(LoginPage.this, "Connection Error", "Are you connected to the internet?");
					}
				});
		NetworkSingleton.getInstance(this).addToRequestQueue(request);
	}
	
	public void authenticate(String email, String username) {
		SharedPreferences sp = getSharedPreferences("com.giftomaticapp.giftomatic.LOGIN_DATA", Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sp.edit();
		editor.putBoolean("authenticated", true);
		editor.putString("email", email);
		editor.putString("username", username);
		editor.commit();
		goToMainPage();
	}
	
	public void goToMainPage() {
		startActivity(new Intent(this, MainActivity.class));
		SignIn.endAct.finish();
		SignUp.endAct.finish();
		finish();
	}
	
	private class LoginHandler implements OnClickListener {
		@Override
		public void onClick(View v) {
			String email = emailField.getText().toString();
			
			if (email.equals("")) {
				Alert.alert(LoginPage.this, "Incomplete Information", "The email field is left blank");
				return;
			}
			
			String password = passwordField.getText().toString();
			if (password.equals("")) {
				Alert.alert(LoginPage.this, "Incomplete Information", "The password field is left blank");
				return;
			}
			
			submit(email, password);
		}
		
	}
}
