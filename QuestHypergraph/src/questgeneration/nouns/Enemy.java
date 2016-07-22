package questgeneration.nouns;

import java.util.ArrayList;

import questgeneration.verbs.Verb;

public class Enemy extends LivingNoun
{
    public Enemy(String theType, ArrayList<Verb> negatedVerbs)
    {
        super(theType, negatedVerbs);
    }   
}
