package com.giftomaticapp.giftomatic;

import butterknife.ButterKnife;
import butterknife.InjectView;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class AgeFragment extends Fragment {
	@InjectView(R.id.ageList)
	ListView ageList;

	public AgeFragment() {
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		String[] age ={"Baby","Kids","Teens","Adult","Old"};
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), getId(), age);
		ageList.setAdapter(adapter);
		View view = inflater.inflate(R.layout.age_filter_fragment, container,
				false);
		ButterKnife.inject(this, view);
		ageList.setOnClickListener(new OnClickListener(AdapterView<?> parent,
				View v, int position) {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(AgeFragment.this, GiftGallery.class);
				intent.putExtra("List", items.get(position));
				startActivity(intent);
			}
		});
		return view;
	}
}
