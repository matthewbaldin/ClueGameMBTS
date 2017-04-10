package clueGame;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class ClueGame extends JFrame {
	private JPanel boardPanel;
	ControlGUI gui;
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
		//unsure
		this.setSize(WIDTH, HEIGHT);
		gui = new ControlGUI();
		this.add(gui, BorderLayout.SOUTH);
		this.add(createBoardPanel(), BorderLayout.NORTH);
		this.setVisible(true);
	}
	//unsure how to proceed
	JPanel createBoardPanel() {
		JPanel result = new JPanel();
		result.setSize(WIDTH, (int)(HEIGHT * ((float)9 / 8)));
		return result;
	}
	public static void main(String[] args) {
		ClueGame game = new ClueGame();
	}
}
