package com.droolsObject;

import java.util.ArrayList;
import java.util.HashMap;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;
import com.droolsObject.Product;

public class droolsCore {
    
     static HashMap<String, Boolean> flags = new HashMap<String, Boolean>();
     static ArrayList<String> yara_matches;

    public droolsCore(HashMap<String, Boolean> flags, ArrayList<String> yara_matches) {
        this.flags = flags;
        this.yara_matches = yara_matches;
    }

    public static void fireRules(){
        KieServices ks = KieServices.Factory.get();
        KieContainer kContainer = ks.getKieClasspathContainer();
        //Get the session named ksession-rule that is defined in kmodule.xml above.
        //Also by default the session returned is always stateful. 
        KieSession kSession = kContainer.newKieSession("ksession-rule");
        Product product = new Product(flags, yara_matches, "gold" ,0);
        FactHandle fact1;
        
        fact1 = kSession.insert(product);
        kSession.fireAllRules();
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        
        System.out.println(product.getSingleFlag("CANARY"));
        System.out.println("The discount for the jewellery product " + product.getType() + " is " + product.getDiscount());

    }

}