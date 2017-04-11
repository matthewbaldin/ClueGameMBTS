package clueGame;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ClueGame extends JFrame {
	private JPanel boardPanel;
	private ControlGUI gui;
	private static Board board;
	private static final int WIDTH = 750;
	private static final int HEIGHT = 700;
	private static final String NAME = "Clue Game";
	//initialize the board and the game window
	public ClueGame() {
		
		board = Board.getInstance();
		board.setConfigFiles("MBSR_ClueLayout.csv", "MBSR_ClueLegend.txt","MBSR_test_players.txt","MBSR_test_weapons.txt");
		board.initialize();
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle(NAME);
		this.setSize(WIDTH, HEIGHT);
		
		gui = new ControlGUI();
		this.add(gui, BorderLayout.SOUTH);
		this.setVisible(true);
	}

	public static void main(String[] args) {
		ClueGame game = new ClueGame();
		JOptionPane.showMessageDialog(game, "You are Mr. Samuel, press Next Player to begin play", "Welcome to Clue", JOptionPane.INFORMATION_MESSAGE);
	}
}
