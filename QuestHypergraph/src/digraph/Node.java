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

package digraph;

import java.util.ArrayList;

public class Node<T>
{
    public T data;
    private int id;
    //nodes which are a target of edges in which this node is the source
    public ArrayList<Integer> descendants; 
    
    //edges in which this node is the source
    public ArrayList<Edge> outEdges;
    
    //edges in which this node is the target
    public ArrayList<Edge> inEdges;
    
    public Node(T theData, int theId)
    {
        data = theData;
        id = theId;
        outEdges = new ArrayList<Edge>();
        inEdges = new ArrayList<Edge>();
        descendants = new ArrayList<Integer>();
    }
    
    public void addEdge(Edge e)
    {
        if(!hasEdge(e))
        {
            if(e.sourceNode == id) outEdges.add(e);
            if(e.targetNode == id) inEdges.add(e);
            if(id != e.targetNode) this.descendants.add(e.targetNode);
        }
    }
    
    public int getId()
    {
        return id;
    }
    
    public boolean hasEdge(Edge e)
    {
        for(Edge currEdge: outEdges)
        {
            if(e.equals(currEdge)) return true;
        }
        
        for(Edge currEdge: inEdges)
        {
            if(e.equals(currEdge)) return true;
        }
        return false;
    }
    
    @Override
    public String toString()
    {
        return data.toString();
    }
}
