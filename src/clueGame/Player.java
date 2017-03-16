package clueGame;

import java.awt.Color;

public abstract class Player {
	private String playerName;
	private int row;
	private int column;
	private Color color;
	public Player() {
		
	}
	public abstract Card disproveSuggestion(Solution suggestion);
	
}
