package main;

/**
 * The Interface IEngine.
 */
public interface IEngine {
	
	/**
	 * Place fixed entities.
	 */
	public void placeFixedEntities();
	
	/**
	 * Compute possible moves.
	 */
	public void computePossibleMoves();
	
	/**
	 * Compute communications.
	 */
	public void computeCommunications();
	
	/**
	 * Compute attack defence.
	 */
	public void computeAttackDefence();
	
	/**
	 * Compute death.
	 */
	public void computeDeath();
	
	public void computeWin();
}
