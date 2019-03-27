
import java.awt.Color;
import java.awt.Point;

public class ZPolyomino extends Polyomino {
	
	private static final Point[] base = {new Point(-1, -1), new Point(0, -1), new Point(0, 0), new Point(1, 0) };
	static Color color = Color.RED;
	String name;
	
	
	public ZPolyomino(String name){
		super(name,color,base);
		
	}
	
	
}
