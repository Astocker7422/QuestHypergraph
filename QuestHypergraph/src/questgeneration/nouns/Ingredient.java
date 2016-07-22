package questgeneration.nouns;

import java.util.ArrayList;

import questgeneration.verbs.Verb;

public class Ingredient extends Object
{
    public Ingredient(String theType, ArrayList<Verb> negatedVerbs)
    {
        super(theType, negatedVerbs);
    }   
}
