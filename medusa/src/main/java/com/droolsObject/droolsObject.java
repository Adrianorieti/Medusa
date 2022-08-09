package com.droolsObject;

import java.util.ArrayList;
import java.util.HashMap;

public class droolsObject {
    
    HashMap<String, Boolean> flags = new HashMap<String, Boolean>();
    ArrayList yara_matches;
    
    public droolsObject(HashMap flags){
        this.flags = flags;
    }
}
