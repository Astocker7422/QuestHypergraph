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

package pathgeneration;
import java.util.ArrayList;
import java.util.Collections;

import hypergraph.Hypergraph;
import pebbler.PebblerHyperedge;

public class HyperedgeMultiMap<T, A>
{
    private final int TABLE_SIZE;
    private ArrayList<PebblerHyperedge<A>>[] table;
    public int size;
    //private Hypergraph<T, A> graph;

    public void setOriginalHypergraph(Hypergraph<T, A> g)
    {
        //graph = g;
    }
    
    public HyperedgeMultiMap(int sz)
    {
        size = 0;
        TABLE_SIZE = sz;
        table = new ArrayList[TABLE_SIZE];
    }

    public ArrayList<PebblerHyperedge<A>> collectAllEdges()
    {
        ArrayList<PebblerHyperedge<A>> edges = new ArrayList<PebblerHyperedge<A>>();

        for(int ell = 0; ell < TABLE_SIZE; ell++)
        {
            if(table[ell] != null)
            {
                edges.addAll(table[ell]);
            }
        }

        return edges;
    }

    public boolean putUnchecked(PebblerHyperedge<A> thatEdge)
    {
        Collections.sort(thatEdge.sourceNodes);
        long hashVal = (thatEdge.targetNode % TABLE_SIZE);

        if(table[(int)hashVal] == null)
        {
            table[(int)hashVal] = new ArrayList<PebblerHyperedge<A>>();
        }

        for(PebblerHyperedge<A> edge: table[(int)hashVal])
        {
            if(edge.equals(thatEdge)) return false;
        }

        table[(int)hashVal].add(thatEdge);
        size++;

        return true;
    }

    public boolean putUnchecked(ArrayList<Integer> ante, int target, A annot)
    {
        return putUnchecked(new PebblerHyperedge<A>(ante, target, annot));
    }

    public ArrayList<PebblerHyperedge<A>> getBasedOnGoal(int goalNodeIndex) throws Exception
    {
        if (goalNodeIndex < 0 || goalNodeIndex >= TABLE_SIZE)
        {
            throw new Exception("HyperEdgeMultimap index out of bounds (" + goalNodeIndex + ")");
        }

        return table[goalNodeIndex];
    }

    public boolean hasEdge(PebblerHyperedge<A> thatEdge)
    {
        ArrayList<PebblerHyperedge<A>> targetEdges = table[thatEdge.targetNode % TABLE_SIZE];

        if (targetEdges == null) return false;

        for(PebblerHyperedge<A> edge:targetEdges)
        {
            if (edge.equals(thatEdge)) return true;
        }

        return false;
    }

    @Override
    public String toString()
    {
        String retS = "";

        for (int ell = 0; ell < TABLE_SIZE; ell++)
        {
            if (table[ell] != null)
            {
                retS += ell + ":\n";
                for(PebblerHyperedge<A> PebbledHyperedge:table[ell])
                {
                    retS += PebbledHyperedge.toString() + "\n";
                }
            }
        }

        return retS;
    }
}
