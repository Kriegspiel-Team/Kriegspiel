package test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import main.Board;
import main.Engine;
import main.EntityLoader;
import model.Cavalry;
import model.Fighter;
import model.Relay;
import model.UnmovableEntity;

public class BoardTest 
{
	@Test
	public void testGetNbrInstance() {
		
		Board b = new Board();
		Engine e = new Engine(b);
		e.initSession();
		e.placeFixedEntities();
		
		assertTrue("Error when computing fighter number without loaded board", b.getNbrInstances(Fighter.class,1)==0);
		assertTrue("Error when computing relay number without loaded board", b.getNbrInstances(Relay.class,0)==0);
		assertTrue("Error when computing cavalry number without loaded board", b.getNbrInstances(Cavalry.class,1)==0);
		
		EntityLoader loader = new EntityLoader(b, "src/main/resources/board/Sample1.ksv", "src/main/resources/board/Map1.txt");
		b.loadBoardWithFile(loader);
		
		assertTrue("Error when computing fighter number", b.getNbrInstances(Fighter.class,1)==15);
		assertTrue("Error when computing relay number", b.getNbrInstances(Relay.class,0)==1);
		assertTrue("Error when computing cavalry number", b.getNbrInstances(Cavalry.class,1)==4);
	}
	
	@Test
	public void testGetNbFighterConnected() {
		
		Board b = new Board();
		Engine e = new Engine(b);
		e.initSession();
		
		assertTrue("Error when computing connected fighter number without loaded board - Team 1", b.getNbFighterConnected(1)==0);
		assertTrue("Error when computing connected fighter number without loaded board - Team 0", b.getNbFighterConnected(0)==0);
		
		
		EntityLoader loader = new EntityLoader(b, "src/main/resources/board/Sample3.ksv", "src/main/resources/board/Map1.kmp");
		loader.loadMap();
		b.loadBoardWithFile(loader);
		e.computeCommunications();
		
		assertTrue("Error when computing connected fighter number with Sample3 - Team 1", b.getNbFighterConnected(1)==13);
		assertTrue("Error when computing connected fighter number with Sample3 - Team 0", b.getNbFighterConnected(0)==13);
		
		b.resetBoard();
		loader = new EntityLoader(b, "src/main/resources/board/Sample4.ksv", "src/main/resources/board/Map1.kmp");
		loader.loadMap();
		b.loadBoardWithFile(loader);
		e.computeCommunications();
		
		
		assertTrue("Error when computing connected fighter number with Sample4 - Team 1", b.getNbFighterConnected(1)==10);
		assertTrue("Error when computing connected fighter number with Sample4 - Team 0", b.getNbFighterConnected(0)==0);
	}
	
	@Test
	public void testResetBoard() {
		
		Board b = new Board();
		Engine e = new Engine(b);
		e.initSession();
		
		EntityLoader loader = new EntityLoader(b, "src/main/resources/board/Sample3.ksv", "src/main/resources/board/Map1.kmp");
		loader.loadMap();
		b.loadBoardWithFile(loader);
		b.resetBoard();
		
		for(int y=0;y<Board.HEIGHT;y++)
		{
			for(int x=0;x<Board.WIDTH;x++)
			{
				assertTrue("Error when reseting the board", b.getMatrix()[x][y]==null);
			}
		}
	}
	
	public void testArsenalIsAttacked() {
		
		Board b = new Board();
		Engine e = new Engine(b);
		e.initSession();
		
		EntityLoader loader = new EntityLoader(b, "src/main/resources/board/Sample4.ksv", "src/main/resources/board/Map1.kmp");
		loader.loadMap();
		b.loadBoardWithFile(loader);
		b.resetBoard();
		
		for(int y=0;y<Board.HEIGHT;y++)
		{
			for(int x=0;x<Board.WIDTH;x++)
			{
				assertTrue("Error when reseting the board", b.getMatrix()[x][y]==null);
			}
		}
	}
	
}
