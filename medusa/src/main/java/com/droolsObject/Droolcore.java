package com.droolsObject;

import java.util.ArrayList;
import java.util.HashMap;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;

public class Droolcore {
    
     private HashMap<String, Boolean> flags = new HashMap<String, Boolean>();
     private ArrayList<String> yara_matches;

    public Droolcore(HashMap<String, Boolean> flags, ArrayList<String> yara_matches) {
        this.flags = flags;
        this.yara_matches = yara_matches;
    }

    /**
     * Fire all rules from drools decision table
     */
    public void fireRules(){
        try {
            
            KieServices ks = KieServices.Factory.get();
            KieContainer kContainer = ks.getKieClasspathContainer();
            // Get the session named ksession-rule that is defined in kmodule.xml above.
            // Also by default the session returned is always stateful. 
            KieSession kSession = kContainer.newKieSession("ksession-rule");
            Droolobj product = new Droolobj(flags, yara_matches);
            FactHandle fact1 = kSession.insert(product);
            kSession.fireAllRules();
    
            if (product.getHasmatches() == 0)
            {
                System.out.println("No matches encountered, the program succesfuly passed the tests! Congrats!");
            }else
            {
                System.out.println("");
                System.out.println("Please check the matched rules");
    
            }
        } catch (Exception e) {
            System.err.print(e);
         }
        
    }

}
