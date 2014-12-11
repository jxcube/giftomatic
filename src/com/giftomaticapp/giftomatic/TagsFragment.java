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

import butterknife.ButterKnife;
import butterknife.InjectView;
import android.support.v4.app.Fragment;
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

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		final ArrayList<String> ageLister = (ArrayList<String>) Arrays
				.asList(getTags());
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
				R.layout.tags_view, ageLister);
		ageList.setAdapter(adapter);
		View view = inflater.inflate(R.layout.age_filter_fragment, container,
				false);
		ButterKnife.inject(this, view);
		ageList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(), GiftGallery.class);
				intent.putExtra("tags", ageLister.get(position));
				startActivity(intent);
			}

		});
		return view;
	}

	private String[] getTags() {
		String url = "http://api.giftomaticapp.com/item";
		JsonArrayRequest request = new JsonArrayRequest(url,
				new Response.Listener<JSONArray>() {
					@Override
					public void onResponse(JSONArray response) {
						try {
							List<Item> items = new ArrayList<Item>();
							for (int i = 0; i < response.length(); i++) {
								JSONObject item = response.getJSONObject(i);
								items.add(new Item(item));
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError err) {

					}
				});
		return null;
	}
}
