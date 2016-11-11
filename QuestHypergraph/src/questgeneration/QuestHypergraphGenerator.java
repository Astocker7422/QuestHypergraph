package questgeneration;

import java.util.ArrayList;

import hypergraph.Hyperedge;
import hypergraph.Hypergraph;
import hypergraph.Hypernode;

public class QuestHypergraphGenerator<T, A>
{
    private Hypergraph quest_hypergraph;
    public ArrayList<Hypernode<T, A>> SequencedElements;
    
    public QuestHypergraphGenerator(Hypergraph theQuestHypergraph)
    {
        quest_hypergraph = theQuestHypergraph;
        SequencedElements = new ArrayList<Hypernode<T, A>>();
    }
    
    private boolean isSequenced(Action theAction)
    {
        if(theAction.requiresSequencing) return true;
        else return false;
    }
    
    public void addAction(Action newAction)
    {
        quest_hypergraph.addNode(newAction);
    }
            
    public void addActions(ArrayList<Action> newActionList)
    {
        for(Action currAction: newActionList)
        {
            quest_hypergraph.addNode(currAction);
        }
    }
            
    public void addEdge(Hyperedge newEdge)
    {
        quest_hypergraph.addEdge(newEdge);
    }
    
    public void addEdges(ArrayList<Hyperedge> newEdgeList)
    {
        for(Hyperedge currEdge: newEdgeList)
        {
            quest_hypergraph.addEdge(currEdge);
        }
    }
    
    public Hypergraph getQuestHypergraph()
    {
        return quest_hypergraph;
    }
}
