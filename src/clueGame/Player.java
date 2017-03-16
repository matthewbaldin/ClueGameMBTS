package clueGame;

import java.awt.Color;
import java.util.Set;

public abstract class Player {
	private String playerName;
	private int row;
	private int column;
	private Color color;
	private Set<Card> myCards;
	private Set<Card> seenCards;
	public Player() {
		
	}
	public abstract Card disproveSuggestion(Solution suggestion);
	
	public Set<Card> getMyCards() {
		return myCards;
	}
	public Set<Card> getSeenCards() {
		return seenCards;
	}
	
}
