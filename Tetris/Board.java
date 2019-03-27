
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel implements ActionListener, KeyListener {

	/*
	 * board 맨위가 0이고, 맨아래가 20 왜이렇게 설정되있는줄은 모르겠지만, 실행은됨. 좌우 양 끝에서 윗 키보드버트 누르면 에러뜸
	 * 줄이 다 찾을때 없애는 코드가 너무 김. 줄이 없어질때 얼마나 없어졌는지도 아직 확실치않음 줄이 없어지고 그 위에게 아래로 내려올때
	 * 많이 느림..
	 */
	Color board[][];
	static int width;
	static int height;
	SPolyomino sshape;
	ZPolyomino zshape;
	IPolyomino ishape;
	TPolyomino tshape;
	OPolyomino oshape;
	LPolyomino lshape;
	FPolyomino fshape;
	Random rand = new Random();
	Color color;
	Point base[];
	String name;
	int curX;
	int curY;
	int x;
	int y;
	Timer timer;
	boolean isStarted;
	boolean isFallingFinished;
	JLabel statusbar;
	int count;

	public Board(TetrisTest test) {
		setFocusable(true);
		addKeyListener(this);
		isStarted = true;
		isFallingFinished = false;
		sshape = new SPolyomino("sshape");
		zshape = new ZPolyomino("zshape");
		ishape = new IPolyomino("ishape");
		tshape = new TPolyomino("tshape");
		oshape = new OPolyomino("oshape");
		lshape = new LPolyomino("lshape");
		fshape = new FPolyomino("fshape");
		width = 10;
		height = 20;
		board = new Color[height][width];
		statusbar = test.getStatusBar();
		count = 0;
		clearboard();
		newPiece();
		timer = new Timer(300, this);
		timer.start();

	}

	public void rotateLeft() {// 다시 제대로 돌아가지 않음.

		for (int i = 0; i < 4; i++) {// 갑들이 바뀜.

			base[i].setLocation(base[i].y, -(base[i].x));

		}
	}

	public Polyomino getShape() {

		int r = rand.nextInt(6);
		switch (r) {
		case 0:
			Init(sshape);
			break;
		case 1:
			Init(zshape);
			break;
		case 2:
			Init(ishape);
			break;
		case 3:
			Init(tshape);
			break;
		case 4:
			Init(oshape);
			break;
		case 5:
			Init(lshape);
			break;
		case 6:
			Init(fshape);
			break;
		}

		return null;

	}

	public void Init(Polyomino polyomino) {
		this.color = polyomino.getColor();
		this.base = polyomino.getArray();
		this.name = polyomino.getName();

	}

	public void newPiece() {
		getShape();
		curX = width / 2;
		curY = 2;
		eraseFullLine();
		//timer.start();
		
		if(tryMove(curX, curY) == false){//curX = 5 curY = 2를 tryMove에 대입시키면 y값이 19이상인지 알수 있음. 
			timer.stop();
			statusbar.setText("GAME OVER");
		}

	}

	private void clearboard() {
		// TODO Auto-generated method stub

		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				board[i][j] = null;

			}

		}
	}
	

	public void paint(Graphics g) {

		int blockSize = 20;
		// draw black board background
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, width * blockSize, height * blockSize);// 검은색 바탕

		// paint one cell of the falling shape (떨어지는 피스)
		if (name != null) {

			for (int i = 0; i < 4; i++) {
				int x = curX + base[i].x;

				int y = curY - base[i].y;

				paintOneCell(g, (x) * blockSize, (y) * blockSize, blockSize + 1, blockSize + 1);// +1은
																								// 네모를
																								// 꼭채워줌.

			}
		}

		for (int i = 0; i < height; i++) {// NEEDED TO REVISE HERE.
			for (int j = 0; j < width; j++) {
				g.setColor(Color.WHITE);// 여기에다가 넣어줘야 선색깔이 안바뀌고 계속 하얀색..
				// draw cells
				g.drawRect(0, 0, (j + 1) * blockSize, (i + 1) * blockSize);// 1을
																			// 붙히지
																			// 않으면
																			// 9칸
																			// 19줄이
																			// 생김.

				if (shapeAt(i, j) != null) {// 피스가 다 떨어지면 board 에 피스 그린다. 이것이
											// 백그라운드를 셋팅해줌. 피스가 안없어지고.
											// 이것으로 인해 새 피스가 떨어질때 안없어지고 BOARD에
											// REMAIN되있는거 같음.

					paintOneCell2(g, (j) * blockSize, (i) * blockSize, blockSize + 1, blockSize + 1);// +1은
					// 네모를
					// 꼭채워줌.

				}
			}

		}

	}

	private void paintOneCell2(Graphics g, int i, int j, int k, int l) {// 피스 색깔
																		// 바뀌지
																		// 않게
																		// 백그라운드에
																		// 저장해줌.
		// TODO Auto-generated method stub

		Color color2 = shapeAt(j / 20, i / 20);// j = height쪽 i = width 쪽 바뀜.
		g.setColor(color2);
		g.fillRect(i, j, k, l);
		g.setColor(Color.WHITE);
		g.drawRect(i, j, k - 1, l - 1);// 다 떨어져도 피스사이를 명확하게 해줄 하얀 줄 생김. 깔금하게 보임.

	}

	private void paintOneCell(Graphics g, int x, int y, int width, int height) {
		// TODO Auto-generated method stub

		g.setColor(color);
		g.fillRect(x, y, width, height);

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if (isFallingFinished == true) {
			isFallingFinished = false;

			newPiece();

		} else {
			oneLineDown();

		}

	}

	private void dropDown() {
		int newY = curY;
		while (newY < 20) {
			if (tryMove(curX, newY + 1) == false)
				break;
			++newY;
		}
		pieceDropped();
		eraseFullLine();
	}

	private void oneLineDown() {
		// TODO Auto-generated method stub
	
		int newY = curY;
		eraseFullLine();
		if (tryMove(curX, newY + 1) == false) // false 가 되어 뻐져 나오면 pieceDropped
												// 실행.

			pieceDropped();// put the piece into the board;
		
		/*int i = 1; 
		for(int j = 0; j < width; j++){
			if(board[i][j] != null){
				timer.stop();
				statusbar.setText("GAME OVER");
			}
		}*/

	}

	private void pieceDropped() {// put the piece into the board.
		// TODO Auto-generated method stub

		// pieceDropped 이 실행 되었다는것은 피스가 다 떨어졌따는의미.
		// 피스가 다 떨어지면 background 죽 board 를 떨어짐을 끝낸 피스색깔과 다시 set해줘야함.
		// set 되면 다시 피스 떨어지고 그 피스 포함해서 다시 board set해줘야함.
		isFallingFinished = true;

		for (int i = 0; i < 4; i++) {
			int x = curX + base[i].x;
			int y = curY - base[i].y;
			board[y][x] = color;// 떨어진 피스의 point 저장.

		}
		eraseFullLine();

	}

	private void eraseFullLine() {
		// TODO Auto-generated method stub

		for (int i = height - 1; i >= 0; i--) {
			boolean fullLine = true;// 이 statement가 for문 밖에 있으면 다 채워진 줄이 없어지지
									// 않음.
									// 이유는 for문 밖에 있으므로 "if(fullLin ==
									// true)"조건문을 통과하지 않음!!
			for (int j = 0; j < width; j++) {

				if (shapeAt(i, j) == null) {
					fullLine = false;
					break;
				}

			}
			if (fullLine == true) {
				++count;// 없어지는 줄 수

				for (int k = i; k > 0; k--) {
					for (int l = 0; l < width; l++) {

						board[k][l] = shapeAt(k - 1, l);// 이렇게 하니까
														// 없어지네...?잘되네..왜지?
						// repaint();//이것을 해줘야 피스떨어지고 새피시 나올때 떨어진피스가 없어지지 않음.
					}
				}
			}
			statusbar.setText(String.valueOf(count));
		}

		repaint();

	}

	private boolean tryMove(int newX, int newY) {
		// TODO Auto-generated method stub

		for (int i = 0; i < 4; ++i) {

			int x = newX + base[i].x;
			int y = newY - base[i].y;

			if (x < 0 || x > 9 || y > 19 || y < 0) {// need to revise here. 좌우
													// 끝에서 피스 돌리면 에러뜸..

				return false;
			}

			else if (shapeAt(y, x) != null) {// 피스 하나 떨어지고 두번때 피스 가 첫번때 피스에 닿았을때
												// 실행됨.

				return false;// false 되면 밑으로(curX = newX) 안가고 바로 oneLineDown 으로
								// 올라감.
			}

		}

		curX = newX;
		curY = newY;
		repaint();
		return true;

	}

	private Color shapeAt(int y, int x) {
		// TODO Auto-generated method stub
		return board[y][x];
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getKeyCode()) {

		case KeyEvent.VK_LEFT:
			tryMove(curX - 1, curY);

			break;
		case KeyEvent.VK_RIGHT:
			tryMove(curX + 1, curY);
			break;
		case KeyEvent.VK_DOWN:
			tryMove(curX, curY + 1);
			break;
		case KeyEvent.VK_UP:

			if (color == Color.green) {
				// do Nothing
			} else {

				rotateLeft();
				if (tryMove(curX, curY) == false) {
					rotateBefore();
					tryMove(curX, curY);
				}
				tryMove(curX, curY);
			}

			break;
		case KeyEvent.VK_SPACE:
			dropDown();
			break;
		case 'd':
			oneLineDown();
			break;
		case 'D':
			oneLineDown();
			break;
		}

	}

	private void rotateBefore() {
		// TODO Auto-generated method stub
		for (int i = 0; i < 4; i++) {
			base[i].setLocation(-(base[i].y), base[i].x);
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

}
