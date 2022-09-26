package com.droolsObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Droolobj {
	
	private HashMap<String, Boolean> flags = new HashMap<String, Boolean>();
	private ArrayList<String> yara_matches;
	private int hasMatches;

	
	public Droolobj(HashMap<String, Boolean> flags, ArrayList<String> yara_matches) {
		this.flags = flags;
		this.yara_matches = yara_matches;
		this.hasMatches = 0;
		
	}

	public Boolean getSingleFlag(String name){
		return flags.get(name);
	}

	public Boolean getSingleMatch(String match)
	{
		return yara_matches.contains(match);
	}

	public Integer getHasmatches() {
		return hasMatches;
	}

	public void setHasMatches(Integer hasFlags) {
		this.hasMatches += hasFlags;
	}
}