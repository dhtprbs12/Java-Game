import java.awt.Color;
import java.awt.Point;

public class TPolyomino extends Polyomino {
	
	private static final Point[] base = { new Point(0, -1), new Point(-1, 0), new Point(0, 0), new Point(1, 0) };
	static Color color = Color.WHITE;
	String name;
	
	
	public TPolyomino(String name){
		super(name,color,base);
	}
	
	

}
