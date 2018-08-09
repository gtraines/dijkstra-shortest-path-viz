import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.geom.Line2D;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.font.FontRenderContext;
import java.awt.font.LineMetrics;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GraphPanel extends JPanel{
	
	private static final long serialVersionUID = 3896761222323780201L;
	private Graph _graph;
	private GraphRender _graphRender;
	private GraphListener _graphListener;
	private String _actionTaken = "";
	
	@Override public void paintComponent(Graphics g) {
		super.setOpaque(false);
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D)g;
		
		drawGraph(g2d);
	}
	
	public void drawGraph(Graphics2D g) {
		
		this._graphListener = new GraphListener(this);
		
		this.setSize(_graphRender.getWidth(), _graphRender.getHeight());
		this.setLayout(new BorderLayout());

		JPanel south = new JPanel();
		south.setSize(_graphRender.getWidth(), 40);
		JButton next = new JButton("next");
		next.setVerticalTextPosition(AbstractButton.CENTER);
		next.setHorizontalTextPosition(AbstractButton.CENTER); 
		next.setMnemonic(KeyEvent.VK_RIGHT);
		next.setActionCommand("next");
		next.addActionListener(this._graphListener);	

		JButton previous = new JButton("previous");
		previous.setVerticalTextPosition(AbstractButton.CENTER);
		previous.setHorizontalTextPosition(AbstractButton.CENTER); 
		previous.setMnemonic(KeyEvent.VK_LEFT);
		previous.setActionCommand("previous");
		previous.addActionListener(this._graphListener);
		
		JButton generateNew = new JButton("new graph");
		generateNew.setVerticalTextPosition(AbstractButton.CENTER);
		generateNew.setHorizontalTextPosition(AbstractButton.CENTER); 
		generateNew.setMnemonic(KeyEvent.VK_ENTER);
		generateNew.setActionCommand("generateNew");
		generateNew.addActionListener(this._graphListener);
	
		south.add(previous);
		south.add(next);
		south.add(generateNew);
		
		for(int i = 0; i < _graph.vertexes.size(); i++)
		{
			Vertex v = _graph.vertexes.get(i);

			for (int c = 0; c < v.edges.size(); c++) {
				Edge e = v.edges.get(c);
				g.setPaint(e.color);
				g.setStroke(new BasicStroke(3));
				g.draw(new Line2D.Float(e.beginning.xCoord, e.beginning.yCoord, e.end.xCoord, e.end.yCoord));
				
				int labelXCoord;
				int labelYCoord;
				
				labelXCoord = ((e.beginning.xCoord + e.end.xCoord)/2) - 10;
				labelYCoord = ((e.beginning.yCoord + e.end.yCoord)/2) - 10;
				
				Font font = g.getFont().deriveFont(Font.BOLD, 12f); 
		        g.setFont(font);  
		        FontRenderContext frc = g.getFontRenderContext();  
		        
		        
		        String lengthLabel = String.valueOf((int)e.getLength());
		        float width = (float)font.getStringBounds(lengthLabel, frc).getWidth();  
		        LineMetrics lm = font.getLineMetrics(lengthLabel, frc);  
		        
		        g.setPaint(Color.BLUE);
				g.drawString(lengthLabel, labelXCoord - 5, labelYCoord - 5);
			}
			
	        Font font = g.getFont().deriveFont(Font.BOLD, 16f); 
	        g.setFont(font);  
	        FontRenderContext frc = g.getFontRenderContext();  
	        
	        String numberLabel = String.valueOf(v.number);
	        float width = (float)font.getStringBounds(numberLabel, frc).getWidth();  
	        LineMetrics lm = font.getLineMetrics(numberLabel, frc);  
	      
	        g.setPaint(v.color); 
	        g.fillRect((int)(v.xCoord - (width/2)), (int)(v.yCoord - (lm.getHeight())), (int)width + 10, (int)lm.getHeight() + 5);
			
	        g.setPaint(Color.black);
			g.drawString(numberLabel, v.xCoord, v.yCoord);
		}
		
        Font font = g.getFont().deriveFont(Font.BOLD, 16f); 
        g.setFont(font);  
        FontRenderContext frc = g.getFontRenderContext(); 
        String actionLabel = _graphRender.getActionTaken();
        float widthA = (float)font.getStringBounds(actionLabel, frc).getWidth();  
      
        g.setPaint(Color.black); 
		g.drawString(actionLabel, (_graphRender.getScreenWidth())/2 - (widthA/2), _graphRender.getScreenHeight() - 90);
		
		String priorityQueueString = this._graphRender.getPriorityQueueString();
		float widthP = (float)font.getStringBounds(priorityQueueString, frc).getWidth(); 
		g.drawString(priorityQueueString, (_graphRender.getScreenWidth()/2 - widthP/2), _graphRender.getScreenHeight() - 70);
		
		//south.add(new JLabel(_graphRender.getActionTaken()));
		this.add(south, BorderLayout.SOUTH);
		this.validate();
		this.revalidate();
		this.repaint();
	}
	
	public void setGraph(Graph graph) {
		this._graph = graph;
	}
	
	public Graph getGraph() {
		return this._graph;
	}
	
	public GraphRender getGraphRender() {
		return this._graphRender;
	}
	
	public void setGraphRender(GraphRender graphRender) {
		this._graphRender = graphRender;
	}
	
	public void setActionTaken(String actionTaken) {
		this._actionTaken = actionTaken;
	}

}
