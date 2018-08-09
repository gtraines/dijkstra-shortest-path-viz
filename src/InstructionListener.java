import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InstructionListener implements ActionListener {
	
	private InstructionPanel _instructionPanel;
	private GraphRender _graphRender;
	 
	public InstructionListener(InstructionPanel instructionPanel, GraphRender graphRender) {
		this._instructionPanel = instructionPanel;
		this._graphRender = graphRender;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
	    if ("next".equals(e.getActionCommand())) {
	    	if(_instructionPanel.getPageNumber() < _instructionPanel.getTotalInstructionPages()) {
	    		_graphRender.nextInstruction();
	    	}
	    	else {
	    		GraphGenerator gg = new GraphGenerator();
	    		Graph graph = gg.Generate(_graphRender.getScreenWidth(), _graphRender.getScreenHeight() - 80,
	    				_instructionPanel.getNumberVertices(), _instructionPanel.getNumberEdges());
	    		_graphRender.setGraph(graph);
	    		_graphRender.setDSP(graph, 0);
	    		_graphRender.drawGraph();
	    	}
	
	    }
	    else if("previous".equals(e.getActionCommand())) {
	    	if(_instructionPanel.getPageNumber() > 1) {
	    		_graphRender.previousInstruction();
	    	}
	    }
	    else {
	    	_graphRender.drawInstructions();

	    }
		
	} 


}
