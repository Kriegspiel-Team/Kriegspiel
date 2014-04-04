package test;
import static org.junit.Assert.assertTrue;


import org.junit.Test;

import main.Board;
import main.BoardFileFormatException;
import main.Engine;
import main.EntityLoader;
import model.Arsenal;
import model.Canon;
import model.Cavalry;
import model.Fighter;
import model.Infantry;
import model.Mountain;
import model.MovableEntity;
import model.Relay;
import model.SwiftCanon;
import model.SwiftRelay;

public class BoardTest 
{
	@Test
	public void testGetNbrInstance() throws BoardFileFormatException {
		
		Board b = new Board();
		Engine e = new Engine(b);
		e.initSession();
		e.placeFixedEntities();
		
		assertTrue("Error when computing fighter number without loaded board", b.getNbrInstances(Fighter.class,1)==0);
		assertTrue("Error when computing relay number without loaded board", b.getNbrInstances(Relay.class,0)==0);
		assertTrue("Error when computing cavalry number without loaded board", b.getNbrInstances(Cavalry.class,1)==0);
		
		EntityLoader loader = new EntityLoader(b, "src/main/resources/board/Sample1.ksv", "src/main/resources/board/Map1.txt");
		loader.loadMovableEntities();
		
		assertTrue("Error when computing fighter number", b.getNbrInstances(Fighter.class,1)==15);
		assertTrue("Error when computing relay number", b.getNbrInstances(Relay.class,0)==1);
		assertTrue("Error when computing cavalry number", b.getNbrInstances(Cavalry.class,1)==4);
	}
	
	@Test
	public void testGetNbFighterConnected() throws BoardFileFormatException {
		
		Board b = new Board();
		Engine e = new Engine(b);
		e.initSession();
		
		assertTrue("Error when computing connected fighter number without loaded board - Team 1", b.getNbFighterConnected(1)==0);
		assertTrue("Error when computing connected fighter number without loaded board - Team 0", b.getNbFighterConnected(0)==0);
		
		
		EntityLoader loader = new EntityLoader(b, "src/main/resources/board/Sample3.ksv", "src/main/resources/board/Map1.kmp");
		loader.loadMap();
		loader.loadMovableEntities();
		e.computeCommunications();
		
		assertTrue("Error when computing connected fighter number with Sample3 - Team 1", b.getNbFighterConnected(1)==13);
		assertTrue("Error when computing connected fighter number with Sample3 - Team 0", b.getNbFighterConnected(0)==13);
		
		b.resetBoard();
		loader = new EntityLoader(b, "src/main/resources/board/Sample4.ksv", "src/main/resources/board/Map1.kmp");
		loader.loadMap();
		loader.loadMovableEntities();
		e.computeCommunications();
		
		assertTrue("Error when computing connected fighter number with Sample4 - Team 1", b.getNbFighterConnected(1)==10);
		assertTrue("Error when computing connected fighter number with Sample4 - Team 0", b.getNbFighterConnected(0)==0);
	}
	
	@Test
	public void testResetBoard() throws BoardFileFormatException {
		
		Board b = new Board();
		Engine e = new Engine(b);
		e.initSession();
		
		EntityLoader loader = new EntityLoader(b, "src/main/resources/board/Sample3.ksv", "src/main/resources/board/Map1.kmp");
		loader.loadMap();
		loader.loadMovableEntities();
		b.resetBoard();
		
		for(int y=0;y<Board.HEIGHT;y++)
		{
			for(int x=0;x<Board.WIDTH;x++)
			{
				assertTrue("Error when reseting the board", b.getMatrix()[x][y]==null);
			}
		}
	}
	
	@Test
	public void testArsenalIsAttacked() throws BoardFileFormatException {
		
		Board b = new Board();
		Engine e = new Engine(b);
		e.initSession();
		
		EntityLoader loader = new EntityLoader(b, "src/main/resources/board/SampleTest1.ksv", "src/main/resources/board/Map1.kmp");
		loader.loadMap();
		loader.loadMovableEntities();
		
		assertTrue("Error when empty arsenal", b.arsenalIsAttacked((Arsenal)b.getMatrix()[14][1]) == false);
		assertTrue("Error when there is an ally unit on an arsenal", b.arsenalIsAttacked((Arsenal)b.getMatrix()[7][3]) == false);
		assertTrue("Error when there is on ennemy fighter on an arsenal", b.arsenalIsAttacked((Arsenal)b.getMatrix()[2][19]) == true);
		assertTrue("Error when there is a relay on an arsenal", b.arsenalIsAttacked((Arsenal)b.getMatrix()[22][19]) == false);
	}
	
	@Test
	public void testPlaceEntity() throws BoardFileFormatException {
		
		Board b = new Board();
		Engine e = new Engine(b);
		e.initSession();
		
		EntityLoader loader = new EntityLoader(b, "src/main/resources/board/SampleTest1.ksv", "src/main/resources/board/Map1.kmp");
		loader.loadMap();
		
		Infantry i = new Infantry(0);
		Relay r = new Relay(0);
		b.placeEntity(0, 0, i);
		b.placeEntity(15, 17, i);
		b.placeEntity(15, 0, r);
		
		assertTrue("Error when placing infantry in an empty square", b.getMatrix()[0][0] instanceof Infantry);
		assertTrue("Error when placing infantry on a mountain", b.getMatrix()[15][17] instanceof Mountain);
		assertTrue("Error when placing relay in an empty square", b.getMatrix()[15][0] instanceof Relay);
	}
	
	@Test
	public void testGetMovableEntities() throws BoardFileFormatException {
		
		Board b = new Board();
		Engine e = new Engine(b);
		e.initSession();

		EntityLoader loader = new EntityLoader(b, "src/main/resources/board/Sample3.ksv", "src/main/resources/board/Map1.kmp");
		loader.loadMap();
		loader.loadMovableEntities();
		e.computeCommunications();
		
		int nb_infantry = 0;
		int nb_cavalry = 0;
		int nb_canon = 0;
		int nb_swiftcanon = 0;
		int nb_relay = 0;
		int nb_swiftrelay= 0;
		
		for(MovableEntity m : b.getMovableEntities())
		{
			if(m instanceof Infantry)
				nb_infantry++;
			if(m instanceof Cavalry)
				nb_cavalry++;
			if(m instanceof Canon)
				nb_canon++;
			if(m instanceof SwiftCanon)
				nb_swiftcanon++;
			if(m instanceof Relay)
				nb_relay++;
			if(m instanceof SwiftRelay)
				nb_swiftrelay++;
		}
		
		assertTrue("Error when getting infantry", nb_infantry == 18);
		assertTrue("Error when getting cavalry", nb_cavalry == 8);
		assertTrue("Error when getting canon", nb_canon == 2);
		assertTrue("Error when getting swiftcanon", nb_swiftcanon == 2);
		assertTrue("Error when getting relay", nb_relay == 2);
		assertTrue("Error when getting swiftrelay", nb_swiftrelay == 2);
	}
	
	@Test
	public void testIsValidSquare() throws BoardFileFormatException
	{
		Board b = new Board();
		Engine e = new Engine(b);
		e.initSession();

		EntityLoader loader = new EntityLoader(b, "src/main/resources/board/Sample3.ksv", "src/main/resources/board/Map1.kmp");
		loader.loadMap();
		loader.loadMovableEntities();
		
		assertTrue("Error with empty square", b.isValidSquare(0,0) == true);
		assertTrue("Error with unit", b.isValidSquare(2,1) == false);
		assertTrue("Error with empty fortress", b.isValidSquare(7,1) == true);
		assertTrue("Error with full arsenal", b.isValidSquare(7,3) == false);
		assertTrue("Error with mountain", b.isValidSquare(9,8) == false);
		assertTrue("Error with negative value", b.isValidSquare(-5, -3) == false);
		assertTrue("Error with a square out of board", b.isValidSquare(50,50) == false);
	}
	
	
	
	
	
}
