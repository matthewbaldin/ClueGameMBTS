package clueGame;

import java.awt.Dialog;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class SuggestionPanel extends JDialog {
	private Board board;
	private boolean landed;
	private JComboBox<String> weapons;
	private JComboBox<String> people;
	private JComboBox<String> rooms;
	
	public SuggestionPanel(Board board, boolean landed) {
		this.landed = landed;
		this.board = board;
		setTitle("Make a guess");
		setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		setSize(400,400);
		setLayout(new GridLayout(4,2));
		add(addRoomLabel());
		add(addRoomGuess());
		add(addPeopleLabel());
		add(addPersonGuess());
		add(addWeaponLabel());
		add(addWeaponGuess());
		add(createSubmit());
		add(createCancel());
	}
	public void setLanded(boolean landed) {
		this.landed = landed;
	}
	private JPanel addRoomGuess()
	{
		JPanel panel=new JPanel();
		panel.setLayout(new GridLayout(1,2));
		if (!landed) {
			rooms = new JComboBox<String>();
			for (char c : board.getLegend().keySet()) {
				if (c != 'W' && c != 'D') {
					rooms.addItem(Board.getLegend().get(c));
				}
			}
			panel.add(rooms);
			panel.setBorder(new TitledBorder(new EtchedBorder(), "Room Guess"));
			return panel;
		}
		else {
			rooms = new JComboBox<String>();
			Player p = Board.getHumanPlayer();
			String roomName = board.getLegend().get(board.getCellAt(p.getRow(),p.getColumn()).getInitial());
			JTextField room = new JTextField(roomName);
			rooms.addItem(roomName);
			room.setEditable(false);
			panel.add(room);
			return panel;
		}
	}
	private JLabel addRoomLabel() {
		JLabel jlroom= new JLabel();
		jlroom.setLayout(new GridLayout(1,2));
		jlroom.setText("Room");
		return jlroom;
	}
	private JLabel addWeaponLabel() {
		JLabel jlpeople = new JLabel();
		jlpeople.setLayout(new GridLayout(1,2));
		jlpeople.setText("Weapon");
		return jlpeople;
	}
	private JPanel addWeaponGuess()
	{
		JPanel panel =new JPanel();
		panel.setLayout(new GridLayout(1,2));
		weapons=new JComboBox<String>();
		for(Card card: board.getWeaponCards())
		{
			weapons.addItem(card.name);
		}
		panel.add(weapons);
		panel.setBorder(new TitledBorder(new EtchedBorder(),"WeaponGuess"));
		return panel;
	}
	private JLabel addPeopleLabel() {
		JLabel jlpeople = new JLabel();
		jlpeople.setLayout(new GridLayout(1,2));
		jlpeople.setText("People");
		return jlpeople;
	}
	private JPanel addPersonGuess()
	{
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,2));
		people = new JComboBox<String>();
		for (Player p : board.getPlayers()){
			people.addItem(p.getPlayerName());
		}
		panel.setBorder(new TitledBorder(new EtchedBorder(), "Person Guess"));
		panel.add(people);
		return panel;
	}
	private JPanel createSubmit() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,2));
		JButton submit = new JButton("Submit");
		submit.addActionListener(new ButtonListener(this.board));
		panel.add(submit);
		return panel;
	}
	private JPanel createCancel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,2));
		JButton cancel = new JButton("Cancel");
		cancel.addActionListener(new ButtonListener(this.board));
		panel.add(cancel);
		return panel;
	}
	public class ButtonListener implements ActionListener {
		Board board;
		ButtonListener(Board board) {
			this.board = board;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand() == "Submit") {
				board.removeHighlights();
				board.repaint();
				Solution suggestion = new Solution(
						new Card("" + people.getSelectedItem(), CardType.PERSON),
						new Card("" + weapons.getSelectedItem(), CardType.WEAPON),
						new Card(rooms.getSelectedItem() + "", CardType.ROOM));
				if (landed) {
					board.handleSuggestion(suggestion, Board.getPlayers().get(ClueGame.currentPlayer));
					ControlGUI.theGUI.updateGuessInfo();
				}
				else {
					if (board.checkAccusation(suggestion)) {
						JOptionPane.showMessageDialog(Board.getInstance(), "You are correct, you win.");
						dispose();
						ClueGame.getInstance().dispose();
					}
					else {
						JOptionPane.showMessageDialog(Board.getInstance(), "You are incorrect.");
					}
				}
				this.board.setHumanFinished(true);
				dispose();
			}
			else if (e.getActionCommand() == "Cancel") {
				dispose();
			}
		}
	}
}
