package com.droolsObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Product {

	private HashMap<String, Boolean> flags = new HashMap<String, Boolean>();
	private ArrayList<String> yara_matches;
	
	public Product(HashMap<String, Boolean> flags, ArrayList<String> yara_matches, String type, int discount) {
		this.flags = flags;
		this.yara_matches = yara_matches;
		this.type = type;
		this.discount = discount;
	}


	private String type;
	private int discount;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getDiscount() {
		return discount;
	}

	public void setDiscount(int discount) {
		this.discount = discount;
	}

}