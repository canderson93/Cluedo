package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import model.tiles.Room;
import controller.Game;
import controller.Player;

@SuppressWarnings("serial")
public class GamePanel extends JPanel {
	public static final int WIDTH = 150;
	private MainWindow window;
	private Game game;
	
	private JButton suggestBtn;
	public GamePanel(Game g, MainWindow window){
		this.game = g;
		this.window = window;
		
		this.setMinimumSize(new Dimension(WIDTH, 0));
		this.setMaximumSize(new Dimension(WIDTH, Integer.MAX_VALUE));
		
		this.setLayout(new BorderLayout());
		
		//Name & Icon at the top of the panel
		JPanel nameFrame = new JPanel(){
			public void paintComponent(Graphics g){
				FontMetrics fm = g.getFontMetrics();
				Player current = game.getCurrent();
				
				g.drawImage(current.getImage(), 0, 0, 32, 32, null);
				
				//Draw the name
				Rectangle2D nameSize = fm.getStringBounds("PLAYER NAME", g);
				Rectangle2D charSize = fm.getStringBounds(current.getName(), g);
				
				g.setFont(g.getFont().deriveFont(Font.BOLD));
				g.drawString("PLAYER NAME", 35, (int)nameSize.getHeight());
				
				//Draw the character
				g.setFont(g.getFont().deriveFont(Font.PLAIN));
				g.drawString(current.getName(), 35, (int)(charSize.getHeight() + nameSize.getHeight()));
			}
		};
		nameFrame.setPreferredSize(new Dimension(WIDTH, 32));
		
		//Buttons at the bottom
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.PAGE_AXIS));
		
		//Suggest Button
		suggestBtn = new JButton("Suggest");
		
		//Show Hand Button
		
		//Next Round button
		JButton roundBtn = new JButton("Next Round");
		roundBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				game.nextRound();
				window.updateWindow();
				GamePanel.this.repaint();
			}
		});
		
		//Add buttons to panel
		buttonPanel.add(suggestBtn);
		buttonPanel.add(roundBtn);
		
		//Add the panels to the pane
		this.add(nameFrame, BorderLayout.NORTH);
		this.add(buttonPanel, BorderLayout.SOUTH);
	}
	
	/**
	 * Perform the conditional checks on this window
	 */
	public void updateWindow(){
		suggestBtn.setEnabled(game.getCurrent().getTile() instanceof Room);
	}
}
