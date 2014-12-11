package com.giftomaticapp.giftomatic;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;


public class Filter extends FragmentActivity implements TabListener {

	final ActionBar bar = getActionBar();
	ViewPager tabSelector;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_filter);
		bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		tabSelector = (ViewPager) findViewById(R.id.containerFilter);
		tabAdaptor tabAdaptor = new tabAdaptor(getSupportFragmentManager());
		tabSelector.setAdapter(tabAdaptor);
		tabSelector
				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						// When swiping between different app sections, select
						// the corresponding tab.
						// We can also use ActionBar.Tab#select() to do this if
						// we have a reference to the
						// Tab.
						bar.setSelectedNavigationItem(position);
					}
				});

		bar.addTab(bar.newTab().setText("Age").setTabListener(this));
		bar.addTab(bar.newTab().setText("Tags").setTabListener(this));
		bar.addTab(bar.newTab().setText("Budget").setTabListener(this));
		// request items
	}

	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		tabSelector.setCurrentItem(tab.getPosition());

	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub

	}
}
