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
