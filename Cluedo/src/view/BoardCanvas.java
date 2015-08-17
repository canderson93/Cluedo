package view;

import java.awt.Dimension;
import java.awt.Graphics;

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
}
