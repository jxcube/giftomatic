package com.giftomaticapp.giftomatic;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import butterknife.OnClick;


public class SignIn extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        SharedPreferences sp = getSharedPreferences("com.giftomaticapp.giftomatic.LOGIN_DATA", Context.MODE_PRIVATE);
        boolean authenticated = sp.getBoolean("authenticated", false);
        if (authenticated) {
        	startActivity(new Intent(this, MainActivity.class));
        	return;
        }
        
        setContentView(R.layout.activity_sign_in);
    }
    
    @OnClick(R.id.email_btn)
    public void registerWithEmail() {
    	// When it is clicked, give user the registration form
		startActivity(new Intent(this, SignUp.class));
    }
    
    @OnClick(R.id.login_btn)
    public void goToLoginPage() {
    	// When it is clicked, go to the login page
		startActivity(new Intent(SignIn.this, LoginPage.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
}
