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