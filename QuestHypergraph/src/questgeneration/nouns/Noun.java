package questgeneration.nouns;

import java.util.ArrayList;

import questgeneration.verbs.Verb;

public abstract class Noun
{
    private String type;
    private ArrayList<Verb> negated_verb_list;
    
    public Noun(String theType, ArrayList<Verb> negatedVerbs)
    {
        type = theType;
        negated_verb_list = negatedVerbs;
    }
    
    public String getType()
    {
        return type;
    }
    
    public ArrayList<Verb> getNegatedVerbs()
    {
        return negated_verb_list;
    }
    
    public boolean equals(Noun thatNoun)
    {
        if(this.getType().equals(thatNoun.getType())) return true;
        else return false;
    }
}
