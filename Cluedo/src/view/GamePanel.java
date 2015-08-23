package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
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
	private Game game;
	
	private Image leftDie;
	private Image rightDie;
	
	private JButton suggestBtn;
	public GamePanel(Game g, MainWindow window){
		this.game = g;
		
		this.setMinimumSize(new Dimension(WIDTH, 0));
		this.setMaximumSize(new Dimension(WIDTH, Integer.MAX_VALUE));
		
		this.setLayout(new BorderLayout());
		
		/* 
		 * Names at the top
		 */
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
		nameFrame.setPreferredSize(new Dimension(WIDTH, 40));
		
		/*
		 * Middle Info panel
		 */
		JPanel infoPanel = new JPanel(){
			public void paintComponent(Graphics g){
				//Draw the two dice
				g.drawImage(leftDie, 0, 0, 50, 50, null);
				g.drawImage(rightDie, 0, 52, 50, 50, null);
			}
		};
		
		/* 
		 * Buttons at the bottom 
		 */
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.PAGE_AXIS));
		
		//Suggest Button
		suggestBtn = new JButton("Suggest");
		suggestBtn.setAlignmentX(CENTER_ALIGNMENT);
		suggestBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		//Accuse Button
		JButton accuseBtn = new JButton("Accuse");
		accuseBtn.setAlignmentX(CENTER_ALIGNMENT);
		accuseBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		//Show Hand Button
		JButton handBtn = new JButton("Show Hand");
		handBtn.setAlignmentX(CENTER_ALIGNMENT);
		handBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		//Next Round button
		JButton roundBtn = new JButton("Next Round");
		roundBtn.setAlignmentX(CENTER_ALIGNMENT);
		roundBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				game.nextRound();
				
				updateDice();
				window.updateWindow();
				GamePanel.this.repaint();
			}
		});
		
		//Add buttons to panel
		buttonPanel.add(suggestBtn);
		buttonPanel.add(accuseBtn);
		buttonPanel.add(handBtn);
		buttonPanel.add(roundBtn);
		
		//Add the panels to the pane
		this.add(nameFrame, BorderLayout.NORTH);
		this.add(infoPanel, BorderLayout.CENTER);
		this.add(buttonPanel, BorderLayout.SOUTH);
		
		updateDice();
	}
	
	/**
	 * Perform the conditional checks on this window
	 */
	public void updateWindow(){
		suggestBtn.setEnabled(game.getCurrent().getTile() instanceof Room);
	}
	
	public void updateDice(){
		int roll = game.getRoll();
		//ranges for a single die
		int min = roll-6;
		int max = roll-1 < 6 ? roll-1 : 6;
		
		int left = (int)(min + Math.random() * (max-min)+1);
		int right = roll-left;
		
		//Debugging stuff
		if (left > 6){System.out.println("Whoops. Left was "+left);}
		if (right > 6){System.out.println("Whoops. Right was "+right);}
		
		leftDie = BoardCanvas.loadImage("dice/die_"+left);
		rightDie = BoardCanvas.loadImage("dice/die_"+right);
	}
}
