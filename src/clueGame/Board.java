package clueGame;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.FileReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Board {
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
	private ArrayList<ComputerPlayer> cpuPlayers;
	private HumanPlayer humanPlayer;
	private Solution theAnswer;
	// variable used for singleton pattern
	private static Board theInstance = new Board();

	// ctor is private to ensure only one can be created
	private Board() {}

	// this method returns the only Board
	public static Board getInstance() {
		return theInstance;
	}
	@Deprecated
	public void setConfigFiles(String boardCF, String roomCF) {
		boardConfigFile = boardCF;
		roomConfigFile = roomCF;
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
				deck.add(new Card(str.substring(0,str.indexOf(",")),CardType.ROOM));
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
		for(int i=0;i<numRows;++i){
			for(int j=0;j<numColumns;++j){

				if(boardIns.get(i).length != numColumns) {
					throw new BadConfigFormatException("Incorrect number of columns: number of columns should be " + numColumns); 
				}

				String initHold=boardIns.get(i)[j];
				if(initHold.length()>1){
					board[i][j]=new BoardCell(i,j,initHold.charAt(0),initHold.charAt(1));
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

	public void initialize() {
		legend = new HashMap<Character, String>();
		targets = new HashSet<BoardCell>();
		deck = new ArrayList<Card>();
		try {
			loadConfigFiles();
		} catch (FileNotFoundException e) {
			System.out.println("Error loading config file " + e);
		} catch (BadConfigFormatException e) {
			System.out.println("There was a config error");
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
		if(pathLength == 0||board[rows][cols].isDoorway())
		{
			if(visited.size()>1){
				targets.add(board[rows][cols]);
				return;
			}
		}

		for (BoardCell x: adjMatrix.get(board[rows][cols]))
		{
			if(!visited.contains(x))
			{
				findTargets(x.getRow(), x.getColumn(), pathLength - 1);
				visited.remove(x);
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
		legend = new HashMap<Character, String>();
		FileReader reader = new FileReader(roomConfigFile);
		Scanner in = new Scanner(reader); 

		while (in.hasNextLine()) {
			String str = in.nextLine();

			if(str.substring(str.lastIndexOf(",") + 2).equals("Card")){
				deck.add(new Card(str.substring(0,str.indexOf(",")),CardType.ROOM));
			}
			else if (!str.substring(str.lastIndexOf(",") + 2).equals("Other")) {

				throw new BadConfigFormatException("Incorrect Room Type: room should be of type 'Card' or 'Other'");			

			}
			
			legend.put(str.charAt(0), str.substring(3, str.indexOf(",", 3)));

		}
		in.close();	
	}
	public void loadWeaponConfig() throws FileNotFoundException, BadConfigFormatException {
		
	}
	public void selectAnswer() {
		
	}
	public Card handleSuggestion(){
		
		return null;
	}
	public boolean checkAccusation(Solution accusation) {
		
		return false;
	}
	public ArrayList<Card> getDeck(){
		return this.deck;
	}
}
