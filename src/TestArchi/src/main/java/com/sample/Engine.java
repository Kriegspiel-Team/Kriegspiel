package com.sample;

import java.util.List;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

/**
 * This is a sample class to launch a rule.
 */
public class Engine implements IEngine{
	
	private Board board;
	private KieServices ks;
	private KieContainer kContainer;
	private KieSession kSession;

    public Engine(Board b) 
    {	
    	this.board = b;
    	
        try 
        {
            // load up the knowledge base
	        ks = KieServices.Factory.get();
    	    kContainer = ks.getKieClasspathContainer();
        	kSession = kContainer.newKieSession("test-rules");
        }
        catch (Throwable t) 
        {
            t.printStackTrace();
        }
    }
    
    public void placeFixedEntities()
    {
        	kSession.insert(board);
        	
        	kSession.getAgenda().getAgendaGroup( "PlaceEntity" ).setFocus();
            kSession.fireAllRules();
    }
    
    public void computePossibleMoves()
    {        	
        	List<MovableEntity> movableEntity = board.getMovableEntity();
        	
        	System.out.println(movableEntity.size() + " movable entity");
        	
        	for (MovableEntity entity : movableEntity){
        		kSession.insert(entity);
        	}            
            
        	
            kSession.getAgenda().getAgendaGroup( "Movement" ).setFocus();
            kSession.fireAllRules();
    }

	public void computeCommunications() {
		
	}
}
