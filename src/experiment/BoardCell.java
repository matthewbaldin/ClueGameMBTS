package experiment;
//import java.math.*;

public class BoardCell{
	
	private int column, row = 0;
	
	public int getColumn() {
		return column;
	}

	public int getRow() {
		return row;
	}

	public BoardCell(int col, int row) {
		super();
		this.column = col;
		this.row = row;
	}
}
