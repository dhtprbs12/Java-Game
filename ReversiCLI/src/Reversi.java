/**
 * This class is a main class that invokes MVC pattern.
 * 
 * @author Sekyun Oh
 *
 */
public class Reversi {
	
	/** 
	 * main(args: String[])
	 * 
	 * This is a main method where a game is invoked.
	 * Basically, it initialized the MVC and interacts with the view.
	 * 
	 * @param args 
	 */
	public static void main(String [] args) {
		
		ReversiModel model = new ReversiModel();
		ReversiController controller = new ReversiController(model);
		ReversiView view = new ReversiView(controller);
		view.welcome();
		
		
		// While the game is being played, interacts with the view.
		do {
			
			try {
				view.getInput();
			} catch (Exception e) {
				System.out.println(e.getMessage());
			} 
			
		}while(!view.isGameOver() && !view.isCornerCase());
		
		// When the game is over
		if(view.winner().equals("Tie")) {
			System.out.println("The game is over and was tie!");
		}else if(view.winner().equals("Human")){
			System.out.println("You win!");
		}else {
			System.out.println("Computer win!");
		}
		
	}

}
