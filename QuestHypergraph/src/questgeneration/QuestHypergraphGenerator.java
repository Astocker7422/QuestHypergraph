package questgeneration;

import java.util.ArrayList;

import hypergraph.Hyperedge;
import hypergraph.Hypergraph;

public class QuestHypergraphGenerator
{
    private Hypergraph quest_hypergraph;
    
    public QuestHypergraphGenerator(Hypergraph theQuestHypergraph)
    {
        quest_hypergraph = theQuestHypergraph;
    }
    
    public void addAction(Action newAction)
    {
// should accept newAction because addNode takes an Object and Action is an Object
        quest_hypergraph.addNode(newAction);
    }
            
    public void addActions(ArrayList<Action> newActionList)
    {
        for(Action currAction: newActionList)
        {
// should accept currAction because addNode takes an Object and Action is an Object
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
