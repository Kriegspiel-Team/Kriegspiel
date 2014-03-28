package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import model.Canon;
import model.Cavalry;
import model.Infantry;
import model.Relay;
import model.SwiftCanon;
import model.SwiftRelay;

public class EntityLoader {
	
	/*
	 * File Format
	 * Piece;Owner;X;Y
	 */
	
	private Board board;
	private String filename;
	
	public EntityLoader(String filename) throws BoardFileFormatException{
		this.board = new Board();
		this.filename = filename;
		
		readFile();
	}
	
	private void readFile() throws BoardFileFormatException{
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(filename));
			
			int cpt = 0;
			String line;
			while ((line = br.readLine()) != null) {
				String[] st = line.split(";");
				
				cpt++;
				
				if (st.length != 4)
					throw new BoardFileFormatException(cpt);
				
				int x = Integer.parseInt(st[2]);
				int y = Integer.parseInt(st[3]);
								
				if (st[0].equals("Infantry")){
					board.placeEntity(x, y, new Infantry(Integer.parseInt(st[1])));
				}
				else if (st[0].equals("Cavalry")){
					board.placeEntity(x, y, new Cavalry(Integer.parseInt(st[1])));
				}
				else if (st[0].equals("Canon")){
					board.placeEntity(x, y, new Canon(Integer.parseInt(st[1])));
				}
				else if (st[0].equals("SwiftCanon")){
					board.placeEntity(x, y, new SwiftCanon(Integer.parseInt(st[1])));
				}
				else if (st[0].equals("Relay")){
					board.placeEntity(x, y, new Relay(Integer.parseInt(st[1])));
				}
				else if (st[0].equals("SwiftRelay")){
					board.placeEntity(x, y, new SwiftRelay(Integer.parseInt(st[1])));
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
	public Board getBoard()
	{
		return this.board;
	}
}
