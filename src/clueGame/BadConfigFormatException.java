package clueGame;

import java.io.FileNotFoundException;

public class BadConfigFormatException extends Exception{

	public BadConfigFormatException() {
		super("Incorrect config format");
	}
	
	public BadConfigFormatException(String message) {
		super(message);
	}
	
}
