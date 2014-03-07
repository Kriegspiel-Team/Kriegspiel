package com.sample;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

/**
 * This is a sample class to launch a rule.
 */
public class Engine {

    public static final void main(String[] args) {
        try {
            // load up the knowledge base
	        KieServices ks = KieServices.Factory.get();
    	    KieContainer kContainer = ks.getKieClasspathContainer();
        	KieSession kSession = kContainer.newKieSession("test-rules");
        	
        	Board b = new Board();
        	b.placeEntity(5, 9, new Infantery());

            kSession.insert(b);
            
            kSession.getAgenda().getAgendaGroup( "PlaceEntity" ).setFocus();
            kSession.fireAllRules();
            kSession.getAgenda().getAgendaGroup( "Movement" ).setFocus();
            kSession.fireAllRules();
        	
            
            
            b.display(5, 9);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}
