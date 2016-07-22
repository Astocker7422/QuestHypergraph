package questgeneration.nouns;

import java.util.ArrayList;

import questgeneration.verbs.Verb;

public abstract class StaticNoun extends Noun
{
    public StaticNoun(String theType, ArrayList<Verb> negatedVerbs)
    {
        super(theType, negatedVerbs);
    }   
}
