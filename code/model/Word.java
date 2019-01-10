package code.model;

import java.util.ArrayList;

/**
 * A Word is a collection of tiles. Words have a point value, direction,
 * and location associated with the board.
 * @author t020
 *
 */
public class Word {

	private ArrayList<Tile> _tiles;
	private Direction _direction;


	/**
	 * 
	 * @return the direction of word on the board
	 */
	private Direction determineDirection()
	{
		if(_tiles.size() < 2)
			return Direction.Invalid;
		
		boolean all = true;
		int x = _tiles.get(0).getX();
		int y = _tiles.get(0).getY();
		for(int i = 1; i < _tiles.size(); i++) {
			Tile tile = _tiles.get(i);
			if(x != tile.getX() || tile.getY() != (y + i) % 20)
			{
				all = false;
				break;
			}
		}
		
		if(all)
			return Direction.Horizontal;

		all = true;
		for(int i = 1; i < _tiles.size(); i++) {
			Tile tile = _tiles.get(i);
			if(y != tile.getY() || tile.getX() != (x + i) % 20)
			{
				all = false;
				break;
			}
		}
		
		if(all)
			return Direction.Vertical;
		
		return Direction.Invalid;
	}
	
	/**
	 * Default constructor for a word. A default word has no tiles
	 * or score.
	 */
	public Word()
	{
		_tiles = new ArrayList<Tile>();
	}
	
	/**
	 * Constructor for a word from an ArrayList of tiles
	 * @param tiles Ordered ArrayList of tiles spelling out a word
	 */
	public Word(ArrayList<Tile> tiles)
	{
		_tiles = tiles;
		this._direction = determineDirection();
	}
	 
	/**
	 * 
	 * @return the score associated with the word
	 */
	public int score()
	{
		int score = 0;
		
		for(Tile t : _tiles)
			score += t.getValue();

		return score;
	}
	
	/**
	 * 
	 * @return the length of the word
	 */
	public int length()
	{
		return _tiles.size();
	}
	
	/**
	 * Returns a string representation of a Word
	 */
	public String toString()
	{
		String wordStr = "";
		for(Tile t : _tiles)
			wordStr += t.getLetter();
		
		return wordStr;
	}
	
	/**
	 * Returns the direction of the word
	 */
	public Direction getDirection() {
		return _direction;
	}
	
	/**
	 * Returns the tiles of this word
	 */
	public ArrayList<Tile> getTiles() {
		return _tiles;
	}
	
	/**
	 * Enum associated with a word's direction.
	 * @author t020
	 *
	 */
	public enum Direction
	{
		Invalid, Horizontal, Vertical
	}
}

