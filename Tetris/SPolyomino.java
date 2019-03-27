

import java.awt.Color;
import java.awt.Point;

public class SPolyomino extends Polyomino {
	
	private static final Point[] base = { new Point(-1, 0), new Point(0, 0), new Point(0, -1), new Point(1, -1) };
	static Color color = Color.BLUE;
	String name;
	
	
	public SPolyomino(String name){
		super(name,color,base);
	}
	
	
	
	

}

