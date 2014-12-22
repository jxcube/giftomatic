package com.giftomaticapp.giftomatic;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;

public class Filter extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		ActionBar bar = getActionBar();

		setContentView(R.layout.activity_filter);

		bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		TabListener tabby = new TabListener() {

			@Override
			public void onTabUnselected(Tab tab, FragmentTransaction ft) {
				// TODO Auto-generated method stu

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
				} else {
					ft.replace(R.id.containerFilter,new FavoriteFragment());
				}

			}

			@Override
			public void onTabReselected(Tab tab, FragmentTransaction ft) {
				// TODO Auto-generated method stub

			}
		};
		bar.addTab(bar.newTab().setText("Age").setTabListener(tabby));
		bar.addTab(bar.newTab().setText("Favorite").setTabListener(tabby));
		bar.addTab(bar.newTab().setText("Tags").setTabListener(tabby));
		bar.addTab(bar.newTab().setText("Budget").setTabListener(tabby));
		bar.setSelectedNavigationItem(0);
		// request items
	}

}
