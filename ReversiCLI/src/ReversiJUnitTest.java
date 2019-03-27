import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

/**
 * This class collects all of the test methods for our controller and model.
 * 
 * @author Sekyun Oh
 *
 */

public class ReversiJUnitTest {

	@Test
	/**
	 * controllerTest()
	 * 
	 * This function basically covers all the code in controller and model. 
	 */
	void controllerTest() {
		ReversiModel model = new ReversiModel();
		ReversiController controller = new ReversiController(model);

		// check if the game is over
		controller.isGameOver();
		controller.winner(); 

		// vertically-top
		controller.humanTurn(5, model.getCharInt('d'));
		controller.currentStatus();

		// check if the game is over
		controller.isGameOver();
		controller.winner();

		controller.computerTurn();
		controller.currentStatus();

		// horizontally-left
		controller.humanTurn(3, model.getCharInt('f'));
		controller.currentStatus();

		controller.computerTurn();
		controller.currentStatus();

		// horizontally-right
		controller.humanTurn(4, model.getCharInt('c'));
		controller.currentStatus();

		controller.computerTurn();
		controller.currentStatus();

		// vertically-top
		controller.humanTurn(6, model.getCharInt('e'));
		controller.currentStatus();

		controller.computerTurn();
		controller.currentStatus();

		// vertically-bottom
		controller.humanTurn(1, model.getCharInt('e'));
		controller.currentStatus();

		controller.computerTurn();
		controller.currentStatus();

		// top-down-left-right
		controller.humanTurn(6, model.getCharInt('b'));
		controller.currentStatus();

		controller.computerTurn();
		controller.currentStatus();

		// top-down-right-left
		controller.humanTurn(3, model.getCharInt('b'));
		controller.currentStatus();

		controller.computerTurn();
		controller.currentStatus();

		// down-top-left-right
		controller.humanTurn(5, model.getCharInt('b'));
		controller.currentStatus();

		controller.computerTurn();
		controller.currentStatus();

		// down-top-left-right
		controller.humanTurn(6, model.getCharInt('c'));
		controller.currentStatus();

		controller.computerTurn();
		controller.currentStatus();
		// down-top-left-right
		controller.humanTurn(2, model.getCharInt('f'));
		controller.currentStatus();
		// down-top-left-right
		controller.humanTurn(1, model.getCharInt('f'));
		controller.currentStatus();
		// random
		controller.computerTurn();
		controller.currentStatus();
		// random
		controller.computerTurn();
		controller.currentStatus();
		// random
		controller.computerTurn();
		controller.currentStatus();
		// random
		controller.computerTurn();
		controller.currentStatus();
		// down-top-left-to-right
		controller.humanTurn(7, 2);

		// check if the game is over
		controller.isGameOver();
		controller.winner();

		// length exception
		assertThrows(Exception.class, () -> {
			controller.parseInput("a11");
		});

		// isLetter exception
		assertThrows(Exception.class, () -> {
			controller.parseInput("11");
		});

		// invalid column exception
		assertThrows(Exception.class, () -> {
			controller.parseInput("i3"); 
		});

		// isDigit exception
		assertThrows(Exception.class, () -> {
			controller.parseInput("aa");
		});

		// invalid row exception
		assertThrows(Exception.class, () -> {
			controller.parseInput("a9");
		});

		// no exception and already occupied exception
		assertThrows(Exception.class, () -> {
			controller.parseInput("d3");
			controller.parseInput("d5");
		});

	}

}
