package evaluator;
import java.util.HashMap;

import main.Board;
import model.Cannon;
import model.Cavalry;
import model.Infantry;
import model.MovableEntity;
import model.Relay;
import model.SwiftCannon;
import model.SwiftRelay;

/**
 * This class is used to compute and store the total potential attack and defence
 * towards each Entity.
 */
public class Potentials {

	/** The board. */
	public Board board;
	
	/** The prevailing matrices. */
	public HashMap<Integer, Integer[][]> matrix_attack;
	public HashMap<Integer, Integer[][]> matrix_defence;
	
	/**
	 * Instantiates a new potentials.
	 *
	 * @param board the board
	 */
	public Potentials(Board board) {
		this.board = board;
		
		this.matrix_attack = new HashMap<Integer, Integer[][]>();
		this.matrix_attack.put(0, new Integer[Board.WIDTH][Board.HEIGHT]);
		this.matrix_attack.put(1, new Integer[Board.WIDTH][Board.HEIGHT]);
		
		this.matrix_defence = new HashMap<Integer, Integer[][]>();
		this.matrix_defence.put(0, new Integer[Board.WIDTH][Board.HEIGHT]);
		this.matrix_defence.put(1, new Integer[Board.WIDTH][Board.HEIGHT]);
	}
	
	/**
	 * Compute potentials and build 4 potentiel matrices
	 */
	public void computePotentials() {

		for(int team = 0; team < 2; team++) {
			for(int y = 0; y < Board.HEIGHT; y++) {
				for(int x = 0; x < Board.WIDTH; x++) {
					
					MovableEntity m = board.getUnit(x, y);
					
					
					int attack = computeEnnemyAttack(x,y,team);
					int defence = computeAllyDefence(x,y,team);
					
					if(m!=null && m.getOwner()==team) {
						m.setEnemyAttack(attack);
						m.setAllyDefence(defence);
					}
					
					matrix_attack.get((team+1)%2)[x][y] = attack;
					matrix_defence.get(team)[x][y] = defence;
				}
			}
		}
	}
	
	/**
	 * Compute ennemy attack received by a unit
	 *
	 * @param xi the x coord
	 * @param yi the y coord
	 * @param owner the owner of the unit on the square
	 * @return the total potential attack by the enemy team on this square.
	 */
	
	public int computeMaxUnitRange()
	{
		int maxUnitRange = Math.max(new Infantry(0).getRange(),new Cavalry(0).getRange());
		maxUnitRange = Math.max(maxUnitRange,new Cannon(0).getRange());
		maxUnitRange = Math.max(maxUnitRange,new SwiftCannon(0).getRange());
		maxUnitRange = Math.max(maxUnitRange,new Relay(0).getRange());
		maxUnitRange = Math.max(maxUnitRange,new SwiftRelay(0).getRange());
		
		return maxUnitRange;
	}
	
	public int computeEnnemyAttack(int xi, int yi, int owner) {
		
		int nbCavalry = 0;
		for(MovableEntity m : board.getMovableEntities())
			if(m instanceof Cavalry)
				nbCavalry++;

		int maxRange = Math.max(computeMaxUnitRange(), nbCavalry);
		
		int attack = 0;
		
		for(int i = -1; i <= 1; i++) {
			for(int j = -1; j <= 1; j++) {
				
				boolean charge = true;
				boolean obstacle = false;
				
				for(int r = 1; r <= maxRange; r++) {
					
					if((i != 0 || j != 0) && !obstacle) {
						
						int x = xi + i*r;
						int y = yi + j*r;
					
						if(board.inBoard(x, y)) {
						
							MovableEntity unit = board.getUnit(x, y);
							if(unit != null && !board.isFriendlyUnit(x, y, owner)) {
								
								if(unit instanceof Cavalry && !board.isFortress(x,y) && charge)
									attack += ((Cavalry)unit).getAttackCharge();
								else {
							
									charge = false;
							
									if(r <= unit.getRange())
										attack += unit.getAttack();
								}
									
							} else {
								if(board.isMountain(x, y))
									obstacle = true;
								charge = false;
							}
						}
					}
				}
			}
		}
		return attack;
	}
	
	/**
	 * Compute ally defence received by a unit
	 *
	 * @param xi the x coordinate
	 * @param yi the y coordinate
	 * @param owner the owner of the unit on the square
	 * @return the total defence provided by this team on this square
	 */
	public int computeAllyDefence(int xi, int yi, int owner) {
		int maxRange = computeMaxUnitRange();

		int defence = 0;
		MovableEntity e = board.getUnit(xi, yi);
		if(e!=null && e.getOwner()==owner)
			defence += e.getDefence();
		
		for(int i = -1; i <= 1; i++) {
			for(int j = -1; j <= 1; j++) {
				
				boolean obstacle = false;
				
				for(int r = 1; r <= maxRange; r++) {
					
					if((i != 0 || j != 0) && !obstacle) {
						
						int x = xi + i*r;
						int y = yi + j*r;
						
						if(board.inBoard(x, y)) {
						
							MovableEntity unit = board.getUnit(x, y);
					
							if(unit != null && board.isFriendlyUnit(x, y, owner)) {
						
								if(r <= unit.getRange())
									defence += unit.getDefence();
								
							} else if(board.isMountain(x, y))
								obstacle = true;
						}
					}
				}
			}
		}
		
		return defence;
	}
}
