package main;

/**
 * The Class BoardFileFormatException.
 */
@SuppressWarnings("serial")
public class BoardFileFormatException extends Exception {
		
	/**
	 * Instantiates a new board file format exception.
	 *
	 * @param line the line in the file that caused the exceptions
	 */
	BoardFileFormatException(int line) {
		System.out.println("Invalid file format at line "+line);
	}
	
}
