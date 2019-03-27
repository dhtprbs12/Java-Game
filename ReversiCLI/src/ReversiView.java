import java.util.Scanner;
/**
 * This class is a view that interacts with a controller.
 * 
 * @author Sekyun Oh
 *
 */
public class ReversiView {
	
	private ReversiController controller;
	private Scanner input;
	private int [] parsedInput;
	private boolean isHumanMoveLegal;
	private boolean isComputerMoveLegal;
	private boolean isCornerCase;
	
	/** 
	 * ReversiView(controller: ReversiController)
	 * 
	 * This is a param-constructor to initialize the controller and a scanner.
	 * 
	 * @param controller the object that interacts with view and model. 
	 */
	public ReversiView(ReversiController controller) {
		this.controller = controller;
		input = new Scanner(System.in);
		isHumanMoveLegal = false;
		isComputerMoveLegal = false;
		isCornerCase = false;
	}
	
	/**
	 * welcome()
	 * 
	 * This function prints a welcome sentence.
	 */
	public void welcome() {
		System.out.println("Welcome to Reversi.\n");
		System.out.println("You are W.");
		controller.currentStatus();
	}
	 
	/**
	 * getInput()
	 * 
	 * This function receives a input from a user.
	 * After the user places, the computer places automatically.
	 * 
	 * @throws Exception that's occurred by invalid input.
	 */
	public void getInput() throws Exception {
		
		System.out.print("Where would you like to place your token? ");
		String userInput = input.next();
		
		// parseInput(input:String) may throw exception.
		parsedInput = controller.parseInput(userInput);
		
		// since row internally takes - 1 		
		isHumanMoveLegal = controller.humanTurn(parsedInput[1], parsedInput[0]);
		
		// if there is a legal move for human, computer turns
		if(isHumanMoveLegal) {
			// print status after human places
			controller.currentStatus();
			
			isComputerMoveLegal = controller.computerTurn();
		
			// if there is no legal move for computer, human turns
			if(!isComputerMoveLegal) {
				System.out.println("There is no legal move for the computer. It is your turn!\n");
			}else {
				// if there is legal move for computer, show status
				System.out.println("The computer places a piece at " + controller.computerPlaceAt);
				controller.currentStatus();
			}
			
		}else {
			// if there is no legal move for human, computer turns
			System.out.println("There is no legal move for you. It is computer's turn!\n");
			
			// corner case, if both human and computer don't have a legal move, game is over.
			isComputerMoveLegal = controller.computerTurn();
			if(!isComputerMoveLegal) {
				isCornerCase = true;
			}
			else {
				while(!isGameOver()) { 
					
					isComputerMoveLegal = controller.computerTurn();
					if(!isComputerMoveLegal) {
						System.out.println("There is no legal move for the computer. It is your turn!\n");
						break;
					}else {
						// if there is legal move for computer, show status
						System.out.println("The computer places a piece at " + controller.computerPlaceAt);
						controller.currentStatus();
					}
				}
			}
		}
	}
	
	
	/**
	 * isCornerCase()
	 * 
	 * This function checks if there are no legal moves for both human and computer. 
	 * 
	 * @return a boolean to check if there are no legal moves for both human and computer.
	 */
	public boolean isCornerCase() {
		return isCornerCase;
	}
	
	/**
	 * isGameOver()
	 * 
	 * This function checks if the game is over or not.
	 * 
	 * @return a boolean value that determines the game is over.
	 */
	public boolean isGameOver() {
		return controller.isGameOver();
	}
	
	/**
	 * winner()
	 * 
	 * This function finds a winner of the game, but it can be a tie.
	 * 
	 * @return a String value that would be the winner or tie.
	 */
	public String winner() {
		return controller.winner();
	}
	
}
