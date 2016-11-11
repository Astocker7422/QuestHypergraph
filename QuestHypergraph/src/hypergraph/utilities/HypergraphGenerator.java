package hypergraph.utilities;

import hypergraph.*;
import digraph.*;
import java.util.ArrayList;
import utilities.*;

public class HypergraphGenerator<T, A>
{
    private ArrayList<Node<T>> node_list;
    
    public HypergraphGenerator()
    {
        node_list = new ArrayList<Node<T>>();
    }
    
    public HypergraphGenerator(ArrayList<Node<T>> nodes)
    {
        node_list = nodes;
    }
    
    public Hypergraph<T, A> genHypergraph(Linearization nodes, int sourceBound)
    {
        Hypergraph<T, A> HG = new Hypergraph<T, A>();
        
        node_list = nodes.getNodes();
        
        //add Linearization nodes as Hypernodes
        for(Node<T> currNode: node_list)
        {
            HG.addNode(currNode.data);
        }
        
        int lastNode = HG.vertices.size() - 1;
        HyperedgeGenerator edgeGen = new HyperedgeGenerator(HG);
        edgeGen.genBoundedHyperedges(lastNode, sourceBound);
        
        return HG;
    }
    
    public ArrayList<Hypergraph<T, A>> genAllHypergraphs(ArrayList<Linearization> nodeListCollection)
    {
        ArrayList<Hypergraph<T, A>> hypergraphCollection = new ArrayList<Hypergraph<T, A>>();
        
        //
        for(Linearization currList: nodeListCollection)
        {
            //changeable parameter (currently varying number of source nodes to each Hyperedge)
            for(int sourceBound = 1; sourceBound <= utilities.Constants.source_bound; sourceBound++)
            {
                hypergraphCollection.add(genHypergraph(currList, sourceBound));
            }
        }
        
        return hypergraphCollection;
    }
    
    public Hypergraph<T, A> HypergraphFromTopologicalList() throws Exception
    {
        Hypergraph<T, A> topologicalHG = new Hypergraph<T, A>();
        ArrayList<Integer> node_id_list = new ArrayList<Integer>();
        
        //add topological sort nodes as Hypernodes
        //create list of nodes' ids
        for(Node<T> currNode: node_list)
        {
            topologicalHG.addNode(currNode.data);
            node_id_list.add(currNode.getId());
        }
        
        for(Hypernode currHypernode: topologicalHG.vertices)
        {
            for(Node<T> currNode: node_list)
            {
                //check if this is the correct node to proccess
                if(currHypernode.data.equals(currNode.data))
                {
                    //add in edges of current node as in edges of current Hypernode
                    for(Edge currEdge: currNode.inEdges)
                    {
                        int[] source = {node_id_list.indexOf(currEdge.sourceNode)};
                        Annotation edgeAnnotation = new Annotation();
                        Hyperedge hyperedgeFromEdge = new Hyperedge(source, currHypernode.id, edgeAnnotation);
                        currHypernode.addInEdge(hyperedgeFromEdge);
                    }
                    //add out edges of current node as out edges of current Hypernode
                    for(Edge currEdge: currNode.outEdges)
                    {
                        int[] source = {currHypernode.id};
                        Annotation edgeAnnotation = new Annotation();
                        Hyperedge hyperedgeFromEdge = new Hyperedge(source, node_id_list.indexOf(currEdge.targetNode), edgeAnnotation);
                        currHypernode.addOutEdge(hyperedgeFromEdge);
                    }
                }
            }
        }
        
        return topologicalHG;
    }
}