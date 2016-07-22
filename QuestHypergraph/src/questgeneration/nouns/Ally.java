package questgeneration.nouns;

import java.util.ArrayList;

import questgeneration.verbs.Verb;

public class Ally extends LivingNoun
{
    public Ally(String theType, ArrayList<Verb> negatedVerbs)
    {
        super(theType, negatedVerbs);
    }   
}
