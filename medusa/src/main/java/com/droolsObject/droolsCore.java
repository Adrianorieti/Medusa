package com.droolsObject;

import java.util.ArrayList;
import java.util.HashMap;
import org.kie.api.KieServices;
import org.kie.api.logger.KieRuntimeLogger;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;

public class droolsCore {
    
     private HashMap<String, Boolean> flags = new HashMap<String, Boolean>();
     private ArrayList<String> yara_matches;

    public droolsCore(HashMap<String, Boolean> flags, ArrayList<String> yara_matches) {
        this.flags = flags;
        this.yara_matches = yara_matches;
    }

    /**
     * Fire all rules from drools decision table
     */
    public void fireRules(){
        KieServices ks = KieServices.Factory.get();
        KieContainer kContainer = ks.getKieClasspathContainer();
        //Get the session named ksession-rule that is defined in kmodule.xml above.
        //Also by default the session returned is always stateful. 
        KieSession kSession = kContainer.newKieSession("ksession-rule");
        Product product = new Product(flags, yara_matches);
        FactHandle fact1 = kSession.insert(product);
        kSession.fireAllRules();
        System.out.println("");
        
        if (product.getHasmatches() == 0)
        {
            System.out.println("No matches encountered, the program succesfuly passed the tests! Congrats!");
        }
        
        // TO-DO correctly populate javaobject and create complete drools decision table
    }

}
