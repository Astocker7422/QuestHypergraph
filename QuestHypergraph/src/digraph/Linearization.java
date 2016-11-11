package digraph;

import java.util.ArrayList;

public class Linearization<T>
{
    private ArrayList<Node<T>> nodes;
    
    public Linearization()
    {
        nodes = new ArrayList<Node<T>>();
    }
    
    public void addNode(Node<T> newNode)
    {
        nodes.add(newNode);
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
    
    @Override
    public String toString()
    {
        return nodes.toString();
    }
}
