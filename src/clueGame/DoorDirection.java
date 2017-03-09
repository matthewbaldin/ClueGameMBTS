package clueGame;

public enum DoorDirection {
	UP("U"),DOWN("D"),LEFT("L"),RIGHT("R"),NONE("");
	private String direction;
	
	DoorDirection(String dir){
		this.direction=dir;
	}

}
