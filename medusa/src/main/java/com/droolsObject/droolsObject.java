package com.droolsObject;

import java.util.ArrayList;
import java.util.HashMap;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;

public class droolsObject {
    
    HashMap<String, Boolean> flags = new HashMap<String, Boolean>();
    ArrayList<String> yara_matches;

    public droolsObject(HashMap<String, Boolean> flags, ArrayList<String> yara_matches){
        this.flags = flags;
        this.yara_matches = yara_matches;
    }


}
