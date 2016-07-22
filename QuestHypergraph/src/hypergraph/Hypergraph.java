package hypergraph;
import java.util.ArrayList;
import java.util.Collections;

import pebbler.PebblerHyperedge;
import pebbler.PebblerHypergraph;
import pebbler.PebblerHypernode;

public class Hypergraph<T, A>
{
    public ArrayList<Hypernode<T, A>> vertices;
    
    public Hypergraph()
    {
        vertices = new ArrayList<Hypernode<T, A>>();
    }
    
    public int size() { return vertices.size(); }
 
    //trys to add a vertex to the graph and returns whether it was successful
    public boolean addNode(T data)
    {
        return vertices.add(new Hypernode<T, A>(data, vertices.size()));
    }
    
    //adds new edge and returns whether it was successful
    public boolean addEdge(Hyperedge<A> newEdge)
    {
        //for every vertex check if the edge already exists
        if (hasEdge(newEdge)) return false;
        
        //for every vertex in the edge's sourcenodes list, add the edge to out edges
        for(Integer currNode: newEdge.sourceNodes)
        {
            vertices.get(currNode).outEdges.add(newEdge);
        }
        
        //add the edge to the edge's targetnode's in edge list
        vertices.get(newEdge.targetNode).inEdges.add(newEdge);

        return true;
    }
    
    //checks and returns if each vertex is incident to the edge
    public boolean hasEdge(Hyperedge<A> e)
    {
        for(Hypernode<T, A> currNode: vertices)
        {
            if(currNode.inEdges.contains(e)) return true;
            if(currNode.outEdges.contains(e)) return true;
        }
        return false;
    }
    
    //returns the node with a specific id
    public Hypernode<T, A> getNode(int id)
    {
        return vertices.get(id);
    }
    
    //return integer-based representation of hypergraph
    public PebblerHypergraph<T, A> getPebbledHypergraph() throws Exception
    {
        //
        // Create the nodes
        //
        ArrayList<PebblerHypernode<A>> pebbledNodes = new ArrayList<PebblerHypernode<A>>(vertices.size());

        for (int v = 0; v < vertices.size(); v++)
        {
            pebbledNodes.add(v, vertices.get(v).createPebbledNode());
        }

        //
        // Create the hyperedges
        //
        for (int v = 0; v < vertices.size(); v++)
        {
            for(Hyperedge<A>currEdge: vertices.get(v).outEdges)
            {
                //only add if it is the "minimum" source node, so the edge is not added twice to any node
                if(v == Collections.min(currEdge.sourceNodes))
                {
                    PebblerHyperedge<A> newEdge = new PebblerHyperedge<A>(currEdge.sourceNodes, currEdge.targetNode, currEdge.annot);
                    for(int src: currEdge.sourceNodes)
                    {
                        pebbledNodes.get(src).addEdge(newEdge);
                    }
                }
            }
        }

        return new PebblerHypergraph<T, A>(this, pebbledNodes);
    }
    
    @Override
    public String toString()
    {
        String graphS = "";
        
        for(Hypernode<T, A> currNode: vertices)
        {
            graphS += "Vertex " + currNode.id + ": ";
            graphS += "(data: " + currNode.data + ", ";
            graphS += "out edges: ";
            for(Hyperedge<A>currEdge: currNode.outEdges)
            {
                graphS += currEdge.toString() + ", ";
            }
            graphS += "in edges: ";
            for(Hyperedge<A>currEdge: currNode.inEdges)
            {
                graphS += currEdge.toString();
            }
            graphS += ")";
        }
        
        return graphS;
    }
}