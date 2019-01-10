package code.view;

import java.awt.Graphics;

import javax.swing.JPanel;

import code.model.Board;
import code.model.Tile;

public class BoardPanel extends JPanel {
	private Board board;
	private static final int BLOCK_SIZE = 25;

	/**
	 * Constructs a JPanel to draw Board
	 */
	public BoardPanel(Board board) {
		this.board = board;
	}

	/**
	 * Draws scrabble board
	 */
	@Override
	public void paint(Graphics g) {
		super.paint(g);

		// column labels
		for (int i = 1; i <= 20; i++) {
			g.drawString(String.format("%2d", i), i * BLOCK_SIZE + BLOCK_SIZE * 4, BLOCK_SIZE);
		}

		// row labels
		for (int i = 1; i <= 20; i++) {
			g.drawString(String.format("%2d", i), BLOCK_SIZE * 4, i * BLOCK_SIZE + BLOCK_SIZE);
		}

		// draw board
		for (int x = 0; x < 20; x++) {
			for (int y = 0; y < 20; y++) {
				Tile tile = board.getTile(x, y);
				g.setColor(tile.getColor());
				g.drawString(" " + tile.getLetter(), BLOCK_SIZE * 5 + x * BLOCK_SIZE, BLOCK_SIZE * 2 + y * BLOCK_SIZE);
			}
		}

	}
}
