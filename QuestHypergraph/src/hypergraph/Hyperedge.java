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

public class Hyperedge<A>
{
    public A annot;
    public ArrayList<Integer> sourceNodes;
    public int targetNode;
    
    public Hyperedge(int[] sources, int target, A theAnnot)
    {
        //creates an arraylist containing the integers that represent the source nodes
        sourceNodes = new ArrayList<Integer>();
        for(int source: sources)
        {
            sourceNodes.add(source);
        }
        targetNode = target;
        annot = theAnnot;
    }
    
    public Hyperedge(ArrayList<Integer> sources, int target, A theAnnot)
    {
        sourceNodes = sources;
        targetNode = target;
        annot = theAnnot;
    }

//    public Hyperedge(ArrayList<Integer> genSubset, int nextInt,
//            Annotation annotation)
//    {
//        // TODO Auto-generated constructor stub
//    }
    
    public boolean contains(Hyperedge otherEdge)
    {
        if(this.sourceNodes.size() < otherEdge.sourceNodes.size()) return false;
        
        ArrayList<Integer> otherSources = otherEdge.sourceNodes;
        
        for(int source : otherSources)
        {
            if(!this.sourceNodes.contains(source)) return false;
        }
        
        return true;
    }

    @Override
    public boolean equals(Object o)
    {
        if(!(o instanceof Hyperedge)) return false;

        @SuppressWarnings("unchecked")
        Hyperedge<A> that = (Hyperedge<A>) o;
        
        //compare target
        if(that.targetNode != this.targetNode) return false;
        
        //compare number of sources
        if(this.sourceNodes.size() != that.sourceNodes.size()) return false;
        //compare sources
        for(Integer thisInt: this.sourceNodes)
        {
            if(!that.sourceNodes.contains(thisInt)) return false;
        }
        return true;
    }
    
    @Override
    public String toString()
    {
        String edgeS = "";
        
        edgeS += "{";
        for(Integer currNode: sourceNodes)
        {
            if(sourceNodes.indexOf(currNode) != sourceNodes.size() - 1) edgeS += currNode + ", ";
            else edgeS += currNode;
        }
        edgeS += "} -> ";
        edgeS += targetNode;
        return edgeS;
    }
}
