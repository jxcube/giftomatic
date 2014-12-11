package com.giftomaticapp.giftomatic;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class tabAdaptor extends FragmentPagerAdapter {

	public tabAdaptor(FragmentManager fragmentManager) {
		super(fragmentManager);
	}

	@Override
	public Fragment getItem(int i) {
		switch (i) {
		case 0:
			return new AgeFragment();
		case 2:
			return new BudgetFragment();
		default:
			return new TagsFragment();
		}
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 3;
	}

}
