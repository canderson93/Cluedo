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
								
				//Calculate center of screen
				Rectangle2D nameSize = fm.getStringBounds("PLAYER NAME", g);
				Rectangle2D charSize = fm.getStringBounds(current.getName(), g);
				int mid = g.getClipBounds().width/2;
				int offset = (int)((Math.max(nameSize.getWidth(), charSize.getWidth()) + 32)/2);
				
				//Draw icon
				g.drawImage(current.getImage(), mid-offset, 0, 32, 32, null);
				
				//Move offset by img size, and draw name
				offset -= 32;
				g.setFont(g.getFont().deriveFont(Font.BOLD));
				g.drawString("PLAYER NAME", mid-offset, (int)nameSize.getHeight());
				
				//Draw the character
				g.setFont(g.getFont().deriveFont(Font.PLAIN));
				g.drawString(current.getName(), mid-offset, (int)(charSize.getHeight() + nameSize.getHeight()));
			}
		};
		nameFrame.setPreferredSize(new Dimension(WIDTH, 40));
		
		/*
		 * Middle Info panel
		 */
		JPanel infoPanel = new JPanel(){
			public void paintComponent(Graphics g){
				int width = g.getClipBounds().width;
				//Draw the two dice
				g.drawImage(leftDie, width/2-51, 0, 50, 50, null);
				g.drawImage(rightDie, width/2+1, 0, 50, 50, null);
				
				String rollString = "Remaining: "+game.getRoll();
				FontMetrics fm = g.getFontMetrics();
				Rectangle2D strSize = fm.getStringBounds(rollString, g);
				
				g.drawString(rollString, (int)((g.getClipBounds().width/2)-(strSize.getWidth()/2)), (int)(50+strSize.getHeight()));
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
				game.suggestion("MISS_SCARLETT", "REVOLVER", (Room)game.getCurrent().getTile());
				
				window.updateWindow();
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
		Player p = game.getCurrent();
		if (p.getTile() instanceof Room){
			suggestBtn.setEnabled(p.getTile() != p.getLastSuggestion());
		} else {
			suggestBtn.setEnabled(false);
		}
		
	}
	
	public void updateDice(){
		int roll = game.getRoll();
		//ranges for a single die
		int min = roll-6 > 0 ? roll-6 : 1;
		int max = roll-1 < 6 ? roll-1 : 6;
		
		int left = (int)(min + Math.random() * (max-min));
		int right = roll-left;
		
		//Debugging stuff
		if (left > 6 || left < 1){System.out.println("Whoops. Left was "+left);}
		if (right > 6 || right < 1){System.out.println("Whoops. Right was "+right);}
		
		leftDie = BoardCanvas.loadImage("dice/die_"+left+".png");
		rightDie = BoardCanvas.loadImage("dice/die_"+right+".png");
	}
}
