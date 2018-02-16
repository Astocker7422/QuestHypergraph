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

package hypergraph.utilities;

import hypergraph.Annotation;
import java.util.Random;
import java.util.ArrayList;

import hypergraph.Hyperedge;
import hypergraph.Hypergraph;

//
// Implements the singleton design pattern
//
public class HyperedgeGenerator<T, A>
{
    private Hypergraph _HG;
    
    public HyperedgeGenerator(Hypergraph theHypergraph)
    {
        _HG = theHypergraph;
    }
    
    public Hyperedge<A> createRandomEdge(Random gen, int numNodes, A annotation)
    {
        //to have random number of source nodes, put random generator as first parameter of genSubset
        //currently at size 3 subset of sources for simple testing
        Hyperedge<A> newEdge = new Hyperedge<A>(utilities.Utilities.genSubset(gen, 3, 0, numNodes), gen.nextInt(numNodes), annotation);
        
        boolean success = true;
        for(int source: newEdge.sourceNodes)
        {
            if(newEdge.targetNode == source)
            {
                success = false;
                break;
            }
        }
        
        if (!success) newEdge = createRandomEdge(gen, numNodes, annotation);
        
        return newEdge;
    }
    
    public void genBoundedHyperedges(int currNode, String sourceNumberOrder) throws Exception
    {
        if(currNode == 0) return;
        
        int sourceBound = Character.getNumericValue(sourceNumberOrder.charAt(currNode - 1));
        
        if((currNode - sourceBound) < 0) return;
        
        ArrayList<Integer> invalid = new ArrayList<Integer>();
        
        for(int targetNode = (_HG.size() - 1); targetNode > currNode; targetNode--)
        {
            ArrayList<Hyperedge> inEdges = _HG.getNode(targetNode).inEdges;
            for(Hyperedge<A> inEdge : inEdges)
            {
                if(inEdge.sourceNodes.contains(currNode))
                {
                    for(int source : inEdge.sourceNodes)
                    {
                        invalid.add(source);
                    }
                }
            }
        }
        
        ArrayList<Integer> sources = new ArrayList<Integer>();
        int nodeIndex = currNode - 1;
        while(sources.size() < sourceBound && nodeIndex >= 0)
        {
            if(!invalid.contains(nodeIndex)) sources.add(nodeIndex);
            nodeIndex--;
        }
        if(sources.isEmpty()) return;
        
        Annotation edgeAnnotation = new Annotation();
        
        Hyperedge boundedEdge = new Hyperedge(sources, currNode, edgeAnnotation);
        _HG.addEdge(boundedEdge);
        
        if(currNode == 1) return;
        genBoundedHyperedges(currNode - 1, sourceNumberOrder);
    }
    
//    public void genBoundedHyperedges(int currNode, int sourceBound)
//    {
//        Random gen = new Random();
//        
//        int bound = gen.nextInt(sourceBound) + 1;
//        
//        if((currNode - bound) < 0 && currNode == 0) return;
//        
//        int[] sources;
//        
//        if((currNode - bound) < 0 && currNode != 0)
//        {
//            sources = new int[1];
//            sources[0] = currNode - 1;
//        }
//        else
//        {
//            sources = new int[bound];
//            int index = 0;
//            for(int count = 1; count <= bound; count++)
//            {
//                sources[index] = currNode - count;
//                index++;
//            }
//        }
//        
//        Annotation edgeAnnotation = new Annotation();
//        
//        Hyperedge boundedEdge = new Hyperedge(sources, currNode, edgeAnnotation);
//        _HG.addEdge(boundedEdge);
//        
//        genBoundedHyperedges(currNode - 1, sourceBound);
//    }
}
