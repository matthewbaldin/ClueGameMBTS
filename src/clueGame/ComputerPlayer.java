package clueGame;

import java.awt.Color;
import java.util.Set;

import javax.swing.JOptionPane;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Random;

public class ComputerPlayer extends Player {
	private char lastRoomChar = 0;
	public ComputerPlayer(String name, Color colour, int row, int col) {
		super(name,colour,row,col);
	}

	@Override
	public Card disproveSuggestion(Solution suggestion) {
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
		int selected = (int) Math.floor(Math.random() * possibilities.size());
		BoardCell selectedCell = null;
		int i = 0;
		for(BoardCell b : possibilities ){
			if(selected == i) {
				selectedCell = b;
			}
			++i;
		}
		return selectedCell;
	}
	public Solution makeAccusation() {
		return null;
	}
	//should only be called after a room was gotten to.
	public Solution createSuggestion() {
		Board board = Board.getInstance();
		ArrayList<Card> persons = board.getPersons();
		ArrayList<Card> weapons = board.getWeapons();
		String roomName = board.getLegend().get(board.getCellAt(this.getRow(),this.getColumn()).getInitial());
		Card room = new Card(roomName , CardType.ROOM);
		for(Card c : this.getSeenCards()) { 
			persons.remove(c);
			weapons.remove(c);
		}
		Card person = persons.get((new Random()).nextInt(persons.size()));
		Card weapon = weapons.get((new Random()).nextInt(weapons.size()));
		return new Solution(person,weapon,room);
	}
	@Override
	public void moveTo(BoardCell b){
		super.moveTo(b);
		if(b.isRoom()){
			this.lastRoomChar = b.getInitial();
		}
	}
	public void move(Board board, int roll, ClueGame game) {
		if(board.getPrevious() != null) {
			if(board.getResponse() == null) {
				if (board.checkAccusation(board.getPrevious())) {
					JOptionPane.showMessageDialog(Board.getInstance(), this.getPlayerName() + " is correct, they win. The solution was " + board.getSolution());
					ClueGame.getInstance().dispose();
				}
			}
		}
		board.setPrevious(null);
		board.setResponse(null);
		board.calcTargets(this.getRow(), this.getColumn(), roll);
		BoardCell cell = pickLocation(board.getTargets());
		moveTo(cell);
		game.repaint();
		if(cell.isRoom()) {
			Solution suggestion = createSuggestion();
			board.handleSuggestion(suggestion, board.getPlayers().get(ClueGame.currentPlayer));
		}
	}
}
