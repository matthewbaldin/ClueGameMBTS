package experiment;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

// Contains the board and adjacency lists
public class IntBoard {
	
	private BoardCell[][] board;
	private HashSet<BoardCell> visitedList = new HashSet<BoardCell>();
	private HashSet<BoardCell> targetsList = new HashSet<BoardCell>();
	private HashMap<BoardCell, HashSet<BoardCell>> adjacentsMap;	
	
	private static final int GRID_SIZE = 4;
	
	public IntBoard(int rows, int cols) {
		super();
		board = new BoardCell[rows][cols];
		
		for (int i = 0; i < rows; i++)
		{
			for (int j = 0; j < cols; j++)
			{
				board[i][j] = new BoardCell(i, j);
			}
		}
		
		calcAdjacencies();
	}
	
	// Returns the column and length of the chosen cell 
	public BoardCell getCell(int cols, int rows) {

		return this.board[cols][rows];
	}

	// calculates the adjacency list for each grid cell
	public void calcAdjacencies()
	{
		adjacentsMap = new HashMap<BoardCell, HashSet<BoardCell>>();
		for (int i = 0; i < GRID_SIZE; i++)
		{
			for (int j =0; j < GRID_SIZE; j++)
			{
				HashSet<BoardCell> adjList = new HashSet<BoardCell>();
				if (i - 1 >= 0) adjList.add(board[i-1][j]);
				if (j - 1 >= 0) adjList.add(board[i][j-1]);
				if (i + 1 < GRID_SIZE) adjList.add(board[i+1][j]);
				if (j + 1 < GRID_SIZE) adjList.add(board[i][j + 1]);
				this.adjacentsMap.put(board[i][j], adjList);
			}
		}
	}
	
	// determines the target cells that are a distance "diceroll" away
	public void calcTargets(BoardCell initCell, int diceRoll)
	{	
		this.visitedList.add(initCell);
		if(diceRoll == 0)
		{
			this.targetsList.add(initCell);
			return;
		}
		
		for (BoardCell x: this.adjacentsMap.get(initCell))
		{
			if(!this.visitedList.contains(x))
			{
				calcTargets(x, diceRoll - 1);
				this.visitedList.remove(x);
			}
		}		
	}
	
	
	// returns list of targets
	public Set<BoardCell> getTargets()
	{
		
		return this.targetsList;
	}
	
	// returns adjacency list for a given cell
	public Set<BoardCell> getAdjList(BoardCell cell) {

		return this.adjacentsMap.get(cell);
	}
	
	
}
