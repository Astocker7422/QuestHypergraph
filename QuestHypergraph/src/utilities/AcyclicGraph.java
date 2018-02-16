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

package utilities;

import java.util.ArrayList;
import java.util.Hashtable;
import digraph.DiGraphInt;

public class AcyclicGraph extends DiGraphInt
{
    public AcyclicGraph()
    {
        super();
    }
    
    @Override
    public void AddEdge(int from, int to)
    {
        AddEdge(edgeMap, from, to);
        AddEdge(transposeEdgeMap, to, from);

        numEdges++;
    }
    
    private void AddEdge(Hashtable<Integer, ArrayList<Integer>> givenEdges, int from, int to)
    {
        if(from > to) return;
        
        // This order needed because we want the goal node of the problem first
        if (!vertices.contains(to)) vertices.add(to);
        if (!vertices.contains(from)) vertices.add(from);

        ArrayList<Integer> fromDependencies = null;
        if (givenEdges.containsKey(from))
        {
            fromDependencies = givenEdges.get(from);
            Utilities.addUnique(fromDependencies, to);
        }
        else
        {
            ArrayList<Integer> toList = new ArrayList<>();
            toList.add(to);
            givenEdges.put(from, toList);
        }
    }
    
    @Override
    public void AddHyperEdge(ArrayList<Integer> fromList, int to)
    {
        for (int from : fromList)
        {
            if(from > to) return;
        }
        
        for (int from : fromList)
        {
            this.AddEdge(from, to);
        }
    }
}
