package com.sample;

import java.io.BufferedReader;
import java.io.FileReader;

public class EntityLoader {
	
	/*
	 * File Format
	 * Piece;Owner;X;Y
	 */
	
	private Board board;
	private String filename;
	
	public EntityLoader(Board board, String filename){
		this.board = board;
		this.filename = filename;
		
		readFile();
	}
	
	private void readFile(){
		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			
			String line;
			while ((line = br.readLine()) != null) {
				String[] st = line.split(";");
				
				if (st.length != 4)
					throw new Exception("Invalid file format");
				
				if (st[0].equals("Infantry")){
					board.placeEntity(Integer.parseInt(st[2]), Integer.parseInt(st[3]), new Canon());
				}
			}
			
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
