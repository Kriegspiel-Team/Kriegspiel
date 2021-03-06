package test;

import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import main.Board;
import main.BoardFileFormatException;
import main.EntityLoader;
import model.Infantry;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class EntityLoaderTest  {
	
	private Board board;
	private EntityLoader loader;
	
	@Before
	public void setUp() throws Exception {
		board = new Board();
		loader = new EntityLoader(board);
	}
	
	@After
	public void tearDown() throws Exception {
		loader = null;
		board = null;
	}
	
	@Test
	public void testEntityLoaderBoard() {
		try {
			new EntityLoader(board);
	    } catch (Exception e) {
	    	fail(e.getMessage());
	    }
	}

	@Test
	public void testEntityLoaderBoardStringString() {		
		try {
			new EntityLoader(board, "movableEntityFilename", "mapFilename");
	    } catch (Exception e) {
	    	fail(e.getMessage());
	    }
	}

	@Test
	public void testSetMovableEntityFilename() throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		loader.setMovableEntityFilename("test");
		
		Field f = loader.getClass().getDeclaredField("movableEntityFilename");
		f.setAccessible(true);
		
		assertTrue(f.get(loader) == "test");
	}

	@Test
	public void testSetMapFilename() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		loader.setMapFilename("testMapFilename");
		
		Field f = loader.getClass().getDeclaredField("mapFilename");
		f.setAccessible(true);
		
		
		assertTrue(f.get(loader) == "testMapFilename");
	}

	@Test
	public void testIsValidFormat() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {		
		Method method = loader.getClass().getDeclaredMethod("isValidFormat", new Class[]{String[].class});
		method.setAccessible(true);
		
		String[] paramErr = {"a","b","c"};
						
		assertFalse("Invalid format", (Boolean) method.invoke(loader, (Object)paramErr));
				
		String[] paramOK = {"Infantry","0","2", "10"};

		assertTrue("Valid format", (Boolean) method.invoke(loader, (Object)paramOK));		
	}

	@Test
	public void testIsValidFileFormat() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {		
		Method method = loader.getClass().getDeclaredMethod("isValidFileFormat", new Class[]{String.class});
		method.setAccessible(true);
		
		String paramFileErr = "src/main/resources/board/ErrSample.ksv";
		
		assertFalse("Invalid file format", (Boolean) method.invoke(loader, (Object)paramFileErr));
		
		String paramFileOK = "src/main/resources/board/Sample1.ksv";
		
		assertTrue("Valid file format", (Boolean) method.invoke(loader, (Object)paramFileOK));
	}
	
	@Test(expected = BoardFileFormatException.class)
	public void testReadFileException() throws BoardFileFormatException {
		loader.setMovableEntityFilename("src/main/resources/board/ErrSample.ksv");
		loader.loadMovableEntities();
	}
	
	@Test
	public void testReadFile() {
		loader.setMovableEntityFilename("src/main/resources/board/Sample1.ksv");
		try {
			loader.loadMovableEntities();
		} catch (BoardFileFormatException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testEntityPlacement() {
		Board b = new Board();
		EntityLoader loader = new EntityLoader(b, "src/main/resources/board/SingleInfantry.ksv", "src/main/resources/board/Map1.kmp");
		try {
			loader.loadMovableEntities();
		} catch (BoardFileFormatException e) {
			e.printStackTrace();
		}
		
		assertEquals("Infantry placed in (7,3)", Infantry.class, b.getMatrix()[7][3].getClass());
		assertEquals("Own to player 0", 0, b.getMatrix()[7][3].getOwner());
	}

}
