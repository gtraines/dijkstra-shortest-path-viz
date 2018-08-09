import java.util.HashMap;
import java.util.Map;


public class Instructions {
	public Map<Integer, String> pages;
	
	public Instructions() {
		pages = new HashMap<Integer, String>();
		pages.put(1, ("Dijkstra's Shortest Path Algorithm \n\n"
						+ "A graphical demonstration by Graham Traines \n\n"
						+ "Edsgar Dijkstra published his shortest-path algorithm in 1959.\n" 
						+ "It belongs to the class of algorithms called single-source, shortest path algorithms.\n "
						+ "Although it originally did not include the use of a priority queue (CLRS pg. 682), it \n"
						+ "appeared in later implementations.\n"
						+ "Dijkstra\'s algorithm operates on directed, weighted graphs where the weights of the edges \n"
						+ "are all "
						+ "\"nonnegative.\" As mentioned, the algorithm uses a priority queue to retrieve, in order, \n"
						+ "the closest (or cheapest) neighbors of the current vertex. \n"
						+ "\n"
						+ "For sparse graphs (that is, those where the number of edges/arcs E is less than the square \n"
						+ "of the number of vertices V), the overall performance of the algorithm is O((V + E) log V) \n"
						+ "(Heineman and Selkow pg. 156). \n\n"
	
						+ "The algorithm first explores the entirety of the tree comprised of all vertices reachable \n"
						+ "from the source vertex. Each edge in the graph is initially assigned a weight of positive infinity,\n "
						+ "then the actual weight is determined as each of the vertices and edges are explored. (This is known \n"
						+ "as \"relaxing\" the edge. Each vertex "
						+ "reached is also assigned a total weight or cost which is the cost from the source node to the last \n"
						+ "vertex, plus the weight or cost of the current edge to the current vertex.\n"
						+ "\n\n"
						+ "Because the algorithm uses the priority queue to choose the cheapest routes first, it is considered \n"
						+ "a greedy algorithm. \n"));
		pages.put(2, "On the next screen you will be shown a randomly generated, directed, weighted graph. \n"
				+ "Edge weights appear in blue. Unexplored vertices and edges are light gray. \n"
				+ "(Note: Some edges will overlap so they will appear to be unexplored although they are \n"
				+ "obscured by an unexplored edge on top of them.)\n"
				+ "Simply press the \"next\" button to see the algorithm perform its exploration \n" 
				+ "and determine the shortest path. The number of vertexes and edges may be changed \n"
				+ "manually in the InstructionPanel class. \n"
				+ "Note: the functionality for the \"previous\" button on the graph is unstable; \n"
				+ "use at your own risk."
				+ "Also, the directions for the edges are not shown graphically so some apparently \n"
				+ "connected vertexes will not be explored. \n \n"
				+ "Source: \n"
				+ "Cormen, Lieserson, Rivest, and Stein. Introduction to Algorithms, 3rd edition, 2009.\n"
				+ "Heineman, Pollice, and Selkow. Algorithms in a Nutshell. 2009\n"
				+ "Sedgewick and Wayne, Algorithms, 4th ed. 2011");
		
	}
	

}
