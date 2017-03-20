package clueGame;

import java.awt.Color;
import java.util.Set;
import java.util.HashSet;

public class ComputerPlayer extends Player {
	private char lastRoomChar = 0;
	public ComputerPlayer(String name, Color colour, int row, int col) {
		super(name,colour,row,col);
	}

	@Override
	public Card disproveSuggestion(Solution suggestion) {
		return null;
	}

	public BoardCell pickLocation(Set<BoardCell> targets) {
		boolean hasNewRoom = false;
		Set<BoardCell> possibilities = new HashSet<BoardCell>();
		for(BoardCell b : targets) {
			if(b.isRoom()){
				if(b.getInitial() != lastRoomChar){
					if(!hasNewRoom) {
						possibilities.clear();
						hasNewRoom = true;
					}
					possibilities.add(b);
				}
				else {
					if(!hasNewRoom){
						possibilities.add(b);
					}
				}
			}
			else {
				if(!hasNewRoom){
					possibilities.add(b);
				}
			}
		}
		int selected = (int) Math.floor(Math.random()*possibilities.size());
		BoardCell selectedCell = null;
		int i = 0;
		for(BoardCell b : possibilities ){
			if(selected == i) {
				selectedCell = b;
			}
			i++;
		}
		return selectedCell;
	}
	public void makeAccusation() {
		
	}
	public void createSuggestion() {
		
	}
}
