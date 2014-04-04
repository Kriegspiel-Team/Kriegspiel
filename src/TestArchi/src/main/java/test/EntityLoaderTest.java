package test;

import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import main.Board;
import main.BoardFileFormatException;
import main.EntityLoader;

import org.junit.Test;

public class EntityLoaderTest {
	
	@Test
	public void testEntityLoaderBoard() {
		Board b = new Board();
		
		try {
			new EntityLoader(b);
	    } catch (Exception e) {
	    	fail(e.getMessage());
	    }
	}

	@Test
	public void testEntityLoaderBoardStringString() {
		Board b = new Board();
		
		try {
			new EntityLoader(b, "movableEntityFilename", "mapFilename");
	    } catch (Exception e) {
	    	fail(e.getMessage());
	    }
	}

	@Test
	public void testSetMovableEntityFilename() throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		Board b = new Board();
		EntityLoader loader = new EntityLoader(b);
		loader.setMovableEntityFilename("test");
		
		Field f = loader.getClass().getDeclaredField("movableEntityFilename");
		f.setAccessible(true);
		
		assertTrue(f.get(loader) == "test");
	}

	@Test
	public void testSetMapFilename() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Board b = new Board();
		EntityLoader loader = new EntityLoader(b);
		loader.setMapFilename("testMapFilename");
		
		Field f = loader.getClass().getDeclaredField("mapFilename");
		f.setAccessible(true);
		
		
		assertTrue(f.get(loader) == "testMapFilename");
	}

	@Test
	public void testIsValidFormat() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Board b = new Board();
		EntityLoader loader = new EntityLoader(b);
		
		Method method = loader.getClass().getDeclaredMethod("isValidFormat", new Class[]{String[].class});
		method.setAccessible(true);
		
		String[] paramErr = {"a","b","c"};
						
		assertFalse("Invalid format", (Boolean) method.invoke(loader, (Object)paramErr));
				
		String[] paramOK = {"Infantry","0","2", "10"};

		assertTrue("Valid format", (Boolean) method.invoke(loader, (Object)paramOK));		
	}

	@Test
	public void testIsValidFileFormat() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Board b = new Board();
		EntityLoader loader = new EntityLoader(b);
		
		Method method = loader.getClass().getDeclaredMethod("isValidFileFormat", new Class[]{String.class});
		method.setAccessible(true);
		
		String paramFileErr = "src/main/resources/board/ErrSample.ksv";
		
		assertFalse("Invalid file format", (Boolean) method.invoke(loader, (Object)paramFileErr));
		
		String paramFileOK = "src/main/resources/board/Sample1.ksv";
		
		assertTrue("Valid file format", (Boolean) method.invoke(loader, (Object)paramFileOK));
	}

	@Test(expected = BoardFileFormatException.class)
	public void testReadFileException() throws BoardFileFormatException {
		Board b = new Board();
		EntityLoader loader = new EntityLoader(b);
		loader.setMovableEntityFilename("src/main/resources/board/ErrSample.ksv");
		loader.loadMovableEntities();
	}
	
	@Test
	public void testReadFile() {
		Board b = new Board();
		EntityLoader loader = new EntityLoader(b);
		loader.setMovableEntityFilename("src/main/resources/board/Sample1.ksv");
		try {
			loader.loadMovableEntities();
		} catch (BoardFileFormatException e) {
			fail(e.getMessage());
		}
	}

}
