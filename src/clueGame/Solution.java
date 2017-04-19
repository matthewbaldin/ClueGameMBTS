package clueGame;

public class Solution {
	public final Card person;
	public final Card weapon;
	public final Card room;
	public Solution(Card person, Card weapon, Card room) {
		this.person = person;
		this.weapon = weapon;
		this.room = room;
	}
	public boolean equals(Solution s){
		if(s == null) {
			return false;
		}
		return this.person.equals(s.person) && this.room.equals(s.room) && this.weapon.equals(s.weapon);
	}
	@Override
	public String toString() {
		String s = person + " in the " + room + " with the " + weapon + ".";
		return s;
	}
}
