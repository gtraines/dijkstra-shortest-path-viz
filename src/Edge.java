import java.math.*;
import java.awt.Color;
import java.awt.Polygon;

public class Edge {
	private double _length;
	public Vertex beginning;
	public Vertex end;
	public Color color;
	
	public Edge(Vertex beginning, Vertex end) {
		this.beginning = beginning;
		this.end = end;
		this._length = Math.sqrt(
				Math.pow((this.beginning.xCoord - this.end.xCoord), 2) 
				+ Math.pow((this.beginning.yCoord - this.end.yCoord), 2)
				);
	}
	
	public double getLength() {
		return _length;
	}

}
