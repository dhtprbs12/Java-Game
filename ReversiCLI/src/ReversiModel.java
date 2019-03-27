import java.util.Hashtable;
/**
 * This class is a model class that contains actual data used in the game.
 * 
 * @author Sekyun Oh
 *
 */
public class ReversiModel {
	
	public static int BOARD_DIMENSION = 9;
	private String [][] board;
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
		for(int i = 0;i < BOARD_DIMENSION; i++) {
			for(int j = 1;j < BOARD_DIMENSION; j++) {
				board[i][j] = " _ ";
			}
		}
		
		//row
		board[0][0] = "1";
		board[1][0] = "2";
		board[2][0] = "3";
		board[3][0] = "4";
		board[4][0] = "5";
		board[5][0] = "6";
		board[6][0] = "7";
		board[7][0] = "8";
		board[8][0] = " ";
		
		//col
		board[8][1] = " a ";
		board[8][2] = " b ";
		board[8][3] = " c ";
		board[8][4] = " d ";
		board[8][5] = " e ";
		board[8][6] = " f ";
		board[8][7] = " g ";
		board[8][8] = " h ";
		
	}
	
	/**
	 * initiateSet()
	 * 
	 * This function initializes hash map.
	 */
	private void initiateSet() {
		//setCharToInt
		setCharToInt.put('a', 1);
		setCharToInt.put('b', 2);
		setCharToInt.put('c', 3);
		setCharToInt.put('d', 4);
		setCharToInt.put('e', 5);
		setCharToInt.put('f', 6);
		setCharToInt.put('g', 7);
		setCharToInt.put('h', 8);
		//setIntToChar
		setIntToChar.put(1, 'a');
		setIntToChar.put(2, 'b');
		setIntToChar.put(3, 'c');
		setIntToChar.put(4, 'd');
		setIntToChar.put(5, 'e');
		setIntToChar.put(6, 'f');
		setIntToChar.put(7, 'g');
		setIntToChar.put(8, 'h');
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
