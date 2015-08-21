package view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import controller.Game;

@SuppressWarnings("serial")
public class MainWindow extends JFrame {
	private BoardCanvas canvas;
	private Game game;
	
	//Window components
	JSplitPane divider; 
	
	public MainWindow(){
		super("Cluedo - Super Hyper Extra Edition");
	
		//Window configuration
		this.setLayout(new GridBagLayout());
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		//Close window dialog
		this.addWindowListener(new WindowAdapter(){
			@Override
			public void windowClosing(WindowEvent ev) {
				int result = JOptionPane.showConfirmDialog(MainWindow.this, "The Killer is still at large!\n"
						+ "Are you sure you want to close?", "HALT!", JOptionPane.YES_NO_OPTION);			
				if (result == JOptionPane.YES_OPTION){
		            MainWindow.this.dispose();
		            System.exit(0);
				}
	        }
		});
		
		JMenuBar menu = createMenu();
				
		this.setJMenuBar(menu);
	}
	
	/**
	 * Creates the JMenuBar for use in the main window
	 * @return
	 */
	private JMenuBar createMenu(){
		JMenuBar menu = new JMenuBar();
		JMenu file = new JMenu("File");
		
		//Menu Item 1
		JMenuItem newGame = new JMenuItem("New Game");
		JMenuItem close = new JMenuItem("Close");
		
		//New game response
		newGame.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				MainWindow.this.startNewGame("board.txt");
			}
		});
		
		//Close Button response
		close.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				//Throw the window close event, so the dialog shows
				MainWindow.this.dispatchEvent(new WindowEvent(MainWindow.this, WindowEvent.WINDOW_CLOSING));
			}
			
		});
		
		file.add(newGame);
		file.add(close);
				
		menu.add(file);
		return menu;
	}
	
	/**
	 * This method recieves the number of players as in input from the player,
	 * and creates a new game object
	 * @param filename
	 */
	private void startNewGame(String filename){
		int players;
		
		while (true){
			String result = JOptionPane.showInputDialog(this, "How many players?", "New Game", JOptionPane.QUESTION_MESSAGE);

			//If the user presses cancel/exit, stop the new game dialog
			if (result == null){
				if (game == null){
					System.exit(0);
				}
				return;
			}
			
			//Try convert the input to a number, and provide an appropriate error message
			try{
				players = Integer.parseInt(result);
				
				if (players >= 3 && players <= 6){
					break;
				}
				
				JOptionPane.showMessageDialog(this, "Invalid selection. Choose between 3 and 6 players", "Invalid Selection", JOptionPane.ERROR_MESSAGE);
			} catch (NumberFormatException e){
				JOptionPane.showMessageDialog(this, "That's not a number!", "Invalid Selection", JOptionPane.ERROR_MESSAGE);
			}
		}
		
		//Set up the board
		if (divider != null){
			this.remove(divider);
		}
		
		this.game = new Game(filename, players);
		this.canvas = new BoardCanvas(game);
		
		JPanel outerPanel = new JPanel(new GridBagLayout());
		JPanel rightPane = new JPanel();
		
		rightPane.setMaximumSize(new Dimension(400, Integer.MAX_VALUE));
								
		//Set constraints to consume all available space
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;
		c.weighty = 1;
		
		//TODO: Make right hand side of this panel
		divider = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, outerPanel, rightPane);
		divider.setResizeWeight(0.5);
		
		outerPanel.add(canvas, c);
		
		JButton button = new JButton("Next Round");
		rightPane.add(button);
		
		button.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				game.nextRound();
			}
			
		});
		
		GridBagConstraints divConstraints = new GridBagConstraints();
		divConstraints.fill = GridBagConstraints.BOTH;
		divConstraints.weightx = 1;
		divConstraints.weighty = 1;
		
		this.add(divider, divConstraints);
		this.pack();
		this.setVisible(true);
	}
	

	public static void main(String[] args) {
		new MainWindow().startNewGame("board.txt");
	}

}
