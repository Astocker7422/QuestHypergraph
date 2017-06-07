package questgeneration;

import java.util.ArrayList;

import hypergraph.Hyperedge;
import hypergraph.Hypergraph;
import hypergraph.Hypernode;
import frontend.ConstraintParser;
import digraph.DiGraph;
import digraph.Linearization;
import digraph.Node;
import hypergraph.Annotation;
import hypergraph.utilities.HypergraphGenerator;
import questgeneration.nouns.Noun;
import questgeneration.verbs.Verb;
import utilities.Constants;

public class QuestHypergraphGenerator<T, A>
{
    private Hypergraph quest_hypergraph;
    public ArrayList<Hypernode<T, A>> SequencedElements;
    private int currIndex = 0;
    
    public QuestHypergraphGenerator(Hypergraph theQuestHypergraph)
    {
        quest_hypergraph = theQuestHypergraph;
        SequencedElements = new ArrayList<Hypernode<T, A>>();
    }
    
    //generates a hypergraph from a text file
    public QuestHypergraphGenerator(String fileName) throws Exception
    {
        ConstraintParser parser = new ConstraintParser(fileName);
        parser.parse();
        
        ActionGenerator actionGen = new ActionGenerator(parser);
        
        ArrayList<Action> actionSet = actionGen.generateAllActions();
        
        DiGraph DG = new DiGraph();
        
        for(Action act : actionSet)
        {
            DG.addNode(act);
        }
        
        addSequencedEdges(DG);
        
        Linearization<Action> order = new Linearization<Action>(DG.topologicalSort());
	ArrayList<Linearization> orders = new ArrayList<Linearization>();
	orders.add(order);
	
	HypergraphGenerator hypergraphGen = new HypergraphGenerator();
        ArrayList<Hypergraph> topologicalHypergraphList = hypergraphGen.genFilteredHypergraphs(orders);
        
        quest_hypergraph = topologicalHypergraphList.get(0);
    }
    
    //generates hypergraph from an action set
    public QuestHypergraphGenerator(ArrayList<Action> actionSet) throws Exception
    {
        DiGraph DG = new DiGraph();
        
        for(Action act : actionSet)
        {
            DG.addNode(act);
        }
        
        addSequencedEdges(DG);
        
        Linearization<Action> order = new Linearization<Action>(DG.topologicalSort());
	ArrayList<Linearization> orders = new ArrayList<Linearization>();
	orders.add(order);
	
	HypergraphGenerator hypergraphGen = new HypergraphGenerator();
        ArrayList<Hypergraph> topologicalHypergraphList = hypergraphGen.genFilteredHypergraphs(orders);
        
        quest_hypergraph = topologicalHypergraphList.get(0);
    }
    
    //gen many quest hypergraphs from a text file
    public ArrayList<Hypergraph> genManyQuestHypergraphs(String fileName) throws Exception
    {
        ConstraintParser parser = new ConstraintParser(fileName);
        parser.parse();
        
        ActionGenerator actionGen = new ActionGenerator(parser);
        
        ArrayList<Action> actionSet = actionGen.generateAllActions();
        
        DiGraph DG = new DiGraph();
        
        for(Action act : actionSet)
        {
            DG.addNode(act);
        }
        
        addSequencedEdges(DG);
        
        ArrayList<Linearization> orders = DG.filteredTopologicalSort();
	
	HypergraphGenerator hypergraphGen = new HypergraphGenerator();
        ArrayList<Hypergraph> topologicalHypergraphList = hypergraphGen.genFilteredHypergraphs(orders);
        
        return topologicalHypergraphList;
    }
    
    public Hypergraph<T, A> genParallelismHG(Action start, Action end, ArrayList<Action> actionSet, ArrayList<Constants.Method> methods) throws Exception
    {   
        quest_hypergraph.addNode(start);
	quest_hypergraph.addNode(end);
        
        int idIncrement = 2;
	
	for(Constants.Method m : methods)
	{
            ArrayList<Action> methodActions = new ArrayList<Action>();
            
            for(Action act : actionSet)
            {
                Verb actVerb = act.getVerb();
                for(Constants.Method verbMethod : actVerb.methods)
                {
                    if(verbMethod == m) methodActions.add(act);
                }
            }
            
            genSubHG(m, methodActions, idIncrement);
            
            idIncrement = quest_hypergraph.size();
	}
	
	return quest_hypergraph;
    }

    void genSubHG(Constants.Method m, ArrayList<Action> actionSet, int idIncrement) throws Exception
    {
	
//	QuestHypergraphGenerator HG_Gen = new QuestHypergraphGenerator(actionSet);
//	ArrayList<Hypernode> newHGVertices = HG_Gen.getQuestHypergraph().vertices;
        
        Hypergraph methodHG = new Hypergraph();
        
        for(Action act : actionSet)
        {
            methodHG.addNode(act);
        }
        
        addSequencedHyperedges(methodHG);
        
        ArrayList<Hypernode> newHGVertices = methodHG.vertices;
        
	for(Hypernode currNode : newHGVertices)
	{
            quest_hypergraph.addNode(currNode.data, currNode.id + idIncrement);
        }
            
        for(Hypernode currNode : newHGVertices)
        {
            ArrayList<Hyperedge> currNodeOut = currNode.outEdges;
            ArrayList<Hyperedge> currNodeIn = currNode.inEdges;
            
            for(Hyperedge outEdge : currNodeOut)
            {
                ArrayList<Integer> oldFromList = outEdge.sourceNodes;
                ArrayList<Integer> newFromList = new ArrayList<Integer>();
                for(Integer from : oldFromList)
                {
                    newFromList.add(from + idIncrement);
                }
                
                int newTarget = outEdge.targetNode + idIncrement;
                
                quest_hypergraph.addEdge(newFromList, newTarget);
            }
            
            for(Hyperedge inEdge : currNodeIn)
            {
                ArrayList<Integer> oldFromList = inEdge.sourceNodes;
                ArrayList<Integer> newFromList = new ArrayList<Integer>();
                for(Integer from : oldFromList)
                {
                    newFromList.add(from + idIncrement);
                }
                
                int newTarget = inEdge.targetNode + idIncrement;
                
                quest_hypergraph.addEdge(newFromList, newTarget);
            }
	}
        
        int[] firstSources = {0};
        
        Annotation firstAnnotation = new Annotation();
        
        for(Hypernode currNode: newHGVertices)
        {
            if(currNode.inEdges.isEmpty())
            {
                Hyperedge firstEdge = new Hyperedge(firstSources, currNode.id + idIncrement, firstAnnotation);
                quest_hypergraph.addEdge(firstEdge);
            }
        }
        
        for(Hypernode currNode: newHGVertices)
        {
            if(currNode.outEdges.isEmpty())
            {
                ArrayList<Integer> lastSources = new ArrayList<Integer>();
                lastSources.add(currNode.id + idIncrement);
                
                Annotation lastAnnotation = new Annotation();
        
                Hyperedge lastEdge = new Hyperedge(lastSources, 1, lastAnnotation);
                quest_hypergraph.addEdge(lastEdge);
            }
        }
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
    
    //add edges to directed graph based on predecessor constraints of contained Actions
    private void addSequencedEdges(DiGraph<Action> DG)
    {
        for(Node<Action> currNode : DG.getVertices())
        {
            Action currNodeAction = currNode.data;
            Verb currNodeVerb = currNodeAction.getVerb();
            if(currNodeAction.requiresSequencing)
            {
                for(Verb preVerb: currNodeVerb.getPredecessorVerbs())
                {
                    boolean sharePreVerb = false;
                    for(Verb preVerbCheck: preVerb.getPredecessorVerbs())
                    {
                        //if the current verb and one of it's predecessor verbs share a predecessor verb,
                        //no need for edge between current verb and the shared predecessor
                        if(preVerb.getPredecessorVerbs().contains(preVerbCheck))
                        {
                            sharePreVerb = true;
                            break;
                        }
                    }
                    
                    if(sharePreVerb) continue;
                    
                    for(Node<Action> compareNode : DG.getVertices())
                    {
                        Action compareNodeAction = compareNode.data;
                        if(/*compareNodeAction.getNoun().equals(currNodeNoun) && */compareNodeAction.getVerb().equals(preVerb))
                        {
                            DG.addEdge(compareNode.getId(), currNode.getId());
                        }
                    }
                }
            }
        }
    }
    
    void addSequencedHyperedges(Hypergraph<Action, A> HG)
    {
        for(Hypernode<Action, A> currNode : HG.vertices)
        {
            Action currNodeAction = currNode.data;
            Verb currNodeVerb = currNodeAction.getVerb();
            if(currNodeAction.requiresSequencing)
            {
                for(Verb preVerb: currNodeVerb.getPredecessorVerbs())
                {
                    boolean sharePreVerb = false;
                    for(Verb preVerbCheck: currNodeVerb.getPredecessorVerbs())
                    {
                        //if the current verb and one of it's predecessor verbs share a predecessor verb,
                        //no need for edge between current verb and the shared predecessor
                        if(preVerbCheck.getPredecessorVerbs().contains(preVerb))
                        {
                            for(Hypernode<Action, A> checkNode: HG.vertices)
                            {
                                Action checkNodeAction = checkNode.data;
                                Verb checkNodeVerb = checkNodeAction.getVerb();
                                if(checkNodeVerb.equals(preVerbCheck))
                                {
                                    sharePreVerb = true;
                                    break;
                                }
                            }
                        }
                    }
                    
                    if(sharePreVerb) continue;
                    
                    for(Hypernode<Action, A> compareNode : HG.vertices)
                    {
                        Action compareNodeAction = compareNode.data;
                        if(compareNodeAction.getVerb().equals(preVerb))
                        {
                            ArrayList<Integer> sources = new ArrayList<Integer>();
                            sources.add(compareNode.id);
                            HG.addEdge(sources, currNode.id);
                        }
                    }
                }
            }
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
