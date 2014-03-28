package model;

public class SwiftRelay extends MovableEntity {
	
	private boolean onCommunications;
	
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
	
	public boolean isOnCommunications() {
		return onCommunications;
	}

	public void setOnCommunications(boolean onCommunications) {
		this.onCommunications = onCommunications;
	}
}
