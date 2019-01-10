package code.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.PriorityQueue;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import code.model.Player;
import code.model.Scrabble;
import code.model.Word;

public class ScrabbleView extends JFrame implements ActionListener {
	private Scrabble game;
	private BoardPanel boardPanel;
	private JMenuItem saveItem;
	private JMenuItem loadItem;
	private PlayerPanel[] playerPanels;

	/**
	 * Constructs a new Scrabble View
	 * 
	 * @param scrabble
	 */
	public ScrabbleView(Scrabble scrabble) {
		this.game = scrabble;

		boardPanel = new BoardPanel(game.getBoard());
		add(boardPanel, BorderLayout.CENTER);

		// create player panels
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(game.getPlayerCount(), 1));
		playerPanels = new PlayerPanel[game.getPlayerCount()];
		for (int i = 0; i < game.getPlayerCount(); i++) {
			playerPanels[i] = new PlayerPanel(game.getPlayer(i), this);
			panel.add(playerPanels[i]);
		}
		add(panel, BorderLayout.SOUTH);

		JMenuBar bar = new JMenuBar();
		JMenu menu = new JMenu("Save and Load");
		bar.add(menu);
		saveItem = new JMenuItem("Save");
		saveItem.addActionListener(this);
		menu.add(saveItem);
		loadItem = new JMenuItem("Load");
		loadItem.addActionListener(this);
		menu.add(loadItem);

		setJMenuBar(bar);

		playerPanels[game.getCurrentPlayer()].enable();

		setSize(800, 600 + game.getPlayerCount() * 30);
		setTitle("Scrabble");
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == saveItem) {
			JFileChooser dlg = new JFileChooser();
			int result = dlg.showOpenDialog(this);
			if (result == JFileChooser.APPROVE_OPTION) {
				File file = dlg.getSelectedFile();
				System.out.println("SAVE: " + file);
				game.save(file.getPath());
			}
		}

		if (e.getSource() == loadItem) {
			JFileChooser dlg = new JFileChooser();
			int result = dlg.showOpenDialog(this);
			if (result == JFileChooser.APPROVE_OPTION) {
				File file = dlg.getSelectedFile();
				System.out.println("LOAD: " + file);
				game.load(file.getPath());
				this.setVisible(false);
				game.startGame();
			}
		}
	}

	/**
	 * Updates the current player index and views
	 */
	public void nextPlayer() {
		playerPanels[game.getCurrentPlayer()].disable();
		game.nextPlayer();
		playerPanels[game.getCurrentPlayer()].enable();
	}

	/**
	 * Returns the scrabble game
	 */
	public Scrabble getGame() {
		return game;
	}

	/**
	 * Returns whether the game is playing
	 */
	public boolean isPlaying() {
		return game.isPlaying();
	}

	/**
	 * Called when game ends
	 */
	public void endGame() {
		Player winner = null;
		int score = 0;

		for(int i = 0; i < game.getPlayerCount(); i++) {
			Player player = game.getPlayer(i);
			if(player.getScore() > score) {
				winner = player;
				score = winner.getScore();
			}
			playerPanels[i].disable();
		}
		
		// display the winner's name
		JOptionPane.showMessageDialog(null, winner.getName() + " wins!", "Winner", JOptionPane.INFORMATION_MESSAGE);
		
		// add winner to records
		PriorityQueue<Player> records = game.readRecordFile();
		
		// display the records
		String message = "";
		int index = 1;
		for(Player player : records) {
			message += index++ + ". " + player.getName() + " Score: " + player.getScore() + "\n";
			if(index >= 20) {
				break;
			}
		}
		JOptionPane.showMessageDialog(this, message);
		
		// save records
		game.saveRecords(records);
	}
	
	/**
	 * Determines whether the word input is a valid word 
	 */
	public boolean isValidWord(Word word) {
		if(word.getDirection() == Word.Direction.Invalid)
			return false;
		
		if(!game.containsWord(word))
			return false;
		
		return true;
	}

	/**
	 * Repaints the board panel
	 */
	public void update() {
		this.boardPanel.repaint();
	}
}
