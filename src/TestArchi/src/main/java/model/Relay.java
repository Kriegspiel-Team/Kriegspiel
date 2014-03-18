package model;

public class Relay extends MovableEntity {
	
	private boolean onCommunications;

	public Relay(int owner)
	{
		super();
		symbol = 'r';
		speed = 1;
		speedLeft = speed;
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
