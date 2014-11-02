package com.giftomaticapp.giftomatic;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

public class Item {
	String name, description, imageUrl;
	double minPrice, maxPrice;
	List<String> tags;
	
	public Item() {
		
	}

	public Item(String name, double minPrice, double maxPrice,
			String description, List<String> tags) {
		this.name = name;
		this.minPrice = minPrice;
		this.maxPrice = maxPrice;
		this.description = description;
		this.tags = tags;
	}
	
	public Item(JSONObject item) throws JSONException {
		name = item.getString("name");
		description = item.getString("description");
		imageUrl = item.getString("imgUrl");
		minPrice = item.getDouble("minPrice");
		maxPrice = item.getDouble("maxPrice");
		tags = new ArrayList<String>();
		for (int i = 0; i < item.getJSONArray("tag").length(); i++) {
			tags.add(item.getJSONArray("tag").getString(i));
		}
	}
	
}
