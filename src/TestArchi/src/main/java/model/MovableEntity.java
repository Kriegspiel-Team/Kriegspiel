package model;

import java.util.HashSet;
import java.util.Set;

import main.Coord;

public class MovableEntity extends Entity {
	
	protected boolean canBeKilled;
	protected boolean mustRetreat;
	protected int speed;
	protected int range;
	protected int attack;
	protected int defence;
	protected int enemyAttack;
	protected int allyDefence;
	protected int speedLeft;
	protected int counterDirectionRule;
	protected Set<Coord> possibleMovement;
	
	public MovableEntity() {
		possibleMovement = new HashSet<Coord>();
		counterDirectionRule = 0;
		canContain = false;
		canBeKilled = false;
		mustRetreat = false;
	}

	public int getSpeed() {
		return speed;
	}
	
	public void resetPossibleMovement() {
		possibleMovement.clear();
	}
	
	public void addPossibleMovement(Coord c) {		
		possibleMovement.add(c);
		
	}

	public Set<Coord> getPossibleMovement() {
		return possibleMovement;
	}

	public int getSpeedLeft() {
		return speedLeft;
	}
	
	public void setSpeedLeft(int speedLeft) {
		this.speedLeft = speedLeft;
	}
	
	public void decreaseSpeedLeft() {
		speedLeft--;
	}
	
	public int getCounterDirectionRule() {
		return counterDirectionRule;
	}
	
	public void setCounterDirectionRule(int n) {
		this.counterDirectionRule = n;
	}

	public void decrCounterDirectionRule() {
		this.counterDirectionRule -= 1;
	}

	public void setConnected(boolean isConnected) {
		this.isConnected = isConnected;
	}
	
	public int getAttack() {
		return attack;
	}
	
	public int getDefence() {
		return defence;
	}
	
	public void setAttack(int attack) {
		this.attack = attack;
	}

	public void setDefence(int defence) {
		this.defence = defence;
	}

	public int getRange() {
		return range;
	}
	
	public int getEnemyAttack() {
		return enemyAttack;
	}

	public void setEnemyAttack(int enemyAttack) {
		this.enemyAttack = enemyAttack;
	}
	
	public int getAllyDefence() {
		return allyDefence;
	}

	public void setAllyDefence(int allyDefence) {
		this.allyDefence = allyDefence;
	}

	public boolean canBeKilled() {
		return canBeKilled;
	}

	public void setCanBeKilled(boolean canBeKilled) {
		this.canBeKilled = canBeKilled;
	}

	public boolean mustRetreat() {
		return mustRetreat;
	}

	public void setMustRetreat(boolean mustRetreat) {
		this.mustRetreat = mustRetreat;
	}

}
