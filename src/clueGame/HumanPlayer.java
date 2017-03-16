package clueGame;

import java.awt.Color;

public class HumanPlayer extends Player {

	public HumanPlayer(String name, Color colour, int row, int col) {
		super(name,colour,row,col);
	}

	@Override
	public Card disproveSuggestion(Solution suggestion) {
		return null;
	}

}
