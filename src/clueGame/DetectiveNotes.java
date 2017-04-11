package clueGame;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.LayoutManager;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class DetectiveNotes extends JDialog{

	public DetectiveNotes() {
		setLayout(new GridLayout(4,2));
		
		add(addPeople());
		add(addPersonGuess());
		add(addRooms());
		add(addRoomGuess());
		add(addWeapons());
		add(addWeaponGuess());
		
	}
	private JPanel addPeople()
	{
		JPanel panel= new JPanel();
		for(Player p:Board.getPlayers())
		{
			JCheckBox person=new JCheckBox(p.getPlayerName());
			panel.add(person);
			
		}
		
		panel.setBorder(new TitledBorder(new EtchedBorder(),"People"));
		return panel;
	}
	
	private JPanel addPersonGuess()
	{
		JPanel panel = new JPanel();

		JComboBox<String> people = new JComboBox<String>();
		people.addItem("Don't Know");
		for (Player p : Board.getPlayers()){
			people.addItem(p.getPlayerName());
		}

		panel.setBorder(new TitledBorder(new EtchedBorder(), "Person Guess"));
		panel.add(people);
		return panel;
	}
	private JPanel addRooms()
	{
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0,2));
		for (char c : Board.getLegend().keySet()) {
			if (c != 'W' && c != 'D') {
				JCheckBox box = new JCheckBox(Board.getLegend().get(c));
				panel.add(box);
			}
		}
		panel.setBorder(new TitledBorder(new EtchedBorder(), "Rooms"));
		return panel;
	}
	
	private JPanel addRoomGuess()
	{
		JPanel panel=new JPanel();
		panel.setLayout(new GridLayout(0,2));
		JComboBox<String> rooms = new JComboBox<String>();
		rooms.addItem("Don't Know");
		for (char c : Board.getLegend().keySet()) {
			if (c != 'W' && c != 'D') {
				rooms.addItem(Board.getLegend().get(c));
			}
		}
		panel.add(rooms);
		panel.setBorder(new TitledBorder(new EtchedBorder(), "Room Guess"));
		return panel;
	}
	private JPanel addWeapons()
	{
		JPanel panel =new JPanel();
		panel.setLayout(new GridLayout(0,2));
		for(Card card: Board.getWeaponCards())
		{
			JCheckBox check=new JCheckBox(card.name);
			panel.add(check);
		}
		panel.setBorder(new TitledBorder(new EtchedBorder(),"Weapons"));
		return panel;
	}
	private JPanel addWeaponGuess()
	{
		JPanel panel =new JPanel();
		panel.setLayout(new GridLayout(0,2));
		JComboBox<String> weapons=new JComboBox<String>();
		weapons.addItem("Don't Know");
		for(Card card: Board.getWeaponCards())
		{
			weapons.addItem(card.name);
		}
		panel.add(weapons);
		panel.setBorder(new TitledBorder(new EtchedBorder(),"WeaponGuess"));
		return panel;
	}

}
