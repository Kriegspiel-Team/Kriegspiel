package com.sample;

import java.nio.file.Paths;
import java.util.List;

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
        	
        	kSession.insert(b);
        	
        	kSession.getAgenda().getAgendaGroup( "PlaceEntity" ).setFocus();
            kSession.fireAllRules();
            
            b.loadBoardWithFile(Paths.get("src/main/resources/board/Sample1.txt").toAbsolutePath().toString());
        	
        	List<MovableEntity> movableEntity = b.getMovableEntity();
        	
        	System.out.println(movableEntity.size() + " movable entity");
        	
        	for (MovableEntity entity : movableEntity){
        		kSession.insert(entity);
        	}            
            
        	
            kSession.getAgenda().getAgendaGroup( "Movement" ).setFocus();
            kSession.fireAllRules();
        	      
            b.display(10, 3, true);
            
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}
