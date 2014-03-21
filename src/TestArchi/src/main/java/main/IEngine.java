package main;

public interface IEngine {
	public void placeFixedEntities();
	public void computePossibleMoves();
	public void computeCommunications();
	public void computeDefenceBonuses();
}
