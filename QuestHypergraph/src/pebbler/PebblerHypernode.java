package pebbler;
import java.util.ArrayList;

public class PebblerHypernode<A>
{
    public int id;
    
    // edges in which this node is a source
    public ArrayList<PebblerHyperedge<A>> outEdges;
    public boolean active;

    public ArrayList<PebblerHyperedge<A>> getOutEdges() { return outEdges; }
    
    public PebblerHypernode(int theId)
    {
        id = theId;
        outEdges = new ArrayList<PebblerHyperedge<A>>();
        active = false;
    }
    
    public void activate() { active = true; }
    public void deactivate() { active = false; }
    public boolean isPebbled() { return active; }
    public int getID() { return id; }
    
    public boolean addEdge(PebblerHyperedge<A> newEdge) throws Exception
    {
        if(!newEdge.sourceNodes.contains(this.id)) throw new Exception("Hyperedge: " + newEdge.toString() + "is not incident to " + this.id);

        //for performance, comment out the next line
        if(outEdges.contains(newEdge)) return false;
        
        return outEdges.add(newEdge);
    }
}