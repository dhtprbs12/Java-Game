
import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class TetrisTest extends JFrame{
	
	JFrame Jframe;
	Board board;
	JLabel statusbar;
	
	public TetrisTest(String title){
		statusbar = new JLabel("0");
		Jframe = new JFrame(title);
		board = new Board(this);
		Jframe.add(board);
		Jframe.add(statusbar, BorderLayout.SOUTH);
		board.newPiece();
		Jframe.setSize(250, 500);
		Jframe.setVisible(true);
		Jframe.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	public JLabel getStatusBar() {
	       return statusbar;
	   }
	
	public static void main(String [] args){
		TetrisTest tetristest = new TetrisTest("Tetris");
	}
	
	

}
