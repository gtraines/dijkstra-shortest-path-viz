
import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.font.FontRenderContext;
import java.awt.font.LineMetrics;
import java.util.*; 

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;


public class InstructionPanel extends JPanel{
	
	private static final long serialVersionUID = 1L; 
	private int _pageNumber;
	private InstructionListener _il;
	private GraphRender _graphRender;
	private Instructions _instructions;
	private int _numberEdges;
	private int _numberVertices;
	
	@Override public void paintComponent(Graphics g) {
		super.setOpaque(false);
		super.paintComponent(g);
		this._il = new InstructionListener(this, this._graphRender);
		this._instructions = new Instructions();
		this._numberVertices = 10;
		this._numberEdges = 20;
		drawInstructions(g);
	}
	
	public void setGraphRender(GraphRender graphRender) {
		this._graphRender = graphRender;
	}
	
	private void drawInstructions(Graphics g) {
		this.setSize(_graphRender.getWidth(), _graphRender.getHeight());
		this.setLayout(new BorderLayout());

		JPanel south = new JPanel();
		south.setSize(_graphRender.getWidth(), 50);
		JButton next = new JButton("next");
		next.setVerticalTextPosition(AbstractButton.CENTER);
		next.setHorizontalTextPosition(AbstractButton.CENTER); 
		next.setMnemonic(KeyEvent.VK_RIGHT);
		next.setActionCommand("next");
		next.addActionListener(this._il);	

		JButton previous = new JButton("previous");
		previous.setVerticalTextPosition(AbstractButton.CENTER);
		previous.setHorizontalTextPosition(AbstractButton.CENTER); 
		previous.setMnemonic(KeyEvent.VK_LEFT);
		previous.setActionCommand("previous");
		previous.addActionListener(this._il);
	
		south.add(previous);
		south.add(next);
		
		JTextArea textArea = new JTextArea();
		textArea.setWrapStyleWord(true);
		textArea.setText(_instructions.pages.get(this._pageNumber));
		
		this.add(textArea, BorderLayout.CENTER);
		this.add(south, BorderLayout.SOUTH);
		this.validate();
		this.revalidate();
		this.repaint();
	}
	
	public void setNumberEdges(int edges) {
		this._numberEdges = edges;
	}
	
	public void setNumberVertices(int vertices) {
		this._numberVertices = vertices;
	}
	
	public int getNumberEdges() {
		return this._numberEdges;
	}
	
	public int getNumberVertices() {
		return this._numberVertices;
	}
	
	public void setPageNumber(int pageNumber) {
		this._pageNumber = pageNumber;
	}
	
	public int getPageNumber() {
		return this._pageNumber;
	}
	
	public int getTotalInstructionPages(){
		return this._instructions.pages.size();
	}

}
