package main;
import java.util.ArrayList;
import java.util.Random;

import digraph.*;
import frontend.ConstraintParser;
import hypergraph.Annotation;
import hypergraph.Hypergraph;
import hypergraph.utilities.HyperedgeGenerator;
import questgeneration.constraints.*;
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
        //sample action HG
        //
//        Hypergraph HG = new Hypergraph();
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

        //
        //Hypergraph to DiGraph
        //
//        DiGraph DG = new DiGraph(HG);
//        System.out.println("Digraph from Hypergraph: " + DG);
//        System.out.println("Length: " + DG.GetLength());
//        System.out.println("Width: " + DG.GetWidth());
        
        //
        //TEST CREATE SKYRIM QUEST
        //
//        ConstraintParser parser = new ConstraintParser("ForbiddenLegend.xml");
//        parser.parse();
//        
//        DiGraph DG = new DiGraph();
//        
//        ActionGenerator actionGen = new ActionGenerator(parser);
//        actionGen.generateAllActions();
//        
//        DG.addNode(actionGen.getAction("go to", "Fulgunthur"));
//        DG.addNode(actionGen.getAction("go to", "Daynas Valen's Journal"));
//        DG.addNode(actionGen.getAction("read", "Daynas Valen's Journal"));
//        DG.addNode(actionGen.getAction("go to", "Daynas Valen's Notes"));
//        DG.addNode(actionGen.getAction("read", "Daynas Valen's Notes"));
//        DG.addNode(actionGen.getAction("kill", "Mikrul"));
//        DG.addNode(actionGen.getAction("collect", "Mikrul's Fragment"));
//        DG.addNode(actionGen.getAction("go to", "Saarthal"));
//        DG.addNode(actionGen.getAction("kill", "Jyric"));
//        DG.addNode(actionGen.getAction("collect", "Jyric's Fragment"));
//        DG.addNode(actionGen.getAction("go to", "Geirmund's Hall"));
//        DG.addNode(actionGen.getAction("kill", "Sigdis"));
//        DG.addNode(actionGen.getAction("collect", "Sigdis's Fragment"));
//        DG.addNode(actionGen.getAction("go to", "Reachwater Rock"));
//        DG.addNode(actionGen.getAction("place", "Mikrul's Fragment"));
//        DG.addNode(actionGen.getAction("place", "Jyric's Fragment"));
//        DG.addNode(actionGen.getAction("place", "Sigdis's Fragment"));
//        DG.addNode(actionGen.getAction("kill", "Ghost Mikrul"));
//        DG.addNode(actionGen.getAction("kill", "Ghost Sigdis"));
//        DG.addNode(actionGen.getAction("kill", "Ghost Jyric"));
//        DG.addNode(actionGen.getAction("collect", "Gauldur Amulet"));
//        
//        DG.addEdge(0, 1);
//        DG.addEdge(1, 2);
//        DG.addEdge(2, 3);
//        DG.addEdge(3, 4);
//        DG.addEdge(4, 5);
//        DG.addEdge(4, 7);
//        DG.addEdge(4, 10);
//        DG.addEdge(5, 6);
//        DG.addEdge(7, 8);
//        DG.addEdge(8, 9);
//        DG.addEdge(10, 11);
//        DG.addEdge(11, 12);
//        DG.addEdge(6, 13);
//        DG.addEdge(9, 13);
//        DG.addEdge(12, 13);
//        DG.addEdge(13, 14);
//        DG.addEdge(13, 15);
//        DG.addEdge(13, 16);
//        DG.addEdge(14, 17);
//        DG.addEdge(15, 17);
//        DG.addEdge(16, 17);
//        DG.addEdge(17, 18);
//        DG.addEdge(18, 19);
//        DG.addEdge(19, 20);
//        
//        System.out.println("Original DiGraph: " + DG);
//        System.out.println("Length: " + DG.GetLength());
//        System.out.println("Width: " + DG.GetWidth());
            
        //
        // TEST LINEARIZATIONS AND HYPERGRAPHS 1 through n nodes, NO EDGES
        //
        int n = 10;
        
        DiGraph DG = new DiGraph();
        
        for(int count = 0; count < n; count++)
        {
            DG.addNode(count);
        }
        
        //
        // TEST LINEARIZATIONS AND HYPERGRAPHS RANDOM EDGES
        //
        
        // 2 nodes
//        int n = 2;
//        
//        DiGraph DG = new DiGraph();
//        
//        for(int count = 0; count < n; count++)
//        {
//            DG.addNode(count);
//        }
//
//        DG.addEdge(0, 1);
        
        // 3 nodes
//        int n = 3;
//        
//        DiGraph DG = new DiGraph();
//        
//        for(int count = 0; count < n; count++)
//        {
//            DG.addNode(count);
//        }
//
//        DG.addEdge(0, 1);
//        DG.addEdge(1, 2);
        
        //4 nodes
//        int n = 4;
//        
//        DiGraph DG = new DiGraph();
//        
//        for(int count = 0; count < n; count++)
//        {
//            DG.addNode(count);
//        }
//
//        DG.addEdge(0, 1);
//        DG.addEdge(0, 2);
//        DG.addEdge(1, 3);
//        DG.addEdge(2, 3);
        
        //5 nodes
//        int n = 5;
//        
//        DiGraph DG = new DiGraph();
//        
//        for(int count = 0; count < n; count++)
//        {
//            DG.addNode(count);
//        }
//        
//        DG.addEdge(0, 1);
//        DG.addEdge(0, 2);
//        DG.addEdge(1, 3);
//        DG.addEdge(2, 3);
//        DG.addEdge(3, 4);
        
        //6 nodes
//        int n = 6;
//        
//        DiGraph DG = new DiGraph();
//        
//        for(int count = 0; count < n; count++)
//        {
//            DG.addNode(count);
//        }
//
//        DG.addEdge(0, 1);
//        DG.addEdge(0, 2);
//        DG.addEdge(1, 3);
//        DG.addEdge(1, 4);
//        DG.addEdge(2, 3);
//        DG.addEdge(2, 4);
//        DG.addEdge(3, 5);
//        DG.addEdge(4, 5);
        
        //7 nodes
//        int n = 7;
//        
//        DiGraph DG = new DiGraph();
//        
//        for(int count = 0; count < n; count++)
//        {
//            DG.addNode(count);
//        }
//        
//        DG.addEdge(0, 1);
//        DG.addEdge(0, 2);
//        DG.addEdge(0, 3);
//        DG.addEdge(1, 4);
//        DG.addEdge(2, 4);
//        DG.addEdge(3, 5);
//        DG.addEdge(4, 6);
//        DG.addEdge(5, 6);
        
        //8 nodes
//        int n = 8;
//        
//        DiGraph DG = new DiGraph();
//        
//        for(int count = 0; count < n; count++)
//        {
//            DG.addNode(count);
//        }
//        
//        DG.addEdge(0, 1);
//        DG.addEdge(0, 2);
//        DG.addEdge(1, 3);
//        DG.addEdge(1, 4);
//        DG.addEdge(2, 5);
//        DG.addEdge(3, 6);
//        DG.addEdge(4, 6);
//        DG.addEdge(5, 6);
//        DG.addEdge(6, 7);
        
        //9 nodes
//        int n = 9;
//        
//        DiGraph DG = new DiGraph();
//        
//        for(int count = 0; count < n; count++)
//        {
//            DG.addNode(count);
//        }
//        
//        DG.addEdge(0, 1);
//        DG.addEdge(0, 2);
//        DG.addEdge(1, 3);
//        DG.addEdge(2, 4);
//        DG.addEdge(2, 5);
//        DG.addEdge(3, 6);
//        DG.addEdge(4, 7);
//        DG.addEdge(5, 7);
//        DG.addEdge(6, 8);
//        DG.addEdge(7, 8);
        
        //10 nodes
//        int n = 10;
//        
//        DiGraph DG = new DiGraph();
//        
//        for(int count = 0; count < n; count++)
//        {
//            DG.addNode(count);
//        }
//        
//        DG.addEdge(0, 1);
//        DG.addEdge(0, 2);
//        DG.addEdge(0, 3);
//        DG.addEdge(1, 4);
//        DG.addEdge(2, 4);
//        DG.addEdge(3, 5);
//        DG.addEdge(4, 6);
//        DG.addEdge(5, 7);
//        DG.addEdge(6, 8);
//        DG.addEdge(7, 8);
//        DG.addEdge(8, 9);
        
        //11 nodes
//        int n = 11;
//        
//        DiGraph DG = new DiGraph();
//        
//        for(int count = 0; count < n; count++)
//        {
//            DG.addNode(count);
//        }
//        
//        DG.addEdge(0, 1);
//        DG.addEdge(0, 2);
//        DG.addEdge(0, 3);
//        DG.addEdge(1, 4);
//        DG.addEdge(1, 5);
//        DG.addEdge(2, 6);
//        DG.addEdge(3, 6);
//        DG.addEdge(4, 7);
//        DG.addEdge(5, 7);
//        DG.addEdge(6, 9);
//        DG.addEdge(7, 8);
//        DG.addEdge(8, 10);
//        DG.addEdge(9, 10);
        
        //
        //TEST GEN ALL LINEARIZATIONS FROM ALL TOPOLOGICAL SORTS
        //
        System.out.println("Generating all linearizations...");
        ArrayList<Linearization<Action>> allTopologicalSorts = DG.allTopologicalSort();
        System.out.println("DONE generating all linearizations.");
        
        System.out.println();
        System.out.println("Filtering linearizations...");
        LinearizationFilter<Action> LFilter = new LinearizationFilter<Action>(allTopologicalSorts, n);
        LFilter.filter();
        System.out.println("DONE filtering linearizations.");
        allTopologicalSorts = LFilter._linearizations;
        
        System.out.println();
        System.out.println("Printing all linearizations...");
        int sortIndex = 1;
        for(Linearization<Action> currSort: allTopologicalSorts)
        {
            System.out.println(sortIndex + ". " + currSort);
            System.out.println();
            sortIndex++;
        }
        System.out.println("DONE printing all linearizations.");
        
        //
        //TEST GEN ALL HYPERGRAPHS FROM ALL LINEARIZATIONS
        //
//        System.out.println("Generating all hypergraphs...");
//        HypergraphGenerator hypergraphGen = new HypergraphGenerator();
//        ArrayList<Hypergraph> topologicalHypergraphList = hypergraphGen.genAllHypergraphs(allTopologicalSorts);
//        System.out.println("DONE generating all hypergraphs.");
//        
//        System.out.println();
//        System.out.println("Printing all hypergraphs...");
//        int graphIndex = 1;
//        for(Hypergraph currGraph: topologicalHypergraphList)
//        {
//            System.out.println(graphIndex + ". Hypergraph: " + currGraph);
//            DiGraph HGDG = new DiGraph(currGraph);
//            System.out.println("DiGraph: " + HGDG);
//            System.out.println("Length: " + HGDG.GetLength());
//            System.out.println("Width: " + HGDG.GetWidth());
//            System.out.println();
//            graphIndex++;
//        }
//        System.out.println("DONE printing all hypergraphs.");
//        
//        System.out.println();
//        System.out.println("Linearizations: " + allTopologicalSorts.size());
//        System.out.println("Hypergraphs: " + topologicalHypergraphList.size());
        
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
