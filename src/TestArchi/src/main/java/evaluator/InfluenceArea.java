package evaluator;

import java.util.List;

import main.Board;
import main.Coord;
import model.Fighter;
import model.MovableEntity;
import model.Relay;
import model.SwiftRelay;

/**
 * This class is used  to compute and store the influence areas of each Entity.
 */
public class InfluenceArea {
	
	/**
	 * Compute all the influence areas
	 *
	 * @param board the board
	 */
	public static void computeAllInfluenceAreas(Board board) {
		List<MovableEntity> listMov = board.getMovableEntities();
		
		for(MovableEntity m : listMov)
			computeInfluenceArea(board, m.getCoord().x, m.getCoord().y, m.getSpeed(), m);
	}
	

	/**
	 * Compute influence areas.
	 *
	 * @param board the board
	 * @param x the x coordinate
	 * @param y the y coordinate
	 * @param speedLeft the recursive counter of remaining moves
	 * @param m the entity to be checked
	 */
	public static void computeInfluenceArea(Board board, int x, int y, int speedLeft, MovableEntity m) {
		
		
		if(speedLeft > 0 && ((m instanceof Fighter && ((Fighter)m).isConnected()) || m instanceof Relay || m instanceof SwiftRelay))
			for(int i = -1; i <= 1; i++)
				for(int j = -1; j <= 1; j++)
					if(board.isValidDestination(x + i, y + j)) {
						m.addPossibleMovement(new Coord(x + i, y + j));
						computeInfluenceArea(board, x + i, y + j, speedLeft - 1, m);
					}
	}

}
