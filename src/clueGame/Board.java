package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import javax.swing.JPanel;

public class Board extends JPanel {
	private int numRows;
	private int numColumns;
	private static final int MAX_BOARD_SIZE = 50;
	private BoardCell[][] board;
	private Map<Character, String> legend;
	private Map<BoardCell, Set<BoardCell>> adjMatrix = new HashMap<BoardCell, Set<BoardCell>>();
	private Set<BoardCell> targets;
	private Set<BoardCell> visited;
	private String boardConfigFile;
	private String roomConfigFile;
	private String playerConfigFile;
	private String weaponConfigFile;
	private ArrayList<Card> deck;
	private ArrayList<Card> playerCards;
	private ArrayList<Card> weaponCards;
	private ArrayList<ComputerPlayer> cpuPlayers;
	private ArrayList<Player> players;
	private HumanPlayer humanPlayer;
	private Solution solution;
	// variable used for singleton pattern
	private static Board theInstance = new Board();

	// ctor is private to ensure only one can be created
	private Board() {}

	// this method returns the only Board
	public static Board getInstance() {
		return theInstance;
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (int i = 0; i < numRows; ++i) {
			for (int j = 0; j < numColumns; ++j) {
				board[i][j].draw(g);
			}
		}
	}
	
	@Deprecated
	public void setConfigFiles(String boardCF, String roomCF) {
		setConfigFiles(boardCF,roomCF,null,null);
	}
	public void setConfigFiles(String boardCF, String roomCF, String playerCF, String weaponCF) {
		boardConfigFile = boardCF;
		roomConfigFile = roomCF;
		playerConfigFile = playerCF;
		weaponConfigFile = weaponCF;
	}
	// Reads legend file into Map instance 
	public void loadRoomConfig() throws FileNotFoundException, BadConfigFormatException {
		
		if(deck == null){
			deck = new ArrayList<Card>();
		}
		legend = new HashMap<Character, String>();
		FileReader reader = new FileReader(roomConfigFile);
		Scanner in = new Scanner(reader); 

		while (in.hasNextLine()) {
			String str = in.nextLine();

			if(str.substring(str.lastIndexOf(",") + 2).equals("Card")){
				deck.add(new Card(str.substring(str.indexOf(",")+1,str.lastIndexOf(",")).trim(),CardType.ROOM));
			}
			else if (!str.substring(str.lastIndexOf(",") + 2).equals("Other")) {

				throw new BadConfigFormatException("Incorrect Room Type: room should be of type 'Card' or 'Other'");			

			}
			
			legend.put(str.charAt(0), str.substring(3, str.indexOf(",", 3)));

		}
		in.close();		
	}
	// Reads board map into the board 2D array
	public void loadBoardConfig() throws FileNotFoundException, BadConfigFormatException {

		FileReader reader = new FileReader(boardConfigFile);
		Scanner in = new Scanner(reader);
		numRows = 0;
		HashMap<Integer, String[]> boardIns=new HashMap<Integer,String[]>();


		while(in.hasNextLine()) {
			String holder=in.nextLine();
			String[] split=holder.split(",");
			String strHold="";
			boardIns.put(numRows,split);
			numRows++;
		}

		numColumns=boardIns.get(0).length;
		board = new BoardCell[numRows][numColumns];
		for(int i=0;i < numRows;++i){
			for(int j=0;j < numColumns;++j){

				if(boardIns.get(i).length != numColumns) {
					throw new BadConfigFormatException("Incorrect number of columns: number of columns should be " + numColumns); 
				}

				String initHold=boardIns.get(i)[j];
				if(initHold.length()>1){
					if (initHold.charAt(1) == 'N') {
						board[i][j]=new BoardCell(i,j,initHold.charAt(0));
						board[i][j].setIsName(true);
					}
					else {
					board[i][j]=new BoardCell(i,j,initHold.charAt(0),initHold.charAt(1));
					}
				}
				else{
					if(!legend.containsKey(initHold.charAt(0))) {
						throw new BadConfigFormatException("Incorrect room initial: this room is not in the legend");
					}
					board[i][j]=new BoardCell(i,j,initHold.charAt(0));
				}
			}
		}
		in.close();
	}
	
	private void getCellNames() {
		for (int i = 0; i < numRows; ++i) {
			for (int j = 0; j < numColumns; ++j) {
				board[i][j].setName(legend.get(board[i][j].getInitial()));
			}
		}
	}
	
	//set up the board for usage in playing the game
	public void initialize() {
		humanPlayer = null;
		legend = new HashMap<Character, String>();
		targets = new HashSet<BoardCell>();
		deck = new ArrayList<Card>();
		players = new ArrayList<Player>();
		cpuPlayers = new ArrayList<ComputerPlayer>();
		try {
			loadConfigFiles();
		} catch (FileNotFoundException e) {
			System.out.println("Error loading config file " + e);
		} catch (BadConfigFormatException e) {
			System.out.println("There was a config error");
			System.out.println(e);
			System.out.println(e.getMessage());
		}
		//required to pass old tests
		if(this.playerConfigFile != null){
			this.dealDeck();
		}
		calcAdjacencies();
	}

	// Find adjacent cells for selected BoardCell
	public void calcAdjacencies() {
		adjMatrix = new HashMap<BoardCell, Set<BoardCell>>();
		for (int i = 0; i < numRows; i++) 
		{
			for (int j = 0; j < numColumns; j++)
			{
				HashSet<BoardCell> adjList = new HashSet<BoardCell>();

				if(board[i][j].isDoorway())
				{
					switch(board[i][j].getDoorDirection())
					{
					case RIGHT:
						adjList.add(board[i][j+1]);
						break;

					case DOWN:
						adjList.add(board[i+1][j]);
						break;

					case LEFT:
						adjList.add(board[i][j-1]);
						break;

					case UP:
						adjList.add(board[i-1][j]);
						break;

					default:
						break;
					}
				}
				else if(board[i][j].isWalkway())
				{
					if(i - 1 >= 0)
					{
						if(!board[i-1][j].isRoom() || (board[i-1][j].isDoorway() && board[i-1][j].getDoorDirection() == DoorDirection.DOWN))
						{
							adjList.add(board[i-1][j]);
						}
					}
					if(j - 1 >= 0) 
					{
						if(!board[i][j-1].isRoom() || (board[i][j-1].isDoorway() && board[i][j-1].getDoorDirection() == DoorDirection.RIGHT))
						{
							adjList.add(board[i][j-1]);
						}
					}
					if(i + 1 < numRows) 
					{
						if(!board[i+1][j].isRoom() || (board[i+1][j].isDoorway() && board[i+1][j].getDoorDirection() == DoorDirection.UP))
						{
							adjList.add(board[i+1][j]);
						}
					}
					if(j + 1 < numColumns) 
					{
						if(!board[i][j+1].isRoom() || (board[i][j+1].isDoorway() && board[i][j+1].getDoorDirection()==DoorDirection.LEFT))
						{
							adjList.add(board[i][j+1]);
						}
					}
				}

				adjMatrix.put(board[i][j], adjList);
			}
		}
		getCellNames();
	}

	// Find potential targets for selected BoardCell
	public void calcTargets(int rows, int cols, int pathLength) {
		visited = new HashSet<BoardCell>();
		targets = new HashSet<BoardCell>();
		findTargets(rows, cols, pathLength);
		
	}
	
	public void findTargets(int rows, int cols, int pathLength)
	{
		visited.add(board[rows][cols]);
		if(pathLength == 0 || board[rows][cols].isDoorway())
		{
			if(visited.size() > 1){
				targets.add(board[rows][cols]);
				return;
			}
		}

		for (BoardCell cell : adjMatrix.get(board[rows][cols]))
		{
			if(!visited.contains(cell))
			{
				findTargets(cell.getRow(), cell.getColumn(), pathLength - 1);
				visited.remove(cell);
			}
		}
	}

	public Map<Character, String> getLegend() {
		return legend;
	}

	public int getNumRows() {
		return numRows;
	}

	public int getNumColumns() {
		return numColumns;
	}

	public BoardCell getCellAt(int row, int cols) {
		return board[row][cols];
	}

	public Set<BoardCell> getAdjList(int rows, int cols) {
		return adjMatrix.get(board[rows][cols]);
	}

	public Set<BoardCell> getTargets() {
		return targets;
	}

	public void loadConfigFiles() throws FileNotFoundException, BadConfigFormatException {
		loadRoomConfig();
		loadBoardConfig();
		if(playerConfigFile != null) {
			loadPlayerConfig();
		}
		if(weaponConfigFile != null) {
			loadWeaponConfig();
		}
	}
	public void loadPlayerConfig() throws FileNotFoundException, BadConfigFormatException {
		if(deck == null){
			deck = new ArrayList<Card>();
		}
		playerCards = new ArrayList<Card>();
		this.cpuPlayers = new ArrayList<ComputerPlayer>();
		FileReader reader = new FileReader(playerConfigFile);
		Scanner in = new Scanner(reader); 

		while (in.hasNextLine()) {
			String name = in.nextLine();
			if(!in.hasNextLine()){
				throw new BadConfigFormatException("Incorrect number of lines in player config file "+playerConfigFile);			
			}
			Card newPerson = new Card(name,CardType.PERSON);
			deck.add(newPerson);
			playerCards.add(newPerson);
			String color = in.nextLine();
			Color colour = convertColor(color);
			if(colour == null){
				throw new BadConfigFormatException("Color format for person " + name + " in player config file "+playerConfigFile);
			}
			if(!in.hasNextLine()){
				throw new BadConfigFormatException("Incorrect number of lines in player config file "+playerConfigFile);			
			}
			String location = in.nextLine();
			String[] startingPos = location.split(" ");
			int row=0;
			int col=0;
			try{
				row = Integer.parseInt(startingPos[0]);
				col = Integer.parseInt(startingPos[1]);
			}
			catch(NumberFormatException e){
				throw new BadConfigFormatException("Player " + name + " has invalid position in " + playerConfigFile);
			}
			if(this.getCellAt(row, col) == null){
				throw new BadConfigFormatException("Player " + name + " is not on the board in " + playerConfigFile);
			}
			else if(!this.getCellAt(row, col).isWalkway()){
				throw new BadConfigFormatException("Player " + name + " is not on a walkway in " + playerConfigFile);
			}
			if(humanPlayer == null) { 
				HumanPlayer p = new HumanPlayer(name,colour,row,col);
				this.humanPlayer = p;
				players.add(this.humanPlayer);
			}
			else
			{
				ComputerPlayer p = new ComputerPlayer(name,colour,row,col);
				this.cpuPlayers.add(p);
				this.players.add(p);
			}
		}
		in.close();	
	}
	public void loadWeaponConfig() throws FileNotFoundException, BadConfigFormatException {
		if(deck == null){
			deck = new ArrayList<Card>();
		}
		weaponCards = new ArrayList<Card>();
		FileReader reader = new FileReader(weaponConfigFile);
		Scanner in = new Scanner(reader);
		while (in.hasNextLine()) {
			String str = in.nextLine();
			Card newWep = new Card(str,CardType.WEAPON);
			deck.add(newWep);
			weaponCards.add(newWep);
		}
		in.close();	
	}
	@SuppressWarnings("unchecked")
	public ArrayList<Card> getPersons() {
		return (ArrayList<Card>)this.playerCards.clone();
	}
	@SuppressWarnings("unchecked")
	public ArrayList<Card> getWeapons() {
		return (ArrayList<Card>)this.weaponCards.clone();
	}
	public void selectAnswer() {
		Card person = null;
		Card weapon = null;
		Card room = null;
		for(Card c : deck){
			switch(c.type){
			case PERSON:
				if(person == null) {
					person = c;
				}
				break;
			case ROOM:
				if(room == null) {
					room = c;
				}
				break;
			case WEAPON:
				if(weapon == null) {
					weapon = c;
				}
				break;
			default:
				break;
			}
		}
		this.solution = new Solution(person,weapon,room);
	}
	public Card handleSuggestion(Solution suggestion, Player accuser){
		int index = players.indexOf(accuser);
		if(index < 0){
			//not a valid accuser, might want to throw an error
			return null;
		}
		
		for(int i = index + 1; i!=index; ++i) {
			if(i >= players.size()){ 
				i -= players.size();
				if(i == index) {
					break;
				}
			}
			Card result = players.get(i).disproveSuggestion(suggestion);
			if(result != null) {
				return result; 
			}
		}
		return null;
	}
	public boolean checkAccusation(Solution accusation) {
		if(solution == null) {
			return false;
		}
		return this.solution.equals(accusation);
	}
	public ArrayList<Card> getDeck(){
		return this.deck;
	}
	public void shuffleDeck(){
		Collections.shuffle(deck);
	}
	/**
	 * This method also creates the solution
	 */
	public void dealDeck(){
		shuffleDeck();
		selectAnswer();
		int currentPlayer = 0;
		for(Card c : deck){
			boolean willDeal=true;
			switch(c.type){
			case PERSON:
				if(c.equals(solution.person) ) {
					willDeal = false;
				}
				break;
			case ROOM:
				if(c.equals(solution.room) ) {
					willDeal = false;
				}
				break;
			case WEAPON:
				if(c.equals(solution.weapon) ) {
					willDeal = false;
				}
				break;
			default:
				break;
			}
			if(willDeal){
				//deals card to the human last, increments through cpuPlayers
				if(currentPlayer == cpuPlayers.size()){
					humanPlayer.giveCard(c);
					currentPlayer = 0;
				}
				else {
					cpuPlayers.get(currentPlayer).giveCard(c);
					currentPlayer++;
				}
			}
		}
	}
	public HumanPlayer getHumanPlayer() {
		return this.humanPlayer;
	}
	public ArrayList<ComputerPlayer> getComputerPlayers() {
		return this.cpuPlayers;
	}
	public ArrayList<Player> getPlayers() {
		return this.players;
	}
	public Solution getSolution() {
		return this.solution;
	}
	public static Color convertColor(String strColor) {
	    Color color; 
	    try {     
	        color = new Color( Integer.valueOf(strColor.substring(0,2), 16),Integer.valueOf(strColor.substring(2,4), 16), Integer.valueOf(strColor.substring(4,6), 16));
	    } catch (Exception e) {  
	        color = null; // Not defined  
	    }
	    return color;
	}
	
}
