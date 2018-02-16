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

public class Linearization<T>
{
    private ArrayList<Node<T>> nodes;
    
    public Linearization()
    {
        nodes = new ArrayList<Node<T>>();
    }
    
    public Linearization(ArrayList<Node<T>> nodeList)
    {
        nodes = nodeList;
    }
    
    public void addNode(Node<T> newNode)
    {
        nodes.add(newNode);
    }
    
    public void addNode(T data)
    {
        nodes.add(new Node<T>(data, nodes.size()));
    }
    
    public Node<T> getNode(int index)
    {
        return nodes.get(index);
    }
    
    public ArrayList<Node<T>> getNodes()
    {
        return nodes;
    }
    
    public Node<T> removeLast()
    {
        Node<T> lastNode = nodes.get(nodes.size() - 1);
        nodes.remove(lastNode);
        return lastNode;
    }
    
    public String toASCIIString()
    {
        String linearization = new String();
        
        for(Node currNode: nodes)
        {
            Integer charInt = currNode.getId() + 33;
            char ASCIIChar = (char) Character.toLowerCase(charInt);
            linearization += ASCIIChar;
        }
        
        return linearization;
    }
    
    public int[] toArray()
    {
        int[] ary = new int[nodes.size()];
        int index = 0;
        for(Node<T> currNode: nodes)
        {
            ary[index] = currNode.getId();
            index++;
        }
        return ary;
    }
    
    @Override
    public String toString()
    {
        return nodes.toString();
    }
}
