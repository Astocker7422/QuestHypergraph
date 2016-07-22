package questgeneration.nouns;

import java.util.ArrayList;

import questgeneration.verbs.Verb;

public abstract class LivingNoun extends Noun
{
    public LivingNoun(String theType, ArrayList<Verb> negatedVerbs)
    {
        super(theType, negatedVerbs);
    }
}
