package pebbler;
import java.util.ArrayList;

import hypergraph.Hypergraph;

public class PebblerHypergraph<T, A>
{
    protected ArrayList<PebblerHypernode<A>> nodes;

    private Hypergraph<T, A> original;
    public Hypergraph<T, A> getOriginalHypergraph() { return original; }
    
    public PebblerHypergraph()
    {
        nodes = new ArrayList<PebblerHypernode<A>>();
    }
    
    public PebblerHypergraph(Hypergraph<T, A> o, ArrayList<PebblerHypernode<A>> nodeList)
    {
        original = o;
        nodes = nodeList;
    }
    
    public void pebbleNode(int id)
    {
    	nodes.get(id).activate();
    }
    
    //
    //loop over source nodes to see if each are pebbled
    //
    public boolean isFullyPebbled(PebblerHyperedge<A> edge)
    {
    	if (edge.isPebbled()) return true;

    	for(Integer src : edge.sourceNodes)
    	{
    		if (!nodes.get(src).isPebbled())
    		{
    			return false;
    		}
    	}

    	edge.pebble();

    	return true;
    }
    
    public boolean isNodePebbled(int id) { return nodes.get(id).isPebbled(); }
    public ArrayList<PebblerHyperedge<A>> getOutEdges(int id) { return nodes.get(id).outEdges; }
    
    @Override
    public String toString()
    {
        String graphS = "";
        
        for(PebblerHypernode<A> currNode: nodes)
        {
            graphS += "Vertex " + currNode.id + "(" + currNode.isPebbled() + "): ";
            graphS += " edges: ";
            for(PebblerHyperedge<A> currEdge: currNode.outEdges)
            {
                graphS += currEdge.toString() + ", ";
            }
            graphS += ") ";
        }
        return graphS;
    }
}