import javax.swing.SwingUtilities;
public class Proj {


	public final static int NUMBER_VERTEXES = 10;
	public final static int NUMBER_EDGES = 20;
	public final static int SCREEN_WIDTH = 800;
	public final static int SCREEN_HEIGHT = 600;
	
	public static void main(String[] args) {

		Runnable doWorkRunnable = new Runnable() {  
			public void run() {  
				GraphRender gr = new GraphRender(SCREEN_WIDTH, SCREEN_HEIGHT);
				gr.drawInstructions();
				}  
			}; 
		SwingUtilities.invokeLater(doWorkRunnable);  

	}



}
