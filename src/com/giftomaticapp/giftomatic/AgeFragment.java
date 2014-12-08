package com.giftomaticapp.giftomatic;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.ButterKnife;
import butterknife.InjectView;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
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
		ArrayList<String> ageLister = new ArrayList<String>();
		ageLister.addAll(Arrays.asList(age));
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.tags_view, ageLister);
		ageList.setAdapter(adapter);
		View view = inflater.inflate(R.layout.age_filter_fragment, container,
				false);
		ButterKnife.inject(this, view);
		ageList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(), ItemDetail.class);
				intent.putExtra("itemId", items.get(position));
				startActivity(intent);
			}
	
		});
		return view;
	}
}
