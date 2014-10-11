package com.giftomaticapp.giftomatic;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

public class SignUp extends Activity {
	private static final String TAG = "SignUp";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_up);
		
		// Get the button object and set the callback handler
		Button loginBtn = (Button) findViewById(R.id.login_btn);
		loginBtn.setOnClickListener(new LoginListener());
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sign_up, menu);
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
	
	public void alert(String title, String message) {
		new AlertDialog.Builder(this)
		.setTitle(title)
		.setMessage(message)
		.setNegativeButton("Close", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				return;
			}
		})
		.show();
	}
	
	public void submit(String username, String email, String password, String gender) {
		String url = "http://api.giftomaticapp.com/user";
		JSONObject data = new JSONObject();
		try {
			data.put("username", username);
			data.put("email", email);
			data.put("password", password);
			data.put("gender", gender);
		} catch (JSONException e) {
			Log.e(TAG, e.getMessage(), e);
		}
		JsonObjectRequest request = new JsonObjectRequest
				(Request.Method.POST, url, data, new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						try {
							if (response.getString("message") != null && response.getString("message").equals("error")) {
								alert("We're Sorry", "Our server is currently experiencing some issues, please try again later");
								return;
							}
							
							alert("Success", "Your account has been successfully created! You can now log in");
						} catch (JSONException e) {
							Log.e(TAG, e.getMessage(), e);
						}
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError err) {
						alert("Connection Problem", "Are you connected to the internet?");
					}
				});
		NetworkSingleton.getInstance(this).addToRequestQueue(request);
	}
	
	private class LoginListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			String password1 = ((EditText) findViewById(R.id.password1)).getText().toString();
			String password2 = ((EditText) findViewById(R.id.password2)).getText().toString();
			
			if (password1.equals("") || password2.equals("")) {
				alert("Incomplete Info", "One of your passwords is left blank");
				return;
			}
			
			if (!password1.equals(password2)) {
				alert("Passwords didn't match!", "Your first password did not match the second password");
				return;
			}
			
			String username = ((EditText) findViewById(R.id.usernameField)).getText().toString();
			
			if (username.equals("")) {
				alert("Incomplete Info", "The username field is left blank");
				return;
			}
			
			if (username.length() < 6) {
				alert("Cannot Submit", "Your username should be at least 6-character long");
				return;
			}
			
			String email = ((EditText) findViewById(R.id.emailField)).getText().toString();
			
			if (email.equals("")) {
				alert("Incomplete Info", "The email field is left blank");
				return;
			}
			
			String gender = "";
			RadioGroup rg = (RadioGroup) findViewById(R.id.sex);
			switch (rg.getCheckedRadioButtonId()) {
			case R.id.male_btn:
				gender = "male";
				break;
			case R.id.female_btn:
				gender = "female";
				break;
			default:
				alert("Incomplete Info", "Please state your gender");
				return;
			}
			
			submit(username, email, password1, gender);
		}
	}
}
