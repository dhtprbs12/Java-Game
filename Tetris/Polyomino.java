import java.awt.Color;
import java.awt.Point;

public class Polyomino {
	
	Point[]array;
	String name;
	Color color;
	
	public Polyomino(String name, Color color, Point[] base){
		this.name = name;
		this.color = color;
		this.array = base;
	}
	
	
	public Color getColor(){
		return color;
	}
	
	public Point[] getArray(){
		return array;
	}
	
	public String getName(){
		return name;
	}


	

}
