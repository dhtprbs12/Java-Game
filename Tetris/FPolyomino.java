
import java.awt.Color;
import java.awt.Point;

public class FPolyomino extends Polyomino {
	
	private static final Point[] base = { new Point(1, -1), new Point(0, -1),
			new Point(0, 0), new Point(0, 1) };
	static Color color = Color.YELLOW;
	String name;
	
	
	public FPolyomino(String name){
		super(name,color,base);
	}
	


}
