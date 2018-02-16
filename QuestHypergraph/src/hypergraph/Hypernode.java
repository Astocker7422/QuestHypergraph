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
    
    public boolean isComplex()
    {
        Hypergraph HG = new Hypergraph();
        if(data.getClass().equals(HG.getClass())) return true;
        return false;
    }
    
    public boolean equals(Hypernode otherNode)
    {
        if(!data.equals(otherNode.data)) return false;
        
        if(inEdges.size() != otherNode.inEdges.size() || outEdges.size() != otherNode.outEdges.size()) return false;
        
        for(int inIndex = 0; inIndex < inEdges.size(); inIndex++)
        {
            Hyperedge<A> thisIn = inEdges.get(inIndex);
            Hyperedge<A> otherIn = inEdges.get(inIndex);
            
            if(!thisIn.equals(otherIn)) return false;
        }
        
        for(int outIndex = 0; outIndex < outEdges.size(); outIndex++)
        {
            Hyperedge<A> thisOut = outEdges.get(outIndex);
            Hyperedge<A> otherOut = outEdges.get(outIndex);
            
            if(!thisOut.equals(otherOut)) return false;
        }
        
        return true;
    }
}
