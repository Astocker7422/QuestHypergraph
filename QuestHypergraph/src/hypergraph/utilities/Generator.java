package hypergraph.utilities;

import java.util.Random;

import hypergraph.Hyperedge;

//
// Implements the singleton design pattern
//
public class Generator<A>
{
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
}