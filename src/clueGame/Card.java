package clueGame;

public class Card {
	private String cardName;
	public final CardType cardType;
	public Card(String name, CardType type) {
		this.cardType = type;
		this.cardName = name;
	}
	public boolean equals(Card c){
		return false;
	}

}
