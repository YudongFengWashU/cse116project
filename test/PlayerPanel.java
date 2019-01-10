package code.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import code.model.Player;
import code.model.Tile;

public class PlayerPanel extends JPanel implements ActionListener {
	private Player player;

	private JLabel label;
	private JComboBox<Tile> tiles;
	private JComboBox<Integer> rows;
	private JComboBox<Integer> cols;
	private JButton placeButton;
	private JButton resetButton;
	private JButton occupyButton;
	private JButton doneButton;
	private ScrabbleView parent;

	/**
	 * Constructs a Player Panel
	 * 
	 * @param player
	 */
	public PlayerPanel(Player player, ScrabbleView parent) {
		this.player = player;
		this.parent = parent;

		// create a label to display player's name and player's scores
		label = new JLabel("Name: " + player.getName() + "   Score :" + player.getScore());
		add(label);

		// tile rack to select
		add(new JLabel("Tile Rack: "));
		tiles = new JComboBox<Tile>();
		for (Tile t : player.getTileRack().getRack()) {
			tiles.addItem(t);
		}
		add(tiles);

		// x
		add(new JLabel("Row: "));
		rows = new JComboBox<Integer>();
		for (int i = 1; i <= 20; i++) {
			rows.addItem(i);
		}
		add(rows);

		// y
		add(new JLabel("Column: "));
		cols = new JComboBox<Integer>();
		for (int i = 1; i <= 20; i++) {
			cols.addItem(i);
		}
		add(cols);

		placeButton = new JButton("Place");
		placeButton.addActionListener(this);
		add(placeButton);

		resetButton = new JButton("Reset");
		resetButton.addActionListener(this);
		add(resetButton);

		occupyButton = new JButton("Occupy");
		occupyButton.addActionListener(this);
		add(occupyButton);

		doneButton = new JButton("Done");
		doneButton.addActionListener(this);
		add(doneButton);

		disable();
	}

	/**
	 * Returns player
	 * 
	 * @return
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * Updates the information displayed
	 */
	public void update() {
		label.setText("Name: " + player.getName() + "   Score :" + player.getScore());
		tiles.removeAllItems();
		for (Tile t : player.getTileRack().getRack()) {
			tiles.addItem(t);
		}
	}

	/**
	 * Sets all components enable
	 */
	public void enable() {
		rows.setEnabled(true);
		cols.setEnabled(true);
		placeButton.setEnabled(true);
		resetButton.setEnabled(true);
		occupyButton.setEnabled(true);
		doneButton.setEnabled(true);
	}

	/**
	 * Sets all components disable
	 */
	public void disable() {
		rows.setEnabled(false);
		cols.setEnabled(false);
		placeButton.setEnabled(false);
		resetButton.setEnabled(false);
		occupyButton.setEnabled(false);
		doneButton.setEnabled(false);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == placeButton) {
			int x = rows.getSelectedIndex();
			int y = cols.getSelectedIndex();
			if (parent.getGame().getBoard().getTile(x, y).getLetter() == ' ') {
				player.placeTile(tiles.getItemAt(tiles.getSelectedIndex()), x, y);
			} else {
				JOptionPane.showMessageDialog(this, "This place has been occurpied!", "Error", JOptionPane.ERROR_MESSAGE, null);
			}
			this.update();
		}

		if (e.getSource() == resetButton) {
			player.resetTiles();
			this.update();
		}

		if (e.getSource() == occupyButton) {
			int x = rows.getSelectedIndex();
			int y = cols.getSelectedIndex();
			if (!player.occupiedTile(x, y)) {
				JOptionPane.showMessageDialog(this, "This place can not occurpy!", "Error", JOptionPane.ERROR_MESSAGE, null);
			}
		}

		if (e.getSource() == doneButton) {
			if(parent.isValidWord(player.getWord())) {
				// sets the tile's color and player
				for(Tile tile : player.getWord().getTiles()) {
					tile.setColor(player.getColor());
					tile.setPlayer(player);
				}
				player.setScore(player.getScore() + player.getWord().score());
			} else {
				// if the word is invalid
				JOptionPane.showMessageDialog(null, "Invalid Word " + player.getWord(), "Error", JOptionPane.ERROR_MESSAGE);
				player.resetTiles();
				this.update();
			}
			
			// reset current word
			player.resetCurrentWord();
			
			// whether the game is end
			if(parent.isPlaying()) {
				parent.nextPlayer();
			} else {
				parent.endGame();
			}
		}

		parent.update();
	}
}
