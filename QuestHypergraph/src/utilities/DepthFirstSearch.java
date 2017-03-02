//has for loop issues

package utilities;

import java.util.ArrayList;
import digraph.*;
import java.util.Stack;
import questgeneration.constraints.LinearizationFilter;

public class DepthFirstSearch<T>
{
    private DiGraph graph;
    private int time;
    private ArrayList<Edge> tree_edges;
    private ArrayList<Edge> back_edges;
    private Stack<Node<T>> topological_list;
    private ArrayList<Linearization<T>> all_topological_sorts;
    
    private Color[] node_colors;
    private int[] node_in_times;
    private int[] node_out_times;
    
    private int[] indegree;
    
    public enum Color{ WHITE(), PURPLE(), BLACK() };
    
    public DepthFirstSearch(DiGraph DG)
    {
        graph = DG;
        time = 0;
        tree_edges = new ArrayList<Edge>();
        back_edges = new ArrayList<Edge>();
        topological_list = new Stack<Node<T>>();
        all_topological_sorts = new ArrayList<Linearization<T>>();
        
        node_colors = new Color[graph.getVertices().size()];
        node_in_times = new int[graph.getVertices().size()];
        node_out_times = new int[graph.getVertices().size()];
        
        indegree = new int[graph.getVertices().size()];
        //tried for loop*
        for(int count = 0; count < graph.getVertices().size(); count++)
        {
            Node<T> currNode = graph.getNode(count);
            indegree[count] = currNode.inEdges.size();
        }
        
        DFS();
    }
    
    public ArrayList<Edge> getBackEdges()
    {
        return back_edges;
    }
    
    public ArrayList<Node<T>> getTopologicalList()
    {
        ArrayList<Node<T>> topList = new ArrayList<Node<T>>();
        while(!topological_list.isEmpty())
        {
            topList.add(topological_list.pop());
        }
        return topList;
    }
    
    public ArrayList<Linearization<T>> getAllTopologicalSorts()
    {
        allTopologicalSort();
        return all_topological_sorts;
    }
    
    public ArrayList<Linearization<T>> getFilteredTopologicalSorts()
    {
        filteredTopologicalSort();
        return all_topological_sorts;
    }
    
    private void DFS()
    {
        //tried for loop*
        for(int count = 0; count < graph.getVertices().size(); count++)
        {
            node_colors[count] = Color.WHITE;
        }
        
        time = 0;
                
        //tried for loop*
        for(int count = 0; count < graph.getVertices().size(); count++)
        {
            //if(node_colors[graph.getVertices().indexOf(currNode)] == Color.WHITE) DFS_Visit(currNode);
            Node<T> currNode = graph.getNode(count);
            if(node_colors[count] == Color.WHITE) DFS_Visit(currNode);
        }
    }
    
    private void DFS_Visit(Node n)
    {
        node_colors[graph.getVertices().indexOf(n)] = Color.PURPLE;
        time = time +1;
        node_in_times[graph.getVertices().indexOf(n)] = time;
        
        //tried for loop with Integers*
        for (Object descendant : n.descendants)
        {
            Node<T> currNode = graph.getNode((int) descendant);
            Edge currEdge = null;
            for(Edge e: currNode.inEdges)
            {
                if(e.sourceNode == n.getId()) currEdge = e;
            }
            if(node_colors[graph.getVertices().indexOf(currNode)] == Color.WHITE)
            {
                tree_edges.add(currEdge);
                DFS_Visit(currNode);
            }
            if(node_colors[graph.getVertices().indexOf(currNode)] == Color.PURPLE)
            {
                back_edges.add(currEdge);
            }
        }
        
        node_colors[graph.getVertices().indexOf(n)] = Color.BLACK;
        time = time + 1;
        node_out_times[graph.getVertices().indexOf(n)] = time;
        topological_list.push(n);
    }
    
    public void allTopologicalSort()
    {
        //tried for loop*
        for(int count = 0; count < graph.getVertices().size(); count++)
        {
            node_colors[count] = Color.WHITE;
        }
        
        Linearization<T> topologicalSort = new Linearization<T>();
        
        time = 0;
        
        allTopologicalSortUtil(topologicalSort, false);
    }
    
    public void filteredTopologicalSort()
    {
        //tried for loop*
        for(int count = 0; count < graph.getVertices().size(); count++)
        {
            node_colors[count] = Color.WHITE;
        }
        
        Linearization<T> topologicalSort = new Linearization<T>();
        
        time = 0;
        
        allTopologicalSortUtil(topologicalSort, true);
    }
    
    private void allTopologicalSortUtil(Linearization<T> sort, boolean filter)
    {
        boolean flag = false;
        
        LinearizationFilter sortFilter = new LinearizationFilter(all_topological_sorts, graph.getVertices().size());
        
        for(int i = 0; i < graph.getVertices().size(); i++)
	{
            Node currNode = graph.getNode(i);
            if(indegree[i] == 0 && node_colors[i] == Color.WHITE)
            {
		for(Object decendant: currNode.descendants)
		{
                    indegree[(int) decendant]--;
                }
		sort.addNode(currNode);
                node_colors[i] = Color.PURPLE;
                
		allTopologicalSortUtil(sort, filter);
                
		node_colors[i] = Color.WHITE;
		sort.removeLast();
                
		for(Object decendant: currNode.descendants)
		{
                    indegree[(int) decendant]++;
                }
                
                flag = true;
            }
                
        }
        Linearization<T> newSort = new Linearization<T>();
        for(int index = 0; index < sort.getNodes().size(); index++)
        {
            newSort.addNode(sort.getNode(index));
        }
        if(!flag && filter)
        {
            sortFilter.filter();
            if(!flag) all_topological_sorts.add(newSort);
        }
    }
}
