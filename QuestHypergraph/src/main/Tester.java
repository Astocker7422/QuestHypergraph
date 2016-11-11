package main;
import java.util.ArrayList;
import java.util.Random;

import digraph.*;
import frontend.ConstraintParser;
import hypergraph.Annotation;
import hypergraph.Hypergraph;
import hypergraph.utilities.HyperedgeGenerator;
import questgeneration.Action;
import questgeneration.ActionGenerator;
import questgeneration.QuestHypergraphGenerator;
import questgeneration.verbs.*;
import questgeneration.nouns.*;
import hypergraph.utilities.*;
import utilities.*;

public class Tester 
{
    
    public static void main(String[] args) throws Exception
    {
        //
        //TEST PARSER AND ACTIONGENERATOR
        //
//        Hypergraph<Action, Annotation> hG = new Hypergraph<Action, Annotation>();
//        final int NUM_NODES = 10;
//        final int NUM_EDGES = 10;
//        
//        QuestHypergraphGenerator questGen = new QuestHypergraphGenerator(hG);
//        
//        ConstraintParser parser = new ConstraintParser("VerbNounList.xml");
//        parser.parse();
//        
//        ActionGenerator actionGen = new ActionGenerator(parser);
//        
//        ArrayList<Action> actionSet = actionGen.generateUniqueActionSet(NUM_NODES);
//        
//        questGen.addActions(actionSet);
//        
//        Random gen = new Random();
//        gen.setSeed(0); //if seed set, all targets are the same
//
//        HyperedgeGenerator<Annotation> edgeGen = new HyperedgeGenerator<Annotation>();
//        for(int count = 0; count < NUM_EDGES; count++)
//        {
//            questGen.addEdge(edgeGen.createRandomEdge(gen, NUM_NODES, new Annotation()));
//        }
//        
//        System.out.println(questGen.getQuestHypergraph());
        
        //
        //TEST DIGRAPH WITH ACTIONS
        //
//        ConstraintParser parser = new ConstraintParser("VerbNounList.xml");
//        parser.parse();
//        
//        ActionGenerator actionGen = new ActionGenerator(parser);
//        
//        ArrayList<Action> actionSet = actionGen.generateAllActions();
//        
//        DiGraph DG = new DiGraph();
//        
//        //create fake Noun to compare to Nouns in generated Actions to ensure only Actions with that Noun are added to DiGraph
//        //might make this method in ActionGenerator called generateActionsOfNoun(), or something similar
//        StaticVerb sampleNegatedVerb = new StaticVerb("go to");
//        ArrayList<Verb> sampleNegVerbList = new ArrayList<Verb>();
//        sampleNegVerbList.add(sampleNegatedVerb);
//        Key compareNoun = new Key("Door Key", sampleNegVerbList);
//        
//        for(Action currAction: actionSet)
//        {
//            if(currAction.getNoun().getClass().equals(compareNoun.getClass()))
//            {
//                DG.addNode(currAction);
//            }
//        }
//        
//        //might make this a method in DiGraph called addSequencedActionEdges(), or something similar
//        for(int nodeNum = 0; nodeNum < DG.getVertices().size(); nodeNum++)
//        {
//            Node currNode = DG.getNode(nodeNum);
//            Action currNodeAction = (Action) currNode.data;
//            Noun currNodeNoun = currNodeAction.getNoun();
//            if(currNodeAction.requiresSequencing)
//            {
//                for(Verb preVerb: currNodeAction.getVerb().getPredecessorVerbs())
//                {
//                    for(int count = 0; count < DG.getVertices().size(); count++)
//                    {
//                        Action compareNodeAction = (Action) DG.getNode(count).data;
//                        if(compareNodeAction.getNoun().equals(currNodeNoun) && compareNodeAction.getVerb().equals(preVerb))
//                        {
//                            DG.addEdge(count, nodeNum);
//                        }
//                    }
//                }
//            }
//        }
//            
//        System.out.println(DG);
//        System.out.println("Length: " + DG.GetLength());
//        System.out.println("Width: " + DG.GetWidth());
        
        //
        //TEST DIGRAPH FROM HYPERGRAPH
        //
        Hypergraph HG = new Hypergraph();
        
        //sample action HG
//        for(int count = 0; count < 6; count++)
//        {
//            HG.addNode(count);
//        }
//        ArrayList<Integer> fromList = new ArrayList<Integer>();
//        fromList.add(0);
//        //(0 -> 1)
//        HG.addEdge(fromList, 1);
//        
//        //(0 -> 2)
//        HG.addEdge(fromList, 2);
//        
//        ArrayList<Integer> fromList2 = new ArrayList<Integer>();
//        fromList2.add(1);
//        fromList2.add(2);
//        fromList2.add(5);
//        //(1,2,5 -> 3)
//        HG.addEdge(fromList2, 3);
//        //(1,2,5 - 4)
//        HG.addEdge(fromList2, 4);
        
          //small HG
//        for(int count = 0; count < 6; count++)
//        {
//            HG.addNode(count);
//        }
//        ArrayList<Integer> fromList = new ArrayList<Integer>();
//        fromList.add(0);
//        fromList.add(1);
//        HG.addEdge(fromList, 2);
//        HG.addEdge(fromList, 3);
//        ArrayList<Integer> fromList2 = new ArrayList<Integer>();
//        fromList2.add(2);
//        fromList2.add(3);
//        HG.addEdge(fromList2, 4);
//        HG.addEdge(fromList2, 5);
        
        //large HG (one source, one sink)
        for(int count = 0; count < 9; count++)
        {
            HG.addNode(count);
        }
        ArrayList<Integer> fromList0 = new ArrayList<Integer>();
        fromList0.add(0);
        HG.addEdge(fromList0, 1); //0-1
        HG.addEdge(fromList0, 2); //0-2
        ArrayList<Integer> fromList1 = new ArrayList<Integer>();
        fromList1.add(1);
        HG.addEdge(fromList1, 3); //1-3
        ArrayList<Integer> fromList1and2 = new ArrayList<Integer>();
        fromList1and2.add(1);
        fromList1and2.add(2);
        HG.addEdge(fromList1and2, 4); //1,2-4
        ArrayList<Integer> fromList2 = new ArrayList<Integer>();
        fromList2.add(2);
        HG.addEdge(fromList2, 5); //2-5
        ArrayList<Integer> fromList3and4 = new ArrayList<Integer>();
        fromList3and4.add(3);
        fromList3and4.add(4);
        HG.addEdge(fromList3and4, 6); //3,4-6
        ArrayList<Integer> fromList4and5 = new ArrayList<Integer>();
        fromList4and5.add(4);
        fromList4and5.add(5);
        HG.addEdge(fromList4and5, 7); //4,5-7
        ArrayList<Integer> fromList7 = new ArrayList<Integer>();
        fromList7.add(7);
        HG.addEdge(fromList7, 8); //7-8
        
        System.out.println(HG);
        
        DiGraph HGDigraph = new DiGraph(HG);
        
        System.out.println(HGDigraph);
        System.out.println("Length: " + HGDigraph.GetLength());
        System.out.println("Width: " + HGDigraph.GetWidth());
        
        //
        //TEST GEN ALL HYPERGRAPHS FROM ALL TOPOLOGICAL SORTS
        //
//        ArrayList<Linearization<Action>> allTopologicalSorts = DG.allTopologicalSort();
//        
//        int sortIndex = 1;
//        for(Linearization<Action> currSort: allTopologicalSorts)
//        {
//            System.out.println(sortIndex + ". " + currSort);
//            System.out.println();
//            sortIndex++;
//        }
//        
//        HypergraphGenerator hypergraphGen = new HypergraphGenerator();
//        ArrayList<Hypergraph> topologicalHypergraphList = hypergraphGen.genAllHypergraphs(allTopologicalSorts);
//        
//        int graphIndex = 1;
//        for(Hypergraph currGraph: topologicalHypergraphList)
//        {
//            System.out.println(graphIndex + ". " + currGraph);
//            System.out.println(new DiGraph(currGraph));
//            System.out.println(new DiGraph(currGraph).GetLength());
//            System.out.println(new DiGraph(currGraph).GetWidth());
//            System.out.println();
//            graphIndex++;
//        }
        
        //
        //TEST GEN ALL TOPOLOGICAL SORTS
        //
//        ArrayList<Linearization<Action>> allTopologicalSorts = DG.allTopologicalSort();
//        int index = 1;
//        for(Linearization<Action> currSort: allTopologicalSorts)
//        {
//            System.out.println(index + ".");
//            System.out.println(currSort);
//            HypergraphGenerator hypergraphGen = new HypergraphGenerator(currSort.getNodes());
//            Hypergraph topologicalHypergraph = hypergraphGen.HypergraphFromTopologicalList();
//            System.out.println(topologicalHypergraph);
//            System.out.println();
//            index++;
//        }
        
        //
        //TEST TOPOLOGICAL SORT TO HYPERGRAPH
        //
//        ArrayList<Action> topologicalList = new ArrayList<Action>(DG.topologicalSort());
//        System.out.println(topologicalList);
//        
//        HypergraphGenerator hypergraphGen = new HypergraphGenerator(topologicalList);
//        Hypergraph topologicalHypergraph = hypergraphGen.HypergraphFromTopologicalList();
//        System.out.println(topologicalHypergraph);
        
        //
        //TEST PEBBLER
        //
//        for(int goalNode = 0; goalNode < NUM_NODES; goalNode++)
//        {
//            for(int nodesSize = 0; nodesSize < NUM_NODES; nodesSize++)
//            {
//                PebbledHypergraph pGraph = hG.getPebbledHypergraph();
//        
//                System.out.println(pGraph);
//                
//                Pebbler pebbler = new Pebbler(hG, pGraph);
//        
//                ArrayList<Integer> nodes = new ArrayList<>(Utilities.genSubset(gen, nodesSize, 0, 9));
//
////                nodes.add(0);
////                nodes.add(2);
////                nodes.add(5);
////                nodes.add(8);
//        
//            pebbler.pebble(nodes);
//        
//            HyperedgeMultiMap map = pebbler.getForwardEdges();
//            System.out.println(map);
//        
//            //int MAX_GIVENS = 4;
//            PathGenerator pathGen = new PathGenerator(hG);
//            PathHashMap pathMap = new PathHashMap(map, hG.size());
//        
//            int GOAL_NODE = goalNode;
//            pathGen.GeneratePathBackwardToLeaves(pathMap, map, GOAL_NODE);
//        
//            // Write code to print all the paths (from the pathHashMap) with goal node 3.
//            for (Path path : pathMap.get(GOAL_NODE))
//            {
//                System.out.println(path);
//            }
//            }
//        }
        
//        ConstraintParser parser = new ConstraintParser("VerbNounList.xml");
//        parser.parse();
//        
//        ActionGenerator actionGen = new ActionGenerator(parser);
//        
//        ArrayList<Action> actionSet = actionGen.generateUniqueActionSet(10);
//        Action action = actionGen.generateAction();
//        System.out.println(action);
//        System.out.println(actionSet);
    }
}
