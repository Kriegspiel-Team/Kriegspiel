package main;

@SuppressWarnings("serial")
public class BoardFileFormatException extends Exception {
		
	BoardFileFormatException(int line) {
		System.out.println("Invalid file format at line "+line);
	}
	
}
