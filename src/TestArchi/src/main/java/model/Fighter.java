package model;

/**
 * The Class Fighter.
 */
public abstract class Fighter extends MovableEntity {
	
	/** Whether it is connected to the com network. */
	protected boolean isConnected;

	/**
	 * Instantiates a new fighter.
	 */
	public Fighter() {
		super();
		this.isConnected = false;
	}
	
	/**
	 * Checks if is connected.
	 *
	 * @return true, if is connected
	 */
	public boolean isConnected() {
		return this.isConnected;
	}
	
	/**
	 * Sets the connection state.
	 *
	 * @param isConnected the new connection state.
	 */
	public void setConnected(boolean isConnected) {
		this.isConnected = isConnected;
	}
}
