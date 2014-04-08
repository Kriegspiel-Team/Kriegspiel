package main;

/**
 * This exception is thrown when trying to load a board or map file and that file in unexpectedly formatted.
 */
@SuppressWarnings("serial")
public class BoardFileFormatException extends Exception {
		
	/**
	 * Instantiates a new board file format exception.
	 *
	 * @param line the line in the file that caused the exception
	 */
	BoardFileFormatException(int line) {
		System.out.println("Invalid file format at line "+line);
	}
	
}
