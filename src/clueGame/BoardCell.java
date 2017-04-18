package clueGame;

import java.awt.Color;
import java.awt.Graphics;

public class BoardCell{
	
	private int column, row;
	private char initial;
	private DoorDirection DoorDirection;
	private boolean isName = false;
	private String name;
	private boolean highlighted = false;
	
	public void setHighlighted(boolean b) {
		highlighted = b;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String s) {
		name = s;
	}
	
	public void setIsName(boolean b) {
		isName = b;
	}
	
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
		if (highlighted) {
			g.setColor(Color.YELLOW);
		}
		if (initial == 'W') {
			g.setColor(Color.BLACK);
			g.drawRect(column * 29, row * 29, 29, 29);
		}
		if (isDoorway()) {
			switch(DoorDirection) {
			case UP:
				g.fillRect((column) * 29, row * 29, 29, (int)((double)29 * 0.2));
				break;
			case DOWN:
				g.fillRect((column) * 29, (row + 1) * 29, 29, -(int)((double)29 * 0.2));
				break;
			case RIGHT:
				g.fillRect((column + 1) * 29, row * 29, -(int)((double)29 * 0.2), 29);
				break;
			case LEFT:
				g.fillRect((column) * 29, row * 29, (int)((double)29 * 0.2), 29);
				break;
			}
		}
		if (isName) {
			g.setColor(Color.DARK_GRAY);
			g.drawString(name, column * 29, row * 29);
		}
	}

}
