package hypergraph;

import java.util.ArrayList;

import pebbler.PebblerHypernode;

public class Hypernode<T, A>
{
    public T data;
    public int id;

    //edges in which this node is a source
    public ArrayList<Hyperedge<A>> outEdges;
    
    //edges in which this node is the target
    public ArrayList<Hyperedge<A>> inEdges;
    
    public Hypernode(T theData, int theId)
    {
        data = theData;
        id = theId;
        outEdges = new ArrayList<Hyperedge<A>>();
        inEdges = new ArrayList<Hyperedge<A>>();
    }
    
    public boolean addInEdge(Hyperedge<A> edge) throws Exception
    {
        if(edge.targetNode != this.id) throw new Exception("Hyperedge<A>: " + edge.toString() + "has a different target node than expected " + this.id);
        //for performance, comment out the next line
        if(inEdges.contains(edge)) return false;
        return inEdges.add(edge);
    }
    
    public boolean addOutEdge(Hyperedge<A> edge) throws Exception
    {
        if(!edge.sourceNodes.contains(this.id)) throw new Exception("Hyperedge<A>: " + edge.toString() + "is not incident to " + this.id);
        //for performance, comment out the next line
        if(outEdges.contains(edge)) return false;
        return outEdges.add(edge);
    }
    
    public Hyperedge getInEdge(int index)
    {
        return inEdges.get(index);
    }
    
    public Hyperedge getOutEdge(int index)
    {
        return outEdges.get(index);
    }
    
    public PebblerHypernode<A> createPebbledNode() 
    {
        return new PebblerHypernode<A>(id);
    }
}
