//Copyright (c) 2018 Alex R. Stocker
//
//Permission is hereby granted, free of charge, to any person obtaining a copy of 
//this software and associated documentation files (the "Software"), to deal in 
//the Software without restriction, including without limitation the rights to use, 
//copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the 
//Software, and to permit persons to whom the Software is furnished to do so, 
//subject to the following conditions:
//
//The above copyright notice and this permission notice shall be included in all 
//copies or substantial portions of the Software.
//
//THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR 
//IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
//FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR 
//COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER 
//IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN 
//CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

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
