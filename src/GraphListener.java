
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;


public class GraphListener implements ActionListener {

	private GraphPanel _graphPanel;
	private DijkstraSP dsp;
	private GraphRender graphRender;
	
	public GraphListener(GraphPanel graphPanel) {
		this._graphPanel = graphPanel;
		this.dsp = graphPanel.getGraphRender().getDSP();
		this.graphRender = graphPanel.getGraphRender();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
	    if ("next".equals(e.getActionCommand())) {
	    	if (dsp.hasNextMove()) {
	    		this.graphRender.setActionTaken(dsp.next());
	    	}
	    	else if (dsp.hasPathTo(_graphPanel.getGraph().vertexes.size() -1))
	    	{
		    	Iterable<Edge> edges = dsp.pathTo(_graphPanel.getGraph().vertexes.size() - 1);
		    	String path = "Shortest path found: ";
		    	int pathLength = 0;
		    	Stack<Edge> edgeStack = new Stack<Edge>();
		    	for (Edge edge : edges) {
		    		edgeStack.push(edge);
		    	}
		    	while (!edgeStack.empty()) {
		    		Edge tempEdge = edgeStack.pop();
		    		path += String.valueOf(tempEdge.beginning.number);
		    		path += "->";
		    		path += String.valueOf(tempEdge.end.number);
		    		path += ", ";
		    		pathLength += (int)tempEdge.getLength();
		    	}

		    	path += "; Path length = " + String.valueOf(pathLength);
		    	this.graphRender.setActionTaken(path);
	    	}
	    	else if (!dsp.hasPathTo(_graphPanel.getGraph().vertexes.size() -1)) {
	    		this.graphRender.setActionTaken("No path exists from 0 ->" + String.valueOf(_graphPanel.getGraph().vertexes.size() -1));
	    	}

	    	this.graphRender.drawGraph();
	    }
	    else if("previous".equals(e.getActionCommand())) {
	    	this.graphRender.setActionTaken(dsp.previous());
	    	this.graphRender.drawGraph();

	    }
	    else if("generateNew".equals(e.getActionCommand())){
    		GraphGenerator gg = new GraphGenerator();
    		Graph graph = gg.Generate(graphRender.getScreenWidth(), graphRender.getScreenHeight() - 80,
    				graphRender.getInstructionPanel().getNumberVertices(), graphRender.getInstructionPanel().getNumberEdges());
    		graphRender.setGraph(graph);
    		graphRender.setDSP(graph, 0);
    		graphRender.setActionTaken("New Graph Generated");
	    	graphRender.drawGraph();

	    }
	    else {
	    	
	    }
		
	}

}
