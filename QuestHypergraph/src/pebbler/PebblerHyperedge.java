package pebbler;
import java.util.ArrayList;

public class PebblerHyperedge<A>
{
    public A annot;
    public ArrayList<Integer> sourceNodes;
    public int targetNode;
    public boolean pebbled;
    
    public PebblerHyperedge(ArrayList<Integer> sources, int target, A theAnnot)
    {
        sourceNodes = sources;
        targetNode = target;
        annot = theAnnot;
        pebbled = false;
    }
    
    public void pebble()
    {
        pebbled = true;
    }
    
    public void clearPebble()
    {
        pebbled = false;
    }

    public boolean isPebbled() { return pebbled; }
    
    @Override
    public String toString()
    {
        String edgeS = "";
        
        edgeS += "{";
        for(Integer currNode: sourceNodes)
        {
            edgeS += currNode + ", ";
        }
        edgeS += "} -> ";
        edgeS += targetNode;
        return edgeS;
    }
}