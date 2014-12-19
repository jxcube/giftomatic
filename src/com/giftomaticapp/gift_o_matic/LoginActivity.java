package com.giftomaticapp.gift_o_matic;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.sromku.simple.fb.Permission;
import com.sromku.simple.fb.SimpleFacebook;
import com.sromku.simple.fb.SimpleFacebookConfiguration;
import com.sromku.simple.fb.entities.Profile;
import com.sromku.simple.fb.listeners.OnLoginListener;
import com.sromku.simple.fb.listeners.OnProfileListener;

/*
 * This activity is composed of 3 fragments:
 * 1. SelectLoginFragment -> users select their login option
 * 2. SignUpFragment -> registration using email address
 * 3. LogInFragment -> login with email address
 */
public class LoginActivity extends Activity implements OnDataDeliveredListener {

	Permission[] permissions = new Permission[] {
			Permission.USER_ABOUT_ME,
			Permission.EMAIL,
			Permission.PUBLISH_ACTION
	};

	SimpleFacebookConfiguration conf = null;
	
	static SimpleFacebook sf = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//		SharedPrefsHelper sphelper = SharedPrefsHelper.getHelper(this);
		//		boolean authenticated = sphelper.getAuthenticated();
		//		if (authenticated) {
		//			startActivity(new Intent(this, MainActivity.class));
		//			finish();
		//		}

		// Check whether or not the user has been authenticated.
		// It uses SharedPreferences API which essentially provides
		// a persistent key-value data-store.
		SharedPreferences sp = this.getSharedPreferences("com.giftomaticapp.giftomatic.LOGIN_DATA", Context.MODE_PRIVATE);
		boolean authenticated = sp.getBoolean("authenticated", false);
		if (authenticated) {
			startActivity(new Intent(this, MainActivity.class));
			finish();
		}

		setContentView(R.layout.activity_login);
		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction().add(R.id.login_container, new SelectLoginFragment()).commit();
		}

		conf = new SimpleFacebookConfiguration.Builder()
		.setAppId(this.getString(R.string.facebook_app_id))
		.setNamespace("giftomaticapp")
		.setPermissions(permissions)
		.build();

		SimpleFacebook.setConfiguration(conf);
	}

	@Override
	protected void onResume() {
		super.onResume();
		sf = SimpleFacebook.getInstance(this);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		sf.onActivityResult(this, requestCode, resultCode, data);
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	@Override
	public void onDataDelivered(Bundle b) {
		SignUpFragment fragment = new SignUpFragment();
		fragment.setArguments(b);
		
		FragmentTransaction transaction = getFragmentManager().beginTransaction();
		transaction.replace(R.id.login_container, fragment);
		transaction.addToBackStack(null);
		transaction.commit();
	}

	/*
	 * This class belongs to the page where user
	 * choose their login option.
	 */
	public static class SelectLoginFragment extends Fragment {
		
		OnDataDeliveredListener callback;

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View view = inflater.inflate(R.layout.fragment_select_login, container, false);
			ButterKnife.inject(this, view);
			return view;
		}
		
		@Override
		public void onAttach(Activity activity) {
			super.onAttach(activity);
			
			try {
				callback = (OnDataDeliveredListener) activity;
			} catch (ClassCastException e) {
				throw new ClassCastException(activity.toString()
	                    + " must implement OnDataDeliveredListener");
			}
		}

		/*
		 * Go to login page when user clicks the login button
		 */
		@OnClick(R.id.to_login_btn)
		public void goToLoginPage() {
			FragmentTransaction transaction = getFragmentManager().beginTransaction();
			transaction.replace(R.id.login_container, new LogInFragment());
			transaction.addToBackStack(null);
			transaction.commit();
		}

		/*
		 * Go to signup page when user clicks the (sign up with) email button
		 */
		@OnClick(R.id.email_btn)
		public void goToSignupPage() {
			FragmentTransaction transaction = getFragmentManager().beginTransaction();
			transaction.replace(R.id.login_container, new SignUpFragment());
			transaction.addToBackStack(null);
			transaction.commit();
		}
		
		@OnClick(R.id.fb_btn)
		public void logInWithFacebook() {
			sf.login(new OnLoginListener() {
				@Override
				public void onLogin() {
					signUpWithNoPassword();
				}
				
				@Override
				public void onNotAcceptingPermissions(Permission.Type type) {
					
				}

				@Override
				public void onThinking() {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void onException(Throwable arg0) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void onFail(String arg0) {
					// TODO Auto-generated method stub
					
				}
			});
		}
		
		public void signUpWithNoPassword() {
			sf.getProfile(new OnProfileListener() {
				@Override
				public void onComplete(Profile profile) {
					Bundle bundle = new Bundle();
					bundle.putString("username", profile.getFirstName() + profile.getLastName());
					bundle.putString("email", profile.getEmail());
					bundle.putString("gender", profile.getGender());
					
					callback.onDataDelivered(bundle);
				}
			});
		}

	}

	public static class LogInFragment extends Fragment {

		// form's r-mail field
		@InjectView(R.id.emailField) EditText emailField;

		// form's password field
		@InjectView(R.id.passwordField) EditText passwordField;

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View view = inflater.inflate(R.layout.fragment_login, container, false);
			ButterKnife.inject(this, view);
			return view;
		}

		/*
		 * When user clicks the login button -> validate & submit form
		 */
		@OnClick(R.id.login_btn)
		public void submit() {

			// START VALIDATION PROCESS
			//
			final String email = emailField.getText().toString();
			if (email.equals("")) {
				Toast.makeText(getActivity(), "The email field is left blank", Toast.LENGTH_SHORT).show();
				return;
			}

			String password = passwordField.getText().toString();
			if (password.equals("")) {
				Toast.makeText(getActivity(), "The passwrod field is left blank", Toast.LENGTH_SHORT).show();
				return;
			}

			String url = "http://api.giftomaticapp.com/auth/login";
			JSONObject data = new JSONObject();
			try {
				data.put("email", email);
				data.put("password", password);
			} catch (JSONException e) {
			}
			//
			// END VALIDATION PROCESS

			// START SUBMISSION PROCESS
			//
			JsonObjectRequest request = new JsonObjectRequest
					(Request.Method.POST, url, data, new Response.Listener<JSONObject>() {
						@Override
						public void onResponse(JSONObject response) {
							String message = "";
							try {
								message = response.getString("message");
							} catch (JSONException e) {
							}
							if (message.equals("error")) {
								Toast.makeText(getActivity(), "Invalid username or password", Toast.LENGTH_SHORT).show();
								return;
							}
							if (message.equals("success")) {
								String username = "";
								try {
									username = response.getString("username");
								} catch (JSONException e) {
								}
								authenticate(email, username);
							}
						}
					}, new Response.ErrorListener() {
						@Override
						public void onErrorResponse(VolleyError err) {
							Toast.makeText(getActivity(), "Connection error: are you connected to the Internet?", Toast.LENGTH_SHORT).show();
						}
					});
			NetworkSingleton.getInstance(getActivity()).addToRequestQueue(request);
			//
			// END SUBMISSION PROCESS
		}

		private void authenticate(String email, String username) {
			//			SharedPrefsHelper sphelper = SharedPrefsHelper.getHelper(getActivity());
			//			sphelper.setAuthenticated(true);
			//			sphelper.setEmail(email);
			//			sphelper.setUsername(username);
			//			sphelper.savePrefs();

			// Store the authentication data to the key-value store
			// so that user can auto login when they come back to the app
			SharedPreferences sp = getActivity().getSharedPreferences("com.giftomaticapp.giftomatic.LOGIN_DATA", Context.MODE_PRIVATE);
			sp.edit().putBoolean("authenticated", true).putString("email", email).putString("username", username).commit();

			// Go to the main page
			startActivity(new Intent(getActivity(), MainActivity.class));
			getActivity().finish();
		}

	}

	public static class SignUpFragment extends Fragment {

		@InjectView(R.id.password1) EditText passwordField1;
		@InjectView(R.id.password2) EditText passwordField2;
		@InjectView(R.id.usernameField) EditText usernameField;
		@InjectView(R.id.emailField) EditText emailField;
		@InjectView(R.id.sex) RadioGroup genderField;

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View view = inflater.inflate(R.layout.fragment_signup, container, false);
			ButterKnife.inject(this, view);
			
			Bundle args = getArguments();
			if (args != null && !args.isEmpty()) {
				usernameField.setText(args.getCharSequence("username"));
				emailField.setText(args.getCharSequence("email"));
				genderField.check(R.id.male_btn);
				if (args.getCharSequence("gender").toString().equals("female")) {
					genderField.check(R.id.female_btn);
				};
				InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Service.INPUT_METHOD_SERVICE);
				imm.showSoftInput(passwordField1, 0);
				passwordField1.requestFocus();
			}
			
			return view;
		}

		@OnClick(R.id.submit_btn)
		public void checkData() {
			// Get the passwords
			String password1 = passwordField1.getText().toString();
			String password2 = passwordField2.getText().toString();

			// Check if it is empty or not
			if (password1.equals("") || password2.equals("")) {
				Toast.makeText(getActivity(), "One of your passwords is left blank", Toast.LENGTH_SHORT).show();
				return;
			}

			// Check if it matches the second password or not
			if (!password1.equals(password2)) {
				Toast.makeText(getActivity(), "Your first password did not match the second password", Toast.LENGTH_SHORT).show();
				return;
			}

			// If password has no issue, continue retrieve the username
			String username = usernameField.getText().toString();

			// Check if it is empty or not
			if (username.equals("")) {
				Toast.makeText(getActivity(), "The username field is left blank", Toast.LENGTH_SHORT).show();
				return;
			}

			// Check: username should be at least 6-character long
			if (username.length() < 6) {
				Toast.makeText(getActivity(), "Your username should be at least 6-character long", Toast.LENGTH_SHORT).show();
				return;
			}

			// If all goes well, continue retrieve the email
			String email = emailField.getText().toString();

			// Check if it is empty or not
			if (email.equals("")) {
				Toast.makeText(getActivity(), "The email field is left blank", Toast.LENGTH_SHORT).show();
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
				Toast.makeText(getActivity(), "Please state your gender", Toast.LENGTH_SHORT).show();
				return;
			}

			// If all requirements are fulfilled, submit the data to the server
			submit(username, email, password1, gender);
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
				//				Log.e(TAG, e.getMessage(), e);
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
									Toast.makeText(getActivity(), response.getString("detail"), Toast.LENGTH_SHORT).show();
									return;
								}

								// If all goes well, alert the user
								Toast.makeText(getActivity(), "Your account has been successfully created! You can now log in", Toast.LENGTH_SHORT).show();
							} catch (JSONException e) {
								//								Log.e(TAG, e.getMessage(), e);
							}
						}
					}, new Response.ErrorListener() {
						/*
						 * Define the action to be taken when there is a connection error.
						 * E.g. no internet connection.
						 */
						@Override
						public void onErrorResponse(VolleyError err) {
							Toast.makeText(getActivity(), "Connection error: are you connected to the internet?", Toast.LENGTH_SHORT).show();
						}
					});
			NetworkSingleton.getInstance(getActivity()).addToRequestQueue(request);
		}

	}
}
