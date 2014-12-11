package com.giftomaticapp.giftomatic;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.widget.FrameLayout;

public class Filter extends Activity implements TabListener {

	final ActionBar bar = getActionBar();
	FrameLayout tabSelector;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_filter);
		bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		tabSelector = (FrameLayout) findViewById(R.id.containerFilter);
		bar.addTab(bar.newTab().setText("Age").setTabListener(this));
		bar.addTab(bar.newTab().setText("Tags").setTabListener(this));
		bar.addTab(bar.newTab().setText("Budget").setTabListener(this));
		bar.setSelectedNavigationItem(1);
		// request items
	}

	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		if (tab.getText().equals("Age")) {
			ft.replace(R.id.containerFilter, new AgeFragment());
		} else if (tab.getText().equals("Tags")) {
			ft.replace(R.id.containerFilter, new TagsFragment());
		} else if (tab.getText().equals("Budget")) {
			ft.replace(R.id.containerFilter, new BudgetFragment());
		}

	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}
}
