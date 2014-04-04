package model;

import java.util.HashSet;
import java.util.Set;

import main.Coord;

/**
 * The Class MovableEntity.
 */
public class MovableEntity extends Entity {
	
	/** Whether the unit can be killed. */
	protected boolean canBeKilled;
	
	/** Whether the unit must retreat. */
	protected boolean mustRetreat;
	
	/** The speed. */
	protected int speed;
	
	/** The range. */
	protected int range;
	
	/** The base attack. */
	protected int attack;
	
	/** The base defence. */
	protected int defence;
	
	/** The enemy attack against this unit. */
	protected int enemyAttack;
	
	/** The total ally defence towards this unit. */
	protected int allyDefence;
	
	/** The possible moves of this unit. */
	protected Set<Coord> possibleMovement;
	
	/**
	 * Instantiates a new movable entity.
	 */
	public MovableEntity() {
		possibleMovement = new HashSet<Coord>();
		canContain = false;
		canBeKilled = false;
		mustRetreat = false;
	}

	/**
	 * Gets the speed.
	 *
	 * @return the speed
	 */
	public int getSpeed() {
		return speed;
	}
	
	/**
	 * Reset possible movement.
	 */
	public void resetPossibleMovement() {
		possibleMovement.clear();
	}
	
	/**
	 * Adds the possible movement.
	 *
	 * @param c the coordinates of the move
	 */
	public void addPossibleMovement(Coord c) {		
		possibleMovement.add(c);
		
	}

	/**
	 * Gets the possible moves.
	 *
	 * @return the possible moves
	 */
	public Set<Coord> getPossibleMovement() {
		return possibleMovement;
	}
	
	/**
	 * Gets the base attack.
	 *
	 * @return the base attack of this unit
	 */
	public int getAttack() {
		return attack;
	}
	
	/**
	 * Gets the base defence.
	 *
	 * @return the base defence of this unit
	 */
	public int getDefence() {
		return defence;
	}
	
	/**
	 * Resets the attack.
	 * 
	 */
	public void setAttackNull() {
		this.attack = 0;
	}

	/**
	 * Sets the defence.
	 *
	 * @param defence the new defence
	 */
	public void setDefence(int defence) {
		this.defence = defence;
	}

	/**
	 * Gets the range.
	 *
	 * @return the range
	 */
	public int getRange() {
		return range;
	}
	
	/**
	 * Gets the enemy attack.
	 *
	 * @return the enemy attack against this unit
	 */
	public int getEnemyAttack() {
		return enemyAttack;
	}

	/**
	 * Sets the enemy attack.
	 *
	 * @param enemyAttack the new enemy attack
	 */
	public void setEnemyAttack(int enemyAttack) {
		this.enemyAttack = enemyAttack;
	}
	
	/**
	 * Gets the ally defence.
	 *
	 * @return the ally defence towards this unit
	 */
	public int getAllyDefence() {
		return allyDefence;
	}

	/**
	 * Sets the ally defence.
	 *
	 * @param allyDefence the new ally defence
	 */
	public void setAllyDefence(int allyDefence) {
		this.allyDefence = allyDefence;
	}

	/**
	 * Can be killed next turn.
	 *
	 * @return true, if this unit can be killed next turn
	 */
	public boolean canBeKilled() {
		return canBeKilled;
	}

	/**
	 * Sets whether the unit can be killed next turn.
	 *
	 * @param canBeKilled the new value of canBeKilled
	 */
	public void setCanBeKilled(boolean canBeKilled) {
		this.canBeKilled = canBeKilled;
	}

	/**
	 * Must retreat next turn.
	 *
	 * @return true, if the unit must retreat next turn
	 */
	public boolean mustRetreat() {
		return mustRetreat;
	}

	/**
	 * Sets the must retreat.
	 *
	 * @param mustRetreat the new value of mustRetreat
	 */
	public void setMustRetreat(boolean mustRetreat) {
		this.mustRetreat = mustRetreat;
	}

}
