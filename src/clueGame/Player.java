package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.util.HashSet;
import java.util.Set;

public abstract class Player {
	private String playerName;
	private int row;
	private int column;
	private Color color;
	private Set<Card> heldCards;
	private Set<Card> seenCards;
	public Player(String name, Color color, int row, int col) {
		this.heldCards = new HashSet<Card>();
		this.seenCards = new HashSet<Card>();
		this.playerName = name;
		this.color = color;
		this.row = row;
		this.column = col;
	}
	public abstract Card disproveSuggestion(Solution suggestion);
	
	public Set<Card> getHeldCards() {
		return heldCards;
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
	public void giveCard(Card c){
		this.heldCards.add(c);
		this.seenCards.add(c);
	}
	public void moveTo(BoardCell b){
		 this.row = b.getRow();
		 this.column = b.getColumn();
		 
	}
	public void showCard(Card shown) {
		seenCards.add(shown);
	}
	public void draw(Graphics g) {
		g.setColor(this.getColor());
		g.fillRect(this.getRow() * 29, this.getColumn() * 29, 29, 29);
	}
	//purely abstract
	public void move(Board board, int roll, ClueGame game) {
		
	}
}
