package com.giftomaticapp.giftomatic;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Item {
	String name, description, imageUrl;
	double minPrice, maxPrice;
	List<String> tags;
	int id;
	public Item() {
		
	}

	public Item(int id, String name, double minPrice, double maxPrice,
			String imageUrl, String description, List<String> tags) {
		this.id = id;
		this.name = name;
		this.minPrice = minPrice;
		this.maxPrice = maxPrice;
		this.description = description;
		this.tags = tags;;
		this.imageUrl = imageUrl;
	}
	
	public Item(JSONObject item) throws JSONException {
		id  =item.getInt("id");
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
	public static Item fromJson(JSONObject json) throws JSONException {
		int id = json.getInt("id");
		String name = json.getString("name");
		String description = json.getString("description");
		double minPrice = json.getDouble("minPrice");
		double maxPrice = json.getDouble("maxPrice");
		String imageUrl = json.getString("imgUrl");
		List<String> tags = new ArrayList<String>();
		JSONArray tagsArray = json.getJSONArray("tag");
		for (int i = 0; i < tagsArray.length(); i++) {
			tags.add(tagsArray.getString(i));
		}
		return new Item(id, name, minPrice, maxPrice, imageUrl, description, tags);
	}
}
