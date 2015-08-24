package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

import model.Characters;
import model.Weapons;
import model.tiles.Room;
import controller.Game;

/**
 * A dialog to get user input for Suggestions and Accusations
 * @author Carl
 *
 */
@SuppressWarnings("serial")
public class MurderDialog extends JDialog {
	private String[] characters = null;
	private String[] weapons = null;
	private String[] rooms = null;
	
	private String selectedChar = "";
	private String selectedWeap = "";
	private String selectedRoom = "";
	
	boolean completed = false;
	
	/**
	 * Constructor to specify that we are making an accusation
	 * @param g
	 */
	public MurderDialog(JFrame parent, Game g){
		super(parent, "Accuse", true);
		
		initCharacters();
		initWeapons();
		initRooms(g);
		
		createPane();
	}
	
	/**
	 * Constructor to specify that we are making a suggestion
	 * @param g
	 * @param r
	 */
	public MurderDialog(JFrame parent, Game g, Room r){
		super(parent, "Suggest", true);
		
		initCharacters();
		initWeapons();
		selectedRoom = r.getName();
		
		createPane();
	}
	
	/**
	 * Initialize the characters string array
	 */
	private void initCharacters(){
		Characters[] ch = Characters.values();
		characters = new String[ch.length];
		for (int i = 0; i < ch.length; i++){
			characters[i] = Game.toTitleCase(ch[i].toString());
		}
	}
	
	/**
	 * Initialize the weapons string array
	 */
	private void initWeapons(){
		Weapons[] we = Weapons.values();
		weapons = new String[we.length];
		for (int i = 0; i < we.length; i++){
			weapons[i] = Game.toTitleCase(we[i].toString());
		}
	}
	
	/**
	 * Initialize the room string array
	 * @param g
	 */
	private void initRooms(Game g){
		List<Room> r = g.getBoard().getRooms();
		rooms = new String[r.size()];
		
		for (int i = 0; i < r.size(); i++){
			rooms[i] = r.get(i).getName();
		}
	}
	
	/**
	 * Create the pane elements
	 */
	private void createPane(){
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		if (this.getParent() != null) {
			Dimension parentSize = getParent().getSize();
			Point p = getParent().getLocation();
			setLocation(p.x + parentSize.width / 4, p.y + parentSize.height / 4);
		}
		this.setLayout(new BorderLayout());
		
		//Create combo box pane, and set options
		JPanel comboPane = new JPanel();
		BoxLayout bl = new BoxLayout(comboPane, BoxLayout.Y_AXIS);
		comboPane.setLayout(bl);
				
		//Initialize the combo box menus
		JComboBox<String> chars = new JComboBox<String>(characters);
		JComboBox<String> weaps = new JComboBox<String>(weapons);
		
		JComboBox<String> rooms;
		if (this.rooms != null){
			rooms = new JComboBox<String>(this.rooms);
		} else {
			String[] ra = {selectedRoom};
			rooms = new JComboBox<String>(ra);
			rooms.setEnabled(false);
		}
		
		//Add the combo boxes to the combo pane
		comboPane.add(chars);
		comboPane.add(weaps);
		comboPane.add(rooms);
		
		JPanel btnPane = new JPanel();
		FlowLayout fl = new FlowLayout();
		btnPane.setLayout(fl);

		fl.setAlignment(FlowLayout.RIGHT);
		
		JButton okBtn = new JButton("Ok");
		JButton closeBtn = new JButton("Cancel");
		
		okBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				selectedChar = ((String)chars.getSelectedItem()).replace(' ', '_').toUpperCase();
				selectedWeap = ((String)weaps.getSelectedItem()).replace(' ', '_').toUpperCase();
				selectedRoom = (String)rooms.getSelectedItem();

				completed = true;
				setVisible(false);
				dispose();
			}
		});
		
		closeBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				dispose();
			}
		});
		
		btnPane.add(okBtn);
		btnPane.add(closeBtn);
		
		this.add(comboPane, BorderLayout.CENTER);
		this.add(btnPane, BorderLayout.SOUTH);
		pack();
		setVisible(true);
	}
	
	public String getCharacter(){return selectedChar;}
	public String getWeapon(){return selectedWeap;}
	public String getRoom(){return selectedRoom;}
	
	public boolean isFinished(){return completed;}
}
