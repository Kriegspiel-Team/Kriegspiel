package main;

import java.util.List;

import model.MovableEntity;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

public class Engine implements IEngine{
	
	private Board board;
	private KieServices ks;
	private KieContainer kContainer;
	private KieSession kSession;

    public Engine(){	
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
    
    public void initSession(Board b)
    {
    	if(kSession != null)
    		kSession.destroy();
    	this.board = b;
    	kSession = kContainer.newKieSession("kriegspiel-knowledge");
    }
    
    public void placeFixedEntities(){
    	kSession.insert(board);
    	
    	kSession.getAgenda().getAgendaGroup( "PlaceEntity" ).setFocus();
        kSession.fireAllRules();
    }
    
    public void computePossibleMoves(){        	
    	
        kSession.getAgenda().getAgendaGroup( "Movement" ).setFocus();
        kSession.fireAllRules();
    }

	public void computeCommunications(){
	
		List<MovableEntity> movableEntity = board.getMovableEntity();
    	    	
    	for (MovableEntity entity : movableEntity){
    		kSession.insert(entity);
    	}            
		
        kSession.getAgenda().getAgendaGroup( "Communication" ).setFocus();
        kSession.fireAllRules();
	}
	
	public void computeAttackDefence(){        	
	    	
		kSession.getAgenda().getAgendaGroup( "Battle" ).setFocus();
		kSession.fireAllRules();
	}
	
	public void computeDeath()
	{
		kSession.getAgenda().getAgendaGroup( "DeathRule" ).setFocus();
		kSession.fireAllRules();
	}
}
