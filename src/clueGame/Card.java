package clueGame;

public class Card {
	public final String name;
	public final CardType type;
	public Card(String name, CardType type) {
		this.type = type;
		this.name = name;
	}
	public boolean equals(Card c){
		return (this.name.equals(c.name)) && this.type == c.type;
	}
}
