
import java.awt.Container;
import java.util.*;
import java.awt.event.*;
import javax.swing.*;

public class GraphRender extends JFrame{
	public static final long serialVersionUID = 2754699462095738197L;
	private Stack<JPanel> jStack = new Stack<JPanel>(); 
	private Graph _graph;
	Container contentPane;
	public int _screenWidth;
	public int _screenHeight;
	private int _pageNumber;
	private InstructionPanel _instructionPanel;
	private DijkstraSP _dsp;
	private String _actionTaken;
	
	public GraphRender(int screenWidth, int screenHeight) {
		super("Dijkstra's Shortest Path Algorithm");
		this._screenHeight = screenHeight;
		this._screenWidth = screenWidth;

		setSize(_screenWidth, _screenHeight);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationByPlatform(true);
		this._actionTaken = "Graph Rendered";
		_pageNumber = 1;
	}
	
	public int getScreenWidth() {
		return this._screenWidth;
	}
	
	public int getScreenHeight() {
		return this._screenHeight;
	}
	
	public void setGraph(Graph graph) {
		this._graph = graph;
	}
	
	public void setDSP(Graph graph, int source) {
		this._dsp = new DijkstraSP(graph, source);
	}
	
	public DijkstraSP getDSP() {
		return _dsp;
	}
	
	public void drawInstructions() {
		InstructionPanel ip = new InstructionPanel();
		ip.setGraphRender(this);
		ip.setPageNumber(this._pageNumber);
		this._instructionPanel = ip;
		this.add(ip);
		validate();
		this.setVisible(true);

	}
	
	public void drawGraph() {
		GraphPanel gp = new GraphPanel();
		gp.setGraph(_graph);
		gp.setGraphRender(this);
		
		this.getContentPane().removeAll();
		this.add(gp);
		validate();
		this.setVisible(true);

	}
	
	public void nextInstruction() {
		this._pageNumber++;
		this.getContentPane().removeAll();
		drawInstructions();
	}
	
	public void previousInstruction() {
		this._pageNumber--;
		this.getContentPane().removeAll();
		drawInstructions();
	}
	
	public void previousGraph() {
		
	}
	
	public void setActionTaken(String actionTaken) {
		this._actionTaken = actionTaken;
	}
	
	public String getActionTaken() {
		return this._actionTaken;
	}
	
	public String getPriorityQueueString() {
		return this._dsp.getCurrentPQ();
	}
	
	public InstructionPanel getInstructionPanel() {
		return this._instructionPanel;
	}

}
