package view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import model.Board;

/**
 * This class is the frame responsible for redrawing the board and the players
 * @author Carl
 *
 */
@SuppressWarnings("serial")
public class BoardCanvas extends JPanel {
	private Board board;
	
	public BoardCanvas(Board b){
		super();
		
		this.board = b;
		
		this.setPreferredSize(new Dimension(b.getWidth()*Board.tileSize, b.getHeight()*Board.tileSize));
	}
	
	@Override
	public void paintComponent(Graphics g){
		board.drawBoard(g);
	}
	
	/**
	 * Load an image from the file system, using a given filename.
	 * 
	 * @param filename
	 * @return
	 */
	public static Image loadImage(String filename) {
		// using the URL means the image loads when stored
		// in a jar or expanded into individual files.
		java.net.URL imageURL = BoardCanvas.class.getClassLoader().getResource(filename);
		
		try {
			Image img = ImageIO.read(imageURL);
			return img;
		} catch (IOException e) {
			// we've encountered an error loading the image. There's not much we
			// can actually do at this point, except to abort the game.
			throw new RuntimeException("Unable to load image: " + filename);
		}
	}
}
