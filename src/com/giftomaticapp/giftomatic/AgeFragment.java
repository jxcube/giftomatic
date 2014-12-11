package com.giftomaticapp.giftomatic;

import java.util.Arrays;
import java.util.List;

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
import butterknife.ButterKnife;
import butterknife.InjectView;

public class AgeFragment extends Fragment {
	@InjectView(R.id.ageList)
	ListView ageList;

	public AgeFragment() {
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.age_filter_fragment, container,
				false);

		ButterKnife.inject(this, view);

		String[] age = { "Elder", "Kids", "Teenager", "Adult" };

		final List<String> ageLister = Arrays.asList(age);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
				R.layout.tags_view, ageLister);

		ageList.setAdapter(adapter);
		System.out.println("apalah");

		ageList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				System.out.println("apalah1");
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(), GiftGallery.class);
				intent.putExtra("tags", ageLister.get(position));
				startActivity(intent);
			}

		});
		System.out.println("apalah2");
		return view;
	}
}
