package model;

/**
 * The Class SwiftRelay.
 */
public class SwiftRelay extends MovableEntity {
	
	/** The on communications. */
	private boolean onCommunications;
	
	/**
	 * Instantiates a new swift relay.
	 *
	 * @param owner the owner
	 */
	public SwiftRelay(int owner)
	{
		super();
		symbol = 'R';
		speed = 2;
		range = 0;
		attack = 0;
		defence = 1;
		enemyAttack = 0;
		allyDefence = 0;
		onCommunications = false;
		this.owner = owner;
	}
	
	/**
	 * Checks if is on communications.
	 *
	 * @return true, if is on communications
	 */
	public boolean isOnCommunications() {
		return onCommunications;
	}

	/**
	 * Sets the on communications.
	 *
	 * @param onCommunications the new on communications
	 */
	public void setOnCommunications(boolean onCommunications) {
		this.onCommunications = onCommunications;
	}
}
