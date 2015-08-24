package view;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.TextAttribute;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.text.AttributedString;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controller.Game;
import model.cards.Card;


public class CardDialog extends JDialog implements ActionListener{
	
	public CardDialog(JFrame parent, String message, List<Card> hand) {
		super(parent, message, true);
		run(parent, hand, null);
	}
	
	public CardDialog(JFrame parent, String message, List<Card> allCards, List< Card> unseenCards){
		super(parent, message, true);	
		for(Card c : allCards){
			System.out.println(c);
		}
		run(parent, allCards, unseenCards);
	}
	
	private void run(JFrame parent, List<Card> allCards, List<Card> unseenCard) {
		
		if(parent != null){		
			Dimension parentSize = parent.getSize(); 
		    Point p = parent.getLocation(); 
		    setLocation(p.x + parentSize.width / 4, p.y + parentSize.height / 4);
		}
		
	    JPanel messagePane = new JPanel();
	    messagePane.setLayout(new GridLayout(0, 6, 2, 2));
	   // JScrollPane scrollPane = new JScrollPane();
	    // messagePane.add(new JLabel(message + "fuck"));
    	for(Card c : allCards){

    	    BufferedImage cardImage = (BufferedImage) BoardCanvas.loadImage("cards/card_front.png");
    	    JLabel picLabel = new JLabel(new ImageIcon(resize(cardImage, 100, 140))){    	
    	    	@Override
    	    	public void paintComponent(Graphics g){
    	    		
    	    		super.paintComponent(g);
    	    		g.setColor(Color.YELLOW);
    	    		String originalStr = Game.toTitleCase(c.getValue());
    	    		AttributedString name = new AttributedString(originalStr);
    	    		FontMetrics fm = g.getFontMetrics();
    				Rectangle2D strSize = fm.getStringBounds(originalStr, g);
    				if(unseenCard != null && !unseenCard.contains(c)){
    					//g.setFont(g.getFont().deriveFont(Font.BOLD));
    					g.setColor(Color.WHITE);
    					name.addAttribute(TextAttribute.STRIKETHROUGH, TextAttribute.STRIKETHROUGH_ON);
    	    		}    				
    				System.out.println("drawing card " + originalStr);
    				g.drawString(name.getIterator(), (int)((g.getClipBounds().width/2)-(strSize.getWidth()/2)), (int)(g.getClipBounds().height/2));    	    		
    	    	}	
    	    };

    		messagePane.add(picLabel); 		
	    }
	    
	    getContentPane().add(messagePane);
	    JPanel buttonPane = new JPanel();
	    JButton button = new JButton("Close"); 
	    buttonPane.add(button); 
	    button.addActionListener(this);
	    getContentPane().add(buttonPane, BorderLayout.SOUTH);
	    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	    pack(); 
	    setVisible(true);
	  }
	
	public static BufferedImage resize(BufferedImage img, int newW, int newH) {
		
		BufferedImage otherImage = img;
		BufferedImage newImage = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = newImage.createGraphics();
		g.drawImage(otherImage, 0, 0, newW, newH, null);
		g.dispose();	
		return newImage;
	}

	public void actionPerformed(ActionEvent e) {
	    setVisible(false); 
	    dispose(); 
	}		
}

/*	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}*/

