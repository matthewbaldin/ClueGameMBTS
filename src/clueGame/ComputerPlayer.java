package clueGame;

import java.util.Set;

public class ComputerPlayer extends Player {

	public ComputerPlayer() {
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
