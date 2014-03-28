package evaluator;
import java.util.HashMap;

import main.Board;
import model.Cavalry;
import model.MovableEntity;

public class Potentials {

	public Board board;
	public HashMap<Integer, Integer[][]> prevailing;
	
	public Potentials(Board board)
	{
		this.board = board;
		this.prevailing = new HashMap<Integer, Integer[][]>();
		this.prevailing.put(0, new Integer[Board.WIDTH][Board.HEIGHT]);
		this.prevailing.put(1, new Integer[Board.WIDTH][Board.HEIGHT]);
	}
	
	public void computePotentials()
	{
		System.out.println(board.toString());
		for(int team = 0; team < 2; team++) {
			for(int y = 0; y < Board.HEIGHT; y++) {
				for(int x = 0; x < Board.WIDTH; x++) {
					
					MovableEntity e = board.getUnit(x, y);
					
					int attack = computeAttack(x,y,team);
					int defence = computeDefence(x,y,team);
					
					if(e!=null && e.getOwner()==team) {
						e.setEnemyAttack(attack);
						e.setAllyDefence(defence);
					}
					
					prevailing.get(team)[x][y] = attack;
				}
			}
		}
	}
	
	public int computeAttack(int xi, int yi, int owner) {
		int maxRange = 4;
		
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
								
								if(unit instanceof Cavalry && !board.isFortress(x,y) && charge) {
									attack += ((Cavalry)unit).getAttackCharge();
								} else {
							
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
	
	public int computeDefence(int xi, int yi, int owner) {
		int maxRange = 3;

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
