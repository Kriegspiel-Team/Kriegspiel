package main;

/**
 * The Interface IEngine.
 */
public interface IEngine {
		
	/**
	 * Compute possible moves.
	 */
	public void computePossibleMoves();
	
	/**
	 * Compute communications.
	 */
	public void computeCommunications();
	
	/**
	 * Compute attack and defence.
	 */
	public void computeAttackDefence();
	
	/**
	 * Compute death.
	 */
	public void computeDeath();
	
	/**
	 * Computes win.
	 */
	public void computeWin();
}
