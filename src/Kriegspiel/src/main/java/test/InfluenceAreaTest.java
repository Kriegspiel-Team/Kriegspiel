package test;

import static org.junit.Assert.*;

import main.Board;
import main.BoardFileFormatException;
import main.Coord;
import main.Engine;
import main.EntityLoader;
import model.Cavalry;
import model.Infantry;
import model.Relay;

import org.junit.Test;

import evaluator.InfluenceArea;

public class InfluenceAreaTest {

	@Test
	public void testRunInfluenceArea() {
		
	}

	@Test
	public void testComputeInfluenceAreas() throws BoardFileFormatException {
		Board board = new Board();
		
		Relay r;
		Cavalry c;
		Infantry i0;
		Infantry i1;
		Infantry i2;
		
		Engine engine = new Engine(board);
		EntityLoader loader = new EntityLoader(board);
		loader.setMapFilename("src/main/resources/board/Map1.kmp");
		loader.loadMap();
		engine.initSession();
		
		// Test sans obstacle
		
		i0 = new Infantry(0);
		board.placeEntity(14, 5, i0);
		
		board.setMapLoaded(true);
		engine.computeCommunications();
		
		InfluenceArea.computeInfluenceArea(board, 14, 5, i0.getSpeed(), i0);
		
		assertTrue(i0.getPossibleMovement().contains(new Coord(14,6)));
		assertTrue(i0.getPossibleMovement().contains(new Coord(14,4)));
		assertTrue(i0.getPossibleMovement().contains(new Coord(15,5)));
		assertTrue(i0.getPossibleMovement().contains(new Coord(13,5)));
		assertTrue(i0.getPossibleMovement().contains(new Coord(15,6)));
		assertTrue(i0.getPossibleMovement().contains(new Coord(15,4)));
		assertTrue(i0.getPossibleMovement().contains(new Coord(13,6)));
		assertTrue(i0.getPossibleMovement().contains(new Coord(13,4)));
		
		board.resetBoard();
		engine.initSession();
		loader.loadMap();
		
		// Test dans un coin de la map
		
		c = new Cavalry(1);
		board.placeEntity(0,19,c);
		
		board.setMapLoaded(true);
		engine.computeCommunications();
		
		InfluenceArea.computeInfluenceArea(board, 0, 19, c.getSpeed(), c);
		
		assertTrue(c.getPossibleMovement().contains(new Coord(0,18)));
		assertTrue(c.getPossibleMovement().contains(new Coord(0,17)));
		assertTrue(c.getPossibleMovement().contains(new Coord(1,19)));
		assertTrue(c.getPossibleMovement().contains(new Coord(2,19)));
		assertTrue(c.getPossibleMovement().contains(new Coord(1,18)));
		assertTrue(c.getPossibleMovement().contains(new Coord(1,17)));
		assertTrue(c.getPossibleMovement().contains(new Coord(2,18)));
		assertTrue(c.getPossibleMovement().contains(new Coord(2,17)));
		
		c.resetPossibleMovement();
		
		board.resetBoard();
		engine.initSession();
		loader.loadMap();
		
		// Test contre une montagne
		
		i0 = new Infantry(0);
		board.placeEntity(10,1,i0);
		
		board.setMapLoaded(true);
		engine.computeCommunications();

		InfluenceArea.computeInfluenceArea(board, 10, 1, i0.getSpeed(), i0);
		
		assertTrue(i0.getPossibleMovement().contains(new Coord(9,1)));
		assertTrue(i0.getPossibleMovement().contains(new Coord(11,1)));
		assertTrue(i0.getPossibleMovement().contains(new Coord(9,0)));
		assertTrue(i0.getPossibleMovement().contains(new Coord(10,0)));
		assertTrue(i0.getPossibleMovement().contains(new Coord(11,0)));
		
		i0.resetPossibleMovement();
		
		board.resetBoard();
		engine.initSession();
		loader.loadMap();
		
		// Test dans un Arnesal
		
		i0 = new Infantry(0);
		board.placeEntity(7,3,i0);
		
		board.setMapLoaded(true);
		engine.computeCommunications();

		InfluenceArea.computeInfluenceArea(board, 7, 3, i0.getSpeed(), i0);
		
		assertTrue(i0.getPossibleMovement().contains(new Coord(7,4)));
		assertTrue(i0.getPossibleMovement().contains(new Coord(7,2)));
		assertTrue(i0.getPossibleMovement().contains(new Coord(6,3)));
		assertTrue(i0.getPossibleMovement().contains(new Coord(8,3)));
		assertTrue(i0.getPossibleMovement().contains(new Coord(6,4)));
		assertTrue(i0.getPossibleMovement().contains(new Coord(6,2)));
		assertTrue(i0.getPossibleMovement().contains(new Coord(8,4)));
		assertTrue(i0.getPossibleMovement().contains(new Coord(8,2)));
		
		i0.resetPossibleMovement();
		
		board.resetBoard();
		engine.initSession();
		loader.loadMap();
		
		// Test dans un col de montagne

		i0 = new Infantry(0);
		board.placeEntity(9,5,i0);
		
		board.setMapLoaded(true);
		engine.computeCommunications();

		InfluenceArea.computeInfluenceArea(board, 9, 5, i0.getSpeed(), i0);
		
		assertTrue(i0.getPossibleMovement().contains(new Coord(8,6)));
		assertTrue(i0.getPossibleMovement().contains(new Coord(8,5)));
		assertTrue(i0.getPossibleMovement().contains(new Coord(8,4)));
		assertTrue(i0.getPossibleMovement().contains(new Coord(10,6)));
		assertTrue(i0.getPossibleMovement().contains(new Coord(10,5)));
		assertTrue(i0.getPossibleMovement().contains(new Coord(10,4)));
		
		i0.resetPossibleMovement();
		
		board.resetBoard();
		engine.initSession();
		loader.loadMap();
		
		// Test à coté d'une autre unité

		i0 = new Infantry(0);
		i1 = new Infantry(0);
		board.placeEntity(7,10,i0);
		board.placeEntity(8,10,i1);
		
		board.setMapLoaded(true);
		engine.computeCommunications();

		InfluenceArea.computeInfluenceArea(board, 8, 10, i1.getSpeed(), i1);
		
		assertTrue(i1.getPossibleMovement().contains(new Coord(8,9)));
		assertTrue(i1.getPossibleMovement().contains(new Coord(8,11)));
		assertTrue(i1.getPossibleMovement().contains(new Coord(9,10)));
		assertTrue(i1.getPossibleMovement().contains(new Coord(9,9)));
		assertTrue(i1.getPossibleMovement().contains(new Coord(9,11)));
		assertTrue(i1.getPossibleMovement().contains(new Coord(7,11)));
		assertTrue(i1.getPossibleMovement().contains(new Coord(7,9)));
		
		i1.resetPossibleMovement();
		
		board.resetBoard();
		engine.initSession();
		loader.loadMap();
		
		// Test à coté d'un fort contenant une unité
		
		i0 = new Infantry(0);
		i1 = new Infantry(0);
		board.placeEntity(7,1,i0);
		board.placeEntity(8,1,i1);
		
		board.setMapLoaded(true);
		engine.computeCommunications();

		InfluenceArea.computeInfluenceArea(board, 8, 1, i1.getSpeed(), i1);
		
		assertTrue(i1.getPossibleMovement().contains(new Coord(7,0)));
		assertTrue(i1.getPossibleMovement().contains(new Coord(8,0)));
		assertTrue(i1.getPossibleMovement().contains(new Coord(9,0)));
		assertTrue(i1.getPossibleMovement().contains(new Coord(9,1)));
		assertTrue(i1.getPossibleMovement().contains(new Coord(7,2)));
		assertTrue(i1.getPossibleMovement().contains(new Coord(8,2)));
		
		i1.resetPossibleMovement();
		
		board.resetBoard();
		engine.initSession();
		loader.loadMap();
		
		// Test dans un coin, coincé par 3 unités
		
		
		r = new Relay(1);
		c = new Cavalry(1);
		i0 = new Infantry(0);
		i1 = new Infantry(0);
		i2 = new Infantry(0);
		board.placeEntity(14,0,r);
		board.placeEntity(0,0,c);
		board.placeEntity(0,1,i0);
		board.placeEntity(1,0,i1);
		board.placeEntity(1,1,i2);
		
		board.setMapLoaded(true);
		engine.computeCommunications();

		InfluenceArea.computeInfluenceArea(board, 0, 0, c.getSpeed(), c);
		
		assertTrue(c.getPossibleMovement().size() == 0);
		
		c.resetPossibleMovement();
		
		board.resetBoard();
		engine.initSession();
		loader.loadMap();
		
		// Test hors des communications

		i0 = new Infantry(0);
		board.placeEntity(5,10,i0);
		
		board.setMapLoaded(true);
		engine.computeCommunications();

		InfluenceArea.computeInfluenceArea(board, 5, 10, i0.getSpeed(), i0);
		
		assertTrue(i0.getPossibleMovement().size() == 0);
		
		i0.resetPossibleMovement();
		
		board.resetBoard();
		engine.initSession();
		loader.loadMap();
		
		// Test de contournement d'une extrémité de montagne pour une cavalerie
		
		c = new Cavalry(1);
		board.placeEntity(15,12,c);
		board.setMapLoaded(true);
		engine.computeCommunications();

		InfluenceArea.computeInfluenceArea(board, 15, 12, c.getSpeed(), c);

		assertTrue(c.getPossibleMovement().contains(new Coord(13,12)));
		assertTrue(c.getPossibleMovement().contains(new Coord(14,12)));
		assertTrue(c.getPossibleMovement().contains(new Coord(16,12)));
		assertTrue(c.getPossibleMovement().contains(new Coord(17,12)));
		assertTrue(c.getPossibleMovement().contains(new Coord(13,11)));
		assertTrue(c.getPossibleMovement().contains(new Coord(14,11)));
		assertTrue(c.getPossibleMovement().contains(new Coord(15,11)));
		assertTrue(c.getPossibleMovement().contains(new Coord(16,11)));
		assertTrue(c.getPossibleMovement().contains(new Coord(17,11)));
		assertTrue(c.getPossibleMovement().contains(new Coord(13,10)));
		assertTrue(c.getPossibleMovement().contains(new Coord(14,10)));
		assertTrue(c.getPossibleMovement().contains(new Coord(15,10)));
		assertTrue(c.getPossibleMovement().contains(new Coord(16,10)));
		assertTrue(c.getPossibleMovement().contains(new Coord(17,10)));
		assertTrue(c.getPossibleMovement().contains(new Coord(16,13)));
		assertTrue(c.getPossibleMovement().contains(new Coord(17,13)));
		assertTrue(c.getPossibleMovement().contains(new Coord(17,14)));
		assertTrue(c.getPossibleMovement().contains(new Coord(16,14)));
		assertTrue(c.getPossibleMovement().contains(new Coord(15,14)));
		
	}

}
