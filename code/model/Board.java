package code.model;


/**
 * A Board is a 0-indexed 20 x 20 array that holds tiles. Unoccupied spaces are
 * represented by a new Tile with a zero point value.
 * 
 * @author t020
 *
 */
public class Board {

	private Tile[][] _board;

	/**
	 * Default constructor for a Board. Creates an instance of a board and
	 * initializes it with new tiles.
	 */
	public Board() {
		_board = new Tile[20][20];
		wipeBoard();

	}

	/**
	 * Return the tile located in x, y
	 * 
	 * @param x
	 *            x position (0 indexed)
	 * @param y
	 *            y position (0 indexed)
	 * @return the tile if x, y is valid, otherwise null
	 */
	public Tile getTile(int x, int y) {
		if (squareIsValid(x, y))
			return _board[x][y];
		else
			return null;
	}

	/**
	 * Wipes the current board by replacing all locations with new tiles.
	 */
	public void wipeBoard() {
		for (int x = 0; x < 20; x++) {
			for (int y = 0; y < 20; y++) {
				_board[x][y] = new Tile();
			}
		}
	}

	/**
	 * Checks if a tile can be placed in this square.
	 * 
	 * @param x
	 *            x position in the array
	 * @param y
	 *            y position in the array
	 * @return Returns true if the tile is has a point and letter value.
	 */
	public boolean squareIsOccupied(int x, int y) {
		if (squareIsValid(wrapLocation(x), wrapLocation(y))) {
			Tile t = _board[wrapLocation(x)][wrapLocation(y)];
			return (t.getValue() != 0);
		}
		return false;
	}

	/**
	 * Checks if a specified position
	 * 
	 * @param x
	 *            x position (0 indexed)
	 * @param y
	 *            y position (0 indexed)
	 * @return true if the specified position is within the board bounds
	 */
	public boolean squareIsValid(int x, int y) {
		return ((x < 20) && (x >= 0) && (y < 20) && (y >= 0));
	}

	/**
	 * Facilitates tile placing from a Tile Rack
	 * 
	 * @param t
	 *            tile to place
	 * @param x
	 *            x position to place the tile (0-indexed)
	 * @param y
	 *            y position to place the tile (0-indexed)
	 * @return true if placed successfully
	 */
	public boolean placeTile(Tile t, int x, int y) {
		if (!squareIsOccupied(x, y) && squareIsValid(x, y)) {
			t.setLocation(x, y);
			_board[x][y] = t;
			return true;
		}
		return false;
	}

	/**
	 * Facilitates returning a tile to a Tile Rack
	 * 
	 * @param t
	 *            tile to remove
	 * @param x
	 *            x position to place the tile (0-indexed)
	 * @param y
	 *            y position to place the tile (0-indexed)
	 * @return the tile if successfully removed, otherwise null
	 */
	public Tile removeTile(int x, int y) {
		if (squareIsOccupied(x, y)) {
			Tile temp = _board[x][y];
			temp.setLocation(-1, -1);
			_board[x][y] = new Tile();
			return temp;
		}

		return null;
	}

	/**
	 * Checks if a location is adjacent to a tile currently on the board
	 * 
	 * @param x
	 *            x position (0 indexed)
	 * @param y
	 *            y position (0 indexed)
	 * @return true if there is an adjacent tile
	 */
	public boolean intersectsWord(int x, int y) {
		return intersectsHorizontal(x, y) || intersectsVertical(x, y);
	}

	/**
	 * Checks if a location is horizontally adjacent to a tile currently on the
	 * board
	 * 
	 * @param x
	 *            x position (0 indexed)
	 * @param y
	 *            y position (0 indexed)
	 * @return true if there is a horizontally adjacent tile
	 */
	public boolean intersectsHorizontal(int x, int y) {
		return squareIsOccupied(wrapLocation(x + 1), wrapLocation(y)) || squareIsOccupied(wrapLocation(x - 1), wrapLocation(y));

	}

	/**
	 * Checks if a location is vertically adjacent to a tile currently on the
	 * board
	 * 
	 * @param x
	 *            x position (0 indexed)
	 * @param y
	 *            y position (0 indexed)
	 * @return true if there is a vertically adjacent tile
	 */
	public boolean intersectsVertical(int x, int y) {
		return squareIsOccupied(wrapLocation(x), wrapLocation(y + 1)) || squareIsOccupied(wrapLocation(x), wrapLocation(y - 1));

	}

	public void setTile(int x, int y, Tile t) {
		_board[x][y] = t;
	}

	/**
	 * A method that returns the wrapped location on the board. Used in checking
	 * adjacent squares.
	 * 
	 * @param z
	 *            the non-wrapped index
	 * @return result index, after wrapping
	 */
	private int wrapLocation(int z) {
		if (z > 19) {
			while (z > 19)
				z = z - 20;
		} else if (z < 0) {
			while (z < 0)
				z = z + 20;
		}
		return z;
	}

}
