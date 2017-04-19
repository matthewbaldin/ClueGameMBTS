package clueGame;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class ControlGUI extends JPanel {
	private JTextField playerName;
	private JTextField diceRoll;
	private JTextField guess;
	private JTextField response;
	private JButton nextPlayer;
	private JButton accuse;
	public static ControlGUI theGUI= null;
	
	public ControlGUI(ClueGame game) {
		this.setLayout(new GridLayout(3,1));
		this.add(this.createButtons(game));
		this.add(this.createPlayerAndRoll());
		this.add(this.createGuessInfo());
		theGUI = this;
	}
	private JPanel createGuessInfo() {
		JPanel result = new JPanel();
		guess = new JTextField("A guess consisting of a weapon, player, and room",40);
		guess.setEditable(false);
		guess.setBorder(new TitledBorder(new EtchedBorder(),"Guess"));
		guess.setToolTipText("The Guess made, if one was made");
		result.add(guess);
		response = new JTextField("Disproving card",20);
		response.setEditable(false);
		response.setBorder(new TitledBorder(new EtchedBorder(),"Guess Response"));
		result.add(response);
		return result;
	}
	private JPanel createButtons(ClueGame game) {
		JPanel result = new JPanel();
		nextPlayer = new JButton("Next player");
		nextPlayer.setToolTipText("Goes to the next player's turn");
		nextPlayer.addActionListener(new ButtonListener(game));
		accuse = new JButton("Make an accusation");
		accuse.setToolTipText("Allows the making of an accusation");
		accuse.addActionListener(new ButtonListener(game));
		result.add(nextPlayer);
		result.add(accuse);
		return result;
	}
	
	private JPanel createPlayerAndRoll() {
		JPanel result = new JPanel();
		//will need to be modified when the GUI actually displays the player name for each turn
		JTextField name = new JTextField("Name",20);
		name.setEditable(false);
		name.setBorder(new TitledBorder(new EtchedBorder(),"Current Player"));
		name.setToolTipText("The name of the current player");
		result.add(name);
		//believe this will need to be moved
		this.playerName = name;
		diceRoll = new JTextField("-",3);
		diceRoll.setEditable(false);
		diceRoll.setBorder(new TitledBorder(new EtchedBorder(),"Roll"));
		result.add(diceRoll);
		return result;
	}
	
	public class ButtonListener implements ActionListener {
		ClueGame game;
		ButtonListener(ClueGame game) {
			this.game = game;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand() == "Next player") {
				if(!Board.getInstance().getHumanFinished()){
					JOptionPane.showMessageDialog(Board.getInstance(), "Your turn has not ended.", "Error", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				else{
					Random rand = new Random();
					int roll = rand.nextInt(6) + 1;
					game.doTurns(roll);
					diceRoll.setText(Integer.toString(roll));
					playerName.setText(game.getBoard().getPlayers().get(game.getCurrentPlayer()).getPlayerName());
					updateGuessInfo();
					game.repaint();
				}
			}
			else if (e.getActionCommand() == "Make an accusation") {
				if (!game.getBoard().getHumanFinished()) {
				SuggestionPanel panel = new SuggestionPanel(game.getBoard(), false);	
				panel.setVisible(true);
				}
				else {
					JOptionPane.showMessageDialog(Board.getInstance(), "It's not your turn!", "Error", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		}
	}
	public void updateGuessInfo() {
		if (Board.getInstance().getPrevious() == null) {
			guess.setText("-");
			response.setText("-");
		}
		else {
			if(Board.getInstance().getResponse() != null) {
				response.setText(Board.getInstance().getResponse() + "");
			}
			else {
				response.setText("No new clues.");
			}
			guess.setText(Board.getInstance().getPrevious() + "");
		}
	}
	
}
