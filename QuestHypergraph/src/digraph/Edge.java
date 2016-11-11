package digraph;

import java.util.ArrayList;

public class Edge
{
    public int sourceNode;
    public int targetNode;
    
    public Edge(int from, int to)
    {
        sourceNode = from;
        targetNode = to;
    }
    
    public boolean equals(Edge thatEdge)
    {
        if(this.sourceNode == thatEdge.sourceNode && this.targetNode == thatEdge.targetNode) return true;
        return false;
    }
    
    @Override
    public String toString()
    {
        String edgeString = "";
        
        edgeString += "Edge (";
        edgeString += "From: " + sourceNode + ", ";
        edgeString += "To: " + targetNode + ")";
        
        return edgeString;
    }
}
