package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import model.Arsenal;
import model.Canon;
import model.Cavalry;
import model.Fortress;
import model.Infantry;
import model.Mountain;
import model.MountainPass;
import model.Relay;
import model.SwiftCanon;
import model.SwiftRelay;

/**
 * The Class EntityLoader.
 */
public class EntityLoader {
	
	/*
	 * File Format
	 * Piece;Owner;X;Y
	 */
	
	/** The board. */
	private Board board;
	
	/** The filename. */
	private String movableEntityFilename;
	
	public void setMovableEntityFilename(String movableEntityFilename) {
		this.movableEntityFilename = movableEntityFilename;
	}

	public void setMapFilename(String mapFilename) {
		this.mapFilename = mapFilename;
	}

	private String mapFilename;
	
	public EntityLoader(Board board) {
		this.board = board;
	}
	
	/**
	 * Instantiates a new entity loader.
	 *
	 * @param board the board
	 * @param movableEntityFilename the path to the file from which to load the data
	 */
	public EntityLoader(Board board, String movableEntityFilename, String mapFilename){
		this.board = board;
		this.movableEntityFilename = movableEntityFilename;
		this.mapFilename = mapFilename;
	}
	
	public boolean isValidFormat() {
		return isValidFileFormat(movableEntityFilename) && isValidFileFormat(mapFilename);
	}
	
	/**
	 * Checks if file is formatted correctly.
	 *
	 * @return true, if file is formatted correctly
	 */
	private boolean isValidFileFormat(String filename){
		boolean valid = true;
		
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(filename));
			
			String line;
			while ((line = br.readLine()) != null) {
				if (line.startsWith("//"))
					continue;
				
				String[] st = line.split(";");
								
				if (!isValidFormat(st)){
					valid = false;
					break;
				}				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				if (br != null)
					br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return valid;
	}
	
	public void loadMap() throws BoardFileFormatException{
		readFile(mapFilename);
	}
	
	public void loadMovableEntities() throws BoardFileFormatException {
		readFile(movableEntityFilename);
	}
	
	/**
	 * Load file.
	 *
	 * @throws BoardFileFormatException the board file format exception
	 */
	private void readFile(String filename) throws BoardFileFormatException{
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(filename));
			
			int cpt = 0;
			String line;
			while ((line = br.readLine()) != null) {
				if (line.startsWith("//"))
					continue;
				
				String[] st = line.split(";");
				
				cpt++;
				
				if (!isValidFormat(st))
					throw new BoardFileFormatException(cpt);
				
				int x = Integer.parseInt(st[2]);
				int y = Integer.parseInt(st[3]);
								
				if (st[0].equals("Infantry")) {
					board.placeEntity(x, y, new Infantry(Integer.parseInt(st[1])));
				}
				else if (st[0].equals("Cavalry")) {
					board.placeEntity(x, y, new Cavalry(Integer.parseInt(st[1])));
				}
				else if (st[0].equals("Canon")) {
					board.placeEntity(x, y, new Canon(Integer.parseInt(st[1])));
				}
				else if (st[0].equals("SwiftCanon")) {
					board.placeEntity(x, y, new SwiftCanon(Integer.parseInt(st[1])));
				}
				else if (st[0].equals("Relay")) {
					board.placeEntity(x, y, new Relay(Integer.parseInt(st[1])));
				}
				else if (st[0].equals("SwiftRelay")) {
					board.placeEntity(x, y, new SwiftRelay(Integer.parseInt(st[1])));
				}
				else if (st[0].equals("Mountain")) {
					board.placeEntity(x, y, new Mountain());
				}
				else if (st[0].equals("MountainPass")) {
					board.placeEntity(x, y, new MountainPass());
				}
				else if (st[0].equals("Fortress")) {
					board.placeEntity(x, y, new Fortress());
				}
				else if (st[0].equals("Arsenal")) {
					board.placeEntity(x, y, new Arsenal(Integer.parseInt(st[1])));
					board.saveArsenalPlacement(x, y);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				if (br != null)
					br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Checks if a string is correctly formatted.
	 *
	 * @param data the data
	 * @return true, if the string is correctly formatted
	 */
	private boolean isValidFormat(String[] data) {
			
		if (data.length != 4)
			return false;
		
		if (!data[0].equals("Infantry") && !data[0].equals("Cavalry") && !data[0].equals("Canon") &&
			!data[0].equals("SwiftCanon") && !data[0].equals("Relay") && !data[0].equals("SwiftRelay") &&
			!data[0].equals("Mountain") && !data[0].equals("MountainPass") && !data[0].equals("Fortress") && !data[0].equals("Arsenal"))
			return false;
		
		if (!data[1].equals("0") && !data[1].equals("1"))
			return false;
		
		if (Integer.parseInt(data[2]) < 0 || Integer.parseInt(data[2]) > Board.WIDTH)
			return false;
		
		if (Integer.parseInt(data[3]) < 0 || Integer.parseInt(data[3]) > Board.HEIGHT)
			return false;

		return true;	
	}
}
