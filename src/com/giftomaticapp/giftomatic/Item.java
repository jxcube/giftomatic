package com.giftomaticapp.giftomatic;

import java.util.List;

import com.orm.SugarRecord;

public class Item extends SugarRecord<Item> {
	String name;
	double minPrice, maxPrice;
	String description;
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
	
	
}
