package main;
import java.util.ArrayList;
import java.util.Random;

import frontend.ConstraintParser;
import hypergraph.Annotation;
import hypergraph.Hypergraph;
import hypergraph.utilities.Generator;
import questgeneration.Action;
import questgeneration.ActionGenerator;
import questgeneration.QuestHypergraphGenerator;

public class Tester 
{
    
    public static void main(String[] args) throws Exception
    {
        Hypergraph<Action, Annotation> hG = new Hypergraph<Action, Annotation>();
        final int NUM_NODES = 10;
        final int NUM_EDGES = 10;
        
        QuestHypergraphGenerator questGen = new QuestHypergraphGenerator(hG);
        
        ConstraintParser parser = new ConstraintParser("VerbNounList.xml");
        parser.parse();
        
        ActionGenerator actionGen = new ActionGenerator(parser);
        
        ArrayList<Action> actionSet = actionGen.generateUniqueActionSet(NUM_NODES);
        
        questGen.addActions(actionSet);
        
        Random gen = new Random();
        gen.setSeed(0); //if seed set, all targets are the same

        Generator<Annotation> edgeGen = new Generator<Annotation>();
        for(int count = 0; count < NUM_EDGES; count++)
        {
            questGen.addEdge(edgeGen.createRandomEdge(gen, NUM_NODES, new Annotation()));
        }
        
        System.out.println(questGen.getQuestHypergraph());
                
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