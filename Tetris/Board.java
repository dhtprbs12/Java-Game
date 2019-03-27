
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
	 * board ������ 0�̰�, �ǾƷ��� 20 ���̷��� �������ִ����� �𸣰�����, ��������. �¿� �� ������ �� Ű�����Ʈ ������ ������
	 * ���� �� ã���� ���ִ� �ڵ尡 �ʹ� ��. ���� �������� �󸶳� ������������ ���� Ȯ��ġ���� ���� �������� �� ������ �Ʒ��� �����ö�
	 * ���� ����..
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

	public void rotateLeft() {// �ٽ� ����� ���ư��� ����.

		for (int i = 0; i < 4; i++) {// ������ �ٲ�.

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
		
		if(tryMove(curX, curY) == false){//curX = 5 curY = 2�� tryMove�� ���Խ�Ű�� y���� 19�̻����� �˼� ����. 
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
		g.fillRect(0, 0, width * blockSize, height * blockSize);// ������ ����

		// paint one cell of the falling shape (�������� �ǽ�)
		if (name != null) {

			for (int i = 0; i < 4; i++) {
				int x = curX + base[i].x;

				int y = curY - base[i].y;

				paintOneCell(g, (x) * blockSize, (y) * blockSize, blockSize + 1, blockSize + 1);// +1��
																								// �׸�
																								// ��ä����.

			}
		}

		for (int i = 0; i < height; i++) {// NEEDED TO REVISE HERE.
			for (int j = 0; j < width; j++) {
				g.setColor(Color.WHITE);// ���⿡�ٰ� �־���� �������� �ȹٲ�� ��� �Ͼ��..
				// draw cells
				g.drawRect(0, 0, (j + 1) * blockSize, (i + 1) * blockSize);// 1��
																			// ������
																			// ������
																			// 9ĭ
																			// 19����
																			// ����.

				if (shapeAt(i, j) != null) {// �ǽ��� �� �������� board �� �ǽ� �׸���. �̰���
											// ��׶��带 ��������. �ǽ��� �Ⱦ�������.
											// �̰����� ���� �� �ǽ��� �������� �Ⱦ������� BOARD��
											// REMAIN���ִ°� ����.

					paintOneCell2(g, (j) * blockSize, (i) * blockSize, blockSize + 1, blockSize + 1);// +1��
					// �׸�
					// ��ä����.

				}
			}

		}

	}

	private void paintOneCell2(Graphics g, int i, int j, int k, int l) {// �ǽ� ����
																		// �ٲ���
																		// �ʰ�
																		// ��׶��忡
																		// ��������.
		// TODO Auto-generated method stub

		Color color2 = shapeAt(j / 20, i / 20);// j = height�� i = width �� �ٲ�.
		g.setColor(color2);
		g.fillRect(i, j, k, l);
		g.setColor(Color.WHITE);
		g.drawRect(i, j, k - 1, l - 1);// �� �������� �ǽ����̸� ��Ȯ�ϰ� ���� �Ͼ� �� ����. ����ϰ� ����.

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
		if (tryMove(curX, newY + 1) == false) // false �� �Ǿ� ���� ������ pieceDropped
												// ����.

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

		// pieceDropped �� ���� �Ǿ��ٴ°��� �ǽ��� �� �����������ǹ�.
		// �ǽ��� �� �������� background �� board �� �������� ���� �ǽ������ �ٽ� set�������.
		// set �Ǹ� �ٽ� �ǽ� �������� �� �ǽ� �����ؼ� �ٽ� board set�������.
		isFallingFinished = true;

		for (int i = 0; i < 4; i++) {
			int x = curX + base[i].x;
			int y = curY - base[i].y;
			board[y][x] = color;// ������ �ǽ��� point ����.

		}
		eraseFullLine();

	}

	private void eraseFullLine() {
		// TODO Auto-generated method stub

		for (int i = height - 1; i >= 0; i--) {
			boolean fullLine = true;// �� statement�� for�� �ۿ� ������ �� ä���� ���� ��������
									// ����.
									// ������ for�� �ۿ� �����Ƿ� "if(fullLin ==
									// true)"���ǹ��� ������� ����!!
			for (int j = 0; j < width; j++) {

				if (shapeAt(i, j) == null) {
					fullLine = false;
					break;
				}

			}
			if (fullLine == true) {
				++count;// �������� �� ��

				for (int k = i; k > 0; k--) {
					for (int l = 0; l < width; l++) {

						board[k][l] = shapeAt(k - 1, l);// �̷��� �ϴϱ�
														// ��������...?�ߵǳ�..����?
						// repaint();//�̰��� ����� �ǽ��������� ���ǽ� ���ö� �������ǽ��� �������� ����.
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

			if (x < 0 || x > 9 || y > 19 || y < 0) {// need to revise here. �¿�
													// ������ �ǽ� ������ ������..

				return false;
			}

			else if (shapeAt(y, x) != null) {// �ǽ� �ϳ� �������� �ι��� �ǽ� �� ù���� �ǽ��� �������
												// �����.

				return false;// false �Ǹ� ������(curX = newX) �Ȱ��� �ٷ� oneLineDown ����
								// �ö�.
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
