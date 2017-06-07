package questgeneration.verbs;

import questgeneration.nouns.Noun;
import utilities.Constants;
import java.util.ArrayList;

public abstract class Verb
{
    private final String name;
    public final ArrayList<Constants.Method> methods;
    private ArrayList<Verb> predecessor_verbs;
    
    public Verb(String theName, ArrayList<String> theMethods)
    {
        name = theName;
        
        methods = new ArrayList<Constants.Method>();
        for(String method : theMethods)
        {
            methods.add(Constants.Method.valueOf(method));
        }
        
        predecessor_verbs = new ArrayList<Verb>();
    }
    
    public Verb(String theName)
    {
        name = theName;
        
        methods = new ArrayList<Constants.Method>();
        
        predecessor_verbs = new ArrayList<Verb>();
    }
    
    public void setPredecessorVerbs(ArrayList<Verb> predecessors)
    {
        predecessor_verbs = predecessors;
    }
    
    public ArrayList<Verb> getPredecessorVerbs()
    {
        return predecessor_verbs;
    }
    
    public String getName()
    {
        return name;
    }
    
    public boolean equals(Verb thatVerb)
    {
        if(name.equals(thatVerb.getName())) return true;
        
        else return false;
    }
    
    public abstract boolean isCompatible(Noun theNoun);
    
    public String toString()
    {
        return this.name;
    }
}
