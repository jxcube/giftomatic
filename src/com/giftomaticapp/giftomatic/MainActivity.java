package com.giftomaticapp.giftomatic;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ButterKnife.inject(this);
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
//		if (id == R.id.action_settings) {
//			return true;
//		}
		if (id == R.id.action_logout) {
//			SharedPrefsHelper sphelper = SharedPrefsHelper.getHelper(this);
//			sphelper.setAuthenticated(false);
//			sphelper.savePrefs();
			SharedPreferences sp = getSharedPreferences("com.giftomaticapp.giftomatic.LOGIN_DATA", Context.MODE_PRIVATE);
			sp.edit().clear().commit();
			startActivity(new Intent(this, LoginActivity.class));
			finish();
		} else if (id == R.id.new_thread) {
			startActivity(new Intent(this, CreateForum.class));
		}
		return super.onOptionsItemSelected(item);
	}
	
	@OnClick(R.id.all_item)
	public void goToAllItem() {
		startActivity(new Intent(this, GiftGallery.class));
	}
	
	@OnClick(R.id.random_item)
	public void displayRandomItem() {
		startActivity(new Intent(this, RandomPage.class));
	}
	
	@OnClick(R.id.forum_btn)
	public void goToForum() {
		startActivity(new Intent(this, BrowseForumActivity.class));
	}
}
