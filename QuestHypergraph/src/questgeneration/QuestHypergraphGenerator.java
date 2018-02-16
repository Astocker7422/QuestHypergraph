//Copyright (c) 2018 Alex R. Stocker
//
//Permission is hereby granted, free of charge, to any person obtaining a copy of 
//this software and associated documentation files (the "Software"), to deal in 
//the Software without restriction, including without limitation the rights to use, 
//copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the 
//Software, and to permit persons to whom the Software is furnished to do so, 
//subject to the following conditions:
//
//The above copyright notice and this permission notice shall be included in all 
//copies or substantial portions of the Software.
//
//THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR 
//IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
//FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR 
//COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER 
//IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN 
//CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

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
import java.util.Iterator;
import java.util.Random;
import questgeneration.nouns.Noun;
import questgeneration.verbs.Verb;
import utilities.Constants;

public class QuestHypergraphGenerator<T, A>
{
    private Hypergraph quest_hypergraph;
    public ArrayList<Hypernode<T, A>> SequencedElements;
    
    private ArrayList<ArrayList<Hypergraph<QuestData, Annotation>>> BRUTALITYHypergraphs = new ArrayList<ArrayList<Hypergraph<QuestData, Annotation>>>();
    private ArrayList<ArrayList<Hypergraph<QuestData, Annotation>>> STEALTHHypergraphs = new ArrayList<ArrayList<Hypergraph<QuestData, Annotation>>>();
    private ArrayList<ArrayList<Hypergraph<QuestData, Annotation>>> WEALTHHypergraphs = new ArrayList<ArrayList<Hypergraph<QuestData, Annotation>>>();
    private ArrayList<ArrayList<Hypergraph<QuestData, Annotation>>> DIPLOMACYHypergraphs = new ArrayList<ArrayList<Hypergraph<QuestData, Annotation>>>();
    private ArrayList<ArrayList<Hypergraph<QuestData, Annotation>>> SORCERYHypergraphs = new ArrayList<ArrayList<Hypergraph<QuestData, Annotation>>>();
    
    public QuestHypergraphGenerator()
    {
        quest_hypergraph = new Hypergraph();
        SequencedElements = new ArrayList<Hypernode<T, A>>();
    }
    
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
    
    //gen many quest hypergraphs from a text file with input desired number of actions
    public ArrayList<Hypergraph> genManyQuestHypergraphs(String fileName, int numActions) throws Exception
    {
        ConstraintParser parser = new ConstraintParser(fileName);
        parser.parse();
        
        ActionGenerator actionGen = new ActionGenerator(parser);
        
        ArrayList<Action> actionSet = actionGen.generateUniqueActionSet(numActions);
        
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
    
    //gen many quest hypergraphs with parallel sub-hypergraphs from text files
    public ArrayList<Hypergraph> genParallelismQuestHypergraphs(String quest, String subquests) throws Exception
    {
        //parse main quest actions
        ConstraintParser parser = new ConstraintParser(quest);
        parser.parse();
        ActionGenerator actionGen = new ActionGenerator(parser);
        //action set size can be adjusted in Constants
        ArrayList<Action> actionSet = actionGen.generateUniqueActionSet(Constants.main_quest_nodes_parallelism);
        
        System.out.println("");
        System.out.println("Main Quest Actions: " + actionSet.size());
        System.out.println(actionSet);
        System.out.println("");
        
        //parse subquest actions
        ConstraintParser sub_parser = new ConstraintParser(subquests);
        sub_parser.parse();
        ActionGenerator sub_actionGen = new ActionGenerator(sub_parser);
        ArrayList<Action> sub_actionSet = sub_actionGen.generateAllActions();
        
        System.out.println("");
        System.out.println("Sub-Quest Actions: " + sub_actionSet.size());
        System.out.println(sub_actionSet);
        System.out.println("");
        
        //determine methods to include
        ArrayList<Constants.Method> methods = new ArrayList<Constants.Method>();
        for(Verb currVerb : sub_parser.getVerbs())
        {
            for(Constants.Method currVerbMethod : currVerb.methods)
            {
                if(!methods.contains(currVerbMethod)) methods.add(currVerbMethod);
            }
        }
        
        //generate all sub quests
        ArrayList<Action> BRUTALITYactions = new ArrayList<Action>();
        ArrayList<Action> STEALTHactions = new ArrayList<Action>();
        ArrayList<Action> WEALTHactions = new ArrayList<Action>();
        ArrayList<Action> DIPLOMACYactions = new ArrayList<Action>();
        ArrayList<Action> SORCERYactions = new ArrayList<Action>();
        
        for(Action act : sub_actionSet)
        {
            Verb actVerb = act.getVerb();
            for(Constants.Method verbMethod : actVerb.methods)
            {
                if(verbMethod == Constants.Method.BRUTALITY) BRUTALITYactions.add(act);
                if(verbMethod == Constants.Method.STEALTH) STEALTHactions.add(act);
                if(verbMethod == Constants.Method.WEALTH) WEALTHactions.add(act);
                if(verbMethod == Constants.Method.DIPLOMACY) DIPLOMACYactions.add(act);
                if(verbMethod == Constants.Method.SORCERY) SORCERYactions.add(act);
            }
	}
        
        System.out.println("BRUTALITY Sub-Actions: " + BRUTALITYactions);
        System.out.println("STEALTH Sub-Actions: " + STEALTHactions);
        System.out.println("WEALTH Sub-Actions: " + WEALTHactions);
        System.out.println("DIPLOMACY Sub-Actions: " + DIPLOMACYactions);
        System.out.println("SORCERY Sub-Actions: " + SORCERYactions);
        System.out.println("");
        
        if(!BRUTALITYactions.isEmpty()) BRUTALITYHypergraphs = genAllMethodHGs(BRUTALITYactions, Constants.Method.BRUTALITY);
        System.out.println("BRUTALITY SUB QUESTS: " + BRUTALITYHypergraphs.size());
        
        if(!STEALTHactions.isEmpty()) STEALTHHypergraphs = genAllMethodHGs(STEALTHactions, Constants.Method.STEALTH);
        System.out.println("STEALTH SUB QUESTS: " + STEALTHHypergraphs.size());
        
        if(!WEALTHactions.isEmpty()) WEALTHHypergraphs = genAllMethodHGs(WEALTHactions, Constants.Method.WEALTH);
        System.out.println("WEALTH SUB QUESTS: " + WEALTHHypergraphs.size());
        
        if(!DIPLOMACYactions.isEmpty()) DIPLOMACYHypergraphs = genAllMethodHGs(DIPLOMACYactions, Constants.Method.DIPLOMACY);
        System.out.println("DIPLOMACY SUB QUESTS: " + DIPLOMACYHypergraphs.size());
        
        if(!SORCERYactions.isEmpty()) SORCERYHypergraphs = genAllMethodHGs(SORCERYactions, Constants.Method.SORCERY);
        System.out.println("SORCERY SUB QUESTS: " + SORCERYHypergraphs.size());
        
        System.out.println("");
        System.out.println("Generating all sub-quests...");
        ArrayList<ArrayList<Hypergraph<QuestData, Annotation>>> subQuests = new ArrayList<ArrayList<Hypergraph<QuestData, Annotation>>>();
        for(int instanceCount = 0; instanceCount < Constants.parallelism_instance_limit; instanceCount++)
        {
            ArrayList<Hypergraph<QuestData, Annotation>> instanceSubQuests = genAllSubQuests(methods, instanceCount);
            subQuests.add(instanceSubQuests);
            
        }
        System.out.println("DONE Generating all sub-quests");
        System.out.println("Number of sub-quests: " + subQuests.size());
        
        ArrayList<ArrayList<QuestData>> questCollection = new ArrayList<ArrayList<QuestData>>();
        
        System.out.println("");
        System.out.println("Adding 1 instance of parallelism...");
        //for each sub quest,
        //create a copy of the main quest action set and add the subquest
        for(Hypergraph<QuestData, Annotation> subQuest : subQuests.get(0))
        {
            ArrayList<QuestData> actionSetCopy = new ArrayList<QuestData>(actionSet);
            actionSetCopy.add(subQuest);
            questCollection.add(actionSetCopy);
        }
        System.out.println("DONE Adding 1 instance of parallelism");
        System.out.println("Number of combinations: " + questCollection.size());
        
        System.out.println("");
        System.out.println("trimming combinations...");
        while(questCollection.size() > Constants.parallelism_combination_limit)
        {
            Random randomGen = new Random();
            int toRemove = randomGen.nextInt(questCollection.size());
            questCollection.remove(toRemove);
        }
        System.out.println("Number of combinations: " + questCollection.size());
        
        int count = 0;
        
        //add sub quests to each collection that already has parallelism
        for(int parallelismInstanceCount = 1; parallelismInstanceCount < Constants.parallelism_instance_limit; parallelismInstanceCount++)
        {
            System.out.println("");
            System.out.println("Adding " + (parallelismInstanceCount+1) + " instances of parallelism");
            
            ArrayList<ArrayList<QuestData>> TEMPQuestCollection = new ArrayList<ArrayList<QuestData>>();
            
            for(ArrayList<QuestData> currQuest : questCollection)
            {
                for(Hypergraph<QuestData, Annotation> subQuest : subQuests.get(parallelismInstanceCount))
                {
                    if(!currQuest.contains(subQuest))
                    {
                        ArrayList<QuestData> currQuestCopy = new ArrayList<QuestData>(currQuest);
                        currQuestCopy.add(subQuest);
                        TEMPQuestCollection.add(currQuestCopy);
                    }
                }
                count += 1;
                System.out.println("Combinations added to: " + count);
            }
            
            System.out.println("DONE Adding " + (parallelismInstanceCount+1) + " instances of parallelism");
            System.out.println("Number of combinations: " + TEMPQuestCollection.size());
            
            count = 0;
            questCollection.clear();
            questCollection.addAll(TEMPQuestCollection);
            
            System.out.println("");
            System.out.println("trimming combinations...");
            while(questCollection.size() > Constants.parallelism_combination_limit)
            {
                Random randomGen = new Random();
                int toRemove = randomGen.nextInt(questCollection.size());
                questCollection.remove(toRemove);
            }
            System.out.println("Number of combinations: " + questCollection.size());
        }
        
        ArrayList<Hypergraph> hypergraphList = new ArrayList<Hypergraph>();
        
        int questCount = 0;
        int processedCount = 0;
        
        System.out.println("");
        System.out.println("trimming combinations...");
        while(questCollection.size() > Constants.parallelism_combination_limit)
        {
            Random randomGen = new Random();
            int toRemove = randomGen.nextInt(questCollection.size());
            questCollection.remove(toRemove);
        }
        System.out.println("Number of combinations: " + questCollection.size());
        
        Runtime r = Runtime.getRuntime();
        r.gc();
        
        System.out.println("");
        System.out.println("Converting to Hypergraphs...");
        //putting Action/Hypergraph lists through hypergraph generation
        for(ArrayList<QuestData> currQuest : questCollection)
        {
            DiGraph DG = new DiGraph();
        
            for(QuestData act : currQuest)
            {
                DG.addNode(act);
            }
        
            addSequencedEdges(DG);
        
            ArrayList<Linearization> orders = DG.filteredTopologicalSort();
	
            HypergraphGenerator hypergraphGen = new HypergraphGenerator();
            hypergraphList.addAll(hypergraphGen.genFilteredHypergraphs(orders));
            
            while(hypergraphList.size() > 6)
            {
                hypergraphList.remove(hypergraphList.size() - 1);
            }
            
            processedCount += 1;
            System.out.println("Processed Combinations: " + processedCount);
            questCount = hypergraphList.size();
            System.out.println("Quests Generated: " + questCount);
        }
        System.out.println("DONE Converting to Hypergraphs");
        
        return hypergraphList;
    }
    
    //generate many possible sub hypergraphs of a single method to add to a parallelism hypergraph
    //genParallelismQuestHypergraphs
    private ArrayList<ArrayList<Hypergraph<QuestData, Annotation>>> genAllMethodHGs(ArrayList<Action> actionSet, Constants.Method method) throws Exception
    {
        ArrayList<ArrayList<Hypergraph<QuestData, Annotation>>> methodHypergraphs = new ArrayList<ArrayList<Hypergraph<QuestData, Annotation>>>();
        
        ArrayList<ArrayList<Action>> instanceActionLists = new ArrayList<ArrayList<Action>>();
        for(int count = 0; count < Constants.parallelism_instance_limit; count++)
        {
            instanceActionLists.add(new ArrayList<Action>());
        }
        
        ArrayList<Integer> added = new ArrayList<Integer>();
        
        //add all actions that share nouns to same action set
        for(int index = 0; index < actionSet.size(); index++)
        {
            if(added.contains(index)) continue;
            
            Action currAct = actionSet.get(index);
            Noun currActNoun = currAct.getNoun();
            
            int listIndex = 0;
            
            for(ArrayList<Action> instanceActionList : instanceActionLists)
            {
                if(instanceActionList.isEmpty())
                {
                    listIndex = instanceActionLists.indexOf(instanceActionList);
                    break;
                }
            }
            
            for(Action act : actionSet)
            {
                if(act.equals(currAct)) continue;
                
                if(act.getNoun().equals(currActNoun))
                {
                    instanceActionLists.get(listIndex).add(act);
                    added.add(actionSet.indexOf(act));
                    if(!added.contains(index))
                    {
                        instanceActionLists.get(listIndex).add(currAct);
                        added.add(index);
                    }
                }
            }
        }
        
        //divide remaining actions among the existing action sets
        int listIndex = 0;
        for(ArrayList<Action> instanceActionList : instanceActionLists)
        {
            if(instanceActionList.isEmpty())
            {
                listIndex = instanceActionLists.indexOf(instanceActionList);
                break;
            }
        }
        for(int index = 0; index < actionSet.size(); index++)
        {
            if(added.contains(index)) continue;
            
            Action currAct = actionSet.get(index);
            
            if(listIndex >= instanceActionLists.size()) listIndex = 0;
            
            instanceActionLists.get(listIndex).add(currAct);
            added.add(actionSet.indexOf(currAct));
            
            listIndex++;
        }
        
        for(ArrayList<Hypergraph<QuestData, Annotation>> instanceSubQuestList : methodHypergraphs)
        {
            if(instanceSubQuestList.isEmpty()) throw new RuntimeException("Too few " + method + " actions for number of instances of parallelism.");
        }
        
        //generate all hypergraphs for each instance list
        for(ArrayList<Action> instanceActionList : instanceActionLists)
        {
            DiGraph DG = new DiGraph();
        
            for(Action act : instanceActionList)
            {
                DG.addNode(act);
            }
        
            addSequencedEdges(DG);
        
            ArrayList<Linearization> orders = DG.filteredTopologicalSort();
        
            HypergraphGenerator hypergraphGen = new HypergraphGenerator();
        
            //stops program if there were not enough actions in the method to fill the desired number of instances of parallelism
            ArrayList<Hypergraph<QuestData, Annotation>> instanceHypergraphs = new ArrayList<Hypergraph<QuestData, Annotation>>();
            try
            {
                instanceHypergraphs = hypergraphGen.genFilteredHypergraphs(orders);
            }
            catch(StringIndexOutOfBoundsException e)
            {
                System.err.println("Too few " + method + " actions for number of instances of parallelism.");
                System.exit(0);
            }
            
//            //TEMPORARY to limit number of hypergraphs for testing
//            while(methodHypergraphs.size() > 10)
//            {
//                methodHypergraphs.remove(0);
//            }
            
            methodHypergraphs.add(instanceHypergraphs);
        }
        
        return methodHypergraphs;
    }
    
    //generates all possible parallelism sub quests to add to main quests
    //genParallelismQuestHypergraphs
    private ArrayList<Hypergraph<QuestData, Annotation>> genAllSubQuests(ArrayList<Constants.Method> methods, int instance) throws Exception
    {
        ArrayList<Hypergraph<QuestData, Annotation>> parallelismHGs = new ArrayList<Hypergraph<QuestData, Annotation>>();
        
        Hypergraph emptyHG = new Hypergraph();
        
        Action dummyAct = new Action();
        
        emptyHG.addNode(dummyAct);
        emptyHG.addNode(dummyAct);
        
        if(methods.contains(Constants.Method.BRUTALITY)) addAllBRUTALITYHypergraphs(emptyHG, 0, 1, parallelismHGs, BRUTALITYHypergraphs.get(instance));
        if(methods.contains(Constants.Method.STEALTH)) addAllSTEALTHHypergraphs(emptyHG, 0, 1, parallelismHGs, STEALTHHypergraphs.get(instance));
        if(methods.contains(Constants.Method.WEALTH)) addAllWEALTHHypergraphs(emptyHG, 0, 1, parallelismHGs, WEALTHHypergraphs.get(instance));
        if(methods.contains(Constants.Method.DIPLOMACY)) addAllDIPLOMACYHypergraphs(emptyHG, 0, 1, parallelismHGs, DIPLOMACYHypergraphs.get(instance));
        if(methods.contains(Constants.Method.SORCERY)) addAllSORCERYHypergraphs(emptyHG, 0, 1, parallelismHGs, SORCERYHypergraphs.get(instance));
        
        return parallelismHGs;
    }
    
    //adds all BRUTALITY sub hypergraphs of each method to a single position in a hypergraph
    private void addAllBRUTALITYHypergraphs(Hypergraph<QuestData, Annotation> HG,
                                            int start, int end, 
                                            ArrayList<Hypergraph<QuestData, Annotation>> parallelismHGs, 
                                            ArrayList<Hypergraph<QuestData, Annotation>> instanceHypergraphs) throws Exception
    {
        //if there are no existing hypergraphs to add sub hypergraphs to,
        if(parallelismHGs.isEmpty())
        {
            //generate a copy of the main quest for each BRUTALITY sub hypergraph with a BRUTALITY sub hypergraph
            for(Hypergraph<QuestData, Annotation> BRUTALITYHypergraph : instanceHypergraphs)
            {
                Hypergraph<QuestData, Annotation> mainQuestCopy = new Hypergraph<QuestData, Annotation>(HG);
                addSubHG(mainQuestCopy, BRUTALITYHypergraph, start, end, mainQuestCopy.size());
                parallelismHGs.add(mainQuestCopy);
            }
            return;
        }
        
        ArrayList<Hypergraph<QuestData, Annotation>> BRUTALITYsubHGs = new ArrayList<Hypergraph<QuestData, Annotation>>();
        
        //if there are existing hypergraphs to add sub hypergraphs to
        //generate copies of each existing quest and add each BRUTALITY sub hypergraph to them
        for(Hypergraph<QuestData, Annotation> BRUTALITYHypergraph : instanceHypergraphs)
        {
            for(Hypergraph<QuestData, Annotation> existingHG : parallelismHGs)
            {
                Hypergraph<QuestData, Annotation> existingQuestCopy = new Hypergraph<QuestData, Annotation>(existingHG);
                addSubHG(existingQuestCopy, BRUTALITYHypergraph, start, end, existingQuestCopy.size());
                BRUTALITYsubHGs.add(existingQuestCopy);
            }
        }
        
        parallelismHGs.clear();
        parallelismHGs.addAll(BRUTALITYsubHGs);
    }
    
    //adds all STEALTH sub hypergraphs of each method to a single position in a hypergraph
    private void addAllSTEALTHHypergraphs(Hypergraph<QuestData, Annotation> HG,
                                            int start, int end, 
                                            ArrayList<Hypergraph<QuestData, Annotation>> parallelismHGs, 
                                            ArrayList<Hypergraph<QuestData, Annotation>> instanceHypergraphs) throws Exception
    {
        //if there are no existing hypergraphs to add sub hypergraphs to,
        if(parallelismHGs.isEmpty())
        {
            //generate a copy of the main quest for each STEALTH sub hypergraph with a BRUTALITY sub hypergraph
            for(Hypergraph<QuestData, Annotation> STEALTHHypergraph : instanceHypergraphs)
            {
                Hypergraph<QuestData, Annotation> mainQuestCopy = new Hypergraph<QuestData, Annotation>(HG);
                addSubHG(mainQuestCopy, STEALTHHypergraph, start, end, mainQuestCopy.size());
                parallelismHGs.add(mainQuestCopy);
            }
            return;
        }
        
        ArrayList<Hypergraph<QuestData, Annotation>> STEALTHsubHGs = new ArrayList<Hypergraph<QuestData, Annotation>>();
        
        //if there are existing hypergraphs to add sub hypergraphs to
        //generate copies of each existing quest and add each STEALTH sub hypergraph to them
        for(Hypergraph<QuestData, Annotation> STEALTHHypergraph : instanceHypergraphs)
        {
            for(Hypergraph<QuestData, Annotation> existingHG : parallelismHGs)
            {
                Hypergraph<QuestData, Annotation> existingQuestCopy = new Hypergraph<QuestData, Annotation>(existingHG);
                addSubHG(existingQuestCopy, STEALTHHypergraph, start, end, existingQuestCopy.size());
                STEALTHsubHGs.add(existingQuestCopy);
            }
        }
        
        parallelismHGs.clear();
        parallelismHGs.addAll(STEALTHsubHGs);
    }
    
    //adds all WEALTH sub hypergraphs of each method to a single position in a hypergraph
    private void addAllWEALTHHypergraphs(Hypergraph<QuestData, Annotation> HG,
                                            int start, int end, 
                                            ArrayList<Hypergraph<QuestData, Annotation>> parallelismHGs, 
                                            ArrayList<Hypergraph<QuestData, Annotation>> instanceHypergraphs) throws Exception
    {
        //if there are no existing hypergraphs to add sub hypergraphs to,
        if(parallelismHGs.isEmpty())
        {
            //generate a copy of the main quest for each WEALTH sub hypergraph with a BRUTALITY sub hypergraph
            for(Hypergraph<QuestData, Annotation> WEALTHHypergraph : instanceHypergraphs)
            {
                Hypergraph<QuestData, Annotation> mainQuestCopy = new Hypergraph<QuestData, Annotation>(HG);
                addSubHG(mainQuestCopy, WEALTHHypergraph, start, end, mainQuestCopy.size());
                parallelismHGs.add(mainQuestCopy);
            }
            return;
        }
        
        ArrayList<Hypergraph<QuestData, Annotation>> WEALTHsubHGs = new ArrayList<Hypergraph<QuestData, Annotation>>();
        
        //if there are existing hypergraphs to add sub hypergraphs to
        //generate copies of each existing quest and add each WEALTH sub hypergraph to them
        for(Hypergraph<QuestData, Annotation> WEALTHHypergraph : instanceHypergraphs)
        {
            for(Hypergraph<QuestData, Annotation> existingHG : parallelismHGs)
            {
                Hypergraph<QuestData, Annotation> existingQuestCopy = new Hypergraph<QuestData, Annotation>(existingHG);
                addSubHG(existingQuestCopy, WEALTHHypergraph, start, end, existingQuestCopy.size());
                WEALTHsubHGs.add(existingQuestCopy);
            }
        }
        
        parallelismHGs.clear();
        parallelismHGs.addAll(WEALTHsubHGs);
    }
    
    //adds all DIPLOMACY sub hypergraphs of each method to a single position in a hypergraph
    private void addAllDIPLOMACYHypergraphs(Hypergraph<QuestData, Annotation> HG,
                                            int start, int end, 
                                            ArrayList<Hypergraph<QuestData, Annotation>> parallelismHGs, 
                                            ArrayList<Hypergraph<QuestData, Annotation>> instanceHypergraphs) throws Exception
    {
        //if there are no existing hypergraphs to add sub hypergraphs to,
        if(parallelismHGs.isEmpty())
        {
            //generate a copy of the main quest for each DIPLOMACY sub hypergraph with a BRUTALITY sub hypergraph
            for(Hypergraph<QuestData, Annotation> DIPLOMACYHypergraph : instanceHypergraphs)
            {
                Hypergraph<QuestData, Annotation> mainQuestCopy = new Hypergraph<QuestData, Annotation>(HG);
                addSubHG(mainQuestCopy, DIPLOMACYHypergraph, start, end, mainQuestCopy.size());
                parallelismHGs.add(mainQuestCopy);
            }
            return;
        }
        
        ArrayList<Hypergraph<QuestData, Annotation>> DIPLOMACYsubHGs = new ArrayList<Hypergraph<QuestData, Annotation>>();
        
        //if there are existing hypergraphs to add sub hypergraphs to
        //generate copies of each existing quest and add each DIPLOMACY sub hypergraph to them
        for(Hypergraph<QuestData, Annotation> DIPLOMACYHypergraph : instanceHypergraphs)
        {
            for(Hypergraph<QuestData, Annotation> existingHG : parallelismHGs)
            {
                Hypergraph<QuestData, Annotation> existingQuestCopy = new Hypergraph<QuestData, Annotation>(existingHG);
                addSubHG(existingQuestCopy, DIPLOMACYHypergraph, start, end, existingQuestCopy.size());
                DIPLOMACYsubHGs.add(existingQuestCopy);
            }
        }
        
        parallelismHGs.clear();
        parallelismHGs.addAll(DIPLOMACYsubHGs);
    }
    
    //adds all SORCERY sub hypergraphs of each method to a single position in a hypergraph
    private void addAllSORCERYHypergraphs(Hypergraph<QuestData, Annotation> HG,
                                            int start, int end, 
                                            ArrayList<Hypergraph<QuestData, Annotation>> parallelismHGs, 
                                            ArrayList<Hypergraph<QuestData, Annotation>> instanceHypergraphs) throws Exception
    {
        //if there are no existing hypergraphs to add sub hypergraphs to,
        if(parallelismHGs.isEmpty())
        {
            //generate a copy of the main quest for each SORCERY sub hypergraph with a BRUTALITY sub hypergraph
            for(Hypergraph<QuestData, Annotation> SORCERYHypergraph : instanceHypergraphs)
            {
                Hypergraph<QuestData, Annotation> mainQuestCopy = new Hypergraph<QuestData, Annotation>(HG);
                addSubHG(mainQuestCopy, SORCERYHypergraph, start, end, mainQuestCopy.size());
                parallelismHGs.add(mainQuestCopy);
            }
            return;
        }
        
        ArrayList<Hypergraph<QuestData, Annotation>> SORCERYsubHGs = new ArrayList<Hypergraph<QuestData, Annotation>>();
        
        //if there are existing hypergraphs to add sub hypergraphs to
        //generate copies of each existing quest and add each SORCERY sub hypergraph to them
        for(Hypergraph<QuestData, Annotation> SORCERYHypergraph : instanceHypergraphs)
        {
            for(Hypergraph<QuestData, Annotation> existingHG : parallelismHGs)
            {
                Hypergraph<QuestData, Annotation> existingQuestCopy = new Hypergraph<QuestData, Annotation>(existingHG);
                addSubHG(existingQuestCopy, SORCERYHypergraph, start, end, existingQuestCopy.size());
                SORCERYsubHGs.add(existingQuestCopy);
            }
        }
        
        parallelismHGs.clear();
        parallelismHGs.addAll(SORCERYsubHGs);
    }
    
    //adds a hypergraph between two vertices in another hypergraph
    private void addSubHG(Hypergraph<QuestData, Annotation> mainHG, Hypergraph<QuestData, Annotation> subHG, int start, int end, int idIncrement) throws Exception
    {
        ArrayList<Hypernode<QuestData, Annotation>> subHGVertices = subHG.vertices;
        
        //add all nodes from sub hypergraph to main hypergraph
	for(Hypernode<QuestData, Annotation> currNode : subHGVertices)
	{
            mainHG.addNode(currNode.data, currNode.id + idIncrement);
        }
            
        //add all edges from sub hypergraph to main hypergraph
        for(Hypernode currNode : subHGVertices)
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
                
                mainHG.addEdge(newFromList, newTarget);
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
                
                mainHG.addEdge(newFromList, newTarget);
            }
	}
        
        //remove edge that the sub hypergraph is replacing
        for(int edgeIndex = 0; edgeIndex < mainHG.getNode(start).outEdges.size(); edgeIndex++)
        {
            if(mainHG.getNode(start).outEdges.get(edgeIndex).targetNode == end) mainHG.getNode(start).outEdges.remove(edgeIndex);
        }
        for(int edgeIndex = 0; edgeIndex < mainHG.getNode(end).inEdges.size(); edgeIndex++)
        {
            if(mainHG.getNode(end).inEdges.get(edgeIndex).sourceNodes.contains(start) && mainHG.getNode(end).inEdges.get(edgeIndex).sourceNodes.size() == 1) mainHG.getNode(end).inEdges.remove(edgeIndex);
        }
        
        //add edge from start node to all beginning nodes of the sub hypergraph
        int[] firstSources = {start};
        
        Annotation firstAnnotation = new Annotation();
        
        for(Hypernode currNode: subHGVertices)
        {
            if(currNode.inEdges.isEmpty())
            {
                Hyperedge firstEdge = new Hyperedge(firstSources, currNode.id + idIncrement, firstAnnotation);
                mainHG.addEdge(firstEdge);
            }
        }
        
        //add edge from all ending nodes of the sub hypergraph to end node
        for(Hypernode currNode: subHGVertices)
        {
            if(currNode.outEdges.isEmpty())
            {
                ArrayList<Integer> lastSources = new ArrayList<Integer>();
                lastSources.add(currNode.id + idIncrement);
                
                Annotation lastAnnotation = new Annotation();
        
                Hyperedge lastEdge = new Hyperedge(lastSources, end, lastAnnotation);
                mainHG.addEdge(lastEdge);
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
    private void addSequencedEdges(DiGraph<QuestData> DG)
    {
        for(Node<QuestData> currNode : DG.getVertices())
        {
            if(currNode.data.isComplex) continue;
            
            Action currNodeAction = (Action) currNode.data;
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
                    
                    for(Node<QuestData> compareNode : DG.getVertices())
                    {
                        if(compareNode.data.isComplex) continue;
                        Action compareNodeAction = (Action) compareNode.data;
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
