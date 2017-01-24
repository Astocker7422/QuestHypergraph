package questgeneration;

import java.util.ArrayList;
import java.util.Random;

import frontend.ConstraintParser;
import questgeneration.nouns.Noun;
import questgeneration.verbs.Verb;

public class ActionGenerator
{
    private ArrayList<Verb> Verb_List;
    private ArrayList<Noun> Noun_List;
    private ArrayList<Action> Action_List;
    Random gen;

    public ActionGenerator(ConstraintParser parsed)
    {
        Verb_List = parsed.getVerbs();
        Noun_List = parsed.getNouns();
        Action_List = new ArrayList<Action>();
        gen = new Random();
    }

    public ArrayList<Action> getGeneratedActions()
    {
        return Action_List;
    }

    public Action generateAction()
    {
        Action newAction;
        Verb actionVerb = Verb_List.get(gen.nextInt(Verb_List.size()));
        Noun actionNoun = Noun_List.get(gen.nextInt(Noun_List.size()));

        //recursive call to ensure valid action created
        if (!actionVerb.isCompatible(actionNoun))
        {
            newAction = generateAction();
        }
        else
        {
            newAction = new Action(actionVerb, actionNoun);
        }

        return newAction;
    }

    public boolean isUnique(Action newAction)
    {
        for (int counter = 0; counter < Action_List.size(); counter++)
        {
            if (newAction.equals(Action_List.get(counter)))
            {
                return false;
            }
        }

        return true;
    }

    public Action generateUniqueAction() 
    {
        Action newAction = generateAction();

        while (!isUnique(newAction)) 
        {
            newAction = generateAction();
        }

        Action_List.add(newAction);

        return newAction;
    }

    public ArrayList<Action> generateActionSet(int actionSetSize)
    {
        ArrayList<Action> newActionSet = new ArrayList<Action>(actionSetSize);
        for (int counter = 0; counter < actionSetSize; counter++)
        {
            newActionSet.add(generateAction());
        }
        return newActionSet;
    }

    public ArrayList<Action> generateUniqueActionSet(int actionSetSize)
    {
        ArrayList<Action> newActionSet = new ArrayList<Action>(actionSetSize);
        for (int counter = 0; counter < actionSetSize; counter++)
        {
            newActionSet.add(generateUniqueAction());
        }
        return newActionSet;
    }
    
    public ArrayList<Action> generateAllActions()
    {
        ArrayList<Action> newActionSet = new ArrayList<Action>();
        for(Noun currNoun: Noun_List)
        {
            for(Verb currVerb: Verb_List)
            {
                if (currVerb.isCompatible(currNoun))
                {
                    Action newAction = new Action(currVerb, currNoun);
                    newActionSet.add(newAction);
                    Action_List.add(newAction);
                }
            }
        }
        return newActionSet;
    }
    
    public Action getAction(String verb, String noun)
    {
        for(Action act: Action_List)
        {
            if(act.getVerb().getName().equalsIgnoreCase(verb) && act.getNoun().getType().equalsIgnoreCase(noun)) return act;
        }
        System.out.println("Action not generated.");
        return null;
    }
}
