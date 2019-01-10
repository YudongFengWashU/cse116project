package code.model;

import java.awt.Color;


public class Tile
{
	private int _pointValue;
	private char _letter;
	private int _x;
	private int _y;
	private Color _color;
	private Player _player;
	
	/**
	 * Default constructor for a Tile. A default tile is blank and has no point value.
	 */
	public Tile()
	{
		_pointValue = 0;
		_letter = ' ';
		_x = -1;
		_y = -1;
	}
	
	/**
	 * Constructor for a tile to be used in game play. A point value is determined by the
	 * letter provided.
	 * @param let case-insensitive character to be placed on the tile
	 */
	public Tile(char let)
	{
		assignValue(let);
	}//Call method to set correct value to letter
	
	/**
	 * Constructor for a tile to be used in game play. 
	 * @param let let case-insensitive character to be placed on the tile
	 * @param color the color to be displayed
	 */
	public Tile(char let, Color color) {
		this(let);
		this._color = color;
	}
	
//accessor methods
	
	/**
	 * 
	 * @return the point value of the tile
	 */
	public int getValue()
	{
		return _pointValue;
	}
	
	/**
	 * 
	 * @return the color of the tile
	 */
	public Color getColor() 
	{
		return _color;
	}
	
	/**
	 * 
	 * @return the letter on the tile
	 */
	public char getLetter()
	{
		return _letter;
	}
	
	/**
	 * If the tile has been placed, this returns the x location on the board (0 indexed)
	 * @return x location on the board; -1 if tile is not placed
	 */
	public int getX()
	{
		return _x;
	}
	
	/**
	 * If the tile has been placed, this returns the y location on the board (0 indexed)
	 * @return y location on the board; -1 if tile is not placed
	 */
	public int getY()
	{
		return _y;
	}
	
	/**
	 * Mutator for the location of the tile on the board
	 * @param x x location for the tile to be placed (0 indexed)
	 * @param y y location for the tile to be placed (0 indexed)
	 */
	public void setLocation(int x, int y)
	{
		_x = x;
		_y = y;
	}
	
	/**
	 * Mutator for the color of the tile to be displayed
	 * @param color
	 */
	public void setColor(Color color) {
		_color = color;
	}
	
	/**
	 * Mutator for the player of the tile.
	 * @param player
	 */
	public void setPlayer(Player player) {
		_player = player;
	}
	
	/**
	 * Mutator for the x location of the tile on the board
	 * @param x x location for the tile to be placed (0 indexed)
	 */
	public void setXLocation(int x)
	{
		setLocation(x, _y);
	}
	
	/**
	 * Mutator for the y location of the tile on the board
	 * @param y y location for the tile to be placed (0 indexed)
	 */
	public void setYLocation(int y)
	{
		setLocation(_x, y);
	}

	public Player getPlayer() {
		return _player;
	}
	
	//Changed this to private. We haven't really used private methods in class,
	//so confirm with Alphonce, but nothing outside of the tile should
	//be able to change its value.
	/**
	 * Assign correct point value to corresponding letter
	 * @param let letter associated with the tile
	 */
	private void assignValue(char let){
		_letter = Character.toUpperCase(let);
		
		
		if(_letter >= 'A' && _letter <= 'Z') {
		
			switch(_letter)
			{
			case 'A':
			case 'E':
			case 'I':
			case 'O':
			case 'U':
				this._pointValue = 1;
				break;
			case 'Y':
				this._pointValue = 2;
				break;
			default:
				this._pointValue = 5;
				break;
			}
		}
		else {
			_pointValue = 0;
		}
	}
	
	@Override
	public String toString() {
		if(getLetter() == ' ')
			return "-";
		else
			return "<" + getLetter() + "," + _player.getName() + ">";
	}
}
