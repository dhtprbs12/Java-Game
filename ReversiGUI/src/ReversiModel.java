import java.io.Serializable;
import java.util.Hashtable;
import java.util.Observable;

/**
 * This class is a model class that contains actual data used in the game.
 * 
 * @author Sekyun Oh
 *
 */
public class ReversiModel extends Observable implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static int BOARD_DIMENSION = 8;
	private String[][] board;
	private Hashtable<Character, Integer> setCharToInt;
	private Hashtable<Integer, Character> setIntToChar;

	/**
	 * ReversiModel(): Constructor
	 * 
	 * This is no-args constructor that initializes variables used in the game.
	 */
	public ReversiModel() {
		board = new String[BOARD_DIMENSION][BOARD_DIMENSION];
		setCharToInt = new Hashtable<Character, Integer>();
		setIntToChar = new Hashtable<Integer, Character>();
		drawBoard();
		initiateSet();
	}

	/**
	 * placeColor(int row, int col)
	 * 
	 * This function places a color at a specific position.
	 * 
	 * @param row: the row you wish to insert "color"
	 * @param col: the col you wish to insert "color"
	 */
	public void placeColor(int row, int col, String color) {
		board[row][col] = color;
		int[] arr = new int[3];
		arr[0] = row;
		arr[1] = col;
		arr[2] = (color == " W ") ? 0 : 1;

		setChanged();
		notifyObservers(arr);
	}

	/**
	 * getAtLocation(int row, int col)
	 * 
	 * This function returns a color at a specific position
	 * 
	 * @param row: the row you wish to get "color"
	 * @param col: the row you wish to get "color"
	 * @return the value at row/col
	 */
	public String getAtLocation(int row, int col) {
		return board[row][col];
	}

	/**
	 * drawBoard()
	 * 
	 * This function draws initial board before the game begins
	 */
	private void drawBoard() {
		for (int i = 0; i < BOARD_DIMENSION; i++) {
			for (int j = 0; j < BOARD_DIMENSION; j++) {
				if ((i == 3 && j == 3) || (i == 4 && j == 4)) {
					board[i][j] = " W ";
				} else if ((i == 3 && j == 4) || (i == 4 && j == 3)) {
					board[i][j] = " B ";
				} else {
					board[i][j] = " _ ";
				}
			}
		}
	}

	/**
	 * initiateSet()
	 * 
	 * This function initializes hash map.
	 */
	private void initiateSet() {
		// setCharToInt
		setCharToInt.put('a', 0);
		setCharToInt.put('b', 1);
		setCharToInt.put('c', 2);
		setCharToInt.put('d', 3);
		setCharToInt.put('e', 4);
		setCharToInt.put('f', 5);
		setCharToInt.put('g', 6);
		setCharToInt.put('h', 7);
		// setIntToChar
		setIntToChar.put(0, 'a');
		setIntToChar.put(1, 'b');
		setIntToChar.put(2, 'c');
		setIntToChar.put(3, 'd');
		setIntToChar.put(4, 'e');
		setIntToChar.put(5, 'f');
		setIntToChar.put(6, 'g');
		setIntToChar.put(7, 'h');
	}

	/**
	 * 
	 * @param c: the character maps to number.
	 * @return the int value maps to the character.
	 */
	public int getCharInt(char c) {
		return setCharToInt.get(c);
	}

	/**
	 * 
	 * @param i: the integer maps to character.
	 * @return the character value maps to the integer.
	 */
	public char getIntChar(int i) {
		return setIntToChar.get(i);
	}

}
