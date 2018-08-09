import java.awt.Color;
import java.util.Dictionary;
import java.util.Stack;



/**
 *  The <tt>DijkstraSP</tt> class represents a data type for solving the
 *  single-source shortest paths problem in edge-weighted digraphs
 *  where the edge weights are nonnegative.
 *  <p>
 *  This implementation uses Dijkstra's algorithm with a binary heap.
 *  The constructor takes time proportional to <em>E</em> log <em>V</em>,
 *  where <em>V</em> is the number of vertices and <em>E</em> is the number of edges.
 *  Afterwards, the <tt>distTo()</tt> and <tt>hasPathTo()</tt> methods take
 *  constant time and the <tt>pathTo()</tt> method takes time proportional to the
 *  number of edges in the shortest path returned.
 *  <p>
 *  For additional documentation, see <a href="/algs4/44sp">Section 4.4</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 */
public class DijkstraSP {
    private double[] distTo;          // distTo[v] = distance  of shortest s->v path
    private Edge[] edgeTo;    // edgeTo[v] = last edge on shortest s->v path
    private IndexMinPQ<Double> pq;    // priority queue of vertices
    private Graph _graph;
    
    private int currentVertexIndex;
    private int currentEdgeIndex;
    private int _start;
    
    private Stack<Integer> previousVertex;
    private Stack<Integer> previousEdgeIndex;
    private Stack<Edge> previousEdge;
    private Stack<String> previousMove;
    private Stack<Double> previousDist;
    /**
     * Computes a shortest paths tree from <tt>s</tt> to every other vertex in
     * the edge-weighted digraph <tt>G</tt>.
     * @param G the edge-weighted digraph
     * @param s the source vertex
     * @throws IllegalArgumentException if an edge weight is negative
     * @throws IllegalArgumentException unless 0 &le; <tt>s</tt> &le; <tt>V</tt> - 1
     */
    public DijkstraSP(Graph G, int s) {
    	this._graph = G;
        distTo = new double[G.vertexes.size()];
        edgeTo = new Edge[G.vertexes.size()];
        for (int v = 0; v < G.vertexes.size(); v++)
            distTo[v] = Double.POSITIVE_INFINITY;
        distTo[s] = 0.0;
        G.vertexes.get(s).color = Color.white;

        // relax vertices in order of distance from s
        pq = new IndexMinPQ<Double>(G.vertexes.size());
        pq.insert(s, distTo[s]);
        
        this._start = s;
        this.currentVertexIndex = s;
        this.currentEdgeIndex = Integer.MIN_VALUE;

        this.previousMove = new Stack<String>();
        this.previousVertex = new Stack<Integer>();
        this.previousEdge = new Stack<Edge>();
        this.previousEdgeIndex = new Stack<Integer>();
        this.previousDist = new Stack<Double>();

        // check optimality conditions
        //assert check(G, s);
    }
    public String next() {
    	if (this.currentVertexIndex == this._start && this.currentEdgeIndex == Integer.MIN_VALUE) {
    		nextVertex();
    		return nextEdge();
    	}
    	else if (hasNextEdge()) {
    		return nextEdge();
    	}
    	else if (hasNextVertex()) {
    		nextVertex();
    		if (hasNextEdge()) {
    			return nextEdge();
    		}
    		return "Advanced to " 
    			+ String.valueOf(this.currentVertexIndex);
    	}
    	return "No actions taken";
    }

    
    public boolean hasNextMove() {
    	if ((this.currentEdgeIndex == Integer.MIN_VALUE || hasNextEdge() == true) || hasNextVertex() == true) {
    		return true;
    	}
    	else {
    		return false;
    	}
    }
    
    public boolean hasNextVertex() {
    	if (!pq.isEmpty()) {
    		return true;
    	} else {
    		return false;
    	}
    }
    
    public boolean hasNextEdge() {
    	if (this.currentEdgeIndex < this._graph.vertexes.get(this.currentVertexIndex).edges.size()) {
    		return true;
    	} else {
    		return false;
    	}
    }
    
    public String getCurrentPQ() {
    	String pqString = "Pending items in priority queue: ";
    	for (int i = 0; i <this._graph.vertexes.size() ; i++) {	
    		if (pq.contains(i)) {
    			pqString += "[ v=" + i + ", distTo=" +  String.valueOf(pq.keyOf(i).intValue()) +"] ";
    		}
    		
    	}
    	return pqString;
    	
    }

    private void nextVertex() {
    	this.previousVertex.push(this.currentVertexIndex);
    	this.previousMove.push("vertex");
        this.currentVertexIndex = pq.delMin();
        this.currentEdgeIndex = 0;
    }
    
    private String nextEdge() {
    	String message = "";
    	if (this._graph.vertexes.get(currentVertexIndex).edges.size() > this.currentEdgeIndex)
    	{
    		Edge e = this._graph.vertexes.get(currentVertexIndex).edges.get(this.currentEdgeIndex);
    		this.previousEdge.push(e);
    		e.color = Color.black;
            message = relax(e);
            this.previousEdgeIndex.push(this.currentEdgeIndex);
            this.currentEdgeIndex++;
    	}
    	else {
    		message = "No outgoing edges from vertex " + this.currentVertexIndex;
    	}
		
        return message;

    }
    private String reAddVertexToPQ(){
		this.previousMove.pop();

		this.previousEdge.peek().color = Color.LIGHT_GRAY;
		this.previousEdge.pop();
		
		//_graph.vertexes.get(this.currentVertexIndex).color = Color.LIGHT_GRAY;
		pq.insert(this.currentVertexIndex, distTo[this.currentVertexIndex]);
		this.previousVertex.push(this.currentVertexIndex);
		return "Re-added vertex " + this.currentVertexIndex + " to priority queue";
    }
    
    private String undoAddNewVertex() {
    	this.previousMove.pop();
    	
    	if (pq.contains(this.previousVertex.peek())) {
        	pq.delete(this.previousVertex.peek());
    	}

    	distTo[this.previousVertex.peek()] = Double.POSITIVE_INFINITY;
    	_graph.vertexes.get(this.previousVertex.peek()).color = Color.LIGHT_GRAY;
    	this.previousEdge.peek().color = Color.LIGHT_GRAY;
    	this.previousEdge.pop();
    	this.currentEdgeIndex = this.previousEdgeIndex.pop();
    	
    	return "Distance to vertex " + this.currentVertexIndex + " set back to infinity";
    }
    
    private String increaseVertexToOldLength() {
    	//increase position in priority queue
		this.previousMove.pop();
		
		this.currentEdgeIndex = this.previousEdgeIndex.pop();
		
    	this.previousEdge.peek().color = Color.LIGHT_GRAY;
    	this.previousEdge.pop();
		
		pq.increaseKey(this.previousVertex.peek(), this.previousDist.peek());
		distTo[this.previousVertex.peek()] = this.previousDist.peek();
		
		return "Set distance to vertex "+ this.previousVertex.pop() + " to "+ this.previousDist.pop();
    }
    
    public String previous() {
    	String message = "";
    	if (!this.previousMove.empty()) {
    		if (this.previousMove.peek() == "vertex") {
    			message = reAddVertexToPQ();
    			this.currentVertexIndex = this.previousVertex.pop();
        		
        	}
        	else if (this.previousMove.peek() == "decreasedKey") {
        		message = increaseVertexToOldLength();
        		
        	}
        	else if (this.previousMove.peek() == "addedNewVertex") {
        		message = undoAddNewVertex();
        		
        	}
    		return message;
    	}
		else {
			return "No previous moves available.";
		}
    	
    }
    
    // relax edge e and update pq if changed
    private String relax(Edge e) {
    	
        int v = e.beginning.number, w = e.end.number;
        String message = "Relaxing edge " + String.valueOf(v) + " -> " + String.valueOf(w) + ";" ;
        if (distTo[w] > distTo[v] + e.getLength()) {  
        	double oldDist = distTo[w];
            distTo[w] = distTo[v] + e.getLength();
            edgeTo[w] = e;
            if (pq.contains(w)) {
            	message += " Found shorter path from source to " 
            			+ String.valueOf(w) + "; Old distance: " 
            			+ String.valueOf((int)oldDist) 
            			+ "; New distance: " 
            			+ String.valueOf((int)distTo[w]);
            	
            	pq.decreaseKey(w, distTo[w]);
            	
            	this.previousMove.push("decreasedKey");
            	this.previousVertex.push(w);
            	this.previousEdge.push(e);
            	this.previousDist.push(oldDist);
            	
            	return message;
            }
            else {
            	e.end.color = Color.white;
            	//added to priority queue here
            	pq.insert(w, distTo[w]);
            	
            	this.previousMove.push("addedNewVertex");
            	this.previousVertex.push(w);
            	this.previousEdge.push(e);
            	
            	message += " Edge length: " + String.valueOf((int)e.getLength());
            	return message;
            }
        }
        message += " Already visited this vertex";
        return message;
    }

    /**
     * Returns the length of a shortest path from the source vertex <tt>s</tt> to vertex <tt>v</tt>.
     * @param v the destination vertex
     * @return the length of a shortest path from the source vertex <tt>s</tt> to vertex <tt>v</tt>;
     *    <tt>Double.POSITIVE_INFINITY</tt> if no such path
     */
    public double distTo(int v) {
        return distTo[v];
    }

    /**
     * Is there a path from the source vertex <tt>s</tt> to vertex <tt>v</tt>?
     * @param v the destination vertex
     * @return <tt>true</tt> if there is a path from the source vertex
     *    <tt>s</tt> to vertex <tt>v</tt>, and <tt>false</tt> otherwise
     */
    public boolean hasPathTo(int v) {
        return distTo[v] < Double.POSITIVE_INFINITY;
    }

    /**
     * Returns a shortest path from the source vertex <tt>s</tt> to vertex <tt>v</tt>.
     * @param v the destination vertex
     * @return a shortest path from the source vertex <tt>s</tt> to vertex <tt>v</tt>
     *    as an iterable of edges, and <tt>null</tt> if no such path
     */
    public Iterable<Edge> pathTo(int v) {
        if (!hasPathTo(v)) return null;
        Stack<Edge> path = new Stack<Edge>();
        for (Edge e = edgeTo[v]; e != null; e = edgeTo[e.beginning.number]) {
        	e.color = Color.red;
            path.push(e);
        }
        return path;
    }

    // check optimality conditions:
    // (i) for all edges e:            distTo[e.to()] <= distTo[e.from()] + e.weight()
    // (ii) for all edge e on the SPT: distTo[e.to()] == distTo[e.from()] + e.weight()
    private boolean check(Graph G, int s) {

        // check that distTo[v] and edgeTo[v] are consistent
        if (distTo[s] != 0.0 || edgeTo[s] != null) {
            System.err.println("distTo[s] and edgeTo[s] inconsistent");
            return false;
        }
        for (int v = 0; v < G.vertexes.size(); v++) {
            if (v == s) continue;
            if (edgeTo[v] == null && distTo[v] != Double.POSITIVE_INFINITY) {
                System.err.println("distTo[] and edgeTo[] inconsistent");
                return false;
            }
        }

        // check that all edges e = v->w satisfy distTo[w] <= distTo[v] + e.weight()
        for (int v = 0; v < G.vertexes.size(); v++) {
            for (Edge e : G.vertexes.get(v).edges) {
                int w = e.end.number;
                if (distTo[v] + e.getLength() < distTo[w]) {
                    System.err.println("edge " + e + " not relaxed");
                    return false;
                }
            }
        }

        // check that all edges e = v->w on SPT satisfy distTo[w] == distTo[v] + e.weight()
        for (int w = 0; w < G.vertexes.size(); w++) {
            if (edgeTo[w] == null) continue;
            Edge e = edgeTo[w];
            int v = e.beginning.number;
            if (w != e.end.number) return false;
            if (distTo[v] + e.getLength() != distTo[w]) {
                System.err.println("edge " + e + " on shortest path not tight");
                return false;
            }
        }
        return true;
    }
}
