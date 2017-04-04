package clueGame;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
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
	public ControlGUI() {
		this.setLayout(new GridLayout(3,1));
		this.add(this.createButtons());
		this.add(this.createPlayerAndRoll());
		this.add(this.createGuessInfo());
	}
	private JPanel createGuessInfo() {
		JPanel result = new JPanel();
		guess = new JTextField("A guess consisting of a weapon, player, and room",40);
		guess.setEditable(true);
		guess.setBorder(new TitledBorder(new EtchedBorder(),"Guess"));
		guess.setToolTipText("The Guess made, if one was made");
		result.add(guess);
		response = new JTextField("Disproving card",20);
		response.setEditable(false);
		response.setBorder(new TitledBorder(new EtchedBorder(),"Guess Response"));
		result.add(response);
		return result;
	}
	private JPanel createButtons() {
		JPanel result = new JPanel();
		nextPlayer = new JButton("Next player");
		nextPlayer.setToolTipText("Goes to the next player's turn");
		accuse = new JButton("Make an accusation");
		accuse.setToolTipText("Allows the making of an accusation");
		result.add(nextPlayer);
		result.add(accuse);
		return result;
	}
	
	private JPanel createPlayerAndRoll() {
		JPanel result = new JPanel();
		JTextField name = new JTextField("Name",20);
		name.setEditable(false);
		name.setBorder(new TitledBorder(new EtchedBorder(),"Current Player"));
		name.setToolTipText("The name of the current player");
		result.add(name);
		this.playerName = name;
		diceRoll = new JTextField("-",3);
		diceRoll.setEditable(false);
		diceRoll.setBorder(new TitledBorder(new EtchedBorder(),"Roll"));
		result.add(diceRoll);
		return result;
	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("GUI");
		frame.setSize(750, 200);	
		ControlGUI gui = new ControlGUI();
		frame.add(gui, BorderLayout.CENTER);
		frame.setVisible(true);
	}
}
