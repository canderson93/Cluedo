package view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import model.Board;
import model.tiles.Tile;

/**
 * This class is the frame responsible for redrawing the board and the players
 * @author Carl
 *
 */
@SuppressWarnings("serial")
public class BoardCanvas extends JPanel implements MouseMotionListener, MouseListener{
	private Board board;
	private Tile highlightedTile;
	
	public BoardCanvas(Board b){
		super();
		this.addMouseMotionListener(this);
		this.addMouseListener(this);
		this.board = b;
		
		this.setPreferredSize(new Dimension(b.getWidth()*Board.tileSize, b.getHeight()*Board.tileSize));
	}
	
	@Override
	public void paintComponent(Graphics g){
		Board.tileSize = Math.min(this.getWidth()/board.getWidth(), this.getHeight()/board.getHeight());
		board.drawBoard(g, this.highlightedTile);
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

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		int mouseX = e.getX() / board.tileSize;
		int mouseY = e.getY() / board.tileSize;
		this.highlightedTile = board.getTile(mouseX, mouseY);
		repaint();		
	}

	@Override
	public void mouseClicked(MouseEvent e) { 

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
