package model;

public class Fighter extends MovableEntity {
	
	protected boolean isConnected;

	public Fighter() {
		super();
		this.isConnected = false;
	}
	
	public boolean isConnected() {
		return this.isConnected;
	}
	
	public void setConnected(boolean isConnected) {
		this.isConnected = isConnected;
	}
}
