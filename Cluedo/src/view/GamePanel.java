package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import model.cards.Card;
import model.tiles.Room;
import controller.Game;
import controller.Player;

@SuppressWarnings("serial")
public class GamePanel extends JPanel implements KeyListener{
	public static final int WIDTH = 150;
	private Game game;
	private MainWindow window;
	private Image leftDie;
	private Image rightDie;
	
	private JButton suggestBtn;
	public GamePanel(Game g, MainWindow window){
		this.game = g;
		this.window = window;
		this.setMinimumSize(new Dimension(WIDTH, 0));
		this.setMaximumSize(new Dimension(WIDTH, Integer.MAX_VALUE));
		addKeyListener(this);
		this.requestFocus();
		this.setLayout(new BorderLayout());
		
		/* 
		 * Names at the top
		 */
		JPanel nameFrame = new JPanel(){
			public void paintComponent(Graphics g){
				FontMetrics fm = g.getFontMetrics();
				Player current = game.getCurrent();
				
				String charName = Game.toTitleCase(current.getName());
								
				//Calculate center of screen
				Rectangle2D nameSize = fm.getStringBounds(current.getUserName(), g);
				Rectangle2D charSize = fm.getStringBounds(charName, g);
				int mid = g.getClipBounds().width/2;
				int offset = (int)((Math.max(nameSize.getWidth(), charSize.getWidth()) + 32)/2);
				
				//Draw icon
				g.drawImage(current.getImage(), mid-offset, 0, 32, 32, null);
				
				//Move offset by img size, and draw name
				offset -= 32;
				g.setFont(g.getFont().deriveFont(Font.BOLD));
				g.drawString(current.getUserName(), mid-offset, (int)nameSize.getHeight());
				
				//Draw the character
				g.setFont(g.getFont().deriveFont(Font.PLAIN));
				g.drawString(charName, mid-offset, (int)(charSize.getHeight() + nameSize.getHeight()));
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
				MurderDialog md = new MurderDialog(window, game, (Room)game.getCurrent().getTile());
				
				if (md.isFinished()){
					List<Card> l = new ArrayList<Card>();
					l.add(game.suggestion(md.getCharacter(), md.getWeapon(), (Room)game.getCurrent().getTile()));
					
					if (l.size() == 0){
						JOptionPane.showMessageDialog(window, "Nobody had any cards!", "Result", JOptionPane.OK_OPTION);
					} else {
						new CardDialog(window, "Result", l);
					}
					
					game.nextRound();
				}
				window.updateWindow();
			}
		});
		
		//Accuse Button
		JButton accuseBtn = new JButton("Accuse");
		accuseBtn.setAlignmentX(CENTER_ALIGNMENT);
		accuseBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				MurderDialog md = new MurderDialog(window, game);
				
				if (md.isFinished()){
					boolean result = game.accusation(md.getRoom(), md.getCharacter(), md.getWeapon());
					if (result){
						//We have a winner! Remove the game window
						window.setVisible(false);
						window.dispose();
						
						JOptionPane.showConfirmDialog(null, "Correct! "+game.getWinner().getUserName()+" is the winner!", "Winner!", JOptionPane.OK_OPTION);
						System.exit(0);
					} else {
						JOptionPane.showMessageDialog(window, "Wrong. Sit in the corner and think about your transgressions.", "Incorrect", JOptionPane.OK_OPTION);
						
						if (game.isFinished()){
							window.setVisible(false);
							window.dispose();
							
							JOptionPane.showMessageDialog(null, "Utter Failure. Nobody wins", "Dominated", JOptionPane.OK_OPTION);
							System.exit(0);
						}
					}
					game.nextRound();
				}
				window.updateWindow();
			}
		});
		
		//Show Hand Button
		JButton handBtn = new JButton("Show Hand");
		handBtn.setAlignmentX(CENTER_ALIGNMENT);
		handBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				new CardDialog(window, "Hand", game.getCurrent().getHand());
			}
		});
		
		//Show Unseen Cards Button
		JButton journalBtn = new JButton("Journal");
		journalBtn.setAlignmentX(CENTER_ALIGNMENT);
		journalBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				new CardDialog(window, "Unseen Cards", game.getAllCards(), game.getCurrent().getUnseenCards());
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
		buttonPanel.add(journalBtn);
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
		
		leftDie = BoardCanvas.loadImage("dice/die_"+left+".png");
		rightDie = BoardCanvas.loadImage("dice/die_"+right+".png");
	}

	@Override
	public void keyTyped(KeyEvent e) {
		if(e.getKeyCode() == (KeyEvent.VK_ESCAPE)){
			window.dispatchEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSING));
		}
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == (KeyEvent.VK_ESCAPE)){
			window.dispatchEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSING));
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
