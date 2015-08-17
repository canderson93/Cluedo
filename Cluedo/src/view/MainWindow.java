package view;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import controller.Game;

@SuppressWarnings("serial")
public class MainWindow extends JFrame {
	private BoardCanvas canvas;
	private Game game;
	
	public MainWindow(Game g){
		super("Cluedo - Super Hyper Extra Edition");
		
		this.game = g;
		this.canvas = new BoardCanvas(game.getBoard());
	
		//Window configuration
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.setPreferredSize(new Dimension(1000, 400));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menu = createMenu();
		
		this.add(canvas);
		
		this.setJMenuBar(menu);
		this.pack();
		this.setVisible(true);
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
		
		file.add(newGame);
		file.add(close);
		
		menu.add(file);
		return menu;
	}
	

	public static void main(String[] args) {
		//TODO: Player Input Dialog goes here
		new MainWindow(new Game("board.txt", 3));
	}

}
