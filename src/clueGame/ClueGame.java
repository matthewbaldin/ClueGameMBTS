package clueGame;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class ClueGame extends JFrame {
	private JPanel boardPanel;
	private ControlGUI gui;
	private JMenuBar menuBar;
	private static Board board;
	private static final String NAME = "Clue Game";
	private int currentPlayer = 0;
	//initialize the board and the game window
	public ClueGame() {
		
		board = Board.getInstance();
		board.setConfigFiles("MBSR_ClueLayout.csv", "MBSR_ClueLegend.txt","MBSR_test_players.txt","MBSR_test_weapons.txt");
		board.initialize();
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle(NAME);
		this.setSize(board.getNumRows() * 37 + 200, board.getNumColumns() * 37);
		
		gui = new ControlGUI();
		this.add(gui, BorderLayout.SOUTH);
		this.add(board, BorderLayout.CENTER);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		menuBar.add(createMenuBar());
		
		this.add(createMyCards(), BorderLayout.EAST);
		
		this.setVisible(true);
	}
	
	private JPanel createMyCards() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(3,1));
		Set<Card> held=Board.getHumanPlayer().getHeldCards();
		JTextField a = new JTextField();
		JTextField b = new JTextField();
		JTextField d = new JTextField();
		for (Card c : held) {
			if(c.type.equals(CardType.PERSON)){
				a = new JTextField(c.name, c.name.length());
				panel.add(a);
				a.setBorder(new TitledBorder(new EtchedBorder(),"Person"));
				a.setEditable(false);
			}
			if(c.type.equals(CardType.ROOM)){
				b = new JTextField(c.name, c.name.length());
				panel.add(b);
				b.setBorder(new TitledBorder(new EtchedBorder(),"Room"));
				b.setEditable(false);
			}
			if(c.type.equals(CardType.WEAPON)){
				d = new JTextField(c.name, c.name.length());
				panel.add(d);
				d.setBorder(new TitledBorder(new EtchedBorder(),"Weapon"));
				d.setEditable(false);
			}
			
		}
		
		panel.setBorder(new TitledBorder(new EtchedBorder(),"My Cards"));
		return panel;
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
				DetectiveNotes menuOp=new DetectiveNotes();
				menuOp.setSize(800,800);
				menuOp.setVisible(true);
			}
		}
		menuItem.addActionListener(new MenuItemListener());
		return menuItem;
	}

	public static void main(String[] args) {
		ClueGame game = new ClueGame();
		JOptionPane.showMessageDialog(game, "You are Mr. Samuel, press Next Player to begin play", "Welcome to Clue", JOptionPane.INFORMATION_MESSAGE);
	}
	public void doTurns() {
		
	}
	
}
