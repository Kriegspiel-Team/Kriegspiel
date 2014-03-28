package main;

import java.util.List;

import model.Fighter;
import model.MovableEntity;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

public class Engine implements IEngine {
	
	private Board board;
	private KieServices ks;
	private KieContainer kContainer;
	private KieSession kSession;

    public Engine(Board b){	
    	board = b;
    	
    	try{
            // load up the knowledge base
	        ks = KieServices.Factory.get();
    	    kContainer = ks.getKieClasspathContainer();
    	    kSession = null;
        }
        catch (Throwable t){
            t.printStackTrace();
        }
    }
    
    public void initSession() {
    	if(kSession != null)
    		kSession.destroy();

    	kSession = kContainer.newKieSession("kriegspiel-knowledge");
    }
    
    @Override
	public void placeFixedEntities() {
    	kSession.insert(board);
    	
    	kSession.getAgenda().getAgendaGroup( "PlaceEntity" ).setFocus();
        kSession.fireAllRules();
    }
    
    @Override
	public void computePossibleMoves() {        	
    	
        kSession.getAgenda().getAgendaGroup( "Movement" ).setFocus();
        kSession.fireAllRules();
    }

	@Override
	public void computeCommunications() {
	
		List<MovableEntity> movableEntity = board.getMovableEntities();
    	    	
    	for (MovableEntity entity : movableEntity){
    		if(entity instanceof Fighter)
    			kSession.insert((Fighter)entity);
    		else 
    			kSession.insert(entity);
    	}            
		
        kSession.getAgenda().getAgendaGroup( "Communication" ).setFocus();
        kSession.fireAllRules();
	}
	
	@Override
	public void computeAttackDefence() {        	
	    	
		kSession.getAgenda().getAgendaGroup( "Battle" ).setFocus();
		kSession.fireAllRules();
	}
	
	@Override
	public void computeDeath() {
		kSession.getAgenda().getAgendaGroup( "DeathRule" ).setFocus();
		kSession.fireAllRules();
	}
}
