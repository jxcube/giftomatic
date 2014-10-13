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
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

public class SignUp extends Activity {
	private static final String TAG = "SignUp";

	@InjectView(R.id.password1) EditText passwordField1;
	@InjectView(R.id.password2) EditText passwordField2;
	@InjectView(R.id.usernameField) EditText usernameField;
	@InjectView(R.id.emailField) EditText emailField;
	@InjectView(R.id.sex) RadioGroup genderField;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_up);
		ButterKnife.inject(this);
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

	/*
	 * This helper method creates an alert dialog.
	 * 
	 * @author Raibima Imam Putra
	 */
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

	/*
	 * This method handles the submission of a new user.
	 * It will submit the data to the server 
	 * through HTTP connection. HTTPS support coming soon!
	 * 
	 * @author Raibima Imam Putra
	 */
	public void submit(String username, String email, String password, String gender) {
		// The URL of our server :)
		String url = "http://api.giftomaticapp.com/user";

		// JSON object to be sent to the server
		JSONObject data = new JSONObject();
		try {
			data.put("username", username);
			data.put("email", email);
			data.put("password", password);
			data.put("gender", gender);
		} catch (JSONException e) {
			Log.e(TAG, e.getMessage(), e);
		}

		// Create the request
		JsonObjectRequest request = new JsonObjectRequest
				(Request.Method.POST, url, data, new Response.Listener<JSONObject>() {
					/*
					 * Defines the response handler
					 */
					@Override
					public void onResponse(JSONObject response) {
						try {
							// Check if there is an error or not in querying to the database
							if (response.getString("message") != null && response.getString("message").equals("error")) {
								alert("We're Sorry", "Our server is currently experiencing some issues, please try again later");
								return;
							}

							// If all goes well, alert the user
							alert("Success", "Your account has been successfully created! You can now log in");
						} catch (JSONException e) {
							Log.e(TAG, e.getMessage(), e);
						}
					}
				}, new Response.ErrorListener() {
					/*
					 * Define the action to be taken when there is a connection error.
					 * E.g. no internet connection.
					 */
					@Override
					public void onErrorResponse(VolleyError err) {
						alert("Connection Problem", "Are you connected to the internet?");
					}
				});
		NetworkSingleton.getInstance(this).addToRequestQueue(request);
	}

	@OnClick(R.id.submit_btn)
	public void checkData() {
		// Get the passwords
		String password1 = passwordField1.getText().toString();
		String password2 = passwordField2.getText().toString();

		// Check if it is empty or not
		if (password1.equals("") || password2.equals("")) {
			alert("Incomplete Info", "One of your passwords is left blank");
			return;
		}

		// Check if it matches the second password or not
		if (!password1.equals(password2)) {
			alert("Passwords didn't match!", "Your first password did not match the second password");
			return;
		}

		// If password has no issue, continue retrieve the username
		String username = usernameField.getText().toString();

		// Check if it is empty or not
		if (username.equals("")) {
			alert("Incomplete Info", "The username field is left blank");
			return;
		}

		// Check: username should be at least 6-character long
		if (username.length() < 6) {
			alert("Cannot Submit", "Your username should be at least 6-character long");
			return;
		}

		// If all goes well, continue retrieve the email
		String email = emailField.getText().toString();

		// Check if it is empty or not
		if (email.equals("")) {
			alert("Incomplete Info", "The email field is left blank");
			return;
		}

		// Retrieve the gender information
		String gender = "";
		switch (genderField.getCheckedRadioButtonId()) {
		case R.id.male_btn:
			gender = "male";
			break;
		case R.id.female_btn:
			gender = "female";
			break;
		default:
			// If it has not been selected..
			alert("Incomplete Info", "Please state your gender");
			return;
		}

		// If all requirements are fulfilled, submit the data to the server
		submit(username, email, password1, gender);
	}
}
