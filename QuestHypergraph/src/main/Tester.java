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
import java.text.DecimalFormat;
import utilities.*;

public class Tester 
{
    
    public static void main(String[] args) throws Exception
    {
        //
        //TEST PARALLEL METHOD PATH GENERATION (OUTDATED!)
        //
//        ConstraintParser parser = new ConstraintParser("ParallelVerbNoun[SUB].xml");
//        parser.parse();
//        
//        ActionGenerator actionGen = new ActionGenerator(parser);
//        ArrayList<Action> actionSet = actionGen.generateAllActions();
//        
//        Hypergraph HG = new Hypergraph();
//        
//        QuestHypergraphGenerator HGgen = new QuestHypergraphGenerator(HG);
//        
//        Action start = new Action(new LivingVerb("Talk To"), new Ally("Boss", new ArrayList<Verb>()));
//        Action end = new Action(new LivingVerb("Go To"), new Ally("Boss", new ArrayList<Verb>()));
//        
//        ArrayList<Constants.Method> methods = new ArrayList<Constants.Method>();
//        
//        methods.add(Constants.Method.BRUTALITY);
//        methods.add(Constants.Method.DIPLOMACY);
//        methods.add(Constants.Method.STEALTH);
//        methods.add(Constants.Method.WEALTH);
//        methods.add(Constants.Method.SORCERY);
//        
//        HGgen.genParallelismHG(start, end, actionSet, methods);
//        
//        System.out.println();
//        System.out.println(HGgen.getQuestHypergraph());
        
        //
        //TEST PARALLEL METHOD PATH GENERATION
        //
        long startTime = System.nanoTime();
        
        Hypergraph HG = new Hypergraph();
        QuestHypergraphGenerator HGgen = new QuestHypergraphGenerator(HG);
        
        ArrayList<Hypergraph> hypergraphList = HGgen.genParallelismQuestHypergraphs("ParallelVerbNoun[MAIN].xml", "ParallelVerbNoun[SUB].xml");
        
        int HGCount = 1;
        System.out.println("Quest Hypergraphs Generated: " + hypergraphList.size());
        for(Hypergraph currHG : hypergraphList)
        {
//            System.out.println(HGCount + ". " + currHG);
//            System.out.println();
            
            int concurrency = currHG.concurrencyCount();
            DecimalFormat f = new DecimalFormat("##.00");
            System.out.print("\"" + HGCount + "\",");
            int min = currHG.minDepth();
            if(min < 7) System.out.print("\"" + 7 + "\",");
            else System.out.print("\"" + min + "\",");
            System.out.print("\"" + currHG.maxDepth() + "\",");
            System.out.print("\"" + concurrency + "\",");
            System.out.print("\"" + f.format(currHG.concurrencyFactor(concurrency)) + "\",");
            System.out.println("\"" + currHG.pathComplexity() + "\"");
            HGCount++;
        }
        
        long endTime = System.nanoTime();

	long elapsedTime = endTime - startTime;

        System.out.println("Elapsed time in milliseconds: " + elapsedTime / 1000000);
        
        //
        //TEST DEPTH METHODS
        //
        
        //SIMPLE
//        Hypergraph HG = new Hypergraph();
//        
//        Hypergraph subHG = new Hypergraph();
//        
//        subHG.addNode(0);
//        subHG.addNode(1);
//        subHG.addNode(2);
//        subHG.addNode(3);
//        subHG.addNode(4);
//        subHG.addNode(5);
//        
//        ArrayList<Integer> from5 = new ArrayList<Integer>();
//        from5.add(0);
//        subHG.addEdge(from5, 1);
//        subHG.addEdge(from5, 2);
//        subHG.addEdge(from5, 4);
//        
//        ArrayList<Integer> from6 = new ArrayList<Integer>();
//        from6.add(1);
//        from6.add(2);
//        subHG.addEdge(from6, 3);
//        
//        ArrayList<Integer> from7 = new ArrayList<Integer>();
//        from7.add(3);
//        subHG.addEdge(from7, 5);
//        
//        ArrayList<Integer> from8 = new ArrayList<Integer>();
//        from8.add(4);
//        subHG.addEdge(from8, 5);
//        
//        HG.addNode(0);
//        HG.addNode(1);
//        HG.addNode(2);
//        HG.addNode(3);
//        HG.addNode(subHG);
//        HG.addNode(5);
//        
//        ArrayList<Integer> from1 = new ArrayList<Integer>();
//        from1.add(0);
//        HG.addEdge(from1, 1);
//        HG.addEdge(from1, 2);
//        HG.addEdge(from1, 4);
//        
//        ArrayList<Integer> from2 = new ArrayList<Integer>();
//        from2.add(1);
//        from2.add(2);
//        HG.addEdge(from2, 3);
//        
//        ArrayList<Integer> from3 = new ArrayList<Integer>();
//        from3.add(3);
//        HG.addEdge(from3, 5);
//        
//        ArrayList<Integer> from4 = new ArrayList<Integer>();
//        from4.add(4);
//        HG.addEdge(from4, 5);
//        
        //GENERATED
//    Hypergraph subHG1 = new Hypergraph();
//                
//    subHG1.addNode(0);
//    subHG1.addNode(1);
//    subHG1.addNode(2);
//    subHG1.addNode(3);
//    subHG1.addNode(4);
//    subHG1.addNode(5);
//    subHG1.addNode(6);
//    subHG1.addNode(7);
//    subHG1.addNode(8);
//    subHG1.addNode(9);
//    subHG1.addNode(10);
//    
//    ArrayList<Integer> from1 = new ArrayList<Integer>();
//    from1.add(0);
//    subHG1.addEdge(from1, 2);
//    subHG1.addEdge(from1, 3);
//    subHG1.addEdge(from1, 4);
//    subHG1.addEdge(from1, 6);
//    subHG1.addEdge(from1, 9);
//    
//    ArrayList<Integer> from2 = new ArrayList<Integer>();
//    from2.add(2);
//    subHG1.addEdge(from2, 1);
//    
//    ArrayList<Integer> from3 = new ArrayList<Integer>();
//    from3.add(3);
//    subHG1.addEdge(from3, 1);
//    
//    ArrayList<Integer> from4 = new ArrayList<Integer>();
//    from4.add(4);
//    subHG1.addEdge(from4, 5);
//    
//    ArrayList<Integer> from5 = new ArrayList<Integer>();
//    from5.add(5);
//    subHG1.addEdge(from5, 1);
//    
//    ArrayList<Integer> from6 = new ArrayList<Integer>();
//    from6.add(6);
//    subHG1.addEdge(from6, 7);
//    
//    ArrayList<Integer> from7 = new ArrayList<Integer>();
//    from7.add(7);
//    subHG1.addEdge(from7, 8);
//    
//    ArrayList<Integer> from8 = new ArrayList<Integer>();
//    from8.add(8);
//    subHG1.addEdge(from8, 1);
//    
//    ArrayList<Integer> from9 = new ArrayList<Integer>();
//    from9.add(9);
//    subHG1.addEdge(from9, 10);
//    
//    ArrayList<Integer> from10 = new ArrayList<Integer>();
//    from10.add(10);
//    subHG1.addEdge(from10, 1);
//        
//    Hypergraph subHG2 = new Hypergraph();
//
//    subHG2.addNode(0);
//    subHG2.addNode(1);
//    subHG2.addNode(2);
//    subHG2.addNode(3);
//    subHG2.addNode(4);
//    subHG2.addNode(5);
//    subHG2.addNode(6);
//    subHG2.addNode(7);
//    subHG2.addNode(8);
//    subHG2.addNode(9);
//    
//    subHG2.addEdge(from1, 2);
//    subHG2.addEdge(from1, 3);
//    subHG2.addEdge(from1, 4);
//    subHG2.addEdge(from1, 5);
//    subHG2.addEdge(from1, 6);
//    
//    ArrayList<Integer> from11 = new ArrayList<Integer>();
//    from11.add(2);
//    subHG2.addEdge(from11, 1);
//    
//    ArrayList<Integer> from12 = new ArrayList<Integer>();
//    from12.add(3);
//    subHG2.addEdge(from12, 1);
//    
//    ArrayList<Integer> from13 = new ArrayList<Integer>();
//    from13.add(4);
//    subHG2.addEdge(from13, 1);
//    
//    ArrayList<Integer> from14 = new ArrayList<Integer>();
//    from14.add(5);
//    subHG2.addEdge(from14, 1);
//    
//    ArrayList<Integer> from15 = new ArrayList<Integer>();
//    from15.add(6);
//    subHG2.addEdge(from15, 7);
//    
//    ArrayList<Integer> from16 = new ArrayList<Integer>();
//    from16.add(7);
//    subHG2.addEdge(from16, 8);
//    
//    ArrayList<Integer> from17 = new ArrayList<Integer>();
//    from17.add(8);
//    subHG2.addEdge(from17, 9);
//    
//    ArrayList<Integer> from18 = new ArrayList<Integer>();
//    from18.add(9);
//    subHG2.addEdge(from18, 1);
//        
//    Hypergraph HG = new Hypergraph();
//    
//    HG.addNode(0);
//    HG.addNode(1);
//    HG.addNode(2);
//    HG.addNode(3);
//    HG.addNode(4);
//    HG.addNode(subHG1);
//    HG.addNode(subHG2);
//    
//    ArrayList<Integer> fromH1 = new ArrayList<Integer>();
//    fromH1.add(0);
//    HG.addEdge(fromH1, 1);
//    
//    ArrayList<Integer> fromH2 = new ArrayList<Integer>();
//    fromH2.add(1);
//    HG.addEdge(fromH2, 2);
//    
//    ArrayList<Integer> fromH3 = new ArrayList<Integer>();
//    fromH3.add(2);
//    HG.addEdge(fromH3, 3);
//    
//    ArrayList<Integer> fromH4 = new ArrayList<Integer>();
//    fromH4.add(3);
//    HG.addEdge(fromH4, 4);
//    
//    ArrayList<Integer> fromH5 = new ArrayList<Integer>();
//    fromH5.add(4);
//    HG.addEdge(fromH5, 5);
//    
//    ArrayList<Integer> fromH6 = new ArrayList<Integer>();
//    fromH6.add(5);
//    HG.addEdge(fromH6, 6);
//        
//    System.out.println(HG);
//    System.out.println("Min: " + HG.minDepth());
//    System.out.println("Max: " + HG.maxDepth());
        
        //
        //TEST GENFILTEREDHYPERGRAPHS
        //
//        QuestHypergraphGenerator questGen = new QuestHypergraphGenerator();
//        ArrayList<Hypergraph> questList = questGen.genManyQuestHypergraphs("ParallelVerbNoun[MAIN].xml");
//        int count = 1;
//        for(Hypergraph quest : questList)
//        {
//            System.out.println(count + ". " + quest);
//            count++;
//        }
        
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
//        int n = DG.getVertices().size();
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
//        int n = 10;
//        
//        DiGraph DG = new DiGraph();
//        
//        for(int count = 0; count < n; count++)
//        {
//            DG.addNode(count);
//        }
        
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
//        DG.addEdge(2, 4);
//        DG.addEdge(3, 5);
//        DG.addEdge(4, 6);
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
//        System.out.println("Generating all linearizations...");
//        ArrayList<Linearization<Action>> allTopologicalSorts = DG.filteredTopologicalSort();
//        System.out.println("DONE generating all linearizations.");
//        
//        System.out.println();
//        System.out.println("Printing all linearizations...");
//        int sortIndex = 1;
//        for(Linearization<Action> currSort: allTopologicalSorts)
//        {
//            System.out.println(sortIndex + ". " + currSort);
//            System.out.println();
//            sortIndex++;
//        }
//        System.out.println("DONE printing all linearizations.");
        
        //
        //TEST GEN ALL HYPERGRAPHS FROM ALL LINEARIZATIONS
        //
//        System.out.println("Generating all hypergraphs...");
//        HypergraphGenerator hypergraphGen = new HypergraphGenerator();
//        ArrayList<Hypergraph> topologicalHypergraphList = hypergraphGen.genFilteredHypergraphs(allTopologicalSorts);
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
//                PebblerHypergraph pGraph = hG.getPebbledHypergraph();
//        
//                System.out.println(pGraph);
//                
//                Pebbler pebbler = new Pebbler(pGraph);
//        
//                ArrayList<Integer> nodes = new ArrayList<>(Utilities.genSubset(gen, nodesSize, 0, 9));
//
////                nodes.add(0);
////                nodes.add(2);
////                nodes.add(5);
////                nodes.add(8);
//        
//              pebbler.pebble(nodes);
//        
//              HyperedgeMultiMap map = pebbler.getForwardEdges();
//              System.out.println(map);
//        
//              //int MAX_GIVENS = 4;
//              PathGenerator pathGen = new PathGenerator(hG);
//              PathHashMap pathMap = new PathHashMap(map, hG.size());
//        
//              int GOAL_NODE = goalNode;
//              pathGen.GeneratePathBackwardToLeaves(pathMap, map, GOAL_NODE);
//        
//              // Write code to print all the paths (from the pathHashMap) with goal node 3.
//              for (Path path : pathMap.get(GOAL_NODE))
//              {
//                System.out.println(path);
//              }
//           }
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