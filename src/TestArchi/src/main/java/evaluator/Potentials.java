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
		for(MovableEntity e : board.getMovableEntity())
			CalculAttack(e);
	}
	
	public void CalculAttack(MovableEntity e)
	{
		int maxRange = 4;
		
		for(int i = -1; i <= 1; i++) {
			for(int j = -1; j <= 1; j++) {
				
				boolean charge = true;
				
				for(int r = 1; r <= maxRange; r++) {
					
					int x = e.getCoord().x + i*r;
					int y = e.getCoord().y + j*r;
					
					if(board.inBoard(x, y) && board.isMovableEntity(x, y) && !board.isFriendlyUnit(x, y, e.getOwner())) {
						
						MovableEntity enemy = board.getUnit(x, y);
						
						if(enemy instanceof Cavalry && charge) {
							e.setEnemyAttack(e.getEnemyAttack()+((Cavalry)enemy).getAttackCharge());
						} else {
							
							charge = false;
							
							if(r <= enemy.getRange())
								e.setEnemyAttack(e.getEnemyAttack()+enemy.getAttack());
						}
					} else {
						charge = false;
					}
				}
			}
		}
	}
}
