package com.giftomaticapp.giftomatic;

import butterknife.InjectView;
import butterknife.OnClick;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class BudgetFragment extends Fragment {
	@InjectView(R.id.budget) EditText budget;
	@OnClick(R.id.budgetSubmit)
	public void budgetsubmit() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(getActivity(), GiftGallery.class);
		intent.putExtra("tags", budget.getText());
		startActivity(intent);
	}
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		return inflater.inflate(R.layout.budget_filter_fragment, container,false);
	}
}
