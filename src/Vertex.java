import java.awt.Color;
import java.util.ArrayList;


public class Vertex {
	public int number;
	public int xCoord;
	public int yCoord;
	public Color color;
	public ArrayList<Edge> edges;
	
	
	
	public Vertex(int number, Color color, int xCoord, int yCoord)
	{
		this.number = number;
		this.color = color;
		this.xCoord = xCoord;
		this.yCoord = yCoord;
		this.edges = new ArrayList<Edge>();
	}

}
