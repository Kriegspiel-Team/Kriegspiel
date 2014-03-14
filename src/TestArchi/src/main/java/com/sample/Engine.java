package com.sample;

import java.nio.file.Paths;

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
        	
        	Board b = new Board(Paths.get("src/main/resources/board/Sample1.txt").toAbsolutePath().toString());


            kSession.insert(b);
            
            kSession.getAgenda().getAgendaGroup( "PlaceEntity" ).setFocus();
            kSession.fireAllRules();
            kSession.getAgenda().getAgendaGroup( "Movement" ).setFocus();
            kSession.fireAllRules();
        	      
            b.display(2, 8, true);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}
