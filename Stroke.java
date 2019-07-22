import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;

// an instance of a stroke consists of a list of points, a colour and a pen size.
public class Stroke {
	private int size;
	private Color colour;
	private ArrayList<Point> points;
		
	// constructor
	public Stroke(){
		points = new ArrayList<Point>();
		this.colour = Color.BLACK;
		this.size = 7;
	}
	
	// getter and setters
	public int getSize(){
		return this.size;
	}
	
	public Color getColour(){
		return this.colour;
	}
	
	public void addPoint(Point p){
		points.add(p);
	}
	
	public Point getPoint(int index){
		return  points.get(index);
	}
	
	public void addPoint(int x, int y){
		points.add(new Point(x,y));
	}
	
	public int setSize(int size){
		return this.size = size;
	}
	
	public Color setColour(Color colour){
		return this.colour = colour;
	}

	public Iterator<Point> getPointIterator(){
		return points.iterator();
	}
}