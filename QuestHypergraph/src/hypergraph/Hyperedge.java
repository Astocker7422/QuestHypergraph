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
