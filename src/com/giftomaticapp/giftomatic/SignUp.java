package com.giftomaticapp.giftomatic;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

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
