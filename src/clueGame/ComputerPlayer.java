package clueGame;

import java.awt.Color;
import java.util.Set;

public class ComputerPlayer extends Player {

	public ComputerPlayer(String name, Color colour, int row, int col) {
		super(name,colour,row,col);
	}

	@Override
	public Card disproveSuggestion(Solution suggestion) {
		return null;
	}

	public BoardCell pickLocation(Set<BoardCell> targets) {
		return null;
	}
	public void makeAccusation() {
		
	}
	public void createSuggestion() {
		
	}
}
