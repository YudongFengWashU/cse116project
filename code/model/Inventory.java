package code.model;

import java.util.ArrayList;
import java.util.Collections;

/**
 * An inventory holds 400 predetermined tiles. Upon creation, the tiles are shuffled.
 * It is possible to draw from the inventory until tiles run out, but it is not possible
 * to return a tile to the inventory. There should only be one Inventory
 * @author t020
 *
 */

//If we're allowed, we should be using a static class or singleton pattern for things
//like the Inventory and Board that there are always only one of. It's not too important
//at the moment, though.

public class Inventory {
	
	private ArrayList<Tile> _tiles;
	
	/**
	 * Default constructor for an Inventory. Creates 400 tiles and shuffles them.
	 */
	public Inventory()
	{
		_tiles = new ArrayList<Tile>();
		
		for(int i = 'A'; i <= 'Z'; i++)
		{
			switch(i)
			{
			case 'A':
			case 'E':
			case 'I':
			case 'O':
			case 'U':
				for(int j = 0; j < 29; j++)
				{
					_tiles.add(new Tile((char) i));
				}
				break;
			case 'Y':
				for(int j = 0; j < 15; j++)
				{
					_tiles.add(new Tile((char) i));
				}
				break;
			default:
				for(int j = 0; j < 12; j++)
				{
					_tiles.add(new Tile((char) i));
				}
				break;
			}

		}
		
		this.shuffle();
	}
	
	/**
	 * Size of the inventory
	 * @return number of tiles remaining
	 */
	public int size()
	{
		return _tiles.size();
	}
	
	/**
	 * Grabs a tile from the bag.
	 * @return a tile, if any remain; null is returned otherwise
	 */
	public Tile getTile()
	{
		if(this.hasTiles())
			return _tiles.remove(0);
		else
			return null;
	}
	
	/**
	 * Shuffles the tiles in the Inventory
	 */
	public void shuffle()
	{
		Collections.shuffle(_tiles);
	}
	
	/**
	 * Checks to see if any tiles remain in the Inventory
	 * @return true if any tiles remain
	 */
	public boolean hasTiles()
	{
		return _tiles.size() != 0;
	}
	
	/**
	 * Resets the inventory from the given string
	 * @param tileString
	 */
	public void setTiles(String tileString) {
		_tiles.clear();
		for(int i = 0; i < tileString.length(); i++) {
			Tile tile = new Tile(tileString.charAt(i));
			_tiles.add(tile);
		}
	}

	@Override
	public String toString() {
		String str = "";
		for(Tile t : _tiles)
			str += t.getLetter();
		return str;
	}
}
