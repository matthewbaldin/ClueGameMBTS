package clueGame;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ClueGame extends JFrame {
	private JPanel boardPanel;
	private ControlGUI gui;
	private JMenuBar menuBar;
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
		this.setSize(board.getNumRows() * 50, board.getNumColumns() * 50);
		
		gui = new ControlGUI();
		this.add(gui, BorderLayout.SOUTH);
		this.add(board, BorderLayout.CENTER);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		menuBar.add(createMenuBar());
		
		this.setVisible(true);
	}
	
	private JMenu createMenuBar() {
		JMenu menu = new JMenu("File");
		menu.add(createDetectiveNotes());
		menu.add(createExit());
		return menu;
	}
	
	private JMenuItem createExit() {
		JMenuItem menuItem = new JMenuItem("Exit");
		class MenuItemListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		}
		menuItem.addActionListener(new MenuItemListener());
		return menuItem;
	}
	
	private JMenuItem createDetectiveNotes() {
		JMenuItem menuItem = new JMenuItem("Detective Notes");
		class MenuItemListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				
			}
		}
		menuItem.addActionListener(new MenuItemListener());
		return menuItem;
	}

	public static void main(String[] args) {
		ClueGame game = new ClueGame();
		JOptionPane.showMessageDialog(game, "You are Mr. Samuel, press Next Player to begin play", "Welcome to Clue", JOptionPane.INFORMATION_MESSAGE);
	}
}
