/**
 * This is a controller that manages the game's process.
 * 
 * @author Sekyun Oh
 *
 */
public class ReversiController {

	private ReversiModel model;

	/**
	 * ReversiController(model: ReversiModel)
	 * 
	 * This is a param-constructor that initializes a model(board) for the game.
	 * 
	 * @param model that becomes a main board of the game.
	 */
	public ReversiController(ReversiModel model) {
		this.model = model;
	}

	/**
	 * humanTurn(row: int, col: int)
	 * 
	 * This function inserts a color at a specific place chosen by the user.
	 * 
	 * @param row: the row where you wish to insert "color"
	 * @param col: the col where you wish to insert "color"
	 * @return a boolean if there is a legal move for human.
	 */
	public boolean humanTurn(int row, int col) {

		// if already occupied
		if (!this.model.getAtLocation(row, col).equals(" _ ")) {
			return false;
		}

		String color = " W ";
		boolean isLegalMove = false;
		int i = 0;

		while (i <= 7) {
			isLegalMove = isLegalMove(row, col, i, color);

			if (isLegalMove) {
				fill(row, col, color);
				break;
			}
			i++;
		}
		return isLegalMove;
	}

	/**
	 * computerTurn()
	 * 
	 * This function inserts a color at a specific place chosen by the computer.
	 * 
	 * @return a String value that indicates a spot where the computer places
	 * @throws Exception that's occurred by invalid input.
	 * @return a boolean if there is a legal move for computer.
	 */
	public boolean computerTurn() {

		int[] temp = findBestMove();
		int row = temp[0];
		int col = temp[1];

		String color = " B ";
		boolean isLegalMove = false;
		int i = 0;

		while (i <= 7) {
			isLegalMove = isLegalMove(row, col, i, color);
			if (isLegalMove) {
				fill(row, col, color);
				break;
			}
			i++;
		}
		return isLegalMove;
	}

	/**
	 * fill(row: int, col: int, color: String)
	 * 
	 * This function is a placing of color actually happened.
	 * 
	 * @param row:   the row where either user/computer wish to insert "color"
	 * @param col:   the col where either user/computer wish to insert "color"
	 * @param color: the color either user/computer want to insert.
	 */
	private void fill(int row, int col, String color) {

		// if row and col are bounded to insert, do below.
		model.placeColor(row, col, color);
		fillHorizontally(row, col, color);
		fillVertically(row, col, color);
		fillToptoDownDiagonal(row, col, color);
		fillDowntoTopDiagonal(row, col, color);

	}

	/**
	 * fillHorizontally(row: int, col: int, color: String)
	 * 
	 * This function checks a legal move horizontally and inserts a color at a
	 * specific place.
	 * 
	 * @param row:   the row where either user/computer wish to insert "color"
	 * @param col:   the col where either user/computer wish to insert "color"
	 * @param color: the color either user/computer want to insert.
	 */
	private void fillHorizontally(int row, int col, String color) {

		// left
		if (isLegalMove(row, col, 0, color)) {
			for (int i = col - 1; i >= 0; i--) {
				if (!(model.getAtLocation(row, i).equals(" _ ")) && !(model.getAtLocation(row, i).equals(color))) {
					model.placeColor(row, i, color);
				} else {
					break;
				}
			}
		}

		// right
		if (isLegalMove(row, col, 1, color)) {
			for (int i = col + 1; i <= 7; i++) {
				if (!(model.getAtLocation(row, i).equals(" _ ")) && !(model.getAtLocation(row, i).equals(color))) {
					model.placeColor(row, i, color);
				} else {
					break;
				}
			}
		}
	}

	/**
	 * fillVertically(row: int, col: int, color: String)
	 * 
	 * This function checks a legal move vertically and inserts a color at a
	 * specific place.
	 * 
	 * @param row:   the row where either user/computer wish to insert "color"
	 * @param col:   the col where either user/computer wish to insert "color"
	 * @param color: the color either user/computer want to insert.
	 */
	private void fillVertically(int row, int col, String color) {

		// top
		if (isLegalMove(row, col, 2, color)) {
			for (int i = row - 1; i >= 0; i--) {
				if (!(model.getAtLocation(i, col).equals(" _ ")) && !(model.getAtLocation(i, col).equals(color))) {
					model.placeColor(i, col, color);
				} else {
					break;
				}
			}
		}

		// bottom
		if (isLegalMove(row, col, 3, color)) {
			for (int i = row + 1; i <= 7; i++) {
				if (!(model.getAtLocation(i, col).equals(" _ ")) && !(model.getAtLocation(i, col).equals(color))) {
					model.placeColor(i, col, color);
				} else {
					break;
				}
			}
		}
	}

	/**
	 * fillToptoDownDiagonal(row: int, col: int, color: String)
	 * 
	 * This function checks a legal move top-down-diagonally (\,/) and inserts a
	 * color at a specific place.
	 * 
	 * @param row:   the row where either user/computer wish to insert "color"
	 * @param col:   the col where either user/computer wish to insert "color"
	 * @param color: the color either user/computer want to insert.
	 */
	private void fillToptoDownDiagonal(int row, int col, String color) {

		// left to right
		if (isLegalMove(row, col, 4, color)) {
			int i = row + 1;
			int j = col + 1;

			while ((i <= 7) && (j <= 7)) {
				if (!(model.getAtLocation(i, j).equals(" _ ")) && !(model.getAtLocation(i, j).equals(color))) {
					model.placeColor(i, j, color);
				} else {
					break;
				}

				i++;
				j++;
			}
		}

		// right to left
		if (isLegalMove(row, col, 5, color)) {
			int i = row + 1;
			int j = col - 1;

			while ((i <= 7) && (j >= 0)) {

				if (!(model.getAtLocation(i, j).equals(" _ ")) && !(model.getAtLocation(i, j).equals(color))) {
					model.placeColor(i, j, color);
				} else {
					break;
				}

				i++;
				j--;
			}
		}
	}

	/**
	 * fillDowntoTopDiagonal(row: int, col: int, color: String)
	 * 
	 * This function checks a legal move down-to-diagonally (\,/) and inserts a
	 * color at a specific place.
	 * 
	 * @param row:   the row where either user/computer wish to insert "color"
	 * @param col:   the col where either user/computer wish to insert "color"
	 * @param color: the color either user/computer want to insert.
	 */
	private void fillDowntoTopDiagonal(int row, int col, String color) {

		// left to right
		if (isLegalMove(row, col, 6, color)) {
			int i = row - 1;
			int j = col + 1;

			while ((i >= 0) && (j <= 7)) {

				if (!(model.getAtLocation(i, j).equals(" _ ")) && !(model.getAtLocation(i, j).equals(color))) {
					model.placeColor(i, j, color);
				} else {
					break;
				}

				i--;
				j++;
			}
		}

		// right to left
		if (isLegalMove(row, col, 7, color)) {
			int i = row - 1;
			int j = col - 1;

			while ((i >= 0) && (j >= 0)) {

				if (!(model.getAtLocation(i, j).equals(" _ ")) && !(model.getAtLocation(i, j).equals(color))) {
					model.placeColor(i, j, color);
				} else {
					break;
				}

				i--;
				j--;
			}
		}
	}

	/**
	 * isLegalMove(row: int, col: int, type: int, color: String)
	 * 
	 * check if there is same color in a line: horizontally, vertically, diagonally
	 * 
	 * @param row:   the row where either user/computer wish to insert "color"
	 * @param col:   the col where either user/computer wish to insert "color"
	 * @param type:  the type of direction
	 * @param color: the color either user/computer want to insert.
	 * @return boolean if there is a legal move.
	 */
	private boolean isLegalMove(int row, int col, int type, String color) {

		boolean isLegalMove = false;

		switch (type) {

		case 0:
			// horizon left
			if (col > 0 && !model.getAtLocation(row, col - 1).equals(color)
					&& !model.getAtLocation(row, col - 1).equals(" _ ")) {
				for (int i = col - 1; i >= 0; i--) {

					if (model.getAtLocation(row, i).equals(color)) {
						// check if there is a blank
						for (int k = col - 1; k >= i; k--) {
							if (model.getAtLocation(row, k).equals(" _ ")) {
								return false;
							}
						}
						isLegalMove = true;
						break;
					}
				}
			}
			break;
		case 1:
			// horizon right
			if (col < 7 && !model.getAtLocation(row, col + 1).equals(color)
					&& !model.getAtLocation(row, col + 1).equals(" _ ")) {
				for (int i = col + 1; i <= 7; i++) {
					if ((model.getAtLocation(row, i).equals(color))) {
						// check if there is a blank
						for (int k = col + 1; k <= i; k++) {
							if (model.getAtLocation(row, k).equals(" _ ")) {
								return false;
							}
						}
						isLegalMove = true;
						break;
					}
				}
			}
			break;
		case 2:
			// vertical top
			if (row > 0 && !model.getAtLocation(row - 1, col).equals(color)
					&& !model.getAtLocation(row - 1, col).equals(" _ ")) {
				for (int i = row - 1; i >= 0; i--) {
					if ((model.getAtLocation(i, col).equals(color))) {
						// check if there is a blank
						for (int k = row - 1; k >= i; k--) {
							if (model.getAtLocation(k, col).equals(" _ ")) {
								return false;
							}
						}
						isLegalMove = true;
						break;
					}
				}
			}
			break;
		case 3:
			// vertical bottom
			if (row < 7 && !model.getAtLocation(row + 1, col).equals(color)
					&& !model.getAtLocation(row + 1, col).equals(" _ ")) {
				for (int i = row + 1; i <= 7; i++) {
					if ((model.getAtLocation(i, col).equals(color))) {
						// check if there is a blank
						for (int k = row + 1; k <= i; k++) {
							if (model.getAtLocation(k, col).equals(" _ ")) {
								return false;
							}
						}
						isLegalMove = true;
						break;
					}
				}
			}
			break;
		case 4:
			// top-down-diagonal: left-to-right(\)
			if ((row < 7 && col < 7) && !model.getAtLocation(row + 1, col + 1).equals(color)
					&& !model.getAtLocation(row + 1, col + 1).equals(" _ ")) {
				int i = row + 1;
				int j = col + 1;
				int temp1 = i;
				int temp2 = j;

				while (i <= 7 && j <= 7) {
					if ((model.getAtLocation(i, j).equals(color))) {
						// check if there is a blank
						while (temp1 <= i && temp2 <= j) {
							if (model.getAtLocation(i, j).equals(" _ ")) {
								return false;
							}
							temp1++;
							temp2++;
						}

						isLegalMove = true;
						break;
					}
					i++;
					j++;
				}
			}
			break;
		case 5:
			// top-down-diagonal: right-to-left(/)
			if ((row < 7 && col > 0) && !model.getAtLocation(row + 1, col - 1).equals(color)
					&& !model.getAtLocation(row + 1, col - 1).equals(" _ ")) {
				int i = row + 1;
				int j = col - 1;
				int temp1 = i;
				int temp2 = j;

				while (i <= 7 && j >= 0) {
					if ((model.getAtLocation(i, j).equals(color))) {
						// check if there is a blank
						while (temp1 <= i && temp2 >= j) {
							if (model.getAtLocation(i, j).equals(" _ ")) {
								return false;
							}
							temp1++;
							temp2--;
						}
						isLegalMove = true;
						break;
					}
					i++;
					j--;
				}
			}
			break;
		case 6:
			// down-top-diagonal: left-to-right(/)
			if ((row > 0 && col < 7) && !model.getAtLocation(row - 1, col + 1).equals(color)
					&& !model.getAtLocation(row - 1, col + 1).equals(" _ ")) {
				int i = row - 1;
				int j = col + 1;
				int temp1 = i;
				int temp2 = j;

				while (i >= 0 && j <= 7) {
					if ((model.getAtLocation(i, j).equals(color))) {
						// check if there is a blank
						while (temp1 >= i && temp2 <= j) {
							if (model.getAtLocation(i, j).equals(" _ ")) {
								return false;
							}
							temp1--;
							temp2++;
						}
						isLegalMove = true;
						break;
					}
					i--;
					j++;
				}
			}
			break;
		default:
			// down-top-diagonal: right-to-left(\)
			if ((row > 0 && col > 0) && !model.getAtLocation(row - 1, col - 1).equals(color)
					&& !model.getAtLocation(row - 1, col - 1).equals(" _ ")) {
				int i = row - 1;
				int j = col - 1;
				int temp1 = i;
				int temp2 = j;

				while (i >= 0 && j >= 0) {
					if ((model.getAtLocation(i, j).equals(color))) {
						// check if there is a blank
						while (temp1 >= i && temp2 >= j) {
							if (model.getAtLocation(i, j).equals(" _ ")) {
								return false;
							}
							temp1--;
							temp2--;
						}
						isLegalMove = true;
						break;
					}
					i--;
					j--;
				}
			}
			break;
		}

		return isLegalMove;
	}

	/**
	 * isGameOver()
	 * 
	 * This function checks if the game is over or not.
	 * 
	 * @return a boolean value that determines the game is over.
	 */
	public boolean isGameOver() {

		boolean isGameOver = true;

		for (int i = 0; i <= 7; i++) {
			for (int j = 0; j <= 7; j++) {
				if (model.getAtLocation(i, j).equals(" _ ")) {
					isGameOver = false;
					return isGameOver;
				}
			}
		}

		return isGameOver;
	}

	// check if it is a right place to put
	public boolean hasAvailablePlace(String color) {

		boolean isLegalMove = false;
		int type = 0;

		for (int i = 0; i <= 7; i++) {
			for (int j = 0; j <= 7; j++) {
				if (model.getAtLocation(i, j).equals(" _ ")) {
					while (type <= 7) {
						isLegalMove = isLegalMove(i, j, type, color);
						if (isLegalMove) {
							return isLegalMove;
						}
						type++;
					}
					type = 0;
				}
			}
		}

		return isLegalMove;

	}

	/**
	 * winner()
	 * 
	 * This function finds a winner of the game, but it can be a tie.
	 * 
	 * @return a String value that would be the winner or tie.
	 */
	public String winner() {
		int[] scores = getScore();
		if (scores[0] == scores[1]) {
			return "Tie";
		}
		return (scores[0] < scores[1]) ? "Computer" : "You";
	}

	/**
	 * findBestMove()
	 * 
	 * This function finds the best place where maximizes the score for the
	 * computer.
	 * 
	 * @return int[] that contains row and col of the best place.
	 */
	private int[] findBestMove() {

		int[] result = new int[2];
		int bestMove = 0;

		for (int i = 0; i <= 7; i++) {
			for (int j = 0; j <= 7; j++) {
				if (model.getAtLocation(i, j).equals(" B ")) {

					int[] horizon = horizontallyBestMove(i, j);
					int[] vertical = verticallyBestMove(i, j);
					int[] topToBottomDiag = topToDownBestMove(i, j);
					int[] downToTopDiag = downToTopBestMove(i, j);

					if (bestMove <= horizon[2]) {
						result[0] = horizon[0];
						result[1] = horizon[1];
						bestMove = horizon[2];
					}

					if (bestMove <= vertical[2]) {
						result[0] = vertical[0];
						result[1] = vertical[1];
						bestMove = vertical[2];
					}

					if (bestMove <= topToBottomDiag[2]) {
						result[0] = topToBottomDiag[0];
						result[1] = topToBottomDiag[1];
						bestMove = topToBottomDiag[2];
					}

					if (bestMove <= downToTopDiag[2]) {
						result[0] = downToTopDiag[0];
						result[1] = downToTopDiag[1];
						bestMove = downToTopDiag[2];
					}
				}
			}
		}

		return result;

	}

	/**
	 * horizontallyBestMove(row: int, col: int)
	 * 
	 * This function finds the best place horizontally where maximizes the score for
	 * the computer.
	 * 
	 * @param row: the row where either user/computer wish to insert "color"
	 * @param col: the col where either user/computer wish to insert "color"
	 * @return int[] that contains row and col of the best place horizontally.
	 */
	private int[] horizontallyBestMove(int row, int col) {

		int temp1 = 0;
		int temp2 = 0;
		int[] result = new int[3];
		int i = 0;

		// left
		// execute only column is greater than 1
		if (col > 0) {
			i = col - 1;
			while (i > 1 && model.getAtLocation(row, i).equals(" W ")) {
				temp1++;
				i--;
			}

			if (model.getAtLocation(row, i).equals(" _ ")) {
				result[0] = row;
				result[1] = i;
				result[2] = temp1;
			}
		}

		// right
		// execute only column is less than 8
		if (col < 7) {
			i = col + 1;
			while (i < 7 && model.getAtLocation(row, i).equals(" W ")) {
				temp2++;
				i++;
			}

			if (temp1 <= temp2 && model.getAtLocation(row, i).equals(" _ ")) {
				result[0] = row;
				result[1] = i;
				result[2] = temp2;
			}
		}

		return result;
	}

	/**
	 * verticallyBestMove(row: int, col: int)
	 * 
	 * This function finds the best place vertically where maximizes the score for
	 * the computer.
	 * 
	 * @param row: the row where either user/computer wish to insert "color"
	 * @param col: the col where either user/computer wish to insert "color"
	 * @return int[] that contains row and col of the best place vertically.
	 */
	private int[] verticallyBestMove(int row, int col) {

		int temp1 = 0;
		int temp2 = 0;
		int[] result = new int[3];
		int i = 0;

		// top
		if (row > 0) {
			i = row - 1;
			while (i > 0 && model.getAtLocation(i, col).equals(" W ")) {
				temp1++;
				i--;
			}

			if (model.getAtLocation(i, col).equals(" _ ")) {
				result[0] = i;
				result[1] = col;
				result[2] = temp1;
			}
		}

		// bottom
		if (row < 7) {
			i = row + 1;
			while (i < 7 && model.getAtLocation(i, col).equals(" W ")) {
				temp2++;
				i++;
			}

			if (temp1 <= temp2 && model.getAtLocation(i, col).equals(" _ ")) {
				result[0] = i;
				result[1] = col;
				result[2] = temp2;
			}
		}

		return result;
	}

	/**
	 * topToDownBestMove(row: int, col: int)
	 * 
	 * This function finds the best place top-down-diagonally where maximizes the
	 * score for the computer.
	 * 
	 * @param row: the row where either user/computer wish to insert "color"
	 * @param col: the col where either user/computer wish to insert "color"
	 * @return int[] that contains row and col of the best place vertically.
	 */
	private int[] topToDownBestMove(int row, int col) {

		int temp1 = 0;
		int temp2 = 0;
		int i1 = 0;
		int i2 = 0;
		int[] result = new int[3];

		// left-to-right: \
		if (row < 7 && col < 7) {
			i1 = row + 1;
			i2 = col + 1;

			while ((i1 < 7 && i2 < 7) && model.getAtLocation(i1, i2).equals(" W ")) {
				temp1++;
				i1++;
				i2++;
			}

			if (model.getAtLocation(i1, i2).equals(" _ ")) {
				result[0] = i1;
				result[1] = i2;
				result[2] = temp1;
			}
		}

		// right-to-left: /
		if (row < 7 && col > 0) {
			i1 = row + 1;
			i2 = col - 1;

			while ((i1 < 7 && i2 > 0) && model.getAtLocation(i1, i2).equals(" W ")) {
				temp2++;
				i1++;
				i2--;
			}

			if (temp1 <= temp2 && model.getAtLocation(i1, i2).equals(" _ ")) {
				result[0] = i1;
				result[1] = i2;
				result[2] = temp2;
			}
		}

		return result;
	}

	/**
	 * downToTopBestMove(row: int, col: int)
	 * 
	 * This function finds the best place down-top-diagonally where maximizes the
	 * score for the computer.
	 * 
	 * @param row: the row where either user/computer wish to insert "color"
	 * @param col: the col where either user/computer wish to insert "color"
	 * @return int[] that contains row and col of the best place vertically.
	 */
	private int[] downToTopBestMove(int row, int col) {

		int temp1 = 0;
		int temp2 = 0;
		int i1 = 0;
		int i2 = 0;
		int[] result = new int[3];

		// left-to-right: /
		if (row > 0 && col < 7) {
			i1 = row - 1;
			i2 = col + 1;

			while ((i1 > 0 && i2 < 7) && model.getAtLocation(i1, i2).equals(" W ")) {
				temp1++;
				i1--;
				i2++;
			}

			if (model.getAtLocation(i1, i2).equals(" _ ")) {
				result[0] = i1;
				result[1] = i2;
				result[2] = temp1;
			}
		}

		// right-to-left: \
		if (row > 0 && col > 0) {
			i1 = row - 1;
			i2 = col - 1;
			while ((i1 > 0 && i2 > 0) && model.getAtLocation(i1, i2).equals(" W ")) {
				temp2++;
				i1--;
				i2--;
			}

			if (temp1 <= temp2 && model.getAtLocation(i1, i2).equals(" _ ")) {
				result[0] = i1;
				result[1] = i2;
				result[2] = temp2;
			}
		}

		return result;
	}

	/**
	 * getScore()
	 * 
	 * This function gets each score for the user and computer.
	 * 
	 * @return int[] that contains [user's score, computer's score].
	 */
	public int[] getScore() {
		int humanScore = 0;
		int computerScore = 0;
		int[] result = new int[2];

		for (int i = 0; i <= 7; i++) {
			for (int j = 0; j <= 7; j++) {
				if (model.getAtLocation(i, j).equals(" W ")) {
					humanScore++;
				} else if (model.getAtLocation(i, j).equals(" B ")) {
					computerScore++;
				}
			}
		}
		result[0] = humanScore;
		result[1] = computerScore;
		return result;
	}

}
