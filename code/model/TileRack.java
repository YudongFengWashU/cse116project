package code.model;

import java.awt.Color;
import java.util.ArrayList;

/**
 * A TileRack belongs to a player, and keeps track of up to 12 tiles.
 * @author t020
 *
 */
public class TileRack 
{
	private ArrayList<Tile> _rack;
	private Inventory _inventory;
	private Color _color;
	private Player _player;
	
	/**
	 * Constructor for a TileRack
	 * @param inventory The inventory the tile rack will draw from
	 */
	public TileRack(Inventory inventory)
	{
		_rack = new ArrayList<Tile>();
		_inventory = inventory;
	}

	/**
	 * Removes a specified tile from the rack.
	 * @param t the tile to be taken from the rack
	 **/
	public Tile takeTileFromRack(Tile t)
	{
		if(_rack.contains(t))
			{
				for(int i = 0; i < _rack.size(); i++)
				{
					if(t == null)
					{
						if(_rack.get(i) == null)
						{
							return _rack.remove(i);
						}
					}
					else if(_rack.get(i).equals(t))
					{
						return _rack.remove(i);
					}
				}
			}
		
		return null;
	}
	
	/**
	 * Places a tile back in the rack.
	 * @param t the tile to be returned to the rack
	 */
	public void returnTileToRack(Tile t)
	{
		_rack.add(t);
	}
	

	/**
	 * Adds a tile from the inventory to the rack.
	 */
	public void drawTile()
	{
		Tile tile = _inventory.getTile();
		if(_color != null)
			tile.setColor(_color);
		if(_player != null)
			tile.setPlayer(_player);
		_rack.add(tile);
	}
	
	/**
	 * Accessor for the number of tiles in the rack. 
	 * @return the current number of tiles in the tile rack.
	 */
	public int size()
	{
		return _rack.size();
	}
	
	/**
	 * Sets the tiles color
	 * @param c
	 */
	public void setColor(Color c) {
		this._color = c;
		for(Tile tile : _rack)
			tile.setColor(c);
	}
	
	/**
	 * Sets the player
	 */
	public void setPlayer(Player player) {
		_player = player;
	}
	
	/**
	 * Resets rack from the given string
	 * @param tileString
	 */
	public void setTiles(String tileString) {
		_rack.clear();
		for(int i = 0; i < tileString.length(); i++) {
			char letter = tileString.charAt(i);
			Tile tile = new Tile(letter, _color);
			tile.setPlayer(_player);
			_rack.add(tile);
		}
	}
	
	/**
	 * Returns all tiles in tile rack
	 */
	public ArrayList<Tile> getRack() {
		return _rack;
	}
	
	@Override
	public String toString() {
		String str = "";
		for(Tile t : _rack)
			str += t.getLetter();
		return str;
	}
}
