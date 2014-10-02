package com.giftomaticapp.giftomatic;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;


public class MainActivity extends Activity {
	
	private ImageButton emailBtn;
	private Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // Get the reference to the (sign up with) email
        // button and define the behavior when the user clicks it
        emailBtn = (ImageButton) findViewById(R.id.email_btn);
        emailBtn.setOnClickListener(new OnClickListener() {
        	@Override
        	public void onClick(View v) {
        		// When it is clicked, give user the registration form
        		startActivity(new Intent(MainActivity.this, SignUp.class));
        	}
        });
        
        // Get the reference to the Login button
        // and define the behavior when the user clicks it
        loginBtn = (Button) findViewById(R.id.login_btn);
        loginBtn.setOnClickListener(new OnClickListener() {
        	@Override
        	public void onClick(View v) {
        		// When it is clicked, go to the login page
        		startActivity(new Intent(MainActivity.this, LoginPage.class));
        	}
        });
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
