package com.giftomaticapp.giftomatic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.orm.dsl.Collection;

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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class TagsFragment extends Fragment {
	@InjectView(R.id.ageList)
	ListView ageList;
	List<String> tags;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.age_filter_fragment, container,
				false);

		ButterKnife.inject(this, view);
		tags = new ArrayList<String>();
		getTags();

		System.out.println("apalah2");
		return view;
	}

	public void printTags() {
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
				R.layout.tags_view, tags);

		ageList.setAdapter(adapter);
		System.out.println("apalah");

		ageList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				System.out.println("apalah1");
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(), GiftGallery.class);
				intent.putExtra("tags", tags.get(position));
				startActivity(intent);
			}

		});
	}

	private void getTags() {
		String url = "http://api.giftomaticapp.com/tags";
		JsonArrayRequest request = new JsonArrayRequest(url,
				new Response.Listener<JSONArray>() {
					@Override
					public void onResponse(JSONArray response) {
						try {
							List<String> items = new ArrayList<String>();
							for (int i = 0; i < response.length(); i++) {
								String tag = response.getString(i);
								items.add(tag);
							}
							tags = items;
							printTags();
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError err) {

					}
				});

	}
}
