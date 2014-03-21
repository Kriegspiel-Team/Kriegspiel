package evaluator;
import main.Board;
import model.Cavalry;
import model.MovableEntity;

public class Potentials {

	public Board board;
	
	public Potentials(Board board)
	{
		this.board = board;
	}
	
	public void UnityPotentials()
	{
		for(MovableEntity e : board.getMovableEntity()) {
			CalculAttack(e);
			CalculDefence(e);
		}
	}
	
	public void CalculAttack(MovableEntity e) {
		int maxRange = 4;
		
		for(int i = -1; i <= 1; i++) {
			for(int j = -1; j <= 1; j++) {
				
				boolean charge = true;
				boolean obstacle = false;
				
				for(int r = 1; r <= maxRange; r++) {
					
					if((i != 0 || j != 0) && !obstacle) {
						
						int x = e.getCoord().x + i*r;
						int y = e.getCoord().y + j*r;
					
						if(board.inBoard(x, y)) {
						
							MovableEntity unit = board.getUnit(x, y);
							if(unit != null && !board.isFriendlyUnit(x, y, e.getOwner())) {
								
								if(unit instanceof Cavalry && !board.isFortress(x,y) && charge) {
									e.setEnemyAttack(e.getEnemyAttack()+((Cavalry)unit).getAttackCharge());
								} else {
							
									charge = false;
							
									if(r <= unit.getRange())
										e.setEnemyAttack(e.getEnemyAttack()+unit.getAttack());
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
	}
	
	public void CalculDefence(MovableEntity e) {
		int maxRange = 3;
		
		e.setAllyDefence(e.getDefence());
		
		for(int i = -1; i <= 1; i++) {
			for(int j = -1; j <= 1; j++) {
				
				boolean obstacle = false;
				
				for(int r = 1; r <= maxRange; r++) {
					
					if((i != 0 || j != 0) && !obstacle) {
						
						int x = e.getCoord().x + i*r;
						int y = e.getCoord().y + j*r;
						
						if(board.inBoard(x, y)) {
						
							MovableEntity unit = board.getUnit(x, y);
					
							if(unit != null && board.isFriendlyUnit(x, y, e.getOwner())) {
						
								if(r <= unit.getRange())
									e.setAllyDefence(e.getAllyDefence()+unit.getDefence());
								
							} else if(board.isMountain(x, y))
								obstacle = true;
						}
					}
				}
			}
		}
	}
}
