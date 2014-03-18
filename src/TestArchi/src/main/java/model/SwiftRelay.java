package model;

public class SwiftRelay extends MovableEntity {
	
	private boolean onCommunications;
	
	public SwiftRelay(int owner)
	{
		super();
		symbol = 'R';
		speed = 2;
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
