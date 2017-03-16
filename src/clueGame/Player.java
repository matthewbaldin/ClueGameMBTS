package clueGame;

import java.awt.Color;
import java.util.HashSet;
import java.util.Set;

public abstract class Player {
	private String playerName;
	private int row;
	private int column;
	private Color color;
	private Set<Card> myCards;
	private Set<Card> seenCards;
	public Player(String name, Color color, int row, int col) {
		this.myCards = new HashSet<Card>();
		this.seenCards = new HashSet<Card>();
		this.playerName = name;
		this.color = color;
		this.row = row;
		this.column = col;
	}
	public abstract Card disproveSuggestion(Solution suggestion);
	
	public Set<Card> getMyCards() {
		return myCards;
	}
	public Set<Card> getSeenCards() {
		return seenCards;
	}
	public String getPlayerName() {
		return this.playerName;
	}
	public Color getColor() {
		return this.color;
	}
	public int getRow() {
		return this.row;
	}
	public int getColumn() {
		return this.column;
	}
	protected void giveCard(Card c){
		this.myCards.add(c);
	}
}
