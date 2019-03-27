import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class ReversiView extends Application implements Observer {

	public String args[];
	public final int SCENE_LENGTH = 385;
	public final int SCENE_HEIGHT = 430;
	public final int SIZE = 8;
	public final String savedFileName = "save_game.dat";
	TilePane tilePane;
	FlowPane flow;
	ReversiController controller;
	ReversiModel model;
	Scene scene;

	public void main(String args[]) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		model = loadGame();
		model.addObserver(this);
		controller = new ReversiController(model);

		// set title
		primaryStage.setTitle("Reversi");

		// create Tile Pane that has stack pane
		tilePane = setTilePane();

		// create label for track the score
		Label white = new Label();
		white.setText("White: ");

		Label whiteValue = new Label();
		whiteValue.setText("2");

		Label symbol = new Label(new String(" - "));

		Label black = new Label();
		black.setText("Black: ");

		Label blackValue = new Label();
		blackValue.setText("2");

		flow = new FlowPane(Orientation.HORIZONTAL);
		flow.getChildren().add(white);
		flow.getChildren().add(whiteValue);
		flow.getChildren().add(symbol);
		flow.getChildren().add(black);
		flow.getChildren().add(blackValue);

		// create drop down menu for save and loading game
		Menu fileMenu = new Menu("File");
		MenuItem newGame = new MenuItem("New Game");
		fileMenu.getItems().add(newGame);
		MenuBar menuBar = new MenuBar();
		menuBar.getMenus().addAll(fileMenu);

		// assemble all panes and menu
		BorderPane root = new BorderPane();
		root.setTop(menuBar);
		root.setCenter(tilePane);
		root.setBottom(flow);

		// show
		scene = new Scene(root, SCENE_LENGTH, SCENE_HEIGHT);
		primaryStage.setScene(scene);
		primaryStage.show();
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			public void handle(WindowEvent we) {
				try {
					saveGame();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});

		// create new game
		newGame.setOnAction(e -> {
			// if file exists, delete
			File file = new File(savedFileName);
			if (file.exists()) {
				file.delete();

			} else {
				// do nothing
			}
			// update view here
			model = new ReversiModel();
			model.addObserver(this);
			controller = new ReversiController(model);
			tilePane = setTilePane();
			setScore();
			root.setCenter(tilePane);
			primaryStage.show();
		});

	}

	private TilePane setTilePane() {
		tilePane = new TilePane();

		tilePane.setPrefColumns(SIZE);
		tilePane.setPrefRows(SIZE);
		tilePane.setPadding(new Insets(SIZE));
		tilePane.setBackground(new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY)));

		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {

				// create circle
				Circle circle = new Circle(20.0f);

				if (model.getAtLocation(i, j).equals(" W ")) {
					circle.setFill(Color.WHITE);
				} else if (model.getAtLocation(i, j).equals(" B ")) {
					circle.setFill(Color.BLACK);
				} else {
					circle.setFill(Color.TRANSPARENT);
				}

				// create Stack Pane that has circle
				StackPane stackPane = new StackPane(circle);
				stackPane.setPadding(new Insets(2));
				stackPane.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
	
					@Override
					public void handle(MouseEvent event) {
						
						// get row and col by converting coordinates
						// height of menubar is 30
						int row = convertCoordinate((int) ((event.getSceneY() - 40) / 44));
						int col = convertCoordinate((int) ((event.getSceneX() - 10) / 44));

						// if a place that human clicks is a appropriate place,
						// execute this if statement
						if (controller.humanTurn(row, col)) {
							
							// after human puts in the appropriate place, renew scores
							setScore();
							
							// to state flip, we need timer here.
							// execute computerTurn every 1.5 sec later
							Timer t = new java.util.Timer();
							t.schedule(new java.util.TimerTask() {
								@Override
								public void run() {
									// to prevent thread problem, need to use Platform runLater()
									// works great now

									// after the human puts, check if computer puts in an appropriate place
									if (controller.computerTurn()) {
										
										// if then, renew score
										Platform.runLater(() -> {
											setScore();
										});
										
										// after computer puts in an available place
										// check if there is an available place for human
										// if then, do nothing
										if (controller.hasAvailablePlace(" W ")) {

										}
										// if there is no available place to put for human
										// the game is over
										else {
											Platform.runLater(() -> {
												alert();
											});
										}
									}
									// if there is no available place to put for the computer
									// the game is over
									else {
										Platform.runLater(() -> {
											alert();
										});
									}
									t.cancel();
								}
							}, 1500);
						}
						
						// if there is no available place to put for human
						else {
							
							// if there is still available place to put for human
							// which means that human just clicks inappropriate place.
							if (controller.hasAvailablePlace(" W ")) {

							}
							// if there is still available place to put for computer
							// which means that game is not over and player must keep playing.
							else if (controller.hasAvailablePlace(" B ")) {

							}
							// if both human and computer can't find an available place to put
							// the game is over
							else {
								alert();
							}
						}
						// if board is full, the game is over
						if (controller.isGameOver()) {
							alert();
						}
					}
				});

				stackPane.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY,
						BorderWidths.DEFAULT)));

				// append stackPane to tilePane
				tilePane.getChildren().add(stackPane);
			}
		}
		return tilePane;
	}

	/**
	 * Observer for update view
	 * 
	 * This method update view every time the data in the model change: the chess
	 * has been put,flip,and restart game.
	 * 
	 * @param observable any class extends it so we can observe
	 * @param arg        message from observable class
	 */
	@Override
	public void update(Observable o, Object arg) {
		if (o instanceof Observable) {
			int[] arr = (int[]) arg;
			int row = arr[0];
			int col = arr[1];
			int color = arr[2]; // if 0, white, if 1, black
			changeColor(row, col, color);
		}
	}

	private int convertCoordinate(int coordinate) {
		if (coordinate >= 7) {
			return 7;
		}
		return coordinate;
	}

	private void changeColor(int row, int col, int color) {

		int position = (row * 7) + row + col;
		StackPane stack = (StackPane) tilePane.getChildren().get(position);
		Circle circle = (Circle) stack.getChildren().get(0);
		if (color == 0) {
			circle.setFill(Color.WHITE);
		} else {
			circle.setFill(Color.BLACK);
		}
	}

	private void setScore() {
		int[] scores = controller.getScore();
		Label white = (Label) flow.getChildren().get(1);
		white.setText(String.valueOf(scores[0]));
		Label black = (Label) flow.getChildren().get(4);
		black.setText(String.valueOf(scores[1]));
	}

	private void alert() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Message");
		alert.setHeaderText("Message");
		alert.setContentText(controller.winner() + " Won!");
		alert.showAndWait();
		// if win or lost, delete save_game.dat if exists
		File file = new File(savedFileName);
		if (file.exists()) {
			file.delete();
		} else {
			// do nothing
		}
	}

	/**
	 * This method saved a game in progress
	 * 
	 * This method will write the progress of the game into a new file the progress
	 * of the game will be recored as data in the model. this method will serialize
	 * the model object to the file
	 * 
	 * @throws IOException
	 */
	private void saveGame() throws IOException {
		try {
			FileOutputStream fos = new FileOutputStream(savedFileName);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(model);
			oos.close();
			fos.close();
		} catch (IOException e) {
			System.out.println("IOException has been throwed");
			e.printStackTrace();
		}

	}

	/**
	 * This method load a game or start a new game
	 * 
	 * If there is saved file in the file system, this program load that fill so
	 * users are able to continue from last game. If there is not a file like that,
	 * we start a new game.
	 * 
	 * @return a model is either record chess from last time or a new model if there
	 *         is not saved file
	 * @throws IOException when the file doesn't exist
	 */
	private ReversiModel loadGame() throws IOException {
		ReversiModel model;
		try {
			File file = new File(savedFileName);
			// If this file exist, which means there is a saved game
			// we load into this game
			if (file.exists()) {
				FileInputStream fis = new FileInputStream(file);
				ObjectInputStream ois = new ObjectInputStream(fis);
				model = (ReversiModel) ois.readObject();
				ois.close();
				fis.close();
			} else {
				// Otherwise we start a new game
				model = new ReversiModel();
			}

			return model;
		} catch (IOException e) {
			System.out.println("IOException has been threw");
			e.printStackTrace();
		} catch (ClassNotFoundException ex) {
			System.out.println("Class not found exception has been threw");
		}
		return null;

	}

}
