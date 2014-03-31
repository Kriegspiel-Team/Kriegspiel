package model;

/**
 * The Class Relay.
 */
public class Relay extends MovableEntity {
	
	/** Whether it is on communications. */
	private boolean onCommunications;

	/**
	 * Instantiates a new relay.
	 *
	 * @param owner the owner
	 */
	public Relay(int owner)
	{
		super();
		symbol = 'r';
		speed = 1;
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
	 * Sets the onCommunications.
	 *
	 * @param onCommunications the new value of onCommunications
	 */
	public void setOnCommunications(boolean onCommunications) {
		this.onCommunications = onCommunications;
	}
}
