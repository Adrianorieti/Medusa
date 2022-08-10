package com.droolsObject;

import java.util.ArrayList;
import java.util.HashMap;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;
import com.droolsObject.Product;

public class droolsObject {
    
    HashMap<String, Boolean> flags = new HashMap<String, Boolean>();
    ArrayList<String> yara_matches;

    public droolsObject(HashMap<String, Boolean> flags, ArrayList<String> yara_matches){
        this.flags = flags;
        this.yara_matches = yara_matches;
    }


    public void fireRules(){
        KieServices ks = KieServices.Factory.get();
        KieContainer kContainer = ks.getKieClasspathContainer();
        //Get the session named ksession-rule that is defined in kmodule.xml above.
        //Also by default the session returned is always stateful. 
        KieSession kSession = kContainer.newKieSession("ksession-rule");
        Product product = new Product();
        product.setType("gold");

        FactHandle fact1;

        fact1 = kSession.insert(product);
        kSession.fireAllRules();

        System.out.println("The discount for the jewellery product " + product.getType() + " is " + product.getDiscount());


        // TO-DO create a drools excel table for behavior
    }

}
