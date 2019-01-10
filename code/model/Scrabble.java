package code.model;

import java.awt.Color;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;

import code.view.ScrabbleView;

/**
 * Scrabble creates the board and inventory as well as facilitates the addition
 * of players. At present, it also handle initiating and completing the game.
 * Later, a turn/gameplay handler may be necessary.
 * 
 * @author t020
 *
 */
public class Scrabble {

	private Board _board;
	private Inventory _inventory;
	private ArrayList<Player> _players;
	private boolean _playing;
	private int _playerCount;
	private int _currentPlayer;
	private Dictionary _dictionary;
	private static Color colors[] = { Color.ORANGE, Color.GREEN, Color.BLUE, Color.RED };
	private static final String recordFilename = "ScrabbleHighScores.txt";

	/**
	 * The default constructor for a new Scrabble. Creates a board and an
	 * inventory.
	 */
	public Scrabble() {
		_inventory = new Inventory();
		_board = new Board();
		_players = new ArrayList<Player>();
		_currentPlayer = 0;
	}

	/**
	 * Adds a player to the game
	 * 
	 * @return true if added successfully
	 */
	public boolean addPlayer(String name) {
		Color color = colors[_playerCount];
		_playerCount++;
		Player player = new Player(_inventory, _board);
		player.setName(name);
		player.setColor(color);
		_players.add(player);

		return true;
	}

	/**
	 * Removes a player from the game
	 * 
	 * @param p
	 *            player to remove
	 * @return true if removed successfully
	 */
	public boolean removePlayer(Player p) {
		_playerCount--;

		return _players.remove(p);
	}

	/**
	 * This initiates the main game loop.
	 */
	public void startGame() {
		new ScrabbleView(this);
		_playing = true;
	}

	/**
	 * Determines whether the game is end.
	 */
	public boolean isEnd() {
		return _inventory.size() == 0;
	}

	/**
	 * Ends the game and announces the winner. Additionally clears the board,
	 * creates a new inventory, and resets the players.
	 */
	public void endGame() {
		Player winning = new Player(_inventory, _board);

		for (Player p : _players) {
			if (p.getScore() > winning.getScore())
				winning = p;
		}

		System.out.println(winning.getName() + " wins!");

		_playing = false;
		_board.wipeBoard();
		_inventory = new Inventory();
		_players = new ArrayList<Player>();

	}

	/**
	 * Returns the amount of players in the game
	 */
	public int getPlayerCount() {
		return _playerCount;
	}

	/**
	 * Returns the board of Scrabble board
	 */
	public Board getBoard() {
		return _board;
	}

	/**
	 * Returns the current player index
	 */
	public int getCurrentPlayer() {
		return _currentPlayer;
	}

	public void nextPlayer() {
		if (isEnd()) {
			endGame();
		} else {
			_currentPlayer = (_currentPlayer + 1) % _playerCount;
		}
	}

	/**
	 * Returns the player at index
	 */
	public Player getPlayer(int index) {
		return _players.get(index);
	}
	
	/**
	 * Returns whether the game is playing
	 */
	public boolean isPlaying() {
		return _playing;
	}

	/**
	 * Sets the dictionary by given file.
	 */
	public void setDictionary(String filename) {
		_dictionary = new Dictionary(filename);
	}
	
	/**
	 * Determines whether the game's dictionary contains the 
	 * given word
	 */
	public boolean containsWord(Word word) {
		return _dictionary.contains(word);
	}

	/**
	 * Saves this scrabble to a file
	 * 
	 * @param filename
	 */
	public void save(String filename) {
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(filename));

			// write board size
			writer.write("20 20");
			writer.newLine();

			// write dictionary file path
			writer.write(_dictionary.getFilepath());
			writer.newLine();

			// write each player
			for (Player player : _players) {
				writer.write(player.getName() + "," + player.getColor().getRGB() + "," + player.getScore() + "," + player.getTileRack().toString());
				writer.newLine();
			}

			// write current player index
			writer.write(_currentPlayer + "");
			writer.newLine();

			// write inventory tiles
			writer.write(_inventory.toString());
			writer.newLine();

			// write board
			for (int x = 0; x < 20; x++) {
				for (int y = 0; y < 20; y++) {
					writer.write(_board.getTile(x, y).toString());
					System.out.println(x + " " + y + " " + _board.getTile(x, y).toString());
				}
				writer.newLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Load scrabble game from a given file
	 */
	public void load(String filename) {
		try {
			Scanner input = new Scanner(new File(filename));

			// read board size
			input.nextLine();

			// read dictionary file path
			this.setDictionary(input.nextLine());

			String line;

			_players.clear();
			while ((line = input.nextLine()).contains(",")) {
				// if line is player information
				String[] parts = line.split(",");
				String name = parts[0];
				String rgb = parts[1];
				String score = parts[2];
				String rackString = parts[3];
				Player player = new Player(_inventory, _board);
				player.setName(name);
				player.setColor(new Color(Integer.parseInt(rgb)));
				player.setScore(Integer.parseInt(score));
				player.getTileRack().setTiles(rackString);
				_players.add(player);
			}

			// read current player index
			_currentPlayer = Integer.parseInt(line);

			// read inventory
			String inventoryString = input.nextLine();
			_inventory.setTiles(inventoryString);

			// read board
			for (int x = 0; x < 20; x++) {
				line = input.nextLine();
				int index = 0;
				int y = 0;
				while (index < line.length()) {
					if (line.charAt(index++) == '-') {
						_board.setTile(x, y++, new Tile());
					} else {
						int end = line.indexOf(">", index);
						String sub = line.substring(index, end);
						String[] parts = sub.split(",");
						String letter = parts[0];
						String name = parts[1];
						Tile tile = new Tile(letter.charAt(0));
						Player player = null;
						for (int i = 0; i < _playerCount; i++) {
							if (_players.get(i).getName().equals(name)) {
								player = _players.get(i);
								break;
							}
						}
						tile.setPlayer(player);
						tile.setColor(tile.getPlayer().getColor());
						_board.setTile(x, y++, tile);
						index = end + 1;
					}
				}
			}

			input.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Reads high scores from a file and returns the priority queue of players
	 */
	public PriorityQueue<Player> readRecordFile() {
		PriorityQueue<Player> players = new PriorityQueue<Player>();
		try {
			Scanner in = new Scanner(new File(recordFilename));
			while(in.hasNextLine()) {
				String line = in.nextLine();
				String[] parts = line.split(",");
				Player player = new Player(null, null);
				player.setName(parts[0]);
				player.setScore(Integer.parseInt(parts[1]));
				players.add(player);
			}
			in.close();
		} catch (FileNotFoundException e) {
		}
		return players;
	}
	
	/**
	 * Save high scores into a file
	 */
	public void saveRecords(PriorityQueue<Player> players) {
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(recordFilename));
			int index = 0;
			for(Player player : players) {
				writer.write(player.getName() + "," + player.getScore());
				writer.newLine();
				index++;
				// only save top 20 scores
				if(index >= 20) {
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
