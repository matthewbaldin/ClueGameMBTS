package clueGame;

import java.awt.Color;
import java.awt.Graphics;

public class BoardCell{
	
	private int column, row;
	private char initial;
	private DoorDirection DoorDirection;
	
	public BoardCell(int row, int col) {
		super();
		this.column = col;
		this.row = row;
	}
	
	public BoardCell(int row, int col,char init) {
		super();
		this.column = col;
		this.row = row;
		this.initial=init;
		this.DoorDirection = DoorDirection.NONE;
	}
	
	public BoardCell(int row, int col,char init, char dir) {
		super();
		this.column = col;
		this.row = row;
		this.initial=init;
		
		switch(dir)
		{
		case 'U':
			this.DoorDirection = DoorDirection.UP;
			break;
		
		case 'D':
			this.DoorDirection = DoorDirection.DOWN;
			break;
			
		case 'L':
			this.DoorDirection = DoorDirection.LEFT;
			break;
			
		case 'R':
			this.DoorDirection = DoorDirection.RIGHT;
			break;
			
		default:
			this.DoorDirection = DoorDirection.NONE;
			break;
		}
	}
	
	public int getColumn() {
		return column;
	}

	public int getRow() {
		return row;
	}
	
	public char getInitial() {
		return initial;
	}
	
	public void setInitial(char init) {
		this.initial = init;
	}

	public boolean isWalkway()
	{
		if(this.initial == 'W')
		{
			return true;
		}
		return false;
	}
	
	public boolean isRoom()
	{
		if (this.initial != 'W')
		{
			return true;
		}
		return false;
	}
	
	public boolean isDoorway()
	{
		if(this.DoorDirection==DoorDirection.NONE)
		return false;
		else return true;
	}
	
	public DoorDirection getDoorDirection() {
		return this.DoorDirection;
	}
	public void draw(Graphics g) {
		if (initial == 'W') {
			g.setColor(Color.BLACK);
			g.drawRect(column * 37, row * 37, 37, 37);
		}
		if (isDoorway()) {
			switch(DoorDirection) {
			case UP:
				g.fillRect((column) * 37, row * 37, 37, (int)((double)37 * 0.2));
				break;
			case DOWN:
				g.fillRect((column) * 37, (row + 1) * 37, 37, -(int)((double)37 * 0.2));
				break;
			case RIGHT:
				g.fillRect((column + 1) * 37, row * 37, -(int)((double)37 * 0.2), 37);
				break;
			case LEFT:
				g.fillRect((column) * 37, row * 37, (int)((double)37 * 0.2), 37);
				break;
			}
		}
	}

}
