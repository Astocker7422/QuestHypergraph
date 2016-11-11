package digraph;

import java.util.ArrayList;

public class Node<T>
{
    public T data;
    private int id;
    //nodes which are a target of edges in which this node is the source
    public ArrayList<Integer> descendants; 
    
    //edges in which this node is the source
    public ArrayList<Edge> outEdges;
    
    //edges in which this node is the target
    public ArrayList<Edge> inEdges;
    
    public Node(T theData, int theId)
    {
        data = theData;
        id = theId;
        outEdges = new ArrayList<Edge>();
        inEdges = new ArrayList<Edge>();
        descendants = new ArrayList<Integer>();
    }
    
    public void addEdge(Edge e)
    {
        if(!hasEdge(e))
        {
            if(e.sourceNode == id) outEdges.add(e);
            if(e.targetNode == id) inEdges.add(e);
            if(id != e.targetNode) this.descendants.add(e.targetNode);
        }
    }
    
    public int getId()
    {
        return id;
    }
    
    public boolean hasEdge(Edge e)
    {
        for(Edge currEdge: outEdges)
        {
            if(e.equals(currEdge)) return true;
        }
        
        for(Edge currEdge: inEdges)
        {
            if(e.equals(currEdge)) return true;
        }
        return false;
    }
    
    @Override
    public String toString()
    {
        return data.toString();
    }
}
