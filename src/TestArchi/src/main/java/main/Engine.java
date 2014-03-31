package main;

import java.util.List;

import model.MovableEntity;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

/**
 * The Class Engine.
 */
public class Engine implements IEngine {
	
	/** The board. */
	private Board board;
	
	/** The ks. */
	private KieServices ks;
	
	/** The k container. */
	private KieContainer kContainer;
	
	/** The k session. */
	private KieSession kSession;

    /**
     * Instantiates a new engine.
     *
     * @param b the board
     */
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
    
    /**
     * Initializes the session.
     */
    public void initSession() {
    	if(kSession != null)
    		kSession.destroy();

    	kSession = kContainer.newKieSession("kriegspiel-knowledge");
    }
    
    /* (non-Javadoc)
     * @see main.IEngine#placeFixedEntities()
     */
    @Override
	public void placeFixedEntities() {
    	kSession.insert(board);
    	
    	kSession.getAgenda().getAgendaGroup( "PlaceEntity" ).setFocus();
        kSession.fireAllRules();
    }
    
    /* (non-Javadoc)
     * @see main.IEngine#computePossibleMoves()
     */
    @Override
	public void computePossibleMoves() {        	
    	
        kSession.getAgenda().getAgendaGroup( "Movement" ).setFocus();
        kSession.fireAllRules();
    }

	/* (non-Javadoc)
	 * @see main.IEngine#computeCommunications()
	 */
	@Override
	public void computeCommunications() {
	
		List<MovableEntity> movableEntity = board.getMovableEntities();
    	    	
    	for (MovableEntity entity : movableEntity){
    		kSession.insert(entity);
    	}            
		
        kSession.getAgenda().getAgendaGroup( "Communication" ).setFocus();
        kSession.fireAllRules();
	}
	
	/* (non-Javadoc)
	 * @see main.IEngine#computeAttackDefence()
	 */
	@Override
	public void computeAttackDefence() {        	
	    	
		kSession.getAgenda().getAgendaGroup( "Battle" ).setFocus();
		kSession.fireAllRules();
	}
	
	/* (non-Javadoc)
	 * @see main.IEngine#computeDeath()
	 */
	@Override
	public void computeDeath() {
		kSession.getAgenda().getAgendaGroup( "DeathRule" ).setFocus();
		kSession.fireAllRules();
	}
}
