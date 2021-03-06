package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;

public class HumanPlayer extends Player {

	public HumanPlayer(String name, Color colour, int row, int col) {
		super(name,colour,row,col);
	}

	@Override
	public Card disproveSuggestion(Solution suggestion) {
		//The below is placeholder code from ComputerPlayer, and will not be in the final version. /*
		ArrayList<Card> matches = new ArrayList<Card>();
		for(Card c : this.getHeldCards()) {
			if(suggestion.person.equals(c)){
				matches.add(c);
			}
			else if(suggestion.room.equals(c)){
				matches.add(c);
			}
			else if(suggestion.weapon.equals(c)){
				matches.add(c);
			}
		}
		Collections.shuffle(matches);
		if(matches.isEmpty()){
			return null;
		}
		//else not needed
		//shuffled matches to get the 0 to be random
		return matches.get(0);
		//*/
	}
	public void move(Board board, int roll, ClueGame game) {
		board.setPrevious(null);
		board.setResponse(null);
		board.humanFinished = false;
		board.calcTargets(this.getRow(), this.getColumn(), roll);
		board.highlight();
		game.repaint();
	}
}
