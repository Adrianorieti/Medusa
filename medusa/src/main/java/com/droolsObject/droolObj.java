package com.droolsObject;

import java.util.ArrayList;
import java.util.HashMap;

public class droolObj {
	
	private HashMap<String, Boolean> flags = new HashMap<String, Boolean>();
	private ArrayList<String> yara_matches;
	private int hasMatches;

	
	public droolObj(HashMap<String, Boolean> flags, ArrayList<String> yara_matches) {
		this.flags = flags;
		this.yara_matches = yara_matches;
		this.hasMatches = 0;
		
	}

	public HashMap<String, Boolean> getFlags() {
		return flags;
	}

	public Boolean getSingleFlag(String name){
		return flags.get(name);
	}

	public void setFlags(HashMap<String, Boolean> flags) {
		this.flags = flags;
	}

	public ArrayList<String> getYara_matches() {
		return yara_matches;
	}

	public void setYara_matches(ArrayList<String> yara_matches) {
		this.yara_matches = yara_matches;
	}
	
	public Boolean getSingleMatch(String match)
	{
		System.out.println(yara_matches);
		return yara_matches.contains(match);
	}

	public Integer getHasmatches() {
		return hasMatches;
	}

	public void setHasMatches(Integer hasFlags) {
		this.hasMatches += hasFlags;
	}
}