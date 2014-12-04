package com.giftomaticapp.giftomatic;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar;
import android.app.ActionBar.TabListener;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ActionBar.Tab;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

public class Filter extends Activity {

	final ActionBar bar = getActionBar();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_filter);
		bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		TabListener ageTabs = new TabListener() {

			@Override
			public void onTabUnselected(Tab tab, FragmentTransaction ft) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onTabSelected(Tab tab, FragmentTransaction ft) {
				// TODO Auto-generated method stub
				Fragment AgeFragment = new AgeFragment();
				ft.replace(R.id.containerFilter, AgeFragment, "Age Fragment");
				ft.addToBackStack("Age Fragment");
				ft.commit();

			}

			@Override
			public void onTabReselected(Tab tab, FragmentTransaction ft) {
				// TODO Auto-generated method stub

			}
		};
		TabListener BudgetTabs = new TabListener() {

			@Override
			public void onTabUnselected(Tab tab, FragmentTransaction ft) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onTabSelected(Tab tab, FragmentTransaction ft) {
				// TODO Auto-generated method stub
				Fragment BudgetFragment = new BudgetFragment();
				ft.replace(R.id.containerFilter, BudgetFragment, "Budget Fragment");
				ft.addToBackStack("Budget Fragment");
				ft.commit();

			}

			@Override
			public void onTabReselected(Tab tab, FragmentTransaction ft) {
				// TODO Auto-generated method stub

			}
		};
		TabListener TagsTabs = new TabListener() {

			@Override
			public void onTabUnselected(Tab tab, FragmentTransaction ft) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onTabSelected(Tab tab, FragmentTransaction ft) {
				// TODO Auto-generated method stub
				Fragment TagsFragment = new TagsFragment();
				ft.replace(R.id.containerFilter, TagsFragment, "Tags Fragment");
				ft.addToBackStack("Tags Fragment");
				ft.commit();

			}

			@Override
			public void onTabReselected(Tab tab, FragmentTransaction ft) {
				// TODO Auto-generated method stub

			}
		};
		bar.addTab(bar.newTab().setText("Age Filter").setTabListener(ageTabs));
		bar.addTab(bar.newTab().setText("Tags Filter").setTabListener(TagsTabs));
		bar.addTab(bar.newTab().setText("Budget Filter")
				.setTabListener(BudgetTabs));
		// request items
	}
	public static class AgeFragment extends Fragment {
		public AgeFragment() {
		}
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			return inflater.inflate(R.layout.age_filter_fragment, container,false);
		}
		@Override
		public void onAttach(Activity activity) {
			// TODO Auto-generated method stub
			super.onAttach(activity);
		}

		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.gift_gallery, menu);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		// int id = item.getItemId();
		// if (id == R.id.action_settings) {
		// return true;
		// }
		return super.onOptionsItemSelected(item);
	}

	public void requestItems(final List<String> tags) {
		// TODO
		CustomTags adapter = new CustomTags(this, tags);
		gridView = (GridView) findViewById(R.id.tags);
		gridView.setAdapter(adapter);
		gridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				Intent intent = new Intent(Filter.this, ItemDetail.class);
				intent.putExtra("itemId", items.get(position).id);
				startActivity(intent);
			}
		});
	}

	public void getItemDataFiltered(String s) {
		String url = "http://api.giftomaticapp.com/item" + s;
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
							requestItems(items);
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError err) {

					}
				});
		NetworkSingleton.getInstance(this).addToRequestQueue(request);
	}

}
