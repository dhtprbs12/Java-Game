import java.awt.Color;
import java.awt.Point;

public class LPolyomino extends Polyomino{
	
	private static final Point[] base = { new Point(-1, 0), new Point(0, 0), new Point(1, 0), new Point(1, -1) };
	static Color color = Color.ORANGE;
	String name;
	
	
	public LPolyomino(String name){
		super(name,color,base);
	}
	
	

}
