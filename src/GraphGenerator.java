import java.awt.Color;




public class GraphGenerator {
	
	public Graph Generate(int screenWidth, int screenHeight, int numberVertexes, int numberEdges)
	{

		Graph graph = new Graph();
		
		for (int i = 0; i < numberVertexes; i++)
		{
			Vertex vertex = new Vertex(i, Color.LIGHT_GRAY, StdRandom.uniform(screenWidth - 20) + 10, StdRandom.uniform(screenHeight - 40) + 10);
			
			graph.vertexes.add(vertex);
		}
		
		int numberAddedEdges = 0;
		
		
		while (numberAddedEdges <= numberEdges)
		{
			int start = StdRandom.uniform(numberVertexes);
			int end = StdRandom.uniform(numberVertexes);
			if (start != end) 
			{
				Vertex startVertex = graph.vertexes.get(start);
				boolean edgeExists = false;
				for(Edge e : startVertex.edges)
				{
					if (e.end.number == end) {
						edgeExists = true;
					}
				}
				if (!edgeExists) {
					for (Edge destinationEdge : graph.vertexes.get(end).edges) {
						if (destinationEdge.end.number == start) {
							edgeExists = true;
						}
					}
				}
				if (!edgeExists)
				{
					Edge edge = new Edge(startVertex, graph.vertexes.get(end));
					edge.color = Color.LIGHT_GRAY;
					startVertex.edges.add(edge);
					numberAddedEdges++;
				}
			}
		}
		
		return graph;
	}

}
