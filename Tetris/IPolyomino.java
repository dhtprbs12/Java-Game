

import java.awt.Color;
import java.awt.Point;

public class IPolyomino extends Polyomino {
	
	private static final Point[] base = { new Point(0, -1), new Point(0, 0), new Point(0, 1), new Point(0, 2) };
	static Color color = Color.MAGENTA;
	String name;
	
	
	public IPolyomino(String name){
		super(name,color,base);
	}
	


}
